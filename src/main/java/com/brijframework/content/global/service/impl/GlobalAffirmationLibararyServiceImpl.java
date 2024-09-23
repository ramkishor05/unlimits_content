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

import com.brijframework.content.constants.DataStatus;
import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.forgin.repository.ResourceClient;
import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;
import com.brijframework.content.global.mapper.GlobalAffirmationLibararyMapper;
import com.brijframework.content.global.model.UIGlobalAffirmationLibarary;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;
import com.brijframework.content.global.service.GlobalAffirmationLibararyService;
import com.brijframework.content.resource.modal.UIResourceModel;
import com.brijframework.content.util.BuilderUtil;

@Service
public class GlobalAffirmationLibararyServiceImpl
		extends CrudServiceImpl<UIGlobalAffirmationLibarary, EOGlobalAffirmationLibarary, Long>
		implements GlobalAffirmationLibararyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAffirmationLibararyServiceImpl.class);

	private static final String RECORD_STATE = "recordState";

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

	@Override
	public JpaRepository<EOGlobalAffirmationLibarary, Long> getRepository() {
		return clientAffirmationLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalAffirmationLibarary, UIGlobalAffirmationLibarary> getMapper() {
		return clientAffirmationLibararyMapper;
	}

	@Override
	public void preAdd(UIGlobalAffirmationLibarary data, Map<String, List<String>> headers, Map<String, Object> filters,
			Map<String, Object> actions) {
		LOGGER.warn("pre add: {}", headers);
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIGlobalAffirmationLibarary data, EOGlobalAffirmationLibarary entity,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		saveResource(data, entity);
	}

	@Override
	public void preUpdate(UIGlobalAffirmationLibarary data, EOGlobalAffirmationLibarary find,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		if (data.getRecordState() == null) {
			data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		}
		saveResource(data, find);
	}

	private void saveResource(UIGlobalAffirmationLibarary data, EOGlobalAffirmationLibarary find) {
		UIResourceModel resource = data.getFileResource();
		ignoreProperties().clear();
		ignoreProperties().add(getPrimaryKey());
		if (resource != null && BuilderUtil.isValidResource(resource)) {
			resource.setIncludeId(true);
			resource.setId(find != null ? find.getResourceId() : null);
			resource.setFolderName(AFFIRMATION);
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
	public void postFetch(EOGlobalAffirmationLibarary findObject, UIGlobalAffirmationLibarary dtoObject,
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
		Optional<EOGlobalAffirmationLibarary> findById = getRepository().findById(id);
		if (findById.isPresent()) {
			EOGlobalAffirmationLibarary eoGlobalAffirmationLibarary = findById.get();
			eoGlobalAffirmationLibarary.setRecordState(DataStatus.DACTIVETED.getStatus());
			getRepository().save(eoGlobalAffirmationLibarary);
			return true;
		}
		return false;
	}
}
