package com.brijframework.content.global.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.forgin.model.FileContent;
import com.brijframework.content.forgin.repository.PexelMediaRepository;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalImageLibarary;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalImagePixelService;

@Service
public class GlobalImagePixelServiceImpl implements GlobalImagePixelService{

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;

	@Autowired
	private PexelMediaRepository pexelMediaRepository;
	
	@Override
	public List<UIGlobalImageLibarary> findByPexels(String name) {
		List<UIGlobalImageLibarary>globalImageLibararies=new ArrayList<UIGlobalImageLibarary>();
		try {
			pexelMediaRepository.getAllFiles(name).forEach(photo->{ 
				UIGlobalImageLibarary globalImageLibarary=new UIGlobalImageLibarary();
				globalImageLibarary.setIdenNo(photo.getId());
				globalImageLibarary.setImageUrl(photo.getUrl());
				globalImageLibararies.add(globalImageLibarary); 
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		return globalImageLibararies;
	}

	@Override
	public PageDetail<UIGlobalImageLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, int pageNumber, int count) {
		String name = "";
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName();
		}
		PageDetail<UIGlobalImageLibarary> returnPageDetail=new PageDetail<UIGlobalImageLibarary>();
		try {
			List<UIGlobalImageLibarary>globalImageLibararies=new ArrayList<UIGlobalImageLibarary>();
			PageDetail<FileContent> pageDetail =pexelMediaRepository.getPageDetail(name, pageNumber, count);
			pageDetail.getElements().forEach(photo->{ 
				UIGlobalImageLibarary globalImageLibarary=new UIGlobalImageLibarary();
				globalImageLibarary.setIdenNo(photo.getId());
				globalImageLibarary.setImageUrl(photo.getUrl());
				globalImageLibararies.add(globalImageLibarary); 
			});
			returnPageDetail.setElements(globalImageLibararies);
			returnPageDetail.setPageCount(pageDetail.getPageCount());
			returnPageDetail.setTotalCount(pageDetail.getTotalCount());
			returnPageDetail.setTotalPages(pageDetail.getTotalPages());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnPageDetail;
	}
	
	@Override
	public PageDetail<UIGlobalImageLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, String name, int pageNumber, int count) {
		EOGlobalTagLibarary eoGlobalTagLibarary = globalTagLibararyRepository.getReferenceById(tagLibararyId);
		if(eoGlobalTagLibarary!=null) {
			name=eoGlobalTagLibarary.getSubCategory().getName()+" "+eoGlobalTagLibarary.getName()+" "+name;
		}
		PageDetail<UIGlobalImageLibarary> returnPageDetail=new PageDetail<UIGlobalImageLibarary>();
		try {
			List<UIGlobalImageLibarary>globalImageLibararies=new ArrayList<UIGlobalImageLibarary>();
			PageDetail<FileContent> pageDetail =pexelMediaRepository.getPageDetail(name, pageNumber, count);
			pageDetail.getElements().forEach(photo->{ 
				UIGlobalImageLibarary globalImageLibarary=new UIGlobalImageLibarary();
				globalImageLibarary.setIdenNo(photo.getId());
				globalImageLibarary.setImageUrl(photo.getUrl());
				globalImageLibararies.add(globalImageLibarary); 
			});
			returnPageDetail.setElements(globalImageLibararies);
			returnPageDetail.setPageCount(pageDetail.getPageCount());
			returnPageDetail.setTotalCount(pageDetail.getTotalCount());
			returnPageDetail.setTotalPages(pageDetail.getTotalPages());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnPageDetail;
	}
}
