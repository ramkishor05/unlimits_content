package com.brijframework.content.util;

import org.brijframework.util.text.StringUtil;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.entities.EOGlobalTenure;
import com.brijframework.content.resource.modal.UIResourceModel;

public class BuilderUtil {
	
	public static String buildPromptLibararyIdenNo(EOGlobalSubCategory subCategory, String promptName) {
		return "Global_Portal_PromptLibarary"+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(promptName);
	}
	
	public static String buildPromptLibararyIdenNo(EOGlobalTenure eoGlobalTenure, String promptName) {
		return "Global_Portal_PromptLibarary"+"_"+replaceContent(eoGlobalTenure.getName())+"_"+replaceContent(promptName);
	}
	
	public static String buildImageLibararyIdenNo(EOGlobalSubCategory subCategory, EOGlobalImageLibarary globalImageLibarary) {
		return "Global_Portal_ImageLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(globalImageLibarary.getName());
	}
	
	public static String buildImageIdenNo(EOGlobalSubCategory subCategory, String imageName) {
		return "Global_Portal_ImageLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(imageName);
	}

	public static String buildTagLibararyIdenNo(EOGlobalSubCategory subCategory, EOGlobalTagLibarary globalTagLibarary) {
		return "Global_Portal_TagLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(globalTagLibarary.getName());
	}
	
	public static String buildTagLibararyIdenNo(EOGlobalSubCategory subCategory, String tagName) {
		return "Global_Portal_TagLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(tagName);
	}
	
	public static String buildSubCategoryIdenNo(EOGlobalMainCategory mainCategory, EOGlobalSubCategory subCategory) {
		return "Global_Portal_SubCategory_"+replaceContent(mainCategory.getName())+"_"+replaceContent(subCategory.getName());
	}
	
	public static String buildMainCategoryIdenNo(EOGlobalMainCategory mainCategory) {
		return "Global_Portal_MainCategory_"+replaceContent(mainCategory.getName());
	}
	
	public static String replaceContent(String text){
		return text.replaceAll("[^._a-zA-Z0-9]+", "_").replace("__","_").replaceAll("__","_");
	}

	public static boolean isValidResource(UIResourceModel resource) {
		return isValidFile(resource) || isValidPoster(resource);
	}
	
	public static boolean isValidFile(UIResourceModel resource) {
		return StringUtil.isNonEmpty(resource.getFileName()) && StringUtil.isNonEmpty(resource.getFileContent());
	}
	
	public static boolean isValidPoster(UIResourceModel resource) {
		return StringUtil.isNonEmpty(resource.getPosterName()) && StringUtil.isNonEmpty(resource.getPosterContent());
	}

}
