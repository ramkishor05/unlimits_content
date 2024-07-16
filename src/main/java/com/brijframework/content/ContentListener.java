package com.brijframework.content;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

import org.brijframework.json.schema.factories.JsonSchemaDataFactory;
import org.brijframework.json.schema.factories.JsonSchemaFile;
import org.brijframework.json.schema.factories.JsonSchemaObject;
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
import com.brijframework.content.util.IdenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

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
							.findByMainCategoryIdAndName(eoGlobalCategoryItem.getMainCategory().getId(), eoGlobalCategoryItem.getName()).orElse(eoGlobalCategoryItem);
					BeanUtils.copyProperties(eoGlobalCategoryItem, findGlobalCategoryItem, "id");
					findGlobalCategoryItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
					EOGlobalSubCategory eoGlobalCategorySave = globalSubCategoryRepository
							.saveAndFlush(findGlobalCategoryItem);
					eoGlobalCategoryItem.setId(eoGlobalCategorySave.getId());
				});

				List<EOGlobalTagLibarary> eoGlobalTagItemJson = instance.getAll(EOGlobalTagLibarary.class);

				eoGlobalTagItemJson.forEach(eoGlobalTagItem -> {
					EOGlobalTagLibarary findGlobalTagItem = globalTagLibararyRepository
							.findByIdenNo(eoGlobalTagItem.getIdenNo()).orElse(eoGlobalTagItem);
					BeanUtils.copyProperties(eoGlobalTagItem, findGlobalTagItem, "id");
					findGlobalTagItem.setRecordState(RecordStatus.ACTIVETED.getStatus());
					EOGlobalTagLibarary eoGlobalTagItemSave = globalTagLibararyRepository
							.saveAndFlush(findGlobalTagItem);
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
					EOGlobalPromptLibarary findGlobalPrompt = globalPromptLibararyRepository
							.findByIdenNo(eoGlobalPrompt.getIdenNo()).orElse(eoGlobalPrompt);
					BeanUtils.copyProperties(eoGlobalPrompt, findGlobalPrompt, "id");
					findGlobalPrompt.setRecordState(RecordStatus.ACTIVETED.getStatus());
					EOGlobalPromptLibarary eoGlobalPromptSave = globalPromptLibararyRepository
							.saveAndFlush(findGlobalPrompt);
					eoGlobalPrompt.setId(eoGlobalPromptSave.getId());
				});

				

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try {
			globalImageLibararyService.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		export_global_main_category();
		export_global_sub_category();
		export_global_portal_tag_libarary();
		
		//copyToAll_global_portal_tag_libarary();
	}

	protected void copyToAll_global_portal_tag_libarary() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_tag_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_prompt_libarary_file_name = "/global_portal_tag_libarary";
		globalTagLibararyRepository.findAll().stream().filter(globalTagLibarary -> globalTagLibarary.getSubCategory()!=null)
		.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
		.forEach((subCategory1, globalTagLibararyList) -> {
			globalSubCategoryRepository.findAll().forEach(subCategory->{
				buildTagLibarary(dirs, global_portal_prompt_libarary_file_name, subCategory, globalTagLibararyList);
			});
		});
		
	}

	protected void export_global_main_category() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_main_category");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String name = "Global_Portal_MainCategory";
		String global_portal_Main_category_file_name = "/global_portal_main_category";
		String fileName = global_portal_Main_category_file_name + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_MainCategory");
		jsonSchemaFile.setOrderSequence(1.0);
		globalMainCategoryRepository.findAll().forEach(globalMainCategory -> {
			JsonSchemaObject jsonObject = new JsonSchemaObject();
			jsonObject.setId(IdenUtil.buildIdenNo(globalMainCategory));
			jsonObject.setName(name);
			jsonObject.setType(globalMainCategory.getClass().getName());
			jsonSchemaFile.setType(globalMainCategory.getClass().getName());
			jsonObject.getProperties().put("idenNo", IdenUtil.buildIdenNo(globalMainCategory));
			jsonObject.getProperties().put("name", globalMainCategory.getName());
			jsonObject.getProperties().put("logoUrl", globalMainCategory.getLogoUrl());
			jsonObject.getProperties().put("orderSequence", globalMainCategory.getOrderSequence());
			jsonObject.getProperties().put("color", globalMainCategory.getColor());
			jsonSchemaFile.getObjects().add(jsonObject);
		});
		ObjectMapper mapper = new ObjectMapper();
		try {
			Files.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchemaFile),
					new File(dirs, fileName), Charset.defaultCharset());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
	}

	protected void export_global_sub_category() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_sub_category");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String name = "Global_Portal_SubCategory";
		String global_portal_sub_category_file_name = "/global_portal_sub_category";
		globalSubCategoryRepository.findAll().stream()
				.collect(Collectors.groupingBy(globalSubCategory -> globalSubCategory.getMainCategory()))
				.forEach((mainCategory, globalSubCategoryList) -> {
					String fileName = global_portal_sub_category_file_name + "_" + IdenUtil.replaceContent(mainCategory.getName()) + ".json";
					JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
					jsonSchemaFile.setId("Global_Portal_SubCategory" + "_" + mainCategory.getName());
					jsonSchemaFile.setOrderSequence(mainCategory.getOrderSequence());
					globalSubCategoryList.forEach(globalSubCategory -> {
						JsonSchemaObject jsonObject = new JsonSchemaObject();
						jsonObject.setId(IdenUtil.buildIdenNo(mainCategory, globalSubCategory));
						jsonObject.setName(name);
						jsonObject.setType(globalSubCategory.getClass().getName());
						jsonSchemaFile.setType(globalSubCategory.getClass().getName());
						jsonObject.getProperties().put("idenNo", IdenUtil.buildIdenNo(mainCategory, globalSubCategory));
						jsonObject.getProperties().put("name", globalSubCategory.getName());
						jsonObject.getProperties().put("logoUrl", globalSubCategory.getLogoUrl());
						jsonObject.getProperties().put("orderSequence", globalSubCategory.getOrderSequence());
						jsonObject.getProperties().put("color", globalSubCategory.getColor());
						jsonObject.getProperties().put("mainCategory", "LK@" + IdenUtil.buildIdenNo(mainCategory));
						jsonSchemaFile.getObjects().add(jsonObject);
					});
					ObjectMapper mapper = new ObjectMapper();
					try {
						Files.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchemaFile),
								new File(dirs, fileName), Charset.defaultCharset());
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					;
				});
	}

	protected void export_global_portal_tag_libarary() {
		File dirs = new File("C:/app_runs/unlimits-resources/resource/global_portal_tag_libarary");
		if (!dirs.exists()) {
			dirs.mkdirs();
		}
		String global_portal_prompt_libarary_file_name = "/global_portal_tag_libarary";
		globalTagLibararyRepository.findAll().stream()
				.collect(Collectors.groupingBy(globalTagLibarary -> globalTagLibarary.getSubCategory()))
				.forEach((subCategory, globalTagLibararyList) -> {
					buildTagLibarary(dirs, global_portal_prompt_libarary_file_name, subCategory, globalTagLibararyList);
				});
	}

	protected void buildTagLibarary(File dirs, String global_portal_prompt_libarary_file_name,
			EOGlobalSubCategory subCategory, List<EOGlobalTagLibarary> globalTagLibararyList) {
		String fileName = global_portal_prompt_libarary_file_name + "_"
				+ subCategory.getMainCategory().getName() + "_" + IdenUtil.replaceContent(subCategory.getName()) + ".json";
		JsonSchemaFile jsonSchemaFile = new JsonSchemaFile();
		jsonSchemaFile.setId("Global_Portal_TagLibarary" + "_" + subCategory.getName());
		jsonSchemaFile.setOrderSequence(subCategory.getOrderSequence());
		globalTagLibararyList.forEach(globalTagLibarary -> {
			String idenNo=IdenUtil.buildIdenNo(subCategory,globalTagLibarary);
			JsonSchemaObject jsonObject = new JsonSchemaObject();
			jsonObject.setId(idenNo);
			jsonObject.setName("Global_Portal_TagLibarary");
			jsonObject.setType(globalTagLibarary.getClass().getName());
			jsonSchemaFile.setType(globalTagLibarary.getClass().getName());
			jsonObject.getProperties().put("idenNo", idenNo);
			jsonObject.getProperties().put("name", globalTagLibarary.getName());
			jsonObject.getProperties().put("orderSequence", globalTagLibarary.getOrderSequence());
			jsonObject.getProperties().put("color", subCategory.getColor());
			jsonObject.getProperties().put("type", globalTagLibarary.getType());
			jsonObject.getProperties().put("subCategory", "LK@" + IdenUtil.buildIdenNo(subCategory.getMainCategory(), subCategory));
			jsonSchemaFile.getObjects().add(jsonObject);
		});
		ObjectMapper mapper = new ObjectMapper();
		try {
			Files.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchemaFile),
					new File(dirs, fileName), Charset.defaultCharset());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}