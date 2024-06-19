package com.brijframework.content;

import java.util.List;

import org.brijframework.json.schema.factories.JsonSchemaDataFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.repository.GlobalMainCategoryRepository;
import com.brijframework.content.global.repository.GlobalPromptLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalImageLibararyService;

@Component
public class ContentListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private GlobalMainCategoryRepository globalMainCategoryRepository;

	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;

	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private GlobalPromptLibararyRepository globalPromptLibararyRepository;
	
	@Autowired
	private GlobalTenureRepository globalTenureRepository;
	
	@Autowired
	private GlobalImageLibararyService globalImageLibararyService;

	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (upload) {

			try {
				
			JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
			List<EOGlobalMainCategory> eoGlobalCategoryGroupJson = instance.getAll(EOGlobalMainCategory.class);

			eoGlobalCategoryGroupJson.forEach(eoGlobalCategoryGroup -> {
				EOGlobalMainCategory findGlobalCategoryGroup = globalMainCategoryRepository
						.findByIdenNo(eoGlobalCategoryGroup.getIdenNo()).orElse(eoGlobalCategoryGroup);
				BeanUtils.copyProperties(eoGlobalCategoryGroup, findGlobalCategoryGroup, "id");
				findGlobalCategoryGroup.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalMainCategory eoGlobalCategoryGroupSave = globalMainCategoryRepository
						.saveAndFlush(findGlobalCategoryGroup);
				eoGlobalCategoryGroup.setId(eoGlobalCategoryGroupSave.getId());
			});

			List<EOGlobalSubCategory> eoGlobalCategoryItemJson = instance.getAll(EOGlobalSubCategory.class);

			eoGlobalCategoryItemJson.forEach(eoGlobalCategoryItem -> {
				EOGlobalSubCategory findGlobalCategoryItem = globalSubCategoryRepository
						.findByIdenNo(eoGlobalCategoryItem.getIdenNo()).orElse(eoGlobalCategoryItem);
				BeanUtils.copyProperties(eoGlobalCategoryItem, findGlobalCategoryItem, "id");
				findGlobalCategoryItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalSubCategory eoGlobalCategorySave = globalSubCategoryRepository.saveAndFlush(findGlobalCategoryItem);
				eoGlobalCategoryItem.setId(eoGlobalCategorySave.getId());
			});

			List<EOGlobalTagLibarary> eoGlobalTagItemJson = instance.getAll(EOGlobalTagLibarary.class);

			eoGlobalTagItemJson.forEach(eoGlobalTagItem -> {
				EOGlobalTagLibarary findGlobalTagItem = globalTagLibararyRepository.findByIdenNo(eoGlobalTagItem.getIdenNo())
						.orElse(eoGlobalTagItem);
				BeanUtils.copyProperties(eoGlobalTagItem, findGlobalTagItem, "id");
				findGlobalTagItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalTagLibarary eoGlobalTagItemSave = globalTagLibararyRepository.saveAndFlush(findGlobalTagItem);
				eoGlobalTagItem.setId(eoGlobalTagItemSave.getId());
			});
			
			List<EOGlobalTenure> eoGlobalTenureJson = instance.getAll(EOGlobalTenure.class);

			eoGlobalTenureJson.forEach(eoGlobalTenure -> {
				EOGlobalTenure findGlobalTenure = globalTenureRepository.findByIdenNo(eoGlobalTenure.getIdenNo())
						.orElse(eoGlobalTenure);
				BeanUtils.copyProperties(eoGlobalTenure, findGlobalTenure, "id");
				findGlobalTenure.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalTenure eoGlobalTenureSave = globalTenureRepository.saveAndFlush(findGlobalTenure);
				eoGlobalTenure.setId(eoGlobalTenureSave.getId());
			});
			
			List<EOGlobalPromptLibarary> eoGlobalPromptJson = instance.getAll(EOGlobalPromptLibarary.class);

			eoGlobalPromptJson.forEach(eoGlobalPrompt -> {
				EOGlobalPromptLibarary findGlobalPrompt = globalPromptLibararyRepository.findByIdenNo(eoGlobalPrompt.getIdenNo())
						.orElse(eoGlobalPrompt);
				BeanUtils.copyProperties(eoGlobalPrompt, findGlobalPrompt, "id");
				findGlobalPrompt.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalPromptLibarary eoGlobalPromptSave = globalPromptLibararyRepository.saveAndFlush(findGlobalPrompt);
				eoGlobalPrompt.setId(eoGlobalPromptSave.getId());
			});
			
			globalImageLibararyService.init();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}
}