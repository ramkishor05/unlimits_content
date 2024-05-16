package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceMediaItem;
import com.brijframework.content.global.entities.EOGlobalMediaItem;


public interface DeviceMediaItemService extends QueryService<UIDeviceMediaItem, EOGlobalMediaItem, Long>{

	
	List<UIDeviceMediaItem> search(String name);

}
