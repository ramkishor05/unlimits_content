package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.mapper.GlobalTenureMapper;
import com.brijframework.content.global.model.UIGlobalTenure;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalTenureService;

@Service
public class GlobalTenureServiceImpl extends CrudServiceImpl<UIGlobalTenure, EOGlobalTenure, Long> implements GlobalTenureService {
	
	@Autowired
	private GlobalTenureRepository globalTenureRepository;
	
	@Autowired
	private GlobalTenureMapper globalTenureMapper;

	@Override
	public JpaRepository<EOGlobalTenure, Long> getRepository() {
		return globalTenureRepository;
	}

	@Override
	public GenericMapper<EOGlobalTenure, UIGlobalTenure> getMapper() {
		return globalTenureMapper;
	}
	
}
