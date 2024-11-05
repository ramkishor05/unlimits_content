package com.brijframework.content.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.service.DeviceImagePixelService;
import com.brijframework.content.forgin.model.FileContent;
import com.brijframework.content.forgin.repository.PexelClient;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;

@Service
public class DeviceImagePixelServiceImpl implements DeviceImagePixelService {

	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceImagePixelServiceImpl.class);

	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private PexelClient pexelMediaRepository;

	@Override
	public List<UIDeviceImageLibarary> fetchListFromPexels(Long subCategoryId, String type, String name) {
		LOGGER.warn("INFO");
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		if (type != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		if (type != null) {
			finalSearch.append(name);
		}
		List<UIDeviceImageLibarary> deviceImageLibararies = new ArrayList<UIDeviceImageLibarary>();
		try {
			pexelMediaRepository.getAllFiles(finalSearch.toString().trim()).forEach(photo -> {
				UIDeviceImageLibarary deviceImageLibarary = new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setImageUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceImageLibararies;
	}

	@Override
	public PageDetail<UIDeviceImageLibarary> fetchPageObjectFromPexels(Long subCategoryId, String type,
			Integer pageNumber, Integer count) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		if (type != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		PageDetail<UIDeviceImageLibarary> returnPageDetail = new PageDetail<UIDeviceImageLibarary>();
		List<UIDeviceImageLibarary> deviceImageLibararies = new ArrayList<UIDeviceImageLibarary>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageLibarary deviceImageLibarary = new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setImageUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary);
			});
			returnPageDetail.setPageCount(pageDetail.getPageCount());
			returnPageDetail.setTotalCount(pageDetail.getTotalCount());
			returnPageDetail.setTotalPages(pageDetail.getTotalPages());
			returnPageDetail.setElements(deviceImageLibararies);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPageDetail;
	}

	@Override
	public PageDetail<UIDeviceImageLibarary> fetchPageObjectForPexels(Long subCategoryId, String type, String name,
			Integer pageNumber, Integer count) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName() + " ");
		}
		if (type != null) {
			finalSearch.append(type + " ");
		}
		if (name != null) {
			finalSearch.append(name + " ");
		}
		PageDetail<UIDeviceImageLibarary> returnPageDetail = new PageDetail<UIDeviceImageLibarary>();
		List<UIDeviceImageLibarary> deviceImageLibararies = new ArrayList<UIDeviceImageLibarary>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageLibarary deviceImageLibarary = new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setImageUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary);
			});
			returnPageDetail.setPageCount(pageDetail.getPageCount());
			returnPageDetail.setTotalCount(pageDetail.getTotalCount());
			returnPageDetail.setTotalPages(pageDetail.getTotalPages());
			returnPageDetail.setElements(deviceImageLibararies);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPageDetail;
	}

	@Override
	public List<UIDeviceImageLibarary> fetchListFromPexels(Long subCategoryId, String name) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		if (name != null) {
			finalSearch.append(name);
		}
		List<UIDeviceImageLibarary> deviceImageLibararies = new ArrayList<UIDeviceImageLibarary>();
		try {
			pexelMediaRepository.getAllFiles(finalSearch.toString().trim()).forEach(photo -> {
				UIDeviceImageLibarary deviceImageLibarary = new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setImageUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceImageLibararies;
	}

	@Override
	public List<UIDeviceImageLibarary> fetchPageListFromPexels(Long subCategoryId, String type, Integer pageNumber,
			Integer count) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName() + " ");
		}
		if (type != null) {
			finalSearch.append(type + " ");
		}
		List<UIDeviceImageLibarary> deviceImageLibararies = new ArrayList<UIDeviceImageLibarary>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageLibarary deviceImageLibarary = new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setImageUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceImageLibararies;
	}

	@Override
	public List<UIDeviceImageLibarary> fetchPageListFromPexels(Long subCategoryId, String type, String name,
			Integer pageNumber, Integer count) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName() + " ");
		}
		if (type != null) {
			finalSearch.append(type + " ");
		}
		if (name != null) {
			finalSearch.append(name + " ");
		}
		List<UIDeviceImageLibarary> deviceImageLibararies = new ArrayList<UIDeviceImageLibarary>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageLibarary deviceImageLibarary = new UIDeviceImageLibarary();
				deviceImageLibarary.setIdenNo(photo.getId());
				deviceImageLibarary.setImageUrl(photo.getUrl());
				deviceImageLibararies.add(deviceImageLibarary);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceImageLibararies;
	}

}
