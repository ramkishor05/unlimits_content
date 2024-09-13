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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.constants.VisualiseType;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagImageMapping;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalImageLibararyMapper;
import com.brijframework.content.global.mapper.GlobalTagLibararyMapper;
import com.brijframework.content.global.model.UIGlobalImageModel;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalTagImageMappingRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalTagLibararyService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalTagLibararyServiceImpl extends CrudServiceImpl<UIGlobalTagLibarary, EOGlobalTagLibarary, Long> implements GlobalTagLibararyService {

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private GlobalTagImageMappingRepository globalTagImageMappingRepository;
	
	@Autowired
	private GlobalTagLibararyMapper globalTagLibararyMapper;
	
	@Autowired
	private GlobalImageLibararyMapper globalImageLibararyMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Override
	public JpaRepository<EOGlobalTagLibarary, Long> getRepository() {
		return globalTagLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagLibarary, UIGlobalTagLibarary> getMapper() {
		return globalTagLibararyMapper;
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
	public void preAdd(UIGlobalTagLibarary data, Map<String, List<String>> headers) {
		globalTagLibararyRepository.findBySubCategoryIdAndName(data.getSubCategoryId(), data.getName()).ifPresent(globalSubCategory->{
			data.setId(globalSubCategory.getId());
		});
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIGlobalTagLibarary data, EOGlobalTagLibarary entity, Map<String, List<String>> headers) {
		if(data.getType()==null) {
			data.setType(VisualiseType.WORDS_PAGE.getType());
		}
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void postAdd(UIGlobalTagLibarary data, EOGlobalTagLibarary entity) {
		saveTagImageMappingList(data, entity);
	}

	private void saveTagImageMappingList(UIGlobalTagLibarary data, EOGlobalTagLibarary entity) {
		List<UIGlobalImageModel> uiImageList = data.getImageList();
		if(!CollectionUtils.isEmpty(uiImageList)) {
			globalTagImageMappingRepository.deleteAllByTagLibararyId(entity.getId());
			List<EOGlobalTagImageMapping> eoTagImageList = new ArrayList<EOGlobalTagImageMapping>();
			uiImageList.forEach(uiImageModel->{
				EOGlobalTagImageMapping globalTagImageMapping=new EOGlobalTagImageMapping();
				globalTagImageMapping.setImageLibarary(globalImageLibararyMapper.modelToDAO(uiImageModel));
				globalTagImageMapping.setTagLibarary(entity);
				eoTagImageList.add(globalTagImageMapping);
			});
			globalTagImageMappingRepository.saveAllAndFlush(eoTagImageList);
		}
	}
	
	@Override
	public void preUpdate(UIGlobalTagLibarary data, EOGlobalTagLibarary entity, Map<String, List<String>> headers) {
		if(data.getType()==null) {
			data.setType(VisualiseType.WORDS_PAGE.getType());
		}
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void postUpdate(UIGlobalTagLibarary data, EOGlobalTagLibarary entity, Map<String, List<String>> headers) {
		saveTagImageMappingList(data, entity);
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
	public void postFetch(EOGlobalTagLibarary findObject, UIGlobalTagLibarary dtoObject) {
		List<EOGlobalTagImageMapping> imageMappingList = globalTagImageMappingRepository.findAllByTagLibararyId(findObject.getId(), RecordStatus.ACTIVETED.getStatusIds());
		if(!CollectionUtils.isEmpty(imageMappingList)) {
			List<UIGlobalImageModel> tagMappingForImageList = globalTagLibararyMapper.tagMappingForImageList(imageMappingList);
			for(UIGlobalImageModel uiDeviceImageModel: tagMappingForImageList) {
				uiDeviceImageModel.setImageUrl(uiDeviceImageModel.getImageUrl().startsWith("/")? serverUrl+""+uiDeviceImageModel.getImageUrl() :  serverUrl+"/"+uiDeviceImageModel.getImageUrl());
			}
			dtoObject.setImageList(tagMappingForImageList);
		}
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
			globalTagImageMappingRepository.deleteAllByTagLibararyId(id);
			return true;
		}
		return false;
	}
}
