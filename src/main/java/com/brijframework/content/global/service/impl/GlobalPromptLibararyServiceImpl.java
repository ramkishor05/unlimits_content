package com.brijframework.content.global.service.impl;

import static com.brijframework.content.constants.ClientConstants.ID;
import static com.brijframework.content.constants.ClientConstants.NAME;
import static com.brijframework.content.constants.ClientConstants.RECORD_STATE;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_ID;
import static com.brijframework.content.constants.ClientConstants.SUB_CATEGORY_REL_ID;
import static com.brijframework.content.constants.ClientConstants.TENURE;
import static com.brijframework.content.constants.ClientConstants.YEAR;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.exceptions.InvalidParameterException;
import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.mapper.GlobalPromptLibararyMapper;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;
import com.brijframework.content.global.repository.GlobalPromptLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalPromptLibararyService;
import com.brijframework.content.util.buildImageLibararyIdenNo;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalPromptLibararyServiceImpl  extends CrudServiceImpl<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> implements GlobalPromptLibararyService {
	
	@Autowired
	private GlobalPromptLibararyRepository globalPromptLibararyRepository;
	
	@Autowired
	private GlobalSubCategoryRepository subCategoryRepository;
	
	@Autowired
	private GlobalTenureRepository globalTenureRepository;
	
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
	public  void init(List<EOGlobalPromptLibarary> eoGlobalPromptJson) {
		eoGlobalPromptJson.forEach(eoGlobalPrompt -> {
			EOGlobalPromptLibarary findGlobalPrompt = globalPromptLibararyRepository
					.findByIdenNo(eoGlobalPrompt.getIdenNo()).orElse(eoGlobalPrompt);
			BeanUtils.copyProperties(eoGlobalPrompt, findGlobalPrompt, "id");
			findGlobalPrompt.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalPromptLibarary eoGlobalPromptSave = globalPromptLibararyRepository
					.saveAndFlush(findGlobalPrompt);
			eoGlobalPrompt.setId(eoGlobalPromptSave.getId());
		});
	}
	

	@Override
	public List<UIGlobalPromptLibarary> findAllByType(String type, MultiValueMap<String, String> headers) {
		return getMapper().mapToDTO(globalPromptLibararyRepository.findAllByType(type));
	}
	
	@Override
	public void preAdd(UIGlobalPromptLibarary data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIGlobalPromptLibarary data, EOGlobalPromptLibarary entity, Map<String, List<String>> headers) {
		saveRelation(data, entity);
	}

	private void saveRelation(UIGlobalPromptLibarary data, EOGlobalPromptLibarary entity) {
		if(StringUtil.isEmpty(data.getName())) {
			throw new InvalidParameterException("Name is required!!");
		}
		if(StringUtil.isEmpty(data.getDescription())) {
			throw new InvalidParameterException("Description is required!!");
		}
		if(data.getSubCategoryId()!=null) {
			EOGlobalSubCategory eoGlobalSubCategory = subCategoryRepository.findById(data.getSubCategoryId()).orElseThrow(()-> new InvalidParameterException("Invalid sub category id."));
			entity.setIdenNo(buildImageLibararyIdenNo.buildPromptLibararyIdenNo(eoGlobalSubCategory, data.getName()));
			entity.setSubCategory(eoGlobalSubCategory);
			Optional<EOGlobalPromptLibarary> promptLibararyOptional = globalPromptLibararyRepository.findBySubCategoryId(eoGlobalSubCategory.getId());
			if(promptLibararyOptional.isPresent()) {
				EOGlobalPromptLibarary eoGlobalPromptLibarary = promptLibararyOptional.get();
				data.setId(eoGlobalPromptLibarary.getId());
				entity.setId(eoGlobalPromptLibarary.getId());
				BeanUtils.copyProperties(data, eoGlobalPromptLibarary);
			}
		} else if(StringUtil.isNonEmpty(data.getSubCategoryName())) {
			EOGlobalSubCategory eoGlobalSubCategory = subCategoryRepository.findByName(data.getSubCategoryName()).orElseThrow(()-> new InvalidParameterException("Invalid sub category name."));
			Optional<EOGlobalPromptLibarary> promptLibararyOptional = globalPromptLibararyRepository.findBySubCategoryId(eoGlobalSubCategory.getId());
			entity.setIdenNo(buildImageLibararyIdenNo.buildPromptLibararyIdenNo(eoGlobalSubCategory, data.getName()));
			entity.setSubCategory(eoGlobalSubCategory);
			if(promptLibararyOptional.isPresent()) {
				EOGlobalPromptLibarary eoGlobalPromptLibarary = promptLibararyOptional.get();
				data.setId(eoGlobalPromptLibarary.getId());
				entity.setId(eoGlobalPromptLibarary.getId());
				BeanUtils.copyProperties(data, eoGlobalPromptLibarary);
			}
		} else if(data.getTenureId()!=null) {
			EOGlobalTenure eoGlobalTenure = globalTenureRepository.findById(data.getTenureId()).orElseThrow(()-> new InvalidParameterException("Invalid tenure id."));
			Optional<EOGlobalPromptLibarary> promptLibararyOptional = globalPromptLibararyRepository.findByTenureId(eoGlobalTenure.getId());
			entity.setIdenNo(buildImageLibararyIdenNo.buildPromptLibararyIdenNo(eoGlobalTenure, data.getName()));
			entity.setTenure(eoGlobalTenure);
			if(promptLibararyOptional.isPresent()) {
				EOGlobalPromptLibarary eoGlobalPromptLibarary = promptLibararyOptional.get();
				data.setId(eoGlobalPromptLibarary.getId());
				entity.setId(eoGlobalPromptLibarary.getId());
				BeanUtils.copyProperties(data, eoGlobalPromptLibarary);
			}
		} else if(data.getTenureYear()!=null) {
			EOGlobalTenure eoGlobalTenure = globalTenureRepository.findOneByYear(data.getTenureYear()).orElseThrow(()-> new InvalidParameterException("Invalid tenure year."));
			Optional<EOGlobalPromptLibarary> promptLibararyOptional = globalPromptLibararyRepository.findByTenureId(eoGlobalTenure.getId());
			entity.setIdenNo(buildImageLibararyIdenNo.buildPromptLibararyIdenNo(eoGlobalTenure, data.getName()));
			entity.setTenure(eoGlobalTenure);
			if(promptLibararyOptional.isPresent()) {
				EOGlobalPromptLibarary eoGlobalPromptLibarary = promptLibararyOptional.get();
				data.setId(eoGlobalPromptLibarary.getId());
				entity.setId(eoGlobalPromptLibarary.getId());
				BeanUtils.copyProperties(data, eoGlobalPromptLibarary);
			}
		} else {
			throw new InvalidParameterException("Please provide any valid key like : subCategoryId or subCategoryName or tenureId or tenureYear");
		}
	}
	
	@Override
	public void preUpdate(UIGlobalPromptLibarary data, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
	}
	
	@Override
	public void preUpdate(UIGlobalPromptLibarary data, EOGlobalPromptLibarary entity,
			Map<String, List<String>> headers) {
		saveRelation(data, entity);
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
