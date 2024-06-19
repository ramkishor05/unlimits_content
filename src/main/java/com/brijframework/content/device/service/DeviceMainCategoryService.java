package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.model.UIDeviceMainCategory;
import com.brijframework.content.global.entities.EOGlobalMainCategory;

public interface DeviceMainCategoryService  extends QueryService<UIDeviceMainCategory, EOGlobalMainCategory, Long>{

	List<UIDeviceMainCategory> getCategoryGroupList(RecordStatus dataStatus);
}
