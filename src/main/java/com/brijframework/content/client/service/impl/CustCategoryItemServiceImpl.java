package com.brijframework.content.client.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.entites.EOCustCategoryItem;
import com.brijframework.content.client.mapper.CustCategoryItemRequestMapper;
import com.brijframework.content.client.mapper.CustCategoryItemResponseMapper;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.client.repository.CustCategoryItemRepository;
import com.brijframework.content.client.rqrs.CustCategoryItemRequest;
import com.brijframework.content.client.rqrs.CustCategoryItemResponse;
import com.brijframework.content.client.service.CustCategoryItemService;

@Service
public class CustCategoryItemServiceImpl implements CustCategoryItemService {

	@Autowired
	CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	CustCategoryItemRepository custCategoryRepository;

	@Autowired
	CustCategoryItemRequestMapper custCategoryRequestMapper;

	@Autowired
	CustCategoryItemResponseMapper custCategoryResponseMapper;

	@Override
	public CustCategoryItemResponse saveCategory(long custAppId, CustCategoryItemRequest custCategoryRequest) {
		Optional<EOCustBusinessApp> findById = custBusinessAppRepository.findById(custAppId);
		if (!findById.isPresent()) {
			return null;
		}
		return saveCategory(findById.get(), custCategoryRequest);
	}

	@Override
	public CustCategoryItemResponse saveCategory(EOCustBusinessApp eoCustBusinessApp, CustCategoryItemRequest custCategoryRequest) {
		EOCustCategoryItem eoCustCategory = custCategoryRequestMapper.mapToDAO(custCategoryRequest);
		eoCustCategory.setCustBusinessApp(eoCustBusinessApp);
		custCategoryRepository.save(eoCustCategory);
		return custCategoryResponseMapper.mapToDTO(eoCustCategory);
	}

	@Override
	public CustCategoryItemResponse getCategory(long custAppId, long id) {
		return custCategoryResponseMapper.mapToDTO(custCategoryRepository.getReferenceById(id));
	}

	@Override
	public List<CustCategoryItemResponse> findAllByType(long custAppId, String typeId) {
		return custCategoryResponseMapper.mapToDTO(custCategoryRepository.findAllByType(custAppId, typeId));
	}

	@Override
	public List<CustCategoryItemResponse> getCategoryList(long custAppId) {
		return custCategoryResponseMapper.mapToDTO(custCategoryRepository.findAllByCustAppId(custAppId));
	}

	@Override
	public boolean deleteCategory(long custAppId, Long id) {
		return false;
	}

}
