package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.rqrs.GlobalTagItemRequest;
import com.brijframework.content.global.rqrs.GlobalTagItemResponse;


public interface GlobalTagItemService {

	GlobalTagItemResponse saveTag(GlobalTagItemRequest GlobalTagItemRequest);

	GlobalTagItemResponse getTag(Long id);

	List<GlobalTagItemResponse> getTagList();

	List<GlobalTagItemResponse> findAllByType(String typeId);

	boolean deleteTag(Long id);

}
