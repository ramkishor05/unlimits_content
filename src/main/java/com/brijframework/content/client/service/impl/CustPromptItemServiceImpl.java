package com.brijframework.content.client.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.entites.EOCustPromptItem;
import com.brijframework.content.client.mapper.CustPromptItemRequestMapper;
import com.brijframework.content.client.mapper.CustPromptItemResponseMapper;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.client.repository.CustPromptItemRepository;
import com.brijframework.content.client.rqrs.CustPromptItemRequest;
import com.brijframework.content.client.rqrs.CustPromptItemResponse;
import com.brijframework.content.client.service.CustPromptItemService;

@Service
public class CustPromptItemServiceImpl implements CustPromptItemService {

	@Autowired
	CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	CustPromptItemRepository custPromptRepository;

	@Autowired
	CustPromptItemRequestMapper custPromptRequestMapper;

	@Autowired
	CustPromptItemResponseMapper custPromptResponseMapper;

	@Override
	public CustPromptItemResponse savePrompt(long custAppId, CustPromptItemRequest custPromptRequest) {
		Optional<EOCustBusinessApp> findById = custBusinessAppRepository.findById(custAppId);
		if (!findById.isPresent()) {
			return null;
		}
		return savePrompt(findById.get(), custPromptRequest);
	}

	@Override
	public CustPromptItemResponse savePrompt(EOCustBusinessApp eoCustBusinessApp, CustPromptItemRequest custPromptRequest) {
		EOCustPromptItem eoCustPrompt = custPromptRequestMapper.mapToDAO(custPromptRequest);
		eoCustPrompt.setCustBusinessApp(eoCustBusinessApp);
		custPromptRepository.save(eoCustPrompt);
		return custPromptResponseMapper.mapToDTO(eoCustPrompt);
	}

	@Override
	public CustPromptItemResponse getPrompt(long custAppId, long id) {
		return custPromptResponseMapper.mapToDTO(custPromptRepository.getReferenceById(id));
	}

	@Override
	public List<CustPromptItemResponse> findAllByType(long custAppId, String typeId) {
		return custPromptResponseMapper.mapToDTO(custPromptRepository.findAllByType(custAppId, typeId));
	}

	@Override
	public List<CustPromptItemResponse> getPromptList(long custAppId) {
		return custPromptResponseMapper.mapToDTO(custPromptRepository.findAllByCustAppId(custAppId));
	}

	@Override
	public boolean deletePrompt(long custAppId, Long id) {
		return false;
	}

}
