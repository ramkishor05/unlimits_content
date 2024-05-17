package com.brijframework.content;

import java.util.List;

import org.brijframework.json.schema.factories.JsonSchemaDataFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.entities.EOGlobalCategoryGroup;
import com.brijframework.content.global.entities.EOGlobalCategoryItem;
import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.entities.EOGlobalTagItem;
import com.brijframework.content.global.repository.GlobalCategoryGroupRepository;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.repository.GlobalMediaItemRepository;
import com.brijframework.content.global.repository.GlobalTagGroupRepository;
import com.brijframework.content.global.repository.GlobalTagItemRepository;
import com.brijframework.content.service.ResourceService;

@Component
public class ContentListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private GlobalCategoryGroupRepository glbCategoryGroupRepository;

	@Autowired
	private GlobalCategoryItemRepository glbCategoryRepository;

	@Autowired
	private GlobalTagGroupRepository glbTagGroupRepository;
	
	@Autowired
	private GlobalTagItemRepository glbTagItemRepository;

	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private GlobalMediaItemRepository globalMediaItemRepository;

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

			List<EOGlobalTagGroup> eoGlobalTagGroupJson = instance.getAll(EOGlobalTagGroup.class);

			eoGlobalTagGroupJson.forEach(eoGlobalTagGroup -> {
				EOGlobalTagGroup findGlobalTagGroup = glbTagGroupRepository.findByIdenNo(eoGlobalTagGroup.getTypeId())
						.orElse(eoGlobalTagGroup);
				BeanUtils.copyProperties(eoGlobalTagGroup, findGlobalTagGroup, "id");
				findGlobalTagGroup.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalTagGroup eoGlobalTagGroupSave = glbTagGroupRepository.saveAndFlush(findGlobalTagGroup);
				eoGlobalTagGroup.setId(eoGlobalTagGroupSave.getId());
			});

			List<EOGlobalTagItem> eoGlobalTagItemJson = instance.getAll(EOGlobalTagItem.class);

			eoGlobalTagItemJson.forEach(eoGlobalTagItem -> {
				EOGlobalTagItem findGlobalTagItem = glbTagItemRepository.findByIdenNo(eoGlobalTagItem.getIdenNo())
						.orElse(eoGlobalTagItem);
				BeanUtils.copyProperties(eoGlobalTagItem, findGlobalTagItem, "id");
				findGlobalTagItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
				EOGlobalTagItem eoGlobalTagItemSave = glbTagItemRepository.saveAndFlush(findGlobalTagItem);
				eoGlobalTagItem.setId(eoGlobalTagItemSave.getId());
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}
}