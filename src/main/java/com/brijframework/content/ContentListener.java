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
import com.brijframework.content.global.entities.EOGlobalTagGroup;
import com.brijframework.content.global.repository.GlobalCategoryGroupRepository;
import com.brijframework.content.global.repository.GlobalCategoryItemRepository;
import com.brijframework.content.global.repository.GlobalTagGroupRepository;

@Component
public class ContentListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private GlobalCategoryGroupRepository glbCategoryGroupRepository;
	
	@Autowired
	private GlobalCategoryItemRepository glbCategoryRepository;
	
	@Autowired
	private GlobalTagGroupRepository glbTagGroupRepository;
	
	@Value("${spring.db.datajson.upload}")
	boolean upload;
	
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    	if(upload) {
    		
    		JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
    		glbCategoryGroupRepository.deleteAll();
    		glbTagGroupRepository.deleteAll();
	    	List<EOGlobalCategoryGroup> eoGlobalCategoryGroupJson = instance.getAll(EOGlobalCategoryGroup.class);
	    	
	    	eoGlobalCategoryGroupJson.forEach(eoGlobalCategoryGroup->{
	    		EOGlobalCategoryGroup findGlobalCategoryGroup = glbCategoryGroupRepository.findByTypeId(eoGlobalCategoryGroup.getTypeId()).orElse(eoGlobalCategoryGroup);
	    		BeanUtils.copyProperties(eoGlobalCategoryGroup, findGlobalCategoryGroup, "id");
	    		findGlobalCategoryGroup.setRecordState(RecordStatus.ACTIVETED.getStatus());
	    		EOGlobalCategoryGroup eoGlobalCategoryGroupSave= glbCategoryGroupRepository.saveAndFlush(findGlobalCategoryGroup);
	    		eoGlobalCategoryGroup.setId(eoGlobalCategoryGroupSave.getId());
	    	});
	    	
	    	List<EOGlobalCategoryItem> eoGlobalCategoryJson = instance.getAll(EOGlobalCategoryItem.class);
	    	
	    	eoGlobalCategoryJson.forEach(eoGlobalCategory->{
    			eoGlobalCategory.setRecordState(RecordStatus.ACTIVETED.getStatus());
    			EOGlobalCategoryItem eoGlobalCategorySave= glbCategoryRepository.saveAndFlush(eoGlobalCategory);
	    		eoGlobalCategory.setId(eoGlobalCategorySave.getId());
	    	});
	    	
	    	List<EOGlobalTagGroup> eoGlobalTagGroupJson = instance.getAll(EOGlobalTagGroup.class);
	    	
	    	eoGlobalTagGroupJson.forEach(eoGlobalTagGroup->{
	    		EOGlobalTagGroup findGlobalTagGroup = glbTagGroupRepository.findByTypeId(eoGlobalTagGroup.getTypeId()).orElse(eoGlobalTagGroup);
	    		BeanUtils.copyProperties(eoGlobalTagGroup, findGlobalTagGroup, "id");
	    		findGlobalTagGroup.setRecordState(RecordStatus.ACTIVETED.getStatus());
	    		EOGlobalTagGroup eoGlobalTagGroupSave= glbTagGroupRepository.saveAndFlush(findGlobalTagGroup);
	    		eoGlobalTagGroup.setId(eoGlobalTagGroupSave.getId());
	    	});
    	}
    }
}