package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.model.UIGlobalTagGroup;
import com.brijframework.content.global.service.GlobalTagGroupService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/tag/group")
@Hidden
public class GlobalTagGroupController extends CrudController<UIGlobalTagGroup, EOGlobalTagGroup, Long> {

	@Autowired
	private GlobalTagGroupService globalTagGroupService;

	@Override
	public CrudService<UIGlobalTagGroup, EOGlobalTagGroup, Long> getService() {
		return globalTagGroupService;
	}
}
