package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.entities.EOGlobalMediaItem;
import com.brijframework.content.global.mapper.GlobalMediaItemRequestMapper;
import com.brijframework.content.global.mapper.GlobalMediaItemResponseMapper;
import com.brijframework.content.global.repository.GlobalMediaItemRepository;
import com.brijframework.content.global.rqrs.GlobalMediaItemRequest;
import com.brijframework.content.global.rqrs.GlobalMediaItemResponse;
import com.brijframework.content.global.service.GlobalMediaItemService;

@Service
public class GlobalMediaItemServiceImpl implements GlobalMediaItemService {
	
	@Autowired
	private GlobalMediaItemRepository globalMediaRepository;
	
	@Autowired
	private GlobalMediaItemRequestMapper globalMediaRequestMapper;
	
	@Autowired
	private GlobalMediaItemResponseMapper globalMediaResponseMapper;

	@Override
	public GlobalMediaItemResponse saveMedia(GlobalMediaItemRequest uiGlobalMedia) {
		EOGlobalMediaItem eoGlobalMedia = globalMediaRequestMapper.mapToDAO(uiGlobalMedia);
		eoGlobalMedia=globalMediaRepository.saveAndFlush(eoGlobalMedia);
		return globalMediaResponseMapper.mapToDTO(eoGlobalMedia);
	}

	@Override
	public GlobalMediaItemResponse getMedia(Long id) {
	    return globalMediaResponseMapper.mapToDTO(globalMediaRepository.findById(id).orElse(null));
	}

	@Override
	public List<GlobalMediaItemResponse> getMediaList() {
		return globalMediaResponseMapper.mapToDTO(globalMediaRepository.findAll());
	}

	@Override
	public List<GlobalMediaItemResponse> findAllByType(String typeId) {
		return globalMediaResponseMapper.mapToDTO(globalMediaRepository.findOneByTypeId(typeId));
	}
	
	@Override
	public boolean deleteMedia(Long id) {
		globalMediaRepository.deleteById(id);
		return true;
	}

}
