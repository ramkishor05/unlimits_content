package com.brijframework.content.client.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.entites.EOCustMediaItem;
import com.brijframework.content.client.mapper.CustMediaItemRequestMapper;
import com.brijframework.content.client.mapper.CustMediaItemResponseMapper;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.client.repository.CustMediaItemRepository;
import com.brijframework.content.client.rqrs.CustMediaItemRequest;
import com.brijframework.content.client.rqrs.CustMediaItemResponse;
import com.brijframework.content.client.service.CustMediaItemService;

@Service
public class CustMediaItemServiceImpl implements CustMediaItemService {

	@Autowired
	CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	CustMediaItemRepository custMediaRepository;

	@Autowired
	CustMediaItemRequestMapper custMediaRequestMapper;

	@Autowired
	CustMediaItemResponseMapper custMediaResponseMapper;

	@Override
	public CustMediaItemResponse saveMedia(long custAppId, CustMediaItemRequest custMediaRequest) {
		Optional<EOCustBusinessApp> findById = custBusinessAppRepository.findById(custAppId);
		if (!findById.isPresent()) {
			return null;
		}
		return saveMedia(findById.get(), custMediaRequest);
	}

	@Override
	public CustMediaItemResponse saveMedia(EOCustBusinessApp eoCustBusinessApp, CustMediaItemRequest custMediaRequest) {
		EOCustMediaItem eoCustMedia = custMediaRequestMapper.mapToDAO(custMediaRequest);
		eoCustMedia.setCustBusinessApp(eoCustBusinessApp);
		custMediaRepository.save(eoCustMedia);
		return custMediaResponseMapper.mapToDTO(eoCustMedia);
	}

	@Override
	public CustMediaItemResponse getMedia(long custAppId, long id) {
		return custMediaResponseMapper.mapToDTO(custMediaRepository.getReferenceById(id));
	}

	@Override
	public List<CustMediaItemResponse> findAllByType(long custAppId, String typeId) {
		return custMediaResponseMapper.mapToDTO(custMediaRepository.findAllByType(custAppId, typeId));
	}

	@Override
	public List<CustMediaItemResponse> getMediaList(long custAppId) {
		return custMediaResponseMapper.mapToDTO(custMediaRepository.findAllByCustAppId(custAppId));
	}

	@Override
	public boolean deleteMedia(long custAppId, Long id) {
		return false;
	}

}
