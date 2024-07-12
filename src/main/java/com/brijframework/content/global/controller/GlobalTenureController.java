package com.brijframework.content.global.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.model.UIGlobalTenure;
import com.brijframework.content.global.service.GlobalTenureService;

import io.swagger.v3.oas.annotations.Hidden;


@RestController
@RequestMapping("/api/global/tenure")
@Hidden
public class GlobalTenureController implements CrudController<UIGlobalTenure, EOGlobalTenure, Long>{

	@Autowired
	private GlobalTenureService globalTenureService;
	
	@Override
	public CrudService<UIGlobalTenure, EOGlobalTenure, Long> getService() {
		return globalTenureService;
	}
}
