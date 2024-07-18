/**
 * 
 */
package com.brijframework.content.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOGLOBAL_AFFIRMATION_LIBARARY")
public class EOGlobalAffirmationLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name ="URL")
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
