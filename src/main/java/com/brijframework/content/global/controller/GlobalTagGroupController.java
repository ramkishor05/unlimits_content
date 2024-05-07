package com.brijframework.content.global.controller;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brijframework.content.controller.CrudController;
import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.model.UIGlobalTagGroup;
import com.brijframework.content.global.service.GlobalTagGroupService;
import com.brijframework.content.service.CrudService;


@RestController
@RequestMapping("/api/global/tag/group")
public class GlobalTagGroupController extends CrudController<UIGlobalTagGroup, EOGlobalTagGroup, Long> {

	@Autowired
	private GlobalTagGroupService globalTagGroupService;
	
	@GetMapping("/type/{typeId}")
	public List<UIGlobalTagGroup> getTagGroupList(@PathVariable(TYPE_ID) String typeId) {
		return globalTagGroupService.findAllByType(typeId);
	}

	@Override
	public CrudService<UIGlobalTagGroup, EOGlobalTagGroup, Long> getService() {
		return globalTagGroupService;
	}
}
