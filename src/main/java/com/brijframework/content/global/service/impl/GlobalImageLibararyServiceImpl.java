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
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.VisualiseType;
import com.brijframework.content.forgin.model.ResourceFile;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalImageLibararyMapper;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalImageLibararyService;
import com.brijframework.content.util.ResourceUtil;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

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
	private GlobalTagLibararyRepository globalTagLibararyRepository;

	@Autowired
	private GlobalImageLibararyMapper globalImageLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;

	@Autowired
	private ResourceUtil resourceUtil;

	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIGlobalImageLibarary> getMapper() {
		return globalImageLibararyMapper;
	}
	
	{
		CustomPredicate<EOGlobalImageLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("subCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalImageLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name"), "%"+filter.getColumnValue()+"%"));
			Path<Object> subCategoryIdPath = root.get("subCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalImageLibarary> tagLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("tagLibarary");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalImageLibarary> tagLibararyName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalTagLibarary> subquery = criteriaQuery.subquery(EOGlobalTagLibarary.class);
			Root<EOGlobalTagLibarary> fromProject = subquery.from(EOGlobalTagLibarary.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name"), "%"+filter.getColumnValue()+"%"));
			Path<Object> subCategoryIdPath = root.get("tagLibarary");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
 
		addCustomPredicate("subCategoryId", subCategoryId);
		addCustomPredicate("subCategory.id", subCategoryId);
		addCustomPredicate("subCategoryName", subCategoryName);
		addCustomPredicate("subCategory.name", subCategoryName);
		
		addCustomPredicate("tagLibararyId", tagLibararyId);
		addCustomPredicate("tagLibarary.id", tagLibararyId);
		addCustomPredicate("tagLibararyName", tagLibararyName);
		addCustomPredicate("tagLibarary.name", tagLibararyName);
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
	public void preAdd(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers) {
		if(data.getType()==null) {
			data.setType(VisualiseType.VISUALISE_WITH_WORDS.getType());
		}
		if(data.getContent()!=null) {
			saveResource(data, entity);
		}
	}

	@Override
	public void preUpdate(UIGlobalImageLibarary data, EOGlobalImageLibarary entity, Map<String, List<String>> headers) {
		if(data.getType()==null) {
			data.setType(VisualiseType.VISUALISE_WITH_WORDS.getType());
		}
		if(data.getContent()!=null) {
			saveResource(data, entity);
		}
	}

	private void saveResource(UIGlobalImageLibarary data, EOGlobalImageLibarary entity) {
		StringBuilder dir=new StringBuilder(TAGS_WITH_IMAGES);
		globalCategoryItemRepository.findById(data.getSubCategoryId()).ifPresent(globalCategoryItem->{
			dir.append("/"+globalCategoryItem.getName());
		});
		globalTagLibararyRepository.findById(data.getTagLibararyId()).ifPresent(globalTagLibarary->{
			dir.append("/"+globalTagLibarary.getName());
		});
		
		ResourceFile uiResource = resourceClient.add(TAGS_WITH_IMAGES, dir.toString(), data.getName());
		
		entity.setUrl(uiResource.getFileUrl());
	}
	
	@Override
	public void init () throws IOException {
		Resource resource = resourceUtil.getResource(TAGS_WITH_IMAGES);
		File resourceFile = resource.getFile();
		if(!resourceFile.exists()) {
			return;
		}
		List<EOGlobalSubCategory> globalCategoryItems = globalCategoryItemRepository.findAll();
		Map<String, EOGlobalSubCategory> globalCategoryNameMap = globalCategoryItems.stream().collect(Collectors.toMap(globalSubCategory->globalSubCategory.getName().toUpperCase(), Function.identity()));
		List<EOGlobalImageLibarary> globalImageLibararys = globalImageLibararyRepository.findAll();
		Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap = globalImageLibararys.stream().collect(Collectors.groupingBy(ImageLibarary->ImageLibarary.getSubCategory().getId()));
		Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap = globalTagLibararyRepository.findAll().stream().collect(Collectors.toMap(globalTagLibarary->buildTagId(globalTagLibarary), Function.identity()));
		loadResourceSubCategory(resourceFile, globalCategoryNameMap, globalCategoryImgMap, globalTagLibararyNameMap);
	}

	private String buildTagId(EOGlobalTagLibarary globalTagLibarary) {
		return buildTagId(globalTagLibarary.getSubCategory(), globalTagLibarary.getName());
	}
	
	private String buildTagId(EOGlobalSubCategory globalSubCategory, String name) {
		return globalSubCategory.getId()+"_"+name;
	}
	
	private void loadResourceSubCategory(File resourceFile, Map<String, EOGlobalSubCategory> globalCategoryNameMap, Map<Long, List<EOGlobalImageLibarary>> globalCategoryImgMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		File[] categoryFolders = resourceFile.listFiles();
		for (File categoryFolder :  categoryFolders) {
			EOGlobalSubCategory globalCategoryItem = globalCategoryNameMap.get(categoryFolder.getName().trim().toUpperCase());
			if(globalCategoryItem==null) {
				continue;
			}
			Map<String, EOGlobalImageLibarary> categoryImgUrlMap = globalCategoryImgMap.getOrDefault(globalCategoryItem.getId(), new ArrayList<EOGlobalImageLibarary>()).stream().collect(Collectors.toMap(EOGlobalImageLibarary::getUrl, Function.identity()));
			File[] tagFiles = categoryFolder.listFiles();
			for(File tagFile:  tagFiles) {
				if(tagFile.isDirectory()) {
					loadResourceTag(tagFile, globalCategoryItem, categoryImgUrlMap, globalTagLibararyNameMap);
				} else {
					loadResourceFile(tagFile, globalCategoryItem, null, categoryImgUrlMap);
				}
			}
		}
	}
	
	private void loadResourceTag(File tageFile, EOGlobalSubCategory globalCategoryItem, Map<String, EOGlobalImageLibarary> categoryImgUrlMap, Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		EOGlobalTagLibarary eoGlobalTagLibarary = findOrCreateTagLibarary(globalCategoryItem, tageFile.getName(), globalTagLibararyNameMap);
		File[] tageFiles = tageFile.listFiles();
		for (File file :  tageFiles) {
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, eoGlobalTagLibarary, categoryImgUrlMap);
			} else {
				loadResourceFile(file, globalCategoryItem, eoGlobalTagLibarary, categoryImgUrlMap);
			}
		}
	}

	private EOGlobalTagLibarary findOrCreateTagLibarary(EOGlobalSubCategory globalCategoryItem, String name,
			Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap) {
		return globalTagLibararyNameMap.getOrDefault(buildTagId(globalCategoryItem,name),createTagLibarary(globalTagLibararyNameMap, globalCategoryItem, name) );
	}
	
	EOGlobalTagLibarary createTagLibarary(Map<String, EOGlobalTagLibarary> globalTagLibararyNameMap, EOGlobalSubCategory globalCategoryItem, String name){
		try {
			EOGlobalTagLibarary eoGlobalTagLibarary= new EOGlobalTagLibarary();
			eoGlobalTagLibarary.setName(name);
			eoGlobalTagLibarary.setSubCategory(globalCategoryItem);
			eoGlobalTagLibarary.setType(VisualiseType.VISUALISE_WITH_IMAGES.getType());
			eoGlobalTagLibarary=globalTagLibararyRepository.save(eoGlobalTagLibarary);
			globalTagLibararyNameMap.put(buildTagId(globalCategoryItem, name), eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}catch (RuntimeException e) {
			EOGlobalTagLibarary eoGlobalTagLibarary=globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, name);
			globalTagLibararyNameMap.put(buildTagId(globalCategoryItem, name), eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}catch (Exception e) {
			EOGlobalTagLibarary eoGlobalTagLibarary=globalTagLibararyRepository.findBySubCategoryAndName(globalCategoryItem, name);
			globalTagLibararyNameMap.put(buildTagId(globalCategoryItem, name), eoGlobalTagLibarary);
			return eoGlobalTagLibarary;
		}
		
	}

	private void loadResourceDir(File resourceFile, EOGlobalSubCategory globalCategoryItem, EOGlobalTagLibarary eoGlobalTagLibarary, Map<String, EOGlobalImageLibarary> categoryImgUrlMap) {
		File[] listFiles = resourceFile.listFiles();
		for (File file :  listFiles) {
			if(file.isDirectory()) {
				loadResourceDir(file, globalCategoryItem, eoGlobalTagLibarary, categoryImgUrlMap);
			} else {
				loadResourceFile(file, globalCategoryItem, eoGlobalTagLibarary, categoryImgUrlMap);
			}
		}
	}

	/**
	 * @param file
	 * @param eoGlobalTagLibarary 
	 * @param globalCategoryItem 
	 * @param categoryImgUrlMap 
	 */
	private void loadResourceFile(File file, EOGlobalSubCategory subCategory, EOGlobalTagLibarary eoGlobalTagLibarary, Map<String, EOGlobalImageLibarary> categoryImgUrlMap) {
		try {
			String url = "resource/"+TAGS_WITH_IMAGES+file.getAbsolutePath().split(TAGS_WITH_IMAGES)[1].replace("\\", "/");
			EOGlobalImageLibarary globalImageLibarary = categoryImgUrlMap.getOrDefault(url, new EOGlobalImageLibarary());
			globalImageLibarary.setIdenNo(url.replace("/", "_").replace(",", "_").toUpperCase().split("\\.")[0].replaceFirst("_", ""));
			globalImageLibarary.setSubCategory(subCategory);
			globalImageLibarary.setName(file.getName());
			globalImageLibarary.setType(VisualiseType.VISUALISE_WITH_IMAGES.getType());
			globalImageLibarary.setUrl(url);
			globalImageLibarary.setTagLibarary(eoGlobalTagLibarary);
			globalImageLibarary=globalImageLibararyRepository.save(globalImageLibarary);
			categoryImgUrlMap.put(url, globalImageLibarary);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
