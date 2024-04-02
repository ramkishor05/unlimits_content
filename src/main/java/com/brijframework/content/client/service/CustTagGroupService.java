package com.brijframework.content.client.service;

import java.util.List;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.rqrs.CustTagGroupRequest;
import com.brijframework.content.client.rqrs.CustTagGroupResponse;

public interface CustTagGroupService {

	CustTagGroupResponse saveTagGroup(long custAppId, CustTagGroupRequest custTagRequest);

	CustTagGroupResponse saveTagGroup(EOCustBusinessApp eoCustBusinessApp, CustTagGroupRequest uiCustTag);

	CustTagGroupResponse getTagGroup(long custAppId, long id);

	List<CustTagGroupResponse> getTagGroupList(long custAppId);

	boolean deleteTagGroup(long custAppId, Long id);

	List<CustTagGroupResponse> findAllByType(long custAppId, String typeId);

}
