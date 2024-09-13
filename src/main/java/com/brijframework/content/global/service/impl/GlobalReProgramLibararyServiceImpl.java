/**
 * 
 */
package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
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

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;
import com.brijframework.content.global.mapper.GlobalReProgramLibararyMapper;
import com.brijframework.content.global.model.UIGlobalReProgramLibarary;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;
import com.brijframework.content.global.service.GlobalReProgramLibararyService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

/**
 * @author omnie
 */
@Service
public class GlobalReProgramLibararyServiceImpl extends CrudServiceImpl<UIGlobalReProgramLibarary, EOGlobalReProgramLibarary, Long>
		implements GlobalReProgramLibararyService {

	private static final String RECORD_STATE = "recordState";

	private static final String POSTER_URL = "posterUrl";

	private static final String MUSIC_URL = "musicUrl";

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private GlobalReProgramLibararyRepository globalReProgramRepository;

	@Autowired
	private GlobalReProgramLibararyMapper globalReProgramLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	private List<String> ignoreProperties;

	@Override
	public JpaRepository<EOGlobalReProgramLibarary, Long> getRepository() {
		return globalReProgramRepository;
	}

	@Override
	public GenericMapper<EOGlobalReProgramLibarary, UIGlobalReProgramLibarary> getMapper() {
		return globalReProgramLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalReProgramLibarary data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		saveResource(data, null);
	}
	
	@Override
	public void preUpdate(UIGlobalReProgramLibarary data, EOGlobalReProgramLibarary find, Map<String, List<String>> headers) {
		if(data.getRecordState()==null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}

	private void saveResource(UIGlobalReProgramLibarary data, EOGlobalReProgramLibarary find) {
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
			if(BuilderUtil.isValidFile(resource)) {
				data.setPosterUrl(resourceFile.getPosterUrl());
			} else {
				ignoreProperties().add(MUSIC_URL);
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
	public void postFetch(EOGlobalReProgramLibarary findObject, UIGlobalReProgramLibarary dtoObject) {
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
		Optional<EOGlobalReProgramLibarary> findById = getRepository().findById(id);
		if(findById.isPresent()) {
			EOGlobalReProgramLibarary eoGlobalReProgramLibarary = findById.get();
			eoGlobalReProgramLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalReProgramLibarary);
			return true;
		}
		return false;
	}
}
