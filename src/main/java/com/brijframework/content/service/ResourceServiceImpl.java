package com.brijframework.content.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.DatatypeConverter;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Value("${server.resource.location}")
	private String rootDir;

	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public Resource getResource(String url) {
		return resourceLoader.getResource(rootDir + url);
	}

	@Override
	public Resource addResource(String name, String base64String) {
		try {
			String[] strings = base64String.split(",");
			byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
			Resource resource = resourceLoader.getResource(rootDir);
			File file2 = new File(resource.getURL().toURI().getPath(), name);
			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file2))) {
				outputStream.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resourceLoader.getResource(rootDir + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
