package com.brijframework.content.global.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIGlobalAffirmationGroup extends UIModel{

	private String name;

	private String description;

	private List<UIGlobalAffirmationItem> affirmations;

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

	public List<UIGlobalAffirmationItem> getAffirmations() {
		return affirmations;
	}

	public void setAffirmations(List<UIGlobalAffirmationItem> Affirmations) {
		this.affirmations = Affirmations;
	}
	
}
