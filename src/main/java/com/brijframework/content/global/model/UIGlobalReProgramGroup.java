package com.brijframework.content.global.model;

import java.util.ArrayList;
import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIGlobalReProgramGroup extends UIModel {

	private String name;

	private String description;

	private List<UIGlobalReProgramItem> reprograms;

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

	public List<UIGlobalReProgramItem> getReprograms() {
		if(reprograms==null) {
			reprograms=new ArrayList<UIGlobalReProgramItem>();
		}
		return reprograms;
	}

	public void setReprograms(List<UIGlobalReProgramItem> reprograms) {
		this.reprograms = reprograms;
	}

}
