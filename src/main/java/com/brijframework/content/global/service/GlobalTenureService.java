package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.model.UIGlobalTenure;


public interface GlobalTenureService extends CrudService<UIGlobalTenure, EOGlobalTenure, Long>{

	void init(List<EOGlobalTenure> eoGlobalTenureJson);

}
