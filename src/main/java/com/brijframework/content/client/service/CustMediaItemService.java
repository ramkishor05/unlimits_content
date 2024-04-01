package com.brijframework.content.client.service;

import java.util.List;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.rqrs.CustMediaItemRequest;
import com.brijframework.content.client.rqrs.CustMediaItemResponse;

public interface CustMediaItemService {

	CustMediaItemResponse saveMedia(long custAppId, CustMediaItemRequest custMediaRequest);

	CustMediaItemResponse saveMedia(EOCustBusinessApp eoCustBusinessApp, CustMediaItemRequest uiCustMedia);

	CustMediaItemResponse getMedia(long custAppId, long id);

	List<CustMediaItemResponse> getMediaList(long custAppId);

	boolean deleteMedia(long custAppId, Long id);

	List<CustMediaItemResponse> findAllByType(long custAppId, String typeId);

}
