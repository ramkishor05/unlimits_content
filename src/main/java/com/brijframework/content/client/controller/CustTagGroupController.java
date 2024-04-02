package com.brijframework.content.client.controller;
import static com.brijframework.content.constants.Constants.CUST_APP_ID;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.client.rqrs.CustTagGroupRequest;
import com.brijframework.content.client.rqrs.CustTagGroupResponse;
import com.brijframework.content.client.service.CustTagGroupService;

@RestController
@RequestMapping("/api/client/tag/group")
public class CustTagGroupController {

	@Autowired
	private CustTagGroupService custTagGroupService;
	
	@PostMapping
	public CustTagGroupResponse addTagGroup(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustTagGroupRequest custTagRequest) {
		return custTagGroupService.saveTagGroup(custAppId,custTagRequest);
	}
	
	@PutMapping
	public CustTagGroupResponse updateTagGroup(@RequestHeader(CUST_APP_ID) long custAppId,@RequestBody CustTagGroupRequest custTagRequest) {
		return custTagGroupService.saveTagGroup(custAppId,custTagRequest);
	}
	
	@GetMapping
	public List<CustTagGroupResponse> getTagGroupList(@RequestHeader(CUST_APP_ID) long custAppId) {
		return custTagGroupService.getTagGroupList(custAppId);
	}
	
	@GetMapping("/{id}")
	public CustTagGroupResponse getTagGroup(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable("id") Long id) {
		return custTagGroupService.getTagGroup(custAppId,id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteTagList(@RequestHeader(CUST_APP_ID) long custAppId, @PathVariable("id") Long id) {
		return custTagGroupService.deleteTagGroup(custAppId,id);
	}
	
	@GetMapping("/type/{typeId}")
	public List<CustTagGroupResponse> getTagGroupList(@RequestHeader(CUST_APP_ID) long custAppId,@PathVariable(TYPE_ID) String typeId) {
		return custTagGroupService.findAllByType(custAppId,typeId);
	}
}
