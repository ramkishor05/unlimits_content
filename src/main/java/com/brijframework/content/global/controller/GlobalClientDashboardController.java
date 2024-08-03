package com.brijframework.content.global.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.global.model.UIGlobalDashboard;
import com.brijframework.content.global.service.impl.GlobalDashboardService;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author ram kishor
 */
@RestController
@RequestMapping(value = "/api/global/dashboard")
@CrossOrigin("*")
@Hidden
public class GlobalClientDashboardController {
	
	private GlobalDashboardService globalDashboardService;
	
	@GetMapping
	public UIGlobalDashboard getGlobalClientDashboard() {
		return globalDashboardService.getDashboard();
	}

}
