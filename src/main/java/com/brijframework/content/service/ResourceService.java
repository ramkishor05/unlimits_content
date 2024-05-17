package com.brijframework.content.service;

import org.springframework.core.io.Resource;

public interface ResourceService {

	Resource getResource(String string);

	Resource addResource(String name, String base64String);

}
