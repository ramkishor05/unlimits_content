package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.rqrs.GlobalTagGroupRequest;
import com.brijframework.content.global.rqrs.GlobalTagGroupResponse;


public interface GlobalTagGroupService {

	GlobalTagGroupResponse saveTagGroup(GlobalTagGroupRequest GlobalTagGroupRequest);

	GlobalTagGroupResponse getTagGroup(Long id);

	List<GlobalTagGroupResponse> getTagGroupList();

	List<GlobalTagGroupResponse> findAllByType(String typeId);

	boolean deleteTagGroup(Long id);

}
