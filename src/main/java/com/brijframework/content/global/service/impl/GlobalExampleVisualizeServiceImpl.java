package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalExampleVisualize;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.mapper.GlobalExampleVisualizeMapper;
import com.brijframework.content.global.model.UIGlobalExampleVisualize;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.repository.GlobalExampleVisualizeRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalExampleVisualizeService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalExampleVisualizeServiceImpl
		extends CrudServiceImpl<UIGlobalExampleVisualize, EOGlobalExampleVisualize, Long>
		implements GlobalExampleVisualizeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExampleVisualizeServiceImpl.class);

	/**
	 * 
	 */

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;

	@Autowired
	private GlobalExampleVisualizeRepository globalExampleVisualizeRepository;

	@Autowired
	private GlobalExampleVisualizeMapper globalExampleVisualizeMapper;
	
	@Autowired
	private GlobalTenureRepository globalTenureRepository;

	@Override
	public JpaRepository<EOGlobalExampleVisualize, Long> getRepository() {
		return globalExampleVisualizeRepository;
	}

	@Override
	public GenericMapper<EOGlobalExampleVisualize, UIGlobalExampleVisualize> getMapper() {
		return globalExampleVisualizeMapper;
	}

	{
		CustomPredicate<EOGlobalExampleVisualize> visualizeYear = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOGlobalTenure> subquery = criteriaQuery.subquery(EOGlobalTenure.class);
				Root<EOGlobalTenure> fromProject = subquery.from(EOGlobalTenure.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> tenurePath = root.get("tenure");
				In<Object> tenureIn = criteriaBuilder.in(tenurePath);
				tenureIn.value(subquery);
				return tenureIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for tenure: " + filter.getColumnValue(), e);
				return null;
			}
		};

		CustomPredicate<EOGlobalExampleVisualize> exampleLibararyId = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOGlobalExampleLibarary> subquery = criteriaQuery.subquery(EOGlobalExampleLibarary.class);
				Root<EOGlobalExampleLibarary> fromProject = subquery.from(EOGlobalExampleLibarary.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.like(fromProject.get("id").as(String.class), "%" + filter.getColumnValue() + "%"));
				Path<Object> exampleLibararyPath = root.get("exampleLibarary");
				In<Object> exampleLibararyIn = criteriaBuilder.in(exampleLibararyPath);
				exampleLibararyIn.value(subquery);
				return exampleLibararyIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for exampleLibarary: " + filter.getColumnValue(), e);
				return null;
			}
		};

		addCustomPredicate("visualizeYear", visualizeYear);
		addCustomPredicate("exampleLibarary.id", exampleLibararyId);
		addCustomPredicate("exampleLibararyId", exampleLibararyId);
	}

	@Override
	public void preAdd(UIGlobalExampleVisualize data, EOGlobalExampleVisualize entityObject,  Map<String, List<String>> headers, Map<String, Object> filters,
			Map<String, Object> actions) {
		LOGGER.warn("pre add: {}", headers);
		findOrCreate(data, entityObject);
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	private void findOrCreate(UIGlobalExampleVisualize dtoObject, EOGlobalExampleVisualize entityObject) {
		if(dtoObject.getExampleLibararyId()!=null &&  dtoObject.getVisualizeYear()!=null) {
			globalExampleVisualizeRepository
			.findByExampleLibararyIdAndTenureYear(dtoObject.getExampleLibararyId(), dtoObject.getVisualizeYear())
			.ifPresent(Visualize -> {
				dtoObject.setId(Visualize.getId());
			});
		}
		if (dtoObject.getVisualizeYear() != null) {
			entityObject.setTenure(globalTenureRepository.findOneByYear(dtoObject.getVisualizeYear()).orElse(null));
		}
		entityObject.setExampleLibarary(globalExampleLibararyRepository.getReferenceById(dtoObject.getExampleLibararyId()));
	}
	
	@Override
	public void preUpdate(UIGlobalExampleVisualize data, EOGlobalExampleVisualize find,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (data.getRecordState() == null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		findOrCreate(data, find);
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (filters != null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}

	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalExampleVisualize> findById = getRepository().findById(id);
		if (findById.isPresent()) {
			EOGlobalExampleVisualize entityObject = findById.get();
			entityObject.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(entityObject);
			return true;
		}
		return false;
	}
	
	@Override
	public List<UIGlobalExampleVisualize> postFetch(List<EOGlobalExampleVisualize> findObjects,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		List<UIGlobalExampleVisualize> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1, op2) -> op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

	@Override
	public void postFetch(EOGlobalExampleVisualize findObject, UIGlobalExampleVisualize dtoObject,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (findObject.getTenure()!=null) {
			dtoObject.setVisualizeYear(findObject.getTenure().getYear());
		}
		if (findObject.getExampleLibarary()!=null) {
			dtoObject.setExampleLibararyId(findObject.getExampleLibarary().getId());
		}
	}

	@Override
	public void deleteByExampleLibararyId(Long id) {
		globalExampleVisualizeRepository.deleteByExampleLibararyId(id);
	}

}
