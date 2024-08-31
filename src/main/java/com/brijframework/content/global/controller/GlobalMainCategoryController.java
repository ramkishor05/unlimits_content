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
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.model.UIGlobalMainCategory;
import com.brijframework.content.global.service.GlobalMainCategoryService;

@RestController
@RequestMapping("api/global/main/category")
@CrossOrigin("*")
public class GlobalMainCategoryController implements CrudController<UIGlobalMainCategory, EOGlobalMainCategory, Long> {

	@Autowired
	private GlobalMainCategoryService globalMainCategoryService;
	
	@Override
	public CrudService<UIGlobalMainCategory, EOGlobalMainCategory, Long> getService() {
		return globalMainCategoryService;
	}
	
	@GetMapping("/status/{status}")
	public List<UIGlobalMainCategory> getCategoryGroupList(@PathVariable("status") RecordStatus  dataStatus) {
		return globalMainCategoryService.getMainCategoryList(dataStatus);
	}
}
