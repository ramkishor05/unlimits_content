package com.brijframework.content.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.device.model.UIDeviceImageModel;
import com.brijframework.content.device.service.DeviceImagePixelService;
import com.brijframework.content.forgin.model.FileContent;
import com.brijframework.content.forgin.repository.PexelMediaRepository;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;

@Service
public class DeviceImagePixelServiceImpl implements DeviceImagePixelService {

	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private PexelMediaRepository pexelMediaRepository;

	@Override
	public List<UIDeviceImageModel> fetchListFromPexels(Long subCategoryId, String type, String name) {
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
		List<UIDeviceImageModel> deviceImageLibararies = new ArrayList<UIDeviceImageModel>();
		try {
			pexelMediaRepository.getAllFiles(finalSearch.toString().trim()).forEach(photo -> {
				UIDeviceImageModel deviceImageLibarary = new UIDeviceImageModel();
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
	public PageDetail<UIDeviceImageModel> fetchPageObjectFromPexels(Long subCategoryId, String type,
			Integer pageNumber, Integer count) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		if (type != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		PageDetail<UIDeviceImageModel> returnPageDetail = new PageDetail<UIDeviceImageModel>();
		List<UIDeviceImageModel> deviceImageLibararies = new ArrayList<UIDeviceImageModel>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageModel deviceImageLibarary = new UIDeviceImageModel();
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
	public PageDetail<UIDeviceImageModel> fetchPageObjectForPexels(Long subCategoryId, String type, String name,
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
		PageDetail<UIDeviceImageModel> returnPageDetail = new PageDetail<UIDeviceImageModel>();
		List<UIDeviceImageModel> deviceImageLibararies = new ArrayList<UIDeviceImageModel>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageModel deviceImageLibarary = new UIDeviceImageModel();
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
	public List<UIDeviceImageModel> fetchListFromPexels(Long subCategoryId, String name) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName());
		}
		if (name != null) {
			finalSearch.append(name);
		}
		List<UIDeviceImageModel> deviceImageLibararies = new ArrayList<UIDeviceImageModel>();
		try {
			pexelMediaRepository.getAllFiles(finalSearch.toString().trim()).forEach(photo -> {
				UIDeviceImageModel deviceImageLibarary = new UIDeviceImageModel();
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
	public List<UIDeviceImageModel> fetchPageListFromPexels(Long subCategoryId, String type, Integer pageNumber,
			Integer count) {
		StringBuffer finalSearch = new StringBuffer();
		EOGlobalSubCategory eoGlobalSubCategory = globalSubCategoryRepository.getReferenceById(subCategoryId);
		if (eoGlobalSubCategory != null) {
			finalSearch.append(eoGlobalSubCategory.getName() + " ");
		}
		if (type != null) {
			finalSearch.append(type + " ");
		}
		List<UIDeviceImageModel> deviceImageLibararies = new ArrayList<UIDeviceImageModel>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageModel deviceImageLibarary = new UIDeviceImageModel();
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
	public List<UIDeviceImageModel> fetchPageListFromPexels(Long subCategoryId, String type, String name,
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
		List<UIDeviceImageModel> deviceImageLibararies = new ArrayList<UIDeviceImageModel>();
		try {
			PageDetail<FileContent> pageDetail = pexelMediaRepository.getPageDetail(finalSearch.toString().trim(),
					pageNumber, count);
			pageDetail.getElements().forEach(photo -> {
				UIDeviceImageModel deviceImageLibarary = new UIDeviceImageModel();
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
