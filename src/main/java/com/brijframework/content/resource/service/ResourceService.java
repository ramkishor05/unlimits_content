package com.brijframework.content.resource.service;

import org.springframework.core.io.Resource;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.content.resource.entities.EOResource;
import com.brijframework.content.resource.modal.UIResource;

public interface ResourceService extends CrudService<UIResource, EOResource, Long> {

	Resource getResource(String type, String url);

}
