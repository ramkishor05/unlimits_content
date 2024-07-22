package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;

import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.mapper.GlobalAffirmationLibararyMapper;
import com.brijframework.content.global.model.UIGlobalAffirmationLibarary;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;
import com.brijframework.content.global.service.GlobalAffirmationLibararyService;
import com.brijframework.content.resource.modal.UIResource;

@Service
public class GlobalAffirmationLibararyServiceImpl implements GlobalAffirmationLibararyService {

	private static final String POSTER_URL = "posterUrl";

	private static final String MUSIC_URL = "musicUrl";

	private static final String AFFIRMATION = "affirmation";

	@Autowired
	private GlobalAffirmationLibararyRepository clientAffirmationLibararyRepository;
	
	@Autowired
	private GlobalAffirmationLibararyMapper clientAffirmationLibararyMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Value("${openapi.service.url}")
	private String serverUrl;

	private List<String> ignoreProperties;

	@Override
	public JpaRepository<EOGlobalAffirmationLibarary, Long> getRepository() {
		return clientAffirmationLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalAffirmationLibarary, UIGlobalAffirmationLibarary> getMapper() {
		return clientAffirmationLibararyMapper;
	}
	
	@Override
	public void preAdd(UIGlobalAffirmationLibarary data,  Map<String, List<String>> headers) {
		saveResource(data, null);
	}
	
	@Override
	public void preUpdate(UIGlobalAffirmationLibarary data, EOGlobalAffirmationLibarary find, Map<String, List<String>> headers) {
		saveResource(data, find);
	}

	private void saveResource(UIGlobalAffirmationLibarary data, EOGlobalAffirmationLibarary find) {
		UIResource resource = data.getFileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if(resource!=null) {
			resource.setIncludeId(true);
			resource.setId(find!=null? find.getResourceId(): null);
			resource.setFolderName(AFFIRMATION);
			UIResource resourceFile =resourceClient.add(resource);
			data.setResourceId(resourceFile.getId());
			if(StringUtil.isNonEmpty(resource.getFileName()) && StringUtil.isNonEmpty(resource.getFileContent())) {
				data.setMusicUrl(resourceFile.getFileUrl());
			}else {
				ignoreProperties().add(MUSIC_URL);
			}
			if(StringUtil.isNonEmpty(resource.getPosterName()) && StringUtil.isNonEmpty(resource.getPosterContent())) {
				data.setPosterUrl(resourceFile.getPosterUrl());
			}else {
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
	public void postFetch(EOGlobalAffirmationLibarary findObject, UIGlobalAffirmationLibarary dtoObject) {
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

}
