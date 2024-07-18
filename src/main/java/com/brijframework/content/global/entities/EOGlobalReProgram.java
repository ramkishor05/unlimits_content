/**
 * 
 */
package com.brijframework.content.global.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOGLOBAL_REPROGRAM")
public class EOGlobalReProgram extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	EOGlobalMindSetLibarary mindSetLibarary;
	
}
