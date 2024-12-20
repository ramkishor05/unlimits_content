package com.brijframework.content.device.model;

import org.unlimits.rest.model.UIModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(content = Include.NON_ABSENT)
public class UIDeviceModel extends UIModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idenNo;
	
	private String title;
	
	private String name;
	
	private String logoUrl;
	
	private String description;

	private String instructions;

	private String color;
	
	private String type;

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		if(type==null) {
			type="";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
