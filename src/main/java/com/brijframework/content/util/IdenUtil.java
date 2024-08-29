package com.brijframework.content.util;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;
import com.brijframework.content.global.entities.EOGlobalMainCategory;
import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;

public class IdenUtil {
	
	public static String buildIdenNo(EOGlobalSubCategory subCategory, EOGlobalImageLibarary globalImageLibarary) {
		return "Global_Portal_ImageLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(globalImageLibarary.getName());
	}
	
	public static String buildImageIdenNo(EOGlobalSubCategory subCategory, String imageName) {
		return "Global_Portal_ImageLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(imageName);
	}

	public static String buildIdenNo(EOGlobalSubCategory subCategory, EOGlobalTagLibarary globalTagLibarary) {
		return "Global_Portal_TagLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(globalTagLibarary.getName());
	}
	
	public static String buildTagIdenNo(EOGlobalSubCategory subCategory, String tagName) {
		return "Global_Portal_TagLibarary"+"_"+replaceContent(subCategory.getMainCategory().getName())+"_"+replaceContent(subCategory.getName())+"_"+replaceContent(tagName);
	}
	
	public static String buildIdenNo(EOGlobalMainCategory mainCategory, EOGlobalSubCategory subCategory) {
		return "Global_Portal_SubCategory_"+replaceContent(mainCategory.getName())+"_"+replaceContent(subCategory.getName());
	}
	
	public static String buildIdenNo(EOGlobalMainCategory mainCategory) {
		return "Global_Portal_MainCategory_"+replaceContent(mainCategory.getName());
	}
	
	public static String replaceContent(String text){
		return text.replace(" ", "_").replaceAll("[^a-zA-Z0-9_]+", "");
	}

}
