/**
 * 
 */
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
import com.brijframework.content.global.entities.EOGlobalReProgramItem;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.mapper.GlobalReProgramItemMapper;
import com.brijframework.content.global.model.UIGlobalReProgramItem;
import com.brijframework.content.global.repository.GlobalReProgramItemRepository;
import com.brijframework.content.global.service.GlobalReProgramItemService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

/**
 * @author omnie
 */
@Service
public class GlobalReProgramItemServiceImpl extends CrudServiceImpl<UIGlobalReProgramItem, EOGlobalReProgramItem, Long>
		implements GlobalReProgramItemService {

	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalReProgramItemServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

	private static final String POSTER_URL = "posterUrl";

	private static final String MUSIC_URL = "musicUrl";

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private GlobalReProgramItemRepository globalReProgramRepository;

	@Autowired
	private GlobalReProgramItemMapper globalReProgramItemMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	{
		CustomPredicate<EOGlobalReProgramItem> reProgramLibararyId = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Subquery<EOGlobalReProgramLibarary> subquery = criteriaQuery.subquery(EOGlobalReProgramLibarary.class);
				Root<EOGlobalReProgramLibarary> fromProject = subquery.from(EOGlobalReProgramLibarary.class);
				subquery.select(fromProject).where(criteriaBuilder.equal(fromProject.get("id"), filter.getColumnValue()));
				Path<Object> reProgramLibararyIdPath = root.get("reProgramLibarary");
				In<Object> reProgramLibararyIdIn = criteriaBuilder.in(reProgramLibararyIdPath);
				reProgramLibararyIdIn.value(subquery);
				return reProgramLibararyIdIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for mainCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};
		addCustomPredicate("reProgramLibarary.id", reProgramLibararyId);
		addCustomPredicate("reProgramLibararyId", reProgramLibararyId);
	}

	@Override
	public JpaRepository<EOGlobalReProgramItem, Long> getRepository() {
		return globalReProgramRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramItem, UIGlobalReProgramItem> getMapper() {
		return globalReProgramItemMapper;
	}
	
	@Override
	public void preAdd(UIGlobalReProgramItem data,EOGlobalReProgramItem find, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		LOGGER.info("pre add");
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		saveResource(data, find);
	}
	
	@Override
	public void preUpdate(UIGlobalReProgramItem data, EOGlobalReProgramItem find, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}

	private void saveResource(UIGlobalReProgramItem data, EOGlobalReProgramItem find) {
		UIResourceModel resource = data.getFileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if(resource!=null && BuilderUtil.isValidResource(resource)) {
			resource.setIncludeId(true);
			resource.setId(find!=null? find.getResourceId(): null);
			resource.setFolderName(REPROGRAM);
			UIResourceModel resourceFile= resourceClient.add(resource);
			resourceFile.setIncludeId(true);
			data.setResourceId(resourceFile.getId());
			if(BuilderUtil.isValidFile(resource)) {
				data.setMusicUrl(resourceFile.getFileUrl());
			} else {
				ignoreProperties().add(MUSIC_URL);
			}
			if(BuilderUtil.isValidPoster(resource)) {
				data.setPosterUrl(resourceFile.getPosterUrl());
			} else {
				ignoreProperties().add(POSTER_URL);
			}
		} else {
			ignoreProperties().add(POSTER_URL);
			ignoreProperties().add(MUSIC_URL);
		}
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public void postFetch(EOGlobalReProgramItem findObject, UIGlobalReProgramItem dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
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
		Optional<EOGlobalReProgramItem> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalReProgramItem eoGlobalReProgramItem = findById.get();
			eoGlobalReProgramItem.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalReProgramItem);
			return true;
		}
		return false;
	}
	
	@Override
	public void deleteByReProgramLibararyId(Long id) {
		globalReProgramRepository.deleteByReProgramLibararyId(id);
	}

	@Override
	public void deleteByReProgramLibararyIdAndIdNotIn(Long id, List<Long> ids) {
		globalReProgramRepository.deleteByReProgramLibararyIdAndIdNotIn(id, ids);
	}
}
