package com.brijframework.content.global.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.model.UIGlobalCategoryGroup;
import com.brijframework.content.global.service.GlobalCategoryGroupService;

@RestController
@RequestMapping("api/global/category/group")
@CrossOrigin("*")
public class GlobalCategoryGroupController {

	@Autowired
	GlobalCategoryGroupService globalCategoryGroupService;
	
	@PostMapping
	public UIGlobalCategoryGroup addCategoryGroup(@RequestBody UIGlobalCategoryGroup globalCategoryGroup) {
		return globalCategoryGroupService.saveCategoryGroup(globalCategoryGroup);
	}
	
	@PutMapping
	public UIGlobalCategoryGroup updateCategoryGroup(@RequestBody UIGlobalCategoryGroup globalCategoryGroup) {
		return globalCategoryGroupService.saveCategoryGroup(globalCategoryGroup);
	}
	
	@GetMapping
	public List<UIGlobalCategoryGroup> getCategoryGroupList() {
		return globalCategoryGroupService.getCategoryGroupList();
	}
	
	@GetMapping("/{id}")
	public UIGlobalCategoryGroup getCategoryList(@PathVariable("id") Long id) {
		return globalCategoryGroupService.getCategoryGroup(id);
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteCategoryList(@PathVariable("id") Long id) {
		return globalCategoryGroupService.deleteCategoryGroup(id);
	}
	
	@GetMapping("/status/{status}")
	public List<UIGlobalCategoryGroup> getCategoryGroupList(@PathVariable("status") RecordStatus  dataStatus) {
		return globalCategoryGroupService.getCategoryGroupList(dataStatus);
	}
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalCategoryGroup> getCategoryGroupList(@PathVariable("typeId") String typeId) {
		return globalCategoryGroupService.getCategoryGroup(typeId);
	}
}
