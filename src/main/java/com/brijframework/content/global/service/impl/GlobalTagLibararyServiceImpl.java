package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalTagLibararyService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalTagLibararyServiceImpl extends CrudServiceImpl<UIGlobalTagLibarary, EOGlobalTagLibarary, Long> implements GlobalTagLibararyService {
	
	private static final String PERCENTAGE = "%";

	private static final String ID = "id";

	private static final String SUB_CATEGORY = "subCategory";

	private static final String NAME = "name";

	private static final String TAG_LIBARARY = "tagLibarary";

	private static final String SUB_CATEGORY_NAME2 = "subCategoryName";

	private static final String SUB_CATEGORY_ID2 = "subCategory.id";

	private static final String SUB_CATEGORY_ID = "subCategoryId";

	private static final String TAG_LIBARARY_NAME2 = "tagLibarary.name";

	private static final String TAG_LIBARARY_NAME = "tagLibararyName";

	private static final String TAG_LIBARARY_ID2 = "tagLibarary.id";

	private static final String TAG_LIBARARY_ID = "tagLibararyId";

	private static final String SUB_CATEGORY_NAME = "subCategory.name";

	private static final String RECORD_STATE = "recordState";

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
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
		addCustomPredicate(SUB_CATEGORY_ID2, subCategoryId);
		addCustomPredicate(SUB_CATEGORY_NAME2, subCategoryName);
		addCustomPredicate(SUB_CATEGORY_NAME, subCategoryName);
		
		addCustomPredicate(TAG_LIBARARY_ID, tagLibararyId);
		addCustomPredicate(TAG_LIBARARY_ID2, tagLibararyId);
		addCustomPredicate(TAG_LIBARARY_NAME, tagLibararyName);
		addCustomPredicate(TAG_LIBARARY_NAME2, tagLibararyName);
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
