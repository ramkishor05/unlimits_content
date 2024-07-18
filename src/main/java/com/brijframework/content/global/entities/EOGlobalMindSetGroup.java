package com.brijframework.content.global.entities;

import java.util.ArrayList;
import java.util.List;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOGLOBAL_MINDSET_ITEM")
public class EOGlobalMindSetGroup extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name ="NAME")
	private String name;
	
	@Column(name ="TITLE")
	private String title;
	
	@Column(name ="DESCRIPTION")
	private String description;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOGlobalMindSetItem> mindSets;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<EOGlobalMindSetItem> getMindSets() {
		if(mindSets==null) {
			mindSets=new ArrayList<EOGlobalMindSetItem>();
		}
		return mindSets;
	}

	public void setMindSets(List<EOGlobalMindSetItem> mindSets) {
		this.mindSets = mindSets;
	}
	
}
