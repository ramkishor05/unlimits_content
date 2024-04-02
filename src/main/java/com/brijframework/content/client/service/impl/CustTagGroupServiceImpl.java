package com.brijframework.content.client.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.entites.EOCustTagGroup;
import com.brijframework.content.client.mapper.CustTagGroupRequestMapper;
import com.brijframework.content.client.mapper.CustTagGroupResponseMapper;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.client.repository.CustTagGroupRepository;
import com.brijframework.content.client.rqrs.CustTagGroupRequest;
import com.brijframework.content.client.rqrs.CustTagGroupResponse;
import com.brijframework.content.client.service.CustTagGroupService;

@Service
public class CustTagGroupServiceImpl implements CustTagGroupService {

	@Autowired
	CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	CustTagGroupRepository custTagRepository;

	@Autowired
	CustTagGroupRequestMapper custTagRequestMapper;

	@Autowired
	CustTagGroupResponseMapper custTagResponseMapper;

	@Override
	public CustTagGroupResponse saveTagGroup(long custAppId, CustTagGroupRequest custTagRequest) {
		Optional<EOCustBusinessApp> findById = custBusinessAppRepository.findById(custAppId);
		if (!findById.isPresent()) {
			return null;
		}
		return saveTagGroup(findById.get(), custTagRequest);
	}

	@Override
	public CustTagGroupResponse saveTagGroup(EOCustBusinessApp eoCustBusinessApp, CustTagGroupRequest custTagRequest) {
		EOCustTagGroup eoCustTag = custTagRequestMapper.mapToDAO(custTagRequest);
		eoCustTag.setCustBusinessApp(eoCustBusinessApp);
		custTagRepository.save(eoCustTag);
		return custTagResponseMapper.mapToDTO(eoCustTag);
	}

	@Override
	public CustTagGroupResponse getTagGroup(long custAppId, long id) {
		return custTagResponseMapper.mapToDTO(custTagRepository.getReferenceById(id));
	}

	@Override
	public List<CustTagGroupResponse> findAllByType(long custAppId, String typeId) {
		return custTagResponseMapper.mapToDTO(custTagRepository.findAllByType(custAppId, typeId));
	}

	@Override
	public List<CustTagGroupResponse> getTagGroupList(long custAppId) {
		return custTagResponseMapper.mapToDTO(custTagRepository.findAllByCustAppId(custAppId));
	}

	@Override
	public boolean deleteTagGroup(long custAppId, Long id) {
		return false;
	}

}
