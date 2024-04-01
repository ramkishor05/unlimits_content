package com.brijframework.content.client.service;

import java.util.List;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.rqrs.CustPromptItemRequest;
import com.brijframework.content.client.rqrs.CustPromptItemResponse;

public interface CustPromptItemService {

	CustPromptItemResponse savePrompt(long custAppId, CustPromptItemRequest custPromptRequest);

	CustPromptItemResponse savePrompt(EOCustBusinessApp eoCustBusinessApp, CustPromptItemRequest uiCustPrompt);

	CustPromptItemResponse getPrompt(long custAppId, long id);

	List<CustPromptItemResponse> getPromptList(long custAppId);

	boolean deletePrompt(long custAppId, Long id);

	List<CustPromptItemResponse> findAllByType(long custAppId, String typeId);

}
