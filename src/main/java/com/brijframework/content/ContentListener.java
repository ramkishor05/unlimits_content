package com.brijframework.content;

import java.io.IOException;
import java.util.List;

import org.brijframework.json.schema.factories.JsonSchemaDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalPromptLibarary;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.global.service.GlobalExampleLibararyService;
import com.brijframework.content.global.service.GlobalImageLibararyService;
import com.brijframework.content.global.service.GlobalMainCategoryService;
import com.brijframework.content.global.service.GlobalPromptLibararyService;
import com.brijframework.content.global.service.GlobalSubCategoryService;
import com.brijframework.content.global.service.GlobalTagResourceService;
import com.brijframework.content.global.service.GlobalTenureService;

@Component
public class ContentListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private GlobalMainCategoryService globalMainCategoryService;

	@Autowired
	private GlobalSubCategoryService globalSubCategoryService;

	@Autowired
	private GlobalTagResourceService globalTagResourceService;

	@Autowired
	private GlobalPromptLibararyService globalPromptLibararyService;

	@Autowired
	private GlobalTenureService globalTenureService;

	@Autowired
	private GlobalImageLibararyService globalImageLibararyService;
	
	@Autowired
	private GlobalExampleLibararyService globalExampleLibararyService;

	@Value("${spring.db.datajson.upload}")
	boolean upload;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (upload) {

			try {
				JsonSchemaDataFactory instance = JsonSchemaDataFactory.getInstance();
				List<EOGlobalMainCategory> eoGlobalCategoryGroupJson = instance.getAll(EOGlobalMainCategory.class);

				//globalMainCategoryService.init(eoGlobalCategoryGroupJson);

				List<EOGlobalSubCategory> eoGlobalCategoryItemJson = instance.getAll(EOGlobalSubCategory.class);

				//globalSubCategoryService.init(eoGlobalCategoryItemJson);

				List<EOGlobalTagLibarary> eoGlobalTagItemJson = instance.getAll(EOGlobalTagLibarary.class);

				//globalTagResourceService.init(eoGlobalTagItemJson);
				
				List<EOGlobalTenure> eoGlobalTenureJson = instance.getAll(EOGlobalTenure.class);

				//globalTenureService.init(eoGlobalTenureJson);
				
				List<EOGlobalPromptLibarary> eoGlobalPromptJson = instance.getAll(EOGlobalPromptLibarary.class);

				//globalPromptLibararyService.init(eoGlobalPromptJson);

				List<EOGlobalExampleLibarary> eoGlobalExampleLibararyJson = instance.getAll(EOGlobalExampleLibarary.class);

				//globalExampleLibararyService.init(eoGlobalExampleLibararyJson);
				
				try {
					globalImageLibararyService.init();
				} catch (IOException e) {
					e.printStackTrace();
				}

				//export_global_main_category();
				//export_global_sub_category();
				//export_global_portal_tag_libarary();
				//export_global_portal_tag_libarary();
				//copyToAll_global_portal_tag_libarary();
				//globalImageLibararyService.export();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}
	
}