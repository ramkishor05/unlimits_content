package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
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
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.mapper.GlobalMindSetLibararyMapper;
import com.brijframework.content.global.model.UIGlobalMindSetItem;
import com.brijframework.content.global.model.UIGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;
import com.brijframework.content.global.service.GlobalMindSetItemService;
import com.brijframework.content.global.service.GlobalMindSetLibararyService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

@Service
public class GlobalMindSetLibararyServiceImpl extends CrudServiceImpl<UIGlobalMindSetLibarary, EOGlobalMindSetLibarary, Long> implements GlobalMindSetLibararyService {

	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalMindSetLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

	private static final String MUSIC_URL = "musicUrl";

	private static final String POSTER_URL = "posterUrl";

	private static final String MINDSET = "mindset";
	
	private static final String MIND_SET_ITEMS = "mindSetItems";

	private static final String MIND_SET_LIBARARY_ID = "mindSetLibararyId";

	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;

	@Autowired
	private GlobalMindSetLibararyMapper globalMindSetLibararyMapper;
	
	@Autowired
	private GlobalMindSetItemService globalMindSetItemService;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	private List<String> ignoreProperties;
	
	@Override
	public JpaRepository<EOGlobalMindSetLibarary, Long> getRepository() {
		return globalMindSetLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalMindSetLibarary, UIGlobalMindSetLibarary> getMapper() {
		return globalMindSetLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalMindSetLibarary data, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		LOGGER.info("pre add");
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}
	
	@Override
	public void postAdd(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary entity, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveResource(data, entity);
	}
	
	private void saveItems(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary entity, Map<String, Object> actions) {
		if(!RestConstant.isExcludeKey(actions, MIND_SET_ITEMS) && RestConstant.isIncludeKey(actions, MIND_SET_ITEMS)) {
			List<Long> ids = data.getMindSetItems().stream().filter(mindSetItem->!StringUtil.isEmpty(mindSetItem.getId())).map(mindSetItem->mindSetItem.getId()).toList();
			globalMindSetItemService.deleteByMindSetLibararyIdAndIdNotIn(entity.getId(), ids);
			data.getMindSetItems().forEach(item->item.setMindSetLibararyId(entity.getId()));
			Map<String, List<String>> headers=new HashMap<String, List<String>>();
			Map<String, Object> filters=new HashMap<String,Object>();
			filters.put(MIND_SET_LIBARARY_ID, entity.getId());
			data.setMindSetItems(globalMindSetItemService.addAll(data.getMindSetItems(), headers, filters, actions));
		}
	}
	
	@Override
	public void merge(UIGlobalMindSetLibarary dtoObject, EOGlobalMindSetLibarary entityObject,
			UIGlobalMindSetLibarary updateDtoObject, EOGlobalMindSetLibarary updateEntityObject,
			 Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveItems(dtoObject, updateEntityObject, actions);
	}
	
	@Override
	public void preUpdate(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary find, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}
	
	private void saveResource(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary find) {
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
	public List<String> ignoreProperties() {
		if(ignoreProperties==null) {
			ignoreProperties=new ArrayList<String>();
		}
		return ignoreProperties;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public void postFetch(EOGlobalMindSetLibarary findObject, UIGlobalMindSetLibarary dtoObject, Map<String, List<String>> headers, Map<String, Object> filters,  Map<String, Object> actions) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getMusicUrl())) {
			dtoObject.setMusicUrl(dtoObject.getMusicUrl().startsWith("/")? serverUrl+""+dtoObject.getMusicUrl() :  serverUrl+"/"+dtoObject.getMusicUrl());
		}
		
		if(StringUtils.isNotEmpty(dtoObject.getPosterUrl())) {
			dtoObject.setPosterUrl(dtoObject.getPosterUrl().startsWith("/")? serverUrl+""+dtoObject.getPosterUrl() :  serverUrl+"/"+dtoObject.getPosterUrl());
		}
		if(!RestConstant.isExcludeKey(actions, MIND_SET_ITEMS) && RestConstant.isIncludeKey(actions, MIND_SET_ITEMS)) {
			filters.put(MIND_SET_LIBARARY_ID, findObject.getId());
			List<UIGlobalMindSetItem> mindSetItems = globalMindSetItemService.findAll(headers, filters, actions);
			dtoObject.setMindSetItems(mindSetItems);
		}
	}
	
	@Override
	public Boolean deleteById(Long id) {
		Optional<EOGlobalMindSetLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalMindSetLibarary eoGlobalMindSetLibarary = findById.get();
			eoGlobalMindSetLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalMindSetLibarary);
			return true;
		}
		return false;
	}
	
}
