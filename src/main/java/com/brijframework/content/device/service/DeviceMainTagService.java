package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceMainTag;
import com.brijframework.content.global.entities.EOGlobalTagGroup;


public interface DeviceMainTagService extends QueryService<UIDeviceMainTag, EOGlobalTagGroup, Long>{

	/**
	 * @param subcategoryId
	 * @return
	 */
	List<UIDeviceMainTag> findAllBySubCategoryId(Long subcategoryId);

}
