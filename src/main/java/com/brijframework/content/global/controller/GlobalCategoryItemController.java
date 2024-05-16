package com.brijframework.content.global.controller;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.model.UIGlobalCategoryItem;
import com.brijframework.content.global.rqrs.GlobalCategoryItemResponse;
import com.brijframework.content.global.service.GlobalCategoryItemService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/category/item")
@CrossOrigin("*")
@Hidden
public class GlobalCategoryItemController extends CrudController<UIGlobalCategoryItem, EOGlobalCategoryItem, Long>{

	@Autowired
	private GlobalCategoryItemService globalCategoryItemService;
	
	@Override
	public CrudService<UIGlobalCategoryItem, EOGlobalCategoryItem, Long> getService() {
		return globalCategoryItemService;
	}
	
	@GetMapping("/type/{typeId}")
	public List<GlobalCategoryItemResponse> getCategoryList(@PathVariable(TYPE_ID) String typeId) {
		return globalCategoryItemService.findAllByType(typeId);
	}

	
}
