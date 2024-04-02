package com.brijframework.content.global.controller;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.global.rqrs.GlobalTagGroupRequest;
import com.brijframework.content.global.rqrs.GlobalTagGroupResponse;
import com.brijframework.content.global.service.GlobalTagGroupService;


@RestController
@RequestMapping("/api/global/tag/group")
public class GlobalTagGroupController {

	@Autowired
	private GlobalTagGroupService GlobalTagGroupService;
	
	@PostMapping
	public GlobalTagGroupResponse addTagGroup(@RequestBody GlobalTagGroupRequest GlobalTagGroupRequest) {
		return GlobalTagGroupService.saveTagGroup(GlobalTagGroupRequest);
	}
	
	@PutMapping
	public GlobalTagGroupResponse updateTagGroup(@RequestBody GlobalTagGroupRequest GlobalTagGroupRequest) {
		return GlobalTagGroupService.saveTagGroup(GlobalTagGroupRequest);
	}
	
	@GetMapping
	public List<GlobalTagGroupResponse> getTagGroupList() {
		return GlobalTagGroupService.getTagGroupList();
	}
	
	@GetMapping("/{id}")
	public GlobalTagGroupResponse getTagGroupList(@PathVariable("id") Long id) {
		return GlobalTagGroupService.getTagGroup(id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteTagGroupList(@PathVariable("id") Long id) {
		return GlobalTagGroupService.deleteTagGroup(id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<GlobalTagGroupResponse> getTagGroupList(@PathVariable(TYPE_ID) String typeId) {
		return GlobalTagGroupService.findAllByType(typeId);
	}
}
