package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_MINDSET_LIBARARY;
import static com.brijframework.content.constants.Constants.TYPE;
import static com.brijframework.content.constants.Constants.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_MINDSET_LIBARARY)
public class EOGlobalMindSetLibarary extends EOGlobalItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=TYPE)
	public String type;
	
	@Column(name=URL)
	public String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
