package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.mapper.GlobalTagGroupMapper;
import com.brijframework.content.global.model.UIGlobalTagGroup;
import com.brijframework.content.global.repository.GlobalTagGroupRepository;
import com.brijframework.content.global.service.GlobalTagGroupService;
import com.brijframework.content.mapper.GenericMapper;
import com.brijframework.content.service.CrudServiceImpl;

@Service
public class GlobalTagGroupServiceImpl extends CrudServiceImpl<UIGlobalTagGroup, EOGlobalTagGroup, Long> implements GlobalTagGroupService {
	
	@Autowired
	private GlobalTagGroupRepository globalTagGroupRepository;
	
	@Autowired
	private GlobalTagGroupMapper globalTagGroupMapper;

	@Override
	public List<UIGlobalTagGroup> findAllByType(String typeId) {
		return globalTagGroupMapper.mapToDTO(globalTagGroupRepository.findOneByTypeId(typeId));
	}

	@Override
	public JpaRepository<EOGlobalTagGroup, Long> getRepository() {
		return globalTagGroupRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagGroup, UIGlobalTagGroup> getMapper() {
		return globalTagGroupMapper;
	}
	

}
