package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.model.UIGlobalSubCategory;
import com.brijframework.content.global.service.GlobalSubCategoryService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/sub/category")
@CrossOrigin("*")
@Hidden
public class GlobalSubCategoryController implements CrudController<UIGlobalSubCategory, EOGlobalSubCategory, Long>{

	@Autowired
	private GlobalSubCategoryService globalSubCategoryService;
	
	@Override
	public CrudService<UIGlobalSubCategory, EOGlobalSubCategory, Long> getService() {
		return globalSubCategoryService;
	}
	
}
