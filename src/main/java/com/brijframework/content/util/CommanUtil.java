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
	}public static int indexOfEndWithNumber(String str) {
		int n = str.length();
		int index = -1;
		for (int i = n - 1; i >= 0; i--) {
			char ch=str.charAt(i);
			if (isNumber(ch)) {
				index=i;
			} else {
				break;
			}
		}
		return index;
	}

	public static boolean isNumber(char ch) {
		return '0' <=ch  && ch <= '9';
	}
	
	public static String ignoreEndWithNumber(String str) {
		int lastIndex = indexOfEndWithNumber(str);
		if(lastIndex==-1) {
			return str;
		}
		return str.substring(0, lastIndex);
	}
}
