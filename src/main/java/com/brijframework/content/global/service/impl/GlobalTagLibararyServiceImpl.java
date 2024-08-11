package com.brijframework.content.global.service.impl;

import static com.brijframework.content.constants.ClientConstants.ID;
import static com.brijframework.content.constants.ClientConstants.NAME;
import static com.brijframework.content.constants.ClientConstants.RECORD_STATE;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_ID;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_NAME;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_REL_ID;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_REL_NAME;
import static com.brijframework.content.constants.ClientConstants.TAG_LIBARARY;
import static com.brijframework.content.constants.ClientConstants.TAG_LIBARARY_ID;
import static com.brijframework.content.constants.ClientConstants.TAG_LIBARARY_NAME;
import static com.brijframework.content.constants.ClientConstants.TAG_LIBARARY_REL_ID;
import static com.brijframework.content.constants.ClientConstants.TAG_LIBARARY_REL_NAME;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.constants.VisualiseType;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalTagLibararyMapper;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalTagLibararyService;
import com.brijframework.content.util.IdenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalTagLibararyServiceImpl extends CrudServiceImpl<UIGlobalTagLibarary, EOGlobalTagLibarary, Long> implements GlobalTagLibararyService {

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;
	
	@Autowired
	private GlobalTagLibararyMapper globalTagLibararyMapper;

	@Override
	public JpaRepository<EOGlobalTagLibarary, Long> getRepository() {
		return globalTagLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagLibarary, UIGlobalTagLibarary> getMapper() {
		return globalTagLibararyMapper;
	}
	
	@Override
	public void init(List<EOGlobalTagLibarary> eoGlobalTagItemJson) {

		eoGlobalTagItemJson.forEach(eoGlobalTagItem -> {
			EOGlobalTagLibarary findGlobalTagItem = globalTagLibararyRepository
					.findByIdenNo(eoGlobalTagItem.getIdenNo()).orElse(eoGlobalTagItem);
			BeanUtils.copyProperties(eoGlobalTagItem, findGlobalTagItem, "id");
			findGlobalTagItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalTagLibarary eoGlobalTagItemSave = globalTagLibararyRepository
					.saveAndFlush(findGlobalTagItem);
			eoGlobalTagItem.setId(eoGlobalTagItemSave.getId());
		});
	}
	
	protected void copyToAll() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_tag_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_prompt_libarary_file_name = "/global_portal_tag_libarary";
		globalTagLibararyRepository.findAll().stream()
		.filter(globalTagLibarary -> globalTagLibarary.getSubCategory() != null)
		.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
		.forEach((subCategory1, globalTagLibararyList) -> {
			globalSubCategoryRepository.findAll().forEach(subCategory -> {
				buildTagLibarary(dirs, global_portal_prompt_libarary_file_name, subCategory,
						globalTagLibararyList);
			});
		});
	}
	

	protected void export_global_portal_tag_libarary() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_tag_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_prompt_libarary_file_name = "/global_portal_tag_libarary";
		globalTagLibararyRepository.findAll().stream()
				.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
				.forEach((subCategory, globalTagLibararyList) -> {
					buildTagLibarary(dirs, global_portal_prompt_libarary_file_name, subCategory, globalTagLibararyList);
				});
	}

	protected void buildTagLibarary(File dirs, String global_portal_prompt_libarary_file_name,
			EOGlobalSubCategory subCategory, List<EOGlobalTagLibarary> globalTagLibararyList) {
		String fileName = global_portal_prompt_libarary_file_name + "_" + subCategory.getMainCategory().getName() + "_"
				+ IdenUtil.replaceContent(subCategory.getName()) + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_TagLibarary" + "_" + subCategory.getName());
		jsonSchemaFile.setOrderSequence(subCategory.getOrderSequence());
		globalTagLibararyList.forEach(globalTagLibarary -> {
			String idenNo = IdenUtil.buildIdenNo(subCategory, globalTagLibarary);
			JsonSchemaObject jsonObject = new JsonSchemaObject();
			jsonObject.setId(idenNo);
			jsonObject.setName("Global_Portal_TagLibarary");
			jsonObject.setType(globalTagLibarary.getClass().getName());
			jsonSchemaFile.setType(globalTagLibarary.getClass().getName());
			jsonObject.getProperties().put("idenNo", idenNo);
			jsonObject.getProperties().put("name", globalTagLibarary.getName());
			jsonObject.getProperties().put("orderSequence", globalTagLibarary.getOrderSequence());
			jsonObject.getProperties().put("color", subCategory.getColor());
			jsonObject.getProperties().put("type", globalTagLibarary.getType());
			jsonObject.getProperties().put("subCategory",
					"LK@" + IdenUtil.buildIdenNo(subCategory.getMainCategory(), subCategory));
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
	}

	{
		CustomPredicate<EOGlobalTagLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get(ID), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get(SUB_CATEGORY);
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalTagLibarary> subCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get(NAME), PERCENTAGE+filter.getColumnValue()+PERCENTAGE));
			Path<Object> subCategoryIdPath = root.get(SUB_CATEGORY);
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalTagLibarary> tagLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalTagLibarary> subquery = criteriaQuery.subquery(EOGlobalTagLibarary.class);
			Root<EOGlobalTagLibarary> fromProject = subquery.from(EOGlobalTagLibarary.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get(ID), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get(TAG_LIBARARY);
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalTagLibarary> tagLibararyName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalTagLibarary> subquery = criteriaQuery.subquery(EOGlobalTagLibarary.class);
			Root<EOGlobalTagLibarary> fromProject = subquery.from(EOGlobalTagLibarary.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get(NAME), PERCENTAGE+filter.getColumnValue()+PERCENTAGE));
			Path<Object> subCategoryIdPath = root.get(TAG_LIBARARY);
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
 
		addCustomPredicate(SUB_CATEGORY_ID, subCategoryId);
		addCustomPredicate(SUB_CATEGORY_REL_ID, subCategoryId);
		addCustomPredicate(SUB_CATEGORY_REL_NAME, subCategoryName);
		addCustomPredicate(SUB_CATEGORY_NAME, subCategoryName);
		
		addCustomPredicate(TAG_LIBARARY_ID, tagLibararyId);
		addCustomPredicate(TAG_LIBARARY_REL_ID, tagLibararyId);
		addCustomPredicate(TAG_LIBARARY_NAME, tagLibararyName);
		addCustomPredicate(TAG_LIBARARY_REL_NAME, tagLibararyName);
	}
	
	@Override
	public void preAdd(UIGlobalTagLibarary data, EOGlobalTagLibarary entity, Map<String, List<String>> headers) {
		if(data.getType()==null) {
			data.setType(VisualiseType.VISUALISE_WITH_WORDS.getType());
		}
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalTagLibarary data, EOGlobalTagLibarary entity, Map<String, List<String>> headers) {
		if(data.getType()==null) {
			data.setType(VisualiseType.VISUALISE_WITH_WORDS.getType());
		}
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public List<UIGlobalTagLibarary> postFetch(List<EOGlobalTagLibarary> findObjects) {
		List<UIGlobalTagLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((uiObject1, uiObject2)->uiObject1.getSubCategoryId().compareTo(uiObject2.getSubCategoryId()));
		return uiObjects;
	}

	@Override
	public Pageable getPageRequest(int pageNumber, int count) {
		return PageRequest.of(pageNumber, count, Sort.by(SUB_CATEGORY_NAME));
	}
	
	@Override
	public Pageable getPageRequest(int pageNumber, int count, Sort sort) {
		return PageRequest.of(pageNumber, count, Sort.by(SUB_CATEGORY_NAME).and(sort));
	}

	@Override
	public Boolean delete(Long id) {
		Optional<EOGlobalTagLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalTagLibarary eoGlobalTagLibarary = findById.get();
			eoGlobalTagLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalTagLibarary);
			return true;
		}
		return false;
	}
}
