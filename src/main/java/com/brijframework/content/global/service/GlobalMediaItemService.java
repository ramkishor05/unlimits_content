package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.rqrs.GlobalMediaItemRequest;
import com.brijframework.content.global.rqrs.GlobalMediaItemResponse;


public interface GlobalMediaItemService {

	GlobalMediaItemResponse saveMedia(GlobalMediaItemRequest GlobalMediaItemRequest);

	GlobalMediaItemResponse getMedia(Long id);

	List<GlobalMediaItemResponse> getMediaList();

	List<GlobalMediaItemResponse> findAllByType(String typeId);

	boolean deleteMedia(Long id);

}
