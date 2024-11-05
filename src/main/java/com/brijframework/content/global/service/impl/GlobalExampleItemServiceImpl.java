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
import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalExampleItemMapper;
import com.brijframework.content.global.model.UIGlobalExampleItem;
import com.brijframework.content.global.repository.GlobalExampleItemRepository;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalExampleItemService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalExampleItemServiceImpl
		extends CrudServiceImpl<UIGlobalExampleItem, EOGlobalExampleItem, Long>
		implements GlobalExampleItemService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExampleItemServiceImpl.class);

	/**
	 * 
	 */

	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;

	@Autowired
	private GlobalExampleItemRepository globalExampleItemRepository;

	@Autowired
	private GlobalExampleItemMapper globalExampleItemMapper;
	
	@Autowired
	private GlobalTenureRepository globalTenureRepository;

	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;

	@Override
	public JpaRepository<EOGlobalExampleItem, Long> getRepository() {
		return globalExampleItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalExampleItem, UIGlobalExampleItem> getMapper() {
		return globalExampleItemMapper;
	}

	{
		CustomPredicate<EOGlobalExampleItem> imageLibararyId = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOGlobalImageLibarary> subquery = criteriaQuery.subquery(EOGlobalImageLibarary.class);
				Root<EOGlobalImageLibarary> fromProject = subquery.from(EOGlobalImageLibarary.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.equal(fromProject.get("id").as(String.class), filter.getColumnValue().toString()));
				Path<Object> imageLibararyPath = root.get("imageLibarary");
				In<Object> imageLibararyIn = criteriaBuilder.in(imageLibararyPath);
				imageLibararyIn.value(subquery);
				return imageLibararyIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for imageLibararyId: " + filter.getColumnValue(), e);
				return null;
			}
		};

		CustomPredicate<EOGlobalExampleItem> tagLibararyId = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Subquery<EOGlobalTagLibarary> subquery = criteriaQuery.subquery(EOGlobalTagLibarary.class);
				Root<EOGlobalTagLibarary> fromProject = subquery.from(EOGlobalTagLibarary.class);
				subquery.select(fromProject)
						.where(criteriaBuilder.like(fromProject.get("id").as(String.class), "%" + filter.getColumnValue() + "%"));
				Path<Object> tagLibararyPath = root.get("tagLibarary");
				In<Object> tagLibararyIn = criteriaBuilder.in(tagLibararyPath);
				tagLibararyIn.value(subquery);
				return tagLibararyIn;
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for tagLibararyId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOGlobalExampleItem> exampleLibararyId = (type, root, criteriaQuery, criteriaBuilder,
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
				LOGGER.error("WARN: unexpected exception for exampleLibararyId: " + filter.getColumnValue(), e);
				return null;
			}
		};

		addCustomPredicate("imageLibararyId", imageLibararyId);
		addCustomPredicate("imageLibarary.id", imageLibararyId);
		addCustomPredicate("tagLibararyId", tagLibararyId);
		addCustomPredicate("tagLibarary.id", tagLibararyId);
		addCustomPredicate("exampleLibararyId", exampleLibararyId);
		addCustomPredicate("exampleLibarary.id", exampleLibararyId);
		
		
	}

	@Override
	public void preAdd(UIGlobalExampleItem data, EOGlobalExampleItem entityObject,  Map<String, List<String>> headers, Map<String, Object> filters,
			Map<String, Object> actions) {
		LOGGER.warn("pre add: {}", headers);
		findOrCreate(data, entityObject);
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	private void findOrCreate(UIGlobalExampleItem dtoObject, EOGlobalExampleItem entityObject) {
		/*if(dtoObject.getExampleLibararyId()!=null &&  dtoObject.getImageLibararyId()!=null) {
			globalExampleItemRepository
			.findByExampleLibararyIdAndImageLibararyId(dtoObject.getExampleLibararyId(), dtoObject.getImageLibararyId())
			.ifPresent(item -> {
				dtoObject.setId(item.getId());
			});
		}
		if(dtoObject.getExampleLibararyId()!=null &&  dtoObject.getTagLibararyId()!=null) {
			globalExampleItemRepository
			.findByExampleLibararyIdAndTagLibararyId(dtoObject.getExampleLibararyId(), dtoObject.getTagLibararyId())
			.ifPresent(item -> {
				dtoObject.setId(item.getId());
			});
		}
		if(dtoObject.getExampleLibararyId()!=null &&  dtoObject.getTagLibararyId()!=null) {
			globalExampleItemRepository
			.findByExampleLibararyIdAndTagLibararyId(dtoObject.getExampleLibararyId(), dtoObject.getTagLibararyId())
			.ifPresent(item -> {
				dtoObject.setId(item.getId());
			});
		}*/
		
		if (dtoObject.getYear() != null) {
			entityObject.setTenure(globalTenureRepository.findOneByYear(dtoObject.getYear()).orElse(null));
		}
		if (dtoObject.getImageLibararyId() != null) {
			entityObject.setImageLibarary(globalImageLibararyRepository.getReferenceById(dtoObject.getImageLibararyId()));
		}
		if (dtoObject.getTagLibararyId() != null) {
			entityObject.setTagLibarary(globalTagLibararyRepository.getReferenceById(dtoObject.getTagLibararyId()));
		}
		if(dtoObject.getExampleLibararyId()!=null) {
			entityObject.setExampleLibarary(globalExampleLibararyRepository.getReferenceById(dtoObject.getExampleLibararyId()));
		}
	}
	
	@Override
	public void preUpdate(UIGlobalExampleItem data, EOGlobalExampleItem find,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (data.getRecordState() == null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		findOrCreate(data, find);
	}

	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalExampleItem> findById = getRepository().findById(id);
		if (findById.isPresent()) {
			EOGlobalExampleItem eoGlobalReprogramItem = findById.get();
			eoGlobalReprogramItem.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalReprogramItem);
			return true;
		}
		return false;
	}
	
	@Override
	public void deleteByExampleLibararyId(Long id) {
		globalExampleItemRepository.deleteByExampleLibararyId(id);
	}
	

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (filters != null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}

	
	@Override
	public List<UIGlobalExampleItem> postFetch(List<EOGlobalExampleItem> findObjects,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		List<UIGlobalExampleItem> uiObjects = super.postFetch(findObjects, headers, filters, actions);
		uiObjects.sort((op1, op2) -> op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

	@Override
	public void postFetch(EOGlobalExampleItem findObject, UIGlobalExampleItem dtoObject,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (findObject.getTenure()!=null) {
			dtoObject.setYear(findObject.getTenure().getYear());
		}
		if (findObject.getImageLibarary()!=null) {
			dtoObject.setImageLibararyId(findObject.getImageLibarary().getId());;
		}
		if (findObject.getTagLibarary()!=null) {
			dtoObject.setTagLibararyId(findObject.getTagLibarary().getId());;
		}
		
		if (findObject.getExampleLibarary()!=null) {
			dtoObject.setExampleLibararyId(findObject.getExampleLibarary().getId());
		}
	}

}
