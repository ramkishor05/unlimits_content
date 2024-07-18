package com.brijframework.content.global.entities;

import java.util.List;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOGLOBAL_REPROGRAM_GROUP")
public class EOGlobalReProgramGroup extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOGlobalReProgramItem> reprograms;

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

	public List<EOGlobalReProgramItem> getReprograms() {
		return reprograms;
	}

	public void setReprograms(List<EOGlobalReProgramItem> reprograms) {
		this.reprograms = reprograms;
	}

}
