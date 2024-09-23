package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
import org.brijframework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalSubCategoryMapper;
import com.brijframework.content.global.model.UIGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.service.GlobalSubCategoryService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalSubCategoryServiceImpl extends CrudServiceImpl<UIGlobalSubCategory, EOGlobalSubCategory, Long> implements GlobalSubCategoryService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalSubCategoryServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

	private static final String SUB_CATEGORY = "sub_category";
	
	private static final String LOGO_URL = "logoUrl";

	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private GlobalSubCategoryMapper globalSubCategoryMapper;
	
	@Autowired
	private ResourceClient resourceClient;

	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalSubCategory, Long> getRepository() {
		return globalSubCategoryRepository;
	}

	@Override
	public GenericMapper<EOGlobalSubCategory, UIGlobalSubCategory> getMapper() {
		return globalSubCategoryMapper;
	}
	
	@Override
	public void init(List<EOGlobalSubCategory> eoGlobalCategoryItemJson){
        LOGGER.info("init");
		eoGlobalCategoryItemJson.forEach(eoGlobalCategoryItem -> {
			EOGlobalSubCategory findGlobalCategoryItem = globalSubCategoryRepository
					.findByMainCategoryIdAndName(eoGlobalCategoryItem.getMainCategory().getId(),
							eoGlobalCategoryItem.getName())
					.orElse(eoGlobalCategoryItem);
			BeanUtils.copyProperties(eoGlobalCategoryItem, findGlobalCategoryItem, "id");
			findGlobalCategoryItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalSubCategory eoGlobalCategorySave = globalSubCategoryRepository
					.saveAndFlush(findGlobalCategoryItem);
			eoGlobalCategoryItem.setId(eoGlobalCategorySave.getId());
		});
	}
	
	@Override
	public void export() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_sub_category");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String name = "Global_Portal_SubCategory";
		String global_portal_sub_category_file_name = "/global_portal_sub_category";
		globalSubCategoryRepository.findAll().stream()
		.collect(Collectors.groupingBy(globalSubCategory -> globalSubCategory.getMainCategory()))
		.forEach((mainCategory, globalSubCategoryList) -> {
			String fileName = global_portal_sub_category_file_name + "_"
					+ BuilderUtil.replaceContent(mainCategory.getName()) + ".json";
			JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
			jsonSchemaFile.setId("Global_Portal_SubCategory" + "_" + mainCategory.getName());
			jsonSchemaFile.setOrderSequence(mainCategory.getOrderSequence());
			globalSubCategoryList.forEach(globalSubCategory -> {
				JsonSchemaObject jsonObject = new JsonSchemaObject();
				jsonObject.setId(BuilderUtil.buildSubCategoryIdenNo(mainCategory, globalSubCategory));
				jsonObject.setName(name);
				jsonObject.setType(globalSubCategory.getClass().getName());
				jsonSchemaFile.setType(globalSubCategory.getClass().getName());
				jsonObject.getProperties().put("idenNo", BuilderUtil.buildSubCategoryIdenNo(mainCategory, globalSubCategory));
				jsonObject.getProperties().put("name", globalSubCategory.getName());
				jsonObject.getProperties().put("logoUrl", globalSubCategory.getLogoUrl());
				jsonObject.getProperties().put("orderSequence", globalSubCategory.getOrderSequence());
				jsonObject.getProperties().put("color", globalSubCategory.getColor());
				jsonObject.getProperties().put("mainCategory", "LK@" + BuilderUtil.buildMainCategoryIdenNo(mainCategory));
				jsonSchemaFile.getObjects().add(jsonObject);
			});
			ObjectMapper mapper = new ObjectMapper();
			try {
				com.google.common.io.Files.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchemaFile),
						new File(dirs, fileName), Charset.defaultCharset());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			;
		});
	}

	{
		CustomPredicate<EOGlobalSubCategory> mainCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
			Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
			Path<Object> subCategoryIdPath = root.get("mainCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalSubCategory> mainCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
			Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name").as(String.class), "%"+filter.getColumnValue()+"%"));
			Path<Object> subCategoryIdPath = root.get("mainCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		 
		addCustomPredicate("mainCategoryId", mainCategoryId);
		addCustomPredicate("mainCategory.id", mainCategoryId);
		addCustomPredicate("mainCategoryName", mainCategoryName);
		addCustomPredicate("mainCategory.name", mainCategoryName);
	}

	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalSubCategory> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalSubCategory eoGlobalSubCategory = findById.get();
			eoGlobalSubCategory.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalSubCategory);
			return true;
		}
		return false;
	}
	
	@Override
	public void preAdd(UIGlobalSubCategory data, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		globalSubCategoryRepository.findByMainCategoryIdAndName(data.getMainCategoryId(), data.getName()).ifPresent(globalSubCategory->{
			data.setId(globalSubCategory.getId());
		});
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIGlobalSubCategory data, EOGlobalSubCategory entity, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, entity);
	}
	
	@Override
	public void preUpdate(UIGlobalSubCategory data, EOGlobalSubCategory entity, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, entity);
	}
	
	private void saveResource(UIGlobalSubCategory data, EOGlobalSubCategory find) {
		UIResourceModel resource = data.getContent();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if (resource != null) {
			resource.setIncludeId(false);
			resource.setFolderName(SUB_CATEGORY);
			resource.setFileName(StringUtil.isNonEmpty(resource.getFileName())? BuilderUtil.replaceContent(resource.getFileName()): BuilderUtil.replaceContent(data.getName()));
			if (StringUtil.isNonEmpty(resource.getFileName()) && StringUtil.isNonEmpty(resource.getFileContent())) {
				UIResourceModel resourceFile = resourceClient.add(resource);
				resourceFile.setIncludeId(false);
				data.setResourceId(resourceFile.getId());
				data.setLogoUrl(resourceFile.getFileUrl());
				find.setLogoUrl(resourceFile.getFileUrl());
			} else {
				ignoreProperties().add(LOGO_URL);
			}
		} else {
			ignoreProperties().add(LOGO_URL);
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public void postFetch(EOGlobalSubCategory findObject, UIGlobalSubCategory dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}
}
