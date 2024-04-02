package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.rqrs.GlobalTagItemRequest;
import com.brijframework.content.global.rqrs.GlobalTagItemResponse;


public interface GlobalTagItemService {

	GlobalTagItemResponse saveTagItem(GlobalTagItemRequest GlobalTagItemRequest);

	GlobalTagItemResponse getTagItem(Long id);

	List<GlobalTagItemResponse> getTagItemList();

	List<GlobalTagItemResponse> findAllByType(String typeId);

	boolean deleteTagItem(Long id);

}
