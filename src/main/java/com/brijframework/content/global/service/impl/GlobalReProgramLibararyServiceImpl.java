/**
 * 
 */
package com.brijframework.content.global.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.constants.RestConstant;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.mapper.GlobalReProgramLibararyMapper;
import com.brijframework.content.global.model.UIGlobalReProgramLibarary;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;
import com.brijframework.content.global.service.GlobalReProgramItemService;
import com.brijframework.content.global.service.GlobalReProgramLibararyService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

/**
 * @author omnie
 */
@Service
public class GlobalReProgramLibararyServiceImpl extends CrudServiceImpl<UIGlobalReProgramLibarary, EOGlobalReProgramLibarary, Long>
		implements GlobalReProgramLibararyService {

	private static final String RE_PROGRAM_ITEMS = "reProgramItems";

	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalReProgramLibararyServiceImpl.class);
	
	private static final String RE_PROGRAM_LIBARARY_ID = "reProgramLibararyId";

	private static final String RECORD_STATE = "recordState";

	private static final String POSTER_URL = "posterUrl";

	private static final String MUSIC_URL = "musicUrl";

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private GlobalReProgramLibararyRepository globalReProgramRepository;

	@Autowired
	private GlobalReProgramLibararyMapper globalReProgramLibararyMapper;
	
	@Autowired
	private GlobalReProgramItemService globalReProgramItemService;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	@Override
	public JpaRepository<EOGlobalReProgramLibarary, Long> getRepository() {
		return globalReProgramRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramLibarary, UIGlobalReProgramLibarary> getMapper() {
		return globalReProgramLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalReProgramLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,
			Map<String, Object> actions) {
		LOGGER.warn("pre add: {}", headers);
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIGlobalReProgramLibarary data, EOGlobalReProgramLibarary entity,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveResource(data, entity);
	}

	@Override
	public void preUpdate(UIGlobalReProgramLibarary data, EOGlobalReProgramLibarary find,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (data.getRecordState() == null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}

	private void saveResource(UIGlobalReProgramLibarary data, EOGlobalReProgramLibarary find) {
		UIResourceModel resource = data.getFileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if (resource != null && BuilderUtil.isValidResource(resource)) {
			resource.setIncludeId(true);
			resource.setId(find != null ? find.getResourceId() : null);
			resource.setFolderName(REPROGRAM);
			UIResourceModel resourceFile = resourceClient.add(resource);
			resourceFile.setIncludeId(true);
			data.setResourceId(resourceFile.getId());
			if (BuilderUtil.isValidFile(resource)) {
				data.setMusicUrl(resourceFile.getFileUrl());
				find.setMusicUrl(resourceFile.getFileUrl());
			} else {
				ignoreProperties().add(MUSIC_URL);
			}
			if (BuilderUtil.isValidPoster(resource)) {
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
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (filters != null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}

	@Override
	public void postFetch(EOGlobalReProgramLibarary findObject, UIGlobalReProgramLibarary dtoObject,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId() + "");
		}
		if (StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/") ? serverUrl + "" + dtoObject.getMusicUrl()
					: serverUrl + "/" + dtoObject.getMusicUrl());
		}

		if (StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/") ? serverUrl + "" + dtoObject.getPosterUrl()
					: serverUrl + "/" + dtoObject.getPosterUrl());
		}
	}

	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalReProgramLibarary> findById = getRepository().findById(id);
		if (findById.isPresent()) {
			EOGlobalReProgramLibarary eoGlobalReprogramLibarary = findById.get();
			eoGlobalReprogramLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalReprogramLibarary);
			return true;
		}
		return false;
	}
	

	protected void saveItems(UIGlobalReProgramLibarary data, EOGlobalReProgramLibarary entity, Map<String, Object> actions) {
		if(!RestConstant.isExcludeKey(actions, RE_PROGRAM_ITEMS) && RestConstant.isIncludeKey(actions, RE_PROGRAM_ITEMS)) {
			
			List<Long> ids = data.getReProgramItems().stream().filter(reProgramItem->!StringUtil.isEmpty(reProgramItem.getId())).map(reProgramItem->reProgramItem.getId()).toList();
			globalReProgramItemService.deleteByReProgramLibararyIdAndIdNotIn(entity.getId(), ids);
			
			Map<String, List<String>> headers=new HashMap<String, List<String>>();
			Map<String, Object> filters=new HashMap<String,Object>();
			filters.put(RE_PROGRAM_LIBARARY_ID, entity.getId());
			data.getReProgramItems().forEach(item->item.setReProgramLibararyId(entity.getId()));
			data.setReProgramItems(globalReProgramItemService.updateAll(data.getReProgramItems(), headers, filters, actions));
		}
	}

}
