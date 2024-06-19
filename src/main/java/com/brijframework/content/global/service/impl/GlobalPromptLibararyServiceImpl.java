package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.mapper.GlobalPromptLibararyMapper;
import com.brijframework.content.global.model.UIGlobalPromptLibarary;
import com.brijframework.content.global.repository.GlobalPromptLibararyRepository;
import com.brijframework.content.global.service.GlobalPromptLibararyService;

@Service
public class GlobalPromptLibararyServiceImpl  extends CrudServiceImpl<UIGlobalPromptLibarary, EOGlobalPromptLibarary, Long> implements GlobalPromptLibararyService {
	
	@Autowired
	private GlobalPromptLibararyRepository globalPromptLibararyRepository;
	
	@Autowired
	private GlobalPromptLibararyMapper globalPromptLibararyMapper;
	

	@Override
	public JpaRepository<EOGlobalPromptLibarary, Long> getRepository() {
		return globalPromptLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalPromptLibarary, UIGlobalPromptLibarary> getMapper() {
		return globalPromptLibararyMapper;
	}

	@Override
	public List<UIGlobalPromptLibarary> findAllByType(String type, MultiValueMap<String, String> headers) {
		return getMapper().mapToDTO(globalPromptLibararyRepository.findAllByType(type));
	}
	
}
