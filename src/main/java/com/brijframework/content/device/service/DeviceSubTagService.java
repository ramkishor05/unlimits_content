package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceSubTag;
import com.brijframework.content.global.entities.EOGlobalTagItem;


public interface DeviceSubTagService extends QueryService<UIDeviceSubTag, EOGlobalTagItem, Long>{

	List<UIDeviceSubTag> findAllByTagId(Long tagId);

}
