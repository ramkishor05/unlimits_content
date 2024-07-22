/**
 * 
 */
package com.brijframework.content.util;

import java.util.regex.Pattern;

/**
 *  @author omnie
 */
public class CommanUtil {

	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
	}
	
	public static String replaceSpecialCharsWithUnderscore(String text) {
		if(text==null) {
			return null;
		}
		return text.replaceAll("[^._a-zA-Z0-9]", "_").replace("__","_").replaceAll("__","_");
	}
}
