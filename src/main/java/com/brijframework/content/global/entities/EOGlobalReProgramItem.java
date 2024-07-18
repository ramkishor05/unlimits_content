package com.brijframework.content.global.entities;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOGLOBAL_REPROGRAM_ITEM")
public class EOGlobalReProgramItem extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name ="NAME")
	private String name;
	
	@Column(name ="DESCRIPTION")
	private String description;
	
	@Column(name = "RESOURCE_ID")
	private Long resourceId;

	@Column(name ="URL")
	private String url;

	@JoinColumn(name = "GROUP_ID", nullable = false, referencedColumnName = "id")
	@ManyToOne(cascade = CascadeType.MERGE)
	private EOGlobalReProgramGroup group;

	public EOGlobalReProgramGroup getGroup() {
		return group;
	}

	public void setGroup(EOGlobalReProgramGroup group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
