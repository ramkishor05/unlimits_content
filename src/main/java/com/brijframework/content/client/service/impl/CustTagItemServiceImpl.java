package com.brijframework.content.client.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.entites.EOCustTagItem;
import com.brijframework.content.client.mapper.CustTagItemRequestMapper;
import com.brijframework.content.client.mapper.CustTagItemResponseMapper;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.client.repository.CustTagItemRepository;
import com.brijframework.content.client.rqrs.CustTagItemRequest;
import com.brijframework.content.client.rqrs.CustTagItemResponse;
import com.brijframework.content.client.service.CustTagItemService;

@Service
public class CustTagItemServiceImpl implements CustTagItemService {

	@Autowired
	CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	CustTagItemRepository custTagRepository;

	@Autowired
	CustTagItemRequestMapper custTagRequestMapper;

	@Autowired
	CustTagItemResponseMapper custTagResponseMapper;

	@Override
	public CustTagItemResponse saveTag(long custAppId, CustTagItemRequest custTagRequest) {
		Optional<EOCustBusinessApp> findById = custBusinessAppRepository.findById(custAppId);
		if (!findById.isPresent()) {
			return null;
		}
		return saveTag(findById.get(), custTagRequest);
	}

	@Override
	public CustTagItemResponse saveTag(EOCustBusinessApp eoCustBusinessApp, CustTagItemRequest custTagRequest) {
		EOCustTagItem eoCustTag = custTagRequestMapper.mapToDAO(custTagRequest);
		eoCustTag.setCustBusinessApp(eoCustBusinessApp);
		custTagRepository.save(eoCustTag);
		return custTagResponseMapper.mapToDTO(eoCustTag);
	}

	@Override
	public CustTagItemResponse getTag(long custAppId, long id) {
		return custTagResponseMapper.mapToDTO(custTagRepository.getReferenceById(id));
	}

	@Override
	public List<CustTagItemResponse> findAllByType(long custAppId, String typeId) {
		return custTagResponseMapper.mapToDTO(custTagRepository.findAllByType(custAppId, typeId));
	}

	@Override
	public List<CustTagItemResponse> getTagList(long custAppId) {
		return custTagResponseMapper.mapToDTO(custTagRepository.findAllByCustAppId(custAppId));
	}

	@Override
	public boolean deleteTag(long custAppId, Long id) {
		return false;
	}

}
