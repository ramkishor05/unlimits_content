package com.brijframework.content.global.service.impl;

import static com.brijframework.content.constants.ClientConstants.ID;
import static com.brijframework.content.constants.ClientConstants.RECORD_STATE;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_ID;
import static com.brijframework.content.constants.ClientConstants.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.mapper.GlobalPromptLibararyMapper;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;
import com.brijframework.content.global.repository.GlobalPromptLibararyRepository;
import com.brijframework.content.global.service.GlobalPromptLibararyService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalPromptLibararyServiceImpl  extends CrudServiceImpl<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> implements GlobalPromptLibararyService {
	
	@Autowired
	private GlobalPromptLibararyRepository globalPromptLibararyRepository;
	
	@Autowired
	private GlobalPromptLibararyMapper globalPromptLibararyMapper;
	
	{
		CustomPredicate<EOGlobalPromptLibarary> subCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalSubCategory> subquery = criteriaQuery.subquery(EOGlobalSubCategory.class);
			Root<EOGlobalSubCategory> fromProject = subquery.from(EOGlobalSubCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get(ID), filter.getColumnValue()));
			Path<Object> subCategoryPath = root.get(SUB_CATEGORY);
			In<Object> subCategoryIn = criteriaBuilder.in(subCategoryPath);
			subCategoryIn.value(subquery);
			return subCategoryIn;
		};
		
		addCustomPredicate(SUB_CATEGORY_ID, subCategoryId);
		
		addCustomPredicate(SUB_CATEGORY_REL_ID, subCategoryId);
		
		addCustomPredicate(SUB_CATEGORY, subCategoryId);
		
		CustomPredicate<EOGlobalPromptLibarary> year = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalTenure> subquery = criteriaQuery.subquery(EOGlobalTenure.class);
			Root<EOGlobalTenure> fromProject = subquery.from(EOGlobalTenure.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get(NAME), PERCENTAGE+filter.getColumnValue()+PERCENTAGE));
			Path<Object> tenurePath = root.get(TENURE);
			In<Object> tenureIn = criteriaBuilder.in(tenurePath);
			tenureIn.value(subquery);
			return tenureIn;
		};
		
		addCustomPredicate(YEAR, year);
	}
	

	@Override
	public JpaRepository<EOGlobalPromptLibarary, Long> getRepository() {
		return globalPromptLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalPromptLibarary, UIGlobalPromptLibarary> getMapper() {
		return globalPromptLibararyMapper;
	}

	@Override
	public List<UIGlobalPromptLibarary> findAllByType(String type, MultiValueMap<String, String> headers) {
		return getMapper().mapToDTO(globalPromptLibararyRepository.findAllByType(type));
	}
	
	@Override
	public void preAdd(UIGlobalPromptLibarary data, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalPromptLibarary data, Map<String, List<String>> headers) {
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
	public Boolean delete(Long id) {
		Optional<EOGlobalPromptLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalPromptLibarary eoGlobalPromptLibarary = findById.get();
			eoGlobalPromptLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalPromptLibarary);
			return true;
		}
		return false;
	}
}
