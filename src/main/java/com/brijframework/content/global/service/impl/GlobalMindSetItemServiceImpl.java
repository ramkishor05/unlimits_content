package com.brijframework.content.global.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.brijframework.content.global.entities.EOGlobalMindSetItem;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.mapper.GlobalMindSetItemMapper;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.global.repository.GlobalMindSetItemRepository;
import com.brijframework.content.global.service.GlobalMindSetItemService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class GlobalMindSetItemServiceImpl
extends CrudServiceImpl<UIGlobalMindSetItem, EOGlobalMindSetItem, Long>
implements GlobalMindSetItemService {

	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalMindSetItemServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

	private static final String MUSIC_URL = "musicUrl";

	private static final String POSTER_URL = "posterUrl";

	private static final String MINDSET = "mindset_items";

	@Autowired
	private GlobalMindSetItemRepository globalMindSetItemRepository;

	@Autowired
	private GlobalMindSetItemMapper globalMindSetItemMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Override
	public JpaRepository<EOGlobalMindSetItem, Long> getRepository() {
		return globalMindSetItemRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetItem, UIGlobalMindSetItem> getMapper() {
		return globalMindSetItemMapper;
	}

	{
		CustomPredicate<EOGlobalMindSetItem> mindSetLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalMindSetLibarary> subquery = criteriaQuery.subquery(EOGlobalMindSetLibarary.class);
				Root<EOGlobalMindSetLibarary> fromProject = subquery.from(EOGlobalMindSetLibarary.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
				Path<Object> mindSetLibararyIdPath = root.get("mindSetLibarary");
				In<Object> mindSetLibararyIdIn = criteriaBuilder.in(mindSetLibararyIdPath);
				mindSetLibararyIdIn.value(subquery);
				return mindSetLibararyIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for mainCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		addCustomPredicate("mindSetLibarary.id", mindSetLibararyId);
		addCustomPredicate("mindSetLibararyId", mindSetLibararyId);
	}
	
	@Override
	public void preAdd(UIGlobalMindSetItem data, Map<String, List<String>> headers) {
		LOGGER.info("pre add");
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preAdd(UIGlobalMindSetItem data, EOGlobalMindSetItem entity, Map<String, List<String>> headers) {
		saveResource(data, entity);
	}
	
	@Override
	public void preUpdate(UIGlobalMindSetItem data, Map<String, List<String>> headers) {
		LOGGER.info("pre add");
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void preUpdate(UIGlobalMindSetItem data, EOGlobalMindSetItem find, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}
	
	private void saveResource(UIGlobalMindSetItem data, EOGlobalMindSetItem find) {
		UIResourceModel resource = data.getFileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if(resource!=null && BuilderUtil.isValidResource(resource)) {
			resource.setIncludeId(true);
			resource.setId(find!=null? find.getResourceId(): null);
			resource.setFolderName(MINDSET);
			UIResourceModel resourceFile= resourceClient.add(resource);
			resourceFile.setIncludeId(true);
			data.setResourceId(resourceFile.getId());
			find.setResourceId(resourceFile.getId());
			if(BuilderUtil.isValidFile(resource)) {
				data.setMusicUrl(resourceFile.getFileUrl());
				find.setMusicUrl(resourceFile.getFileUrl());
			} else {
				ignoreProperties().add(MUSIC_URL);
			}
			if(BuilderUtil.isValidPoster(resource)) {
				data.setPosterUrl(resourceFile.getPosterUrl());
				find.setPosterUrl(resourceFile.getPosterUrl());
			} else {
				ignoreProperties().add(POSTER_URL);
			}
		} else {
			ignoreProperties().add(POSTER_URL);
			ignoreProperties().add(MUSIC_URL);
		}
	}
	
	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.add(POSTER_URL);
		ignoreProperties.add(MUSIC_URL);
		return ignoreProperties;
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public void postFetch(EOGlobalMindSetItem findObject, UIGlobalMindSetItem dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/")? serverUrl+""+dtoObject.getMusicUrl() :  serverUrl+"/"+dtoObject.getMusicUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
	}
	
	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalMindSetItem> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalMindSetItem eoGlobalMindSetItem = findById.get();
			eoGlobalMindSetItem.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalMindSetItem);
			return true;
		}
		return false;
	}

	@Override
	public void deleteByMindSetLibararyIdAndIdNotIn(Long id, List<Long> ids) {
		globalMindSetItemRepository.deleteByMindSetLibararyIdAndIdNotIn( id, ids);
	}
	
}
