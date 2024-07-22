package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.mapper.GlobalSubCategoryMapper;
import com.brijframework.content.global.model.UIGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.service.GlobalSubCategoryService;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.service.ResourceService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalSubCategoryServiceImpl extends CrudServiceImpl<UIGlobalSubCategory, EOGlobalSubCategory, Long> implements GlobalSubCategoryService {
	
	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private GlobalSubCategoryMapper globalSubCategoryMapper;
	
	@Autowired
	private ResourceService resourceService;

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

	{
		CustomPredicate<EOGlobalSubCategory> mainCategoryId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
			Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
			Path<Object> subCategoryIdPath = root.get("mainCategory");
			In<Object> subCategoryIdIn = criteriaBuilder.in(subCategoryIdPath);
			subCategoryIdIn.value(subquery);
			return subCategoryIdIn;
		};
		
		CustomPredicate<EOGlobalSubCategory> mainCategoryName= (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Subquery<EOGlobalMainCategory> subquery = criteriaQuery.subquery(EOGlobalMainCategory.class);
			Root<EOGlobalMainCategory> fromProject = subquery.from(EOGlobalMainCategory.class);
			subquery.select(fromProject).where(criteriaBuilder.like(fromProject.get("name"), "%"+filter.getColumnValue()+"%"));
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
	public Boolean delete(Long id) {
		Optional<EOGlobalSubCategory> findById = globalSubCategoryRepository.findById(id);
		if(findById.isPresent()) {
			EOGlobalSubCategory eoGlobalSubCategory = findById.get();
			eoGlobalSubCategory.setRecordState(DataStatus.DACTIVETED.getStatus());
			globalSubCategoryRepository.save(eoGlobalSubCategory);
			return true;
		}
		return false;
	}

	@Override
	public void preAdd(UIGlobalSubCategory data, EOGlobalSubCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			UIResource resource = resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(resource.getFileUrl());
		}
	}

	@Override
	public void preUpdate(UIGlobalSubCategory data, EOGlobalSubCategory entity, Map<String, List<String>> headers) {
		if(data.getContent()!=null) {
			UIResource resource = resourceService.add(data.getContent(), new HashMap<String, List<String>>());
			entity.setLogoUrl(resource.getFileUrl());
		}
	}
	
	@Override
	public void postFetch(EOGlobalSubCategory findObject, UIGlobalSubCategory dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getLogoUrl())) {
			dtoObject.setLogoUrl(dtoObject.getLogoUrl().startsWith("/")? serverUrl+""+dtoObject.getLogoUrl() :  serverUrl+"/"+dtoObject.getLogoUrl());
		}
	}
}
