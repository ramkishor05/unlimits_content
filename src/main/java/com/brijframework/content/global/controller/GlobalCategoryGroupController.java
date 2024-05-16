package com.brijframework.content.global.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;
import com.brijframework.content.global.service.GlobalCategoryGroupService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("api/global/category/group")
@CrossOrigin("*")
@Hidden
public class GlobalCategoryGroupController extends CrudController<UIGlobalCategoryGroup, EOGlobalCategoryGroup, Long> {

	@Autowired
	private GlobalCategoryGroupService globalCategoryGroupService;
	
	@Override
	public CrudService<UIGlobalCategoryGroup, EOGlobalCategoryGroup, Long> getService() {
		return globalCategoryGroupService;
	}
	
	@GetMapping("/status/{status}")
	public List<UIGlobalCategoryGroup> getCategoryGroupList(@PathVariable("status") RecordStatus  dataStatus) {
		return globalCategoryGroupService.getCategoryGroupList(dataStatus);
	}
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalCategoryGroup> getCategoryGroupList(@PathVariable("typeId") String typeId) {
		return globalCategoryGroupService.getCategoryGroup(typeId);
	}
}
