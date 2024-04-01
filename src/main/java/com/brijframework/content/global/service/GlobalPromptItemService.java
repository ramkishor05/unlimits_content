package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.rqrs.GlobalPromptItemRequest;
import com.brijframework.content.global.rqrs.GlobalPromptItemResponse;


public interface GlobalPromptItemService {

	GlobalPromptItemResponse savePrompt(GlobalPromptItemRequest GlobalPromptItemRequest);

	GlobalPromptItemResponse getPrompt(Long id);

	List<GlobalPromptItemResponse> getPromptList();

	List<GlobalPromptItemResponse> findAllByType(String typeId);

	boolean deletePrompt(Long id);

}
