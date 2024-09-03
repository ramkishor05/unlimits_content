package com.brijframework.content.global.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.mapper.GlobalMainCategoryMapper;
import com.brijframework.content.global.model.UIGlobalMainCategory;
import com.brijframework.content.global.repository.GlobalMainCategoryRepository;
import com.brijframework.content.global.service.GlobalMainCategoryService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.resource.service.ResourceService;
import com.brijframework.content.util.IdenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

@Service
public class GlobalMainCategoryServiceImpl extends CrudServiceImpl<UIGlobalMainCategory, EOGlobalMainCategory, Long> implements GlobalMainCategoryService {
	
	private static final String RECORD_STATE = "recordState";

	@Autowired
	private GlobalMainCategoryRepository globalMainCategoryRepository;
	
	@Autowired
	private GlobalMainCategoryMapper globalMainCategoryMapper;

	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public JpaRepository<EOGlobalMainCategory, Long> getRepository() {
		return globalMainCategoryRepository;
	}

	@Override
	public GenericMapper<EOGlobalMainCategory, UIGlobalMainCategory> getMapper() {
		return globalMainCategoryMapper;
	}

	@Override
	public List<UIGlobalMainCategory> getMainCategoryList(RecordStatus dataStatus) {
		return globalMainCategoryMapper.mapToDTO(globalMainCategoryRepository.getCategoryGroupListByStatus(dataStatus.getStatusIds()));
	}
	
	@Override
	public void init(List<EOGlobalMainCategory> eoGlobalCategoryGroupJson) {

		eoGlobalCategoryGroupJson.forEach(eoGlobalCategoryGroup -> {
			EOGlobalMainCategory findGlobalCategoryGroup = globalMainCategoryRepository
					.findByIdenNo(eoGlobalCategoryGroup.getIdenNo()).orElse(eoGlobalCategoryGroup);
			BeanUtils.copyProperties(eoGlobalCategoryGroup, findGlobalCategoryGroup, "id");
			findGlobalCategoryGroup.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalMainCategory eoGlobalCategoryGroupSave = globalMainCategoryRepository
					.saveAndFlush(findGlobalCategoryGroup);
			eoGlobalCategoryGroup.setId(eoGlobalCategoryGroupSave.getId());
		});
	}
	
	@Override
	public void export() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_main_category");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String name = "Global_Portal_MainCategory";
		String global_portal_Main_category_file_name = "/global_portal_main_category";
		String fileName = global_portal_Main_category_file_name + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_MainCategory");
		jsonSchemaFile.setOrderSequence(1.0);
		globalMainCategoryRepository.findAll().forEach(globalMainCategory -> {
			JsonSchemaObject jsonObject = new JsonSchemaObject();
			jsonObject.setId(IdenUtil.buildIdenNo(globalMainCategory));
			jsonObject.setName(name);
			jsonObject.setType(globalMainCategory.getClass().getName());
			jsonSchemaFile.setType(globalMainCategory.getClass().getName());
			jsonObject.getProperties().put("idenNo", IdenUtil.buildIdenNo(globalMainCategory));
			jsonObject.getProperties().put("name", globalMainCategory.getName());
			jsonObject.getProperties().put("logoUrl", globalMainCategory.getLogoUrl());
			jsonObject.getProperties().put("orderSequence", globalMainCategory.getOrderSequence());
			jsonObject.getProperties().put("color", globalMainCategory.getColor());
			jsonSchemaFile.getObjects().add(jsonObject);
		});
		ObjectMapper mapper = new ObjectMapper();
		try {
			Files.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchemaFile),
					new File(dirs, fileName), Charset.defaultCharset());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
	}
	
	@Override
	public void preAdd(UIGlobalMainCategory data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIGlobalMainCategory data, EOGlobalMainCategory entity, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		if(data.getContent()!=null) {
			UIResourceModel add = resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(add.getFileUrl());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalMainCategory data, EOGlobalMainCategory entity, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		if(data.getContent()!=null) {
			UIResourceModel add = resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(add.getFileUrl());
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public void postFetch(EOGlobalMainCategory findObject, UIGlobalMainCategory dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}
	
	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalMainCategory> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalMainCategory eoGlobalMainCategory = findById.get();
			eoGlobalMainCategory.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalMainCategory);
			return true;
		}
		return false;
	}
}
