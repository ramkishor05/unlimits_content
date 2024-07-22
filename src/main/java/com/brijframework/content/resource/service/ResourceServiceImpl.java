package com.brijframework.content.resource.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.resource.entities.EOResource;
import com.brijframework.content.resource.mapper.ResourceMapper;
import com.brijframework.content.resource.modal.UIResource;
import com.brijframework.content.resource.repository.ResourceRepository;
import com.brijframework.content.util.ResourceUtil;

import jakarta.xml.bind.DatatypeConverter;

@Service
public class ResourceServiceImpl extends CrudServiceImpl<UIResource, EOResource, Long> implements ResourceService {

	@Autowired
	private ResourceUtil resourceUtil;

	@Autowired
	private ResourceMapper resourceMapper;

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public JpaRepository<EOResource, Long> getRepository() {
		return resourceRepository;
	}

	@Override
	public GenericMapper<EOResource, UIResource> getMapper() {
		return resourceMapper;
	}

	@Override
	public Resource getResource(String type, String url) {
		return resourceUtil.getResource(type, url);
	}

	@Override
	public Resource getResource(String url) {
		return resourceUtil.getResource(url);
	}

	@Override
	public void postAdd(UIResource uiResource, EOResource entityResource) {
		try {
			uiResource.setId(entityResource.getId());
			if(StringUtil.isEmpty(uiResource.getFolderName())) {
				return;
			}
			Resource resource = resourceUtil.getResource();
			File resourceFile = resource.getFile();
			if(!resourceFile.exists()) {
				resourceFile.mkdirs();
			}
			File folderFile = new File(resourceFile, uiResource.getFolderName());
			if(!folderFile.exists()) {
				folderFile.mkdirs();
			}
			File parentFile = uiResource.getIncludeId()!=null && uiResource.getIncludeId()?   new File(folderFile, entityResource.getId()+""): folderFile;
			if(!parentFile.exists()) {
				parentFile.mkdirs();
			}
			if(StringUtil.isNonEmpty(uiResource.getFileContent()) && StringUtil.isNonEmpty(uiResource.getFileName())) {
				writeFile(parentFile, uiResource.getFileName(), uiResource.getFileContent());
			}
			if(StringUtil.isNonEmpty(uiResource.getPosterContent()) && StringUtil.isNonEmpty(uiResource.getPosterName())) {
				writeFile(parentFile, uiResource.getPosterName(), uiResource.getPosterContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeFile(File parentFile, String fileName, String base64Content) throws IOException, FileNotFoundException {
		String[] fileContent = base64Content.split(",");
		byte[] fileBytes = DatatypeConverter.parseBase64Binary(fileContent[1]);
		File dataFile = new File(parentFile, fileName);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dataFile))) {
			outputStream.write(fileBytes);
		}
	}

}
