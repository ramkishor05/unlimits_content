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
}
