package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;
import com.brijframework.content.global.mapper.GlobalMindSetLibararyMapper;
import com.brijframework.content.global.model.UIGlobalMindSetLibarary;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;
import com.brijframework.content.global.service.GlobalMindSetLibararyService;
import com.brijframework.content.resource.modal.UIResource;

@Service
public class GlobalMindSetLibararyServiceImpl implements GlobalMindSetLibararyService {

	private static final String RECORD_STATE = "recordState";

	private static final String MUSIC_URL = "musicUrl";

	private static final String POSTER_URL = "posterUrl";

	private static final String MINDSET = "mindset";

	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;

	@Autowired
	private GlobalMindSetLibararyMapper globalMindSetLibararyMapper;
	
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
	public void preAdd(UIGlobalMindSetLibarary data, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, null);
	}
	
	@Override
	public void preUpdate(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary find, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}
	
	private void saveResource(UIGlobalMindSetLibarary data, EOGlobalMindSetLibarary find) {
		UIResource resource = data.getFileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if(resource!=null) {
			resource.setIncludeId(true);
			resource.setId(find!=null? find.getResourceId(): null);
			resource.setFolderName(MINDSET);
			UIResource resourceFile= resourceClient.add(resource);
			resourceFile.setIncludeId(true);
			data.setResourceId(resourceFile.getId());
			if(StringUtil.isNonEmpty(resource.getFileName()) && StringUtil.isNonEmpty(resource.getFileContent())) {
				data.setMusicUrl(resourceFile.getFileUrl());
			} else {
				ignoreProperties().add(MUSIC_URL);
			}
			if(StringUtil.isNonEmpty(resource.getPosterName()) && StringUtil.isNonEmpty(resource.getPosterContent())) {
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
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		if(filters!=null && !filters.containsKey(RECORD_STATE)) {
			filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
		}
	}
	
	@Override
	public void postFetch(EOGlobalMindSetLibarary findObject, UIGlobalMindSetLibarary dtoObject) {
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
	public Boolean delete(Long id) {
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
