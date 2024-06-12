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
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.entities.EOGlobalCategoryTag;
import com.brijframework.content.global.entities.EOGlobalPrompt;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.repository.GlobalCategoryGroupRepository;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.repository.GlobalPromptRepository;
import com.brijframework.content.global.repository.GlobalTagItemRepository;
import com.brijframework.content.global.repository.GlobalTenureRepository;
import com.brijframework.content.global.service.GlobalCategoryImageService;

@Component
public class ContentListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private GlobalCategoryGroupRepository glbCategoryGroupRepository;

	@Autowired
	private GlobalCategoryItemRepository glbCategoryRepository;

	@Autowired
	private GlobalTagItemRepository glbTagItemRepository;
	
	@Autowired
	private GlobalPromptRepository glbPromptRepository;
	
	@Autowired
	private GlobalTenureRepository glbTenureRepository;
	
	@Autowired
	private GlobalCategoryImageService categoryImageService;

	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (upload) {

			try {
				
			JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
			List<EOGlobalCategoryGroup> eoGlobalCategoryGroupJson = instance.getAll(EOGlobalCategoryGroup.class);

			eoGlobalCategoryGroupJson.forEach(eoGlobalCategoryGroup -> {
				EOGlobalCategoryGroup findGlobalCategoryGroup = glbCategoryGroupRepository
						.findByIdenNo(eoGlobalCategoryGroup.getIdenNo()).orElse(eoGlobalCategoryGroup);
				BeanUtils.copyProperties(eoGlobalCategoryGroup, findGlobalCategoryGroup, "id");
				findGlobalCategoryGroup.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalCategoryGroup eoGlobalCategoryGroupSave = glbCategoryGroupRepository
						.saveAndFlush(findGlobalCategoryGroup);
				eoGlobalCategoryGroup.setId(eoGlobalCategoryGroupSave.getId());
			});

			List<EOGlobalCategoryItem> eoGlobalCategoryItemJson = instance.getAll(EOGlobalCategoryItem.class);

			eoGlobalCategoryItemJson.forEach(eoGlobalCategoryItem -> {
				EOGlobalCategoryItem findGlobalCategoryItem = glbCategoryRepository
						.findByIdenNo(eoGlobalCategoryItem.getIdenNo()).orElse(eoGlobalCategoryItem);
				BeanUtils.copyProperties(eoGlobalCategoryItem, findGlobalCategoryItem, "id");
				findGlobalCategoryItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalCategoryItem eoGlobalCategorySave = glbCategoryRepository.saveAndFlush(findGlobalCategoryItem);
				eoGlobalCategoryItem.setId(eoGlobalCategorySave.getId());
			});

			List<EOGlobalCategoryTag> eoGlobalTagItemJson = instance.getAll(EOGlobalCategoryTag.class);

			eoGlobalTagItemJson.forEach(eoGlobalTagItem -> {
				EOGlobalCategoryTag findGlobalTagItem = glbTagItemRepository.findByIdenNo(eoGlobalTagItem.getIdenNo())
						.orElse(eoGlobalTagItem);
				BeanUtils.copyProperties(eoGlobalTagItem, findGlobalTagItem, "id");
				findGlobalTagItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalCategoryTag eoGlobalTagItemSave = glbTagItemRepository.saveAndFlush(findGlobalTagItem);
				eoGlobalTagItem.setId(eoGlobalTagItemSave.getId());
			});
			
			List<EOGlobalTenure> eoGlobalTenureJson = instance.getAll(EOGlobalTenure.class);

			eoGlobalTenureJson.forEach(eoGlobalTenure -> {
				EOGlobalTenure findGlobalTenure = glbTenureRepository.findByIdenNo(eoGlobalTenure.getIdenNo())
						.orElse(eoGlobalTenure);
				BeanUtils.copyProperties(eoGlobalTenure, findGlobalTenure, "id");
				findGlobalTenure.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalTenure eoGlobalTenureSave = glbTenureRepository.saveAndFlush(findGlobalTenure);
				eoGlobalTenure.setId(eoGlobalTenureSave.getId());
			});
			
			List<EOGlobalPrompt> eoGlobalPromptJson = instance.getAll(EOGlobalPrompt.class);

			eoGlobalPromptJson.forEach(eoGlobalPrompt -> {
				EOGlobalPrompt findGlobalPrompt = glbPromptRepository.findByIdenNo(eoGlobalPrompt.getIdenNo())
						.orElse(eoGlobalPrompt);
				BeanUtils.copyProperties(eoGlobalPrompt, findGlobalPrompt, "id");
				findGlobalPrompt.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalPrompt eoGlobalPromptSave = glbPromptRepository.saveAndFlush(findGlobalPrompt);
				eoGlobalPrompt.setId(eoGlobalPromptSave.getId());
			});
			
			categoryImageService.init();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}
}