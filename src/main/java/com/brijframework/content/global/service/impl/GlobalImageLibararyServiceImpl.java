package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalImageLibararyMapper;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.service.GlobalImageLibararyService;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;
import com.brijframework.content.util.ResourceUtil;

@Service
public class GlobalImageLibararyServiceImpl extends CrudServiceImpl<UIGlobalImageLibarary, EOGlobalImageLibarary, Long> implements GlobalImageLibararyService {
	
	/**
	 * 
	 */
	private static final String TAGS_WITH_IMAGES = "tags_with_images";

	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private GlobalSubCategoryRepository globalCategoryItemRepository;

	@Autowired
	private GlobalImageLibararyMapper globalImageLibararyMapper;

	@Autowired
	private ResourceUtil resourceUtil;
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIGlobalImageLibarary> getMapper() {
		return globalImageLibararyMapper;
	}

	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalImageLibarary> findById = globalImageLibararyRepository.findById(id);
		if(findById.isPresent()) {
			EOGlobalImageLibarary eoGlobalImageLibarary = findById.get();
			eoGlobalImageLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			globalImageLibararyRepository.save(eoGlobalImageLibarary);
			return true;
		}
		return false;
	}
	
	@Override
	public void init () throws IOException {
		Resource resource = resourceUtil.getResource(TAGS_WITH_IMAGES);
		File resourceFile = resource.getFile();
		if(!resourceFile.exists()) {
			return;
		}
		List<EOGlobalSubCategory> globalCategoryItems = globalCategoryItemRepository.findAll();
		Map<String, EOGlobalSubCategory> globalCategoryNameMap = globalCategoryItems.stream().collect(Collectors.toMap(EOGlobalSubCategory::getName, Function.identity()));
		List<EOGlobalImageLibarary> globalImageLibararys = globalImageLibararyRepository.findAll();
		Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap = globalImageLibararys.stream().collect(Collectors.groupingBy(ImageLibarary->ImageLibarary.getSubCategory().getId()));
		loadResourceTags(resourceFile, globalCategoryNameMap, globalCategoryImgMap);
	}
	
	private void loadResourceTags(File resourceFile, Map<String, EOGlobalSubCategory> globalCategoryNameMap, Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap) {
		File[] listFiles = resourceFile.listFiles();
		for (File file :  listFiles) {
			EOGlobalSubCategory globalCategoryItem = globalCategoryNameMap.get(file.getName());
			if(globalCategoryItem==null) {
				continue;
			}
			Map<String, EOGlobalImageLibarary> categoryImgUrlMap = globalCategoryImgMap.getOrDefault(globalCategoryItem.getId(), new ArrayList<EOGlobalImageLibarary>()).stream().collect(Collectors.toMap(EOGlobalImageLibarary::getUrl, Function.identity()));
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, categoryImgUrlMap);
			} else {
				loadResourceFile(file, globalCategoryItem, categoryImgUrlMap);
			}
		}
	}

	private void loadResourceDir(File resourceFile, EOGlobalSubCategory globalCategoryItem, Map<String, EOGlobalImageLibarary> categoryImgUrlMap) {
		File[] listFiles = resourceFile.listFiles();
		for (File file :  listFiles) {
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, categoryImgUrlMap);
			} else {
				loadResourceFile(file, globalCategoryItem, categoryImgUrlMap);
			}
		}
	}

	/**
	 * @param file
	 * @param globalCategoryItem 
	 * @param categoryImgUrlMap 
	 */
	private void loadResourceFile(File file, EOGlobalSubCategory subCategory, Map<String, EOGlobalImageLibarary> categoryImgUrlMap) {
		String url = "resource/"+TAGS_WITH_IMAGES+file.getAbsolutePath().split(TAGS_WITH_IMAGES)[1].replace("\\", "/");
		EOGlobalImageLibarary globalImageLibarary = categoryImgUrlMap.getOrDefault(url, new EOGlobalImageLibarary());
		globalImageLibarary.setIdenNo(url.replace("/", "_").replace(",", "_").toUpperCase().split("\\.")[0].replaceFirst("_", ""));
		globalImageLibarary.setSubCategory(subCategory);
		globalImageLibarary.setName(file.getName());
		int dotIndex = file.getName().lastIndexOf('.'); 
		String extension = (dotIndex > 0) ? file.getName().substring(dotIndex + 1) : ""; 
		globalImageLibarary.setType(extension);
		globalImageLibarary.setUrl(url);
		globalImageLibarary=globalImageLibararyRepository.save(globalImageLibarary);
		categoryImgUrlMap.put(url, globalImageLibarary);
		
	}
	
	@Override
	protected void preAdd(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			saveResource(data, entity);
		}
	}

	private void saveResource(UIGlobalImageLibarary data, EOGlobalImageLibarary entity) {
		StringBuilder dir=new StringBuilder(TAGS_WITH_IMAGES);
		globalCategoryItemRepository.findById(data.getGroupId()).ifPresent(globalCategoryItem->{
			dir.append("/"+globalCategoryItem.getName());
		});;
		UIResource uiResource=new UIResource();
		uiResource.setFileContent(data.getContent());
		uiResource.setFileName(data.getName());
		uiResource.setFolderName(dir.toString());
		resourceService.add(uiResource, new HashMap<String, List<String>>());
		entity.setUrl(uiResource.getFileUrl());
	}
	
	@Override
	protected void preUpdate(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			saveResource(data, entity);
		}
	}

}
