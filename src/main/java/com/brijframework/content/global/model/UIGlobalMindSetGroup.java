package com.brijframework.content.global.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIGlobalMindSetGroup extends UIModel{

	private String name;

	private String description;

	private List<UIGlobalMindSetItem> mindSets;

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

	public List<UIGlobalMindSetItem> getMindSets() {
		return mindSets;
	}

	public void setMindSets(List<UIGlobalMindSetItem> mindSets) {
		this.mindSets = mindSets;
	}
	
}
