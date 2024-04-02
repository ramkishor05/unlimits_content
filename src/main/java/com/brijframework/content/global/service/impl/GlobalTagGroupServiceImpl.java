package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.mapper.GlobalTagGroupRequestMapper;
import com.brijframework.content.global.mapper.GlobalTagGroupResponseMapper;
import com.brijframework.content.global.repository.GlobalTagGroupRepository;
import com.brijframework.content.global.rqrs.GlobalTagGroupRequest;
import com.brijframework.content.global.rqrs.GlobalTagGroupResponse;
import com.brijframework.content.global.service.GlobalTagGroupService;

@Service
public class GlobalTagGroupServiceImpl implements GlobalTagGroupService {
	
	@Autowired
	private GlobalTagGroupRepository globalTagGroupRepository;
	
	@Autowired
	private GlobalTagGroupRequestMapper globalTagGroupRequestMapper;
	
	@Autowired
	private GlobalTagGroupResponseMapper globalTagGroupResponseMapper;

	@Override
	public GlobalTagGroupResponse saveTagGroup(GlobalTagGroupRequest uiGlobalTag) {
		EOGlobalTagGroup eoGlobalTag = globalTagGroupRequestMapper.mapToDAO(uiGlobalTag);
		eoGlobalTag=globalTagGroupRepository.saveAndFlush(eoGlobalTag);
		return globalTagGroupResponseMapper.mapToDTO(eoGlobalTag);
	}

	@Override
	public GlobalTagGroupResponse getTagGroup(Long id) {
	    return globalTagGroupResponseMapper.mapToDTO(globalTagGroupRepository.findById(id).orElse(null));
	}

	@Override
	public List<GlobalTagGroupResponse> getTagGroupList() {
		return globalTagGroupResponseMapper.mapToDTO(globalTagGroupRepository.findAll());
	}

	@Override
	public List<GlobalTagGroupResponse> findAllByType(String typeId) {
		return globalTagGroupResponseMapper.mapToDTO(globalTagGroupRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deleteTagGroup(Long id) {
		globalTagGroupRepository.deleteById(id);
		return true;
	}

}
