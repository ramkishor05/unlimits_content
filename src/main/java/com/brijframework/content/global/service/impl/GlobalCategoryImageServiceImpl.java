package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.brijframework.content.global.entities.EOGlobalCategoryImage;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.mapper.GlobalCategoryImageMapper;
import com.brijframework.content.global.model.UIGlobalCategoryImage;
import com.brijframework.content.global.repository.GlobalCategoryImageRepository;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.service.GlobalCategoryImageService;
import com.brijframework.content.util.ResourceUtil;

@Service
public class GlobalCategoryImageServiceImpl extends CrudServiceImpl<UIGlobalCategoryImage, EOGlobalCategoryImage, Long> implements GlobalCategoryImageService {
	
	/**
	 * 
	 */
	private static final String TAGS_WITH_IMAGES = "tags_with_images";

	@Autowired
	private GlobalCategoryImageRepository globalCategoryImageRepository;
	
	@Autowired
	private GlobalCategoryItemRepository globalCategoryItemRepository;

	@Autowired
	private GlobalCategoryImageMapper globalCategoryImageMapper;

	@Autowired
	private ResourceUtil resourceUtil;

	@Override
	public JpaRepository<EOGlobalCategoryImage, Long> getRepository() {
		return globalCategoryImageRepository;
	}

	@Override
	public GenericMapper<EOGlobalCategoryImage, UIGlobalCategoryImage> getMapper() {
		return globalCategoryImageMapper;
	}

	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalCategoryImage> findById = globalCategoryImageRepository.findById(id);
		if(findById.isPresent()) {
			EOGlobalCategoryImage eoGlobalCategoryImage = findById.get();
			eoGlobalCategoryImage.setRecordState(DataStatus.DACTIVETED.getStatus());
			globalCategoryImageRepository.save(eoGlobalCategoryImage);
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
		List<EOGlobalCategoryItem> globalCategoryItems = globalCategoryItemRepository.findAll();
		Map<String, EOGlobalCategoryItem> globalCategoryNameMap = globalCategoryItems.stream().collect(Collectors.toMap(EOGlobalCategoryItem::getName, Function.identity()));
		List<EOGlobalCategoryImage> globalCategoryImages = globalCategoryImageRepository.findAll();
		Map<Long, List<EOGlobalCategoryImage>> globalCategoryImgMap = globalCategoryImages.stream().collect(Collectors.groupingBy(categoryImage->categoryImage.getGlobalCategoryItem().getId()));
		loadResourceTags(resourceFile, globalCategoryNameMap, globalCategoryImgMap);
	}
	
	private void loadResourceTags(File resourceFile, Map<String, EOGlobalCategoryItem> globalCategoryNameMap, Map<Long, List<EOGlobalCategoryImage>> globalCategoryImgMap) {
		File[] listFiles = resourceFile.listFiles();
		for (File file :  listFiles) {
			EOGlobalCategoryItem globalCategoryItem = globalCategoryNameMap.get(file.getName());
			if(globalCategoryItem==null) {
				continue;
			}
			Map<String, EOGlobalCategoryImage> categoryImgUrlMap = globalCategoryImgMap.getOrDefault(globalCategoryItem.getId(), new ArrayList<EOGlobalCategoryImage>()).stream().collect(Collectors.toMap(EOGlobalCategoryImage::getUrl, Function.identity()));
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, categoryImgUrlMap);
			} else {
				loadResourceFile(file, globalCategoryItem, categoryImgUrlMap);
			}
		}
	}

	private void loadResourceDir(File resourceFile, EOGlobalCategoryItem globalCategoryItem, Map<String, EOGlobalCategoryImage> categoryImgUrlMap) {
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
	private void loadResourceFile(File file, EOGlobalCategoryItem globalCategoryItem, Map<String, EOGlobalCategoryImage> categoryImgUrlMap) {
		String url = file.getAbsolutePath().split(TAGS_WITH_IMAGES)[1].replace("\\", "/");
		EOGlobalCategoryImage globalCategoryImage = categoryImgUrlMap.getOrDefault(url, new EOGlobalCategoryImage());
		globalCategoryImage.setIdenNo(url.replace("/", "_").replace(",", "_").toUpperCase().split("\\.")[0].replaceFirst("_", ""));
		globalCategoryImage.setGlobalCategoryItem(globalCategoryItem);
		globalCategoryImage.setName(file.getName());
		int dotIndex = file.getName().lastIndexOf('.'); 
		String extension = (dotIndex > 0) ? file.getName().substring(dotIndex + 1) : ""; 
		globalCategoryImage.setType(extension);
		globalCategoryImage.setUrl(url);
		globalCategoryImage=globalCategoryImageRepository.save(globalCategoryImage);
		categoryImgUrlMap.put(url, globalCategoryImage);
		
	}

}
