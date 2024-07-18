/**
 * 
 */
package com.brijframework.content.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOGLOBAL_AFFIRMATION")
public class EOGlobalAffirmation extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name ="URL")
	private String url;
	
	@ManyToOne
	private EOGlobalMindSetLibarary mindSetLibarary;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
