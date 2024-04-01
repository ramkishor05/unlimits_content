package com.brijframework.content.client.service;

import java.util.List;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.rqrs.CustTagItemRequest;
import com.brijframework.content.client.rqrs.CustTagItemResponse;

public interface CustTagItemService {

	CustTagItemResponse saveTag(long custAppId, CustTagItemRequest custTagRequest);

	CustTagItemResponse saveTag(EOCustBusinessApp eoCustBusinessApp, CustTagItemRequest uiCustTag);

	CustTagItemResponse getTag(long custAppId, long id);

	List<CustTagItemResponse> getTagList(long custAppId);

	boolean deleteTag(long custAppId, Long id);

	List<CustTagItemResponse> findAllByType(long custAppId, String typeId);

}
