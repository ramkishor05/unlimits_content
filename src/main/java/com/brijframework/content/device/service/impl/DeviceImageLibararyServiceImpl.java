package com.brijframework.content.device.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.QueryServiceImpl;

import com.brijframework.content.device.mapper.DeviceImageLibararyMapper;
import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.service.DeviceImageLibararyService;
import com.brijframework.content.forgin.PexelMediaRepository;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;

@Service
public class DeviceImageLibararyServiceImpl extends QueryServiceImpl<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> implements DeviceImageLibararyService {
	
	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private DeviceImageLibararyMapper deviceImageLibararyMapper;
	
	@Autowired
	private PexelMediaRepository pexelMediaRepository;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOGlobalImageLibarary, Long> getRepository() {
		return globalImageLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalImageLibarary, UIDeviceImageLibarary> getMapper() {
		return deviceImageLibararyMapper;
	}
	
	@Override
	public List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId, String name) {
		if(StringUtils.isEmpty(name)) {
			List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId);
			if(eoGlobalImageLibararies.isEmpty()) {
				return  findByPexels(subCategoryId,tagLibararyId, "");
			}
			return postFetch(eoGlobalImageLibararies);
		} else {
			List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId, name);
			if(eoGlobalImageLibararies.isEmpty()) {
				return findByPexels(subCategoryId,tagLibararyId, name);
			}
			return postFetch(eoGlobalImageLibararies);
		}
	}
	
	private List<UIDeviceImageLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, String name) {
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName();
		}
		List<UIDeviceImageLibarary>deviceImageLibararies=new ArrayList<UIDeviceImageLibarary>();
		try {
			pexelMediaRepository.getAllFiles(name).forEach(photo->{ 
				UIDeviceImageLibarary deviceImageLibarary=new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary); 
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return deviceImageLibararies;
	}

	@Override
	public List<UIDeviceImageLibarary> search(Long subCategoryId, Long tagLibararyId) {
		List<EOGlobalImageLibarary> eoGlobalImageLibararies = globalImageLibararyRepository.filter(subCategoryId, tagLibararyId);
		return postFetch(eoGlobalImageLibararies);
	}
	
	public void postFetch(EOGlobalImageLibarary findObject, UIDeviceImageLibarary dtoObject) {
		if(StringUtils.isEmpty(dtoObject.getIdenNo())) {
			dtoObject.setIdenNo(findObject.getId()+"");
		}
		if(StringUtils.isNotEmpty(dtoObject.getUrl())) {
			dtoObject.setUrl(dtoObject.getUrl().startsWith("/")? serverUrl+""+dtoObject.getUrl() :  serverUrl+"/"+dtoObject.getUrl());
		}
	}

	@Override
	public List<UIDeviceImageLibarary> postFetch(List<EOGlobalImageLibarary> findObjects) {
		List<UIDeviceImageLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op1.getOrderSequence().compareTo(op2.getOrderSequence()));
		return uiObjects;
	}

	@Override
	public PageDetail search(@RequestHeader(required =false) MultiValueMap<String,String> headers,Long subCategoryId, Long tagLibararyId, int pageNumber, int count) {
		Map<String, String> filters=new HashMap<String, String>();
		Sort by = Sort.by("name");
		PageDetail fetchPageObject = fetchPageObject(headers,pageNumber, count, by, filters);
		if(fetchPageObject!=null && fetchPageObject.getElements().size()>0) {
			return fetchPageObject;
		}
		return findByPexels(subCategoryId, tagLibararyId, pageNumber, count);
	}

	@Override
	public PageDetail search(@RequestHeader(required =false) MultiValueMap<String,String> headers,Long subCategoryId, Long tagLibararyId, String name, int pageNumber, int count) {
		Map<String, String> filters=new HashMap<String, String>();
		filters.put("name", name);
		Sort by = Sort.by("name");
		PageDetail fetchPageObject = fetchPageObject(headers,pageNumber, count, by, filters);
		if(fetchPageObject!=null && fetchPageObject.getElements().size()>0) {
			return fetchPageObject;
		}
		return findByPexels(subCategoryId, tagLibararyId, name, pageNumber, count);
	}
	
	private PageDetail findByPexels(Long subCategoryId, Long tagLibararyId, int pageNumber, int count) {
		String name = "";
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName();
		}
		try {
			return pexelMediaRepository.getPageDetail(name, pageNumber, count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private PageDetail findByPexels(Long subCategoryId, Long tagLibararyId, String name, int pageNumber, int count) {
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName()+" "+name;
		}
		try {
			return pexelMediaRepository.getPageDetail(name, pageNumber, count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
