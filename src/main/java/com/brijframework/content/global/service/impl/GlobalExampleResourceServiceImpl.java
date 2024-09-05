package com.brijframework.content.global.service.impl;

import java.util.List;

import org.brijframework.util.text.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalExampleItem;
import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalExampleResource;
import com.brijframework.content.global.repository.GlobalExampleItemRepository;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalExampleResourceService;

@Service
public class GlobalExampleResourceServiceImpl implements GlobalExampleResourceService {
	
	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;
	
	@Autowired 
	private GlobalTenureRepository  globalTenureRepository;
	
	@Autowired 
	private GlobalImageLibararyRepository  globalImageLibararyRepository;
	
	@Autowired 
	private GlobalTagLibararyRepository  globalTagLibararyRepository;
	
	@Autowired
	private GlobalExampleItemRepository globalExampleItemRepository;
	
	@Override
	public void init(List<UIGlobalExampleResource> globalExampleResources) {
		globalExampleResources.forEach(globalExampleResource -> {
			EOGlobalExampleLibarary findGlobalExampleLibarary = globalExampleLibararyRepository.findByIdenNo(globalExampleResource.getIdenNo()).orElse(new EOGlobalExampleLibarary());
			BeanUtils.copyProperties(globalExampleResource, findGlobalExampleLibarary, "id","exampleItems");
			findGlobalExampleLibarary.setSubCategory(globalSubCategoryRepository.findByName(globalExampleResource.getSubCategoryName()).orElse(null));
			findGlobalExampleLibarary.setRecordState(RecordStatus.ACTIVETED.getStatus());
			EOGlobalExampleLibarary eoGlobalExampleItemSave = globalExampleLibararyRepository
					.saveAndFlush(findGlobalExampleLibarary);
			globalExampleItemRepository.deleteByExampleLibararyId(eoGlobalExampleItemSave.getId());
			globalExampleResource.getExampleItems().forEach(globalExampleItemResource->{
				EOGlobalExampleItem findExampleItem =  new EOGlobalExampleItem();
				findExampleItem.setExampleLibarary(eoGlobalExampleItemSave);
				if(globalExampleItemResource.getYear()!=null) {
					findExampleItem.setTenure(globalTenureRepository.findOneByYear(globalExampleItemResource.getYear()));
				}
				if(StringUtil.isNonEmpty(globalExampleItemResource.getImage())) {
					List<EOGlobalImageLibarary> imageLibararies = globalImageLibararyRepository.findAllByImageUrl(globalExampleItemResource.getImage());
					if(imageLibararies !=null  && imageLibararies.size()>0) {
						findExampleItem.setImageLibarary(imageLibararies.get(0));
					}
				}
				if(StringUtil.isNonEmpty(globalExampleItemResource.getTag())) {
					List<EOGlobalTagLibarary> tagLibararies = globalTagLibararyRepository.findAllByName(globalExampleItemResource.getTag());
					if(tagLibararies !=null  && tagLibararies.size()>0) {
						findExampleItem.setTagLibarary(tagLibararies.get(0));
					}
				}
				findExampleItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				globalExampleItemRepository.saveAndFlush(findExampleItem);
			});
		});
	}

}
