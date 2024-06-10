/**
 * 
 */
package com.brijframework.content.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 *  @author omnie
 */
@Component
public class ResourceUtil {


	@Value("${server.resource.location}")
	private String rootDir;

	@Autowired
	private ResourceLoader resourceLoader;
	
	public Resource getResource() {
		return resourceLoader.getResource(rootDir);
	}
	
	public Resource getResource(String url) {
		return resourceLoader.getResource(rootDir+"/" + url);
	}
	
	public Resource getResource(String type, String url) {
		return resourceLoader.getResource(rootDir+"/"+type+"/" + url);
	}
	
}
