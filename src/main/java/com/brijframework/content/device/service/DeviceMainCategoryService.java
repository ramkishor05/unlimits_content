package com.brijframework.content.device.service;

import java.util.List;

import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.device.model.UIDeviceMainCategoryModel;
import com.brijframework.content.global.entities.EOGlobalMainCategory;

public interface DeviceMainCategoryService  extends QueryService<UIDeviceMainCategoryModel, EOGlobalMainCategory, Long>{

	List<UIDeviceMainCategoryModel> getCategoryGroupList(RecordStatus dataStatus);
}
