package com.brijframework.content.global.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.mapper.GlobalTagLibararyMapper;
import com.brijframework.content.global.model.UIGlobalTagLibarary;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalTagLibararyService;

@Service
public class GlobalTagLibararyServiceImpl extends CrudServiceImpl<UIGlobalTagLibarary, EOGlobalTagLibarary, Long> implements GlobalTagLibararyService {
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private GlobalTagLibararyMapper globalTagLibararyMapper;

	@Override
	public JpaRepository<EOGlobalTagLibarary, Long> getRepository() {
		return globalTagLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalTagLibarary, UIGlobalTagLibarary> getMapper() {
		return globalTagLibararyMapper;
	}
	
	@Override
	protected List<UIGlobalTagLibarary> postFetch(List<EOGlobalTagLibarary> findObjects) {
		List<UIGlobalTagLibarary> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((uiObject1, uiObject2)->uiObject1.getSubCategoryId().compareTo(uiObject2.getSubCategoryId()));
		return uiObjects;
	}

	@Override
	public Pageable getPageRequest(int pageNumber, int count) {
		return PageRequest.of(pageNumber, count, Sort.by("subCategory.name"));
	}
	
	@Override
	public Pageable getPageRequest(int pageNumber, int count, Sort sort) {
		return PageRequest.of(pageNumber, count, Sort.by("subCategory.name").and(sort));
	}

}
