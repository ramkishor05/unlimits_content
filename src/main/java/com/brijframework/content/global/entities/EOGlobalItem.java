package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.DESCRIPTION;
import static com.brijframework.content.constants.Constants.IDEN_NO;
import static com.brijframework.content.constants.Constants.INSTRUCTIONS;
import static com.brijframework.content.constants.Constants.LOGO_URL;
import static com.brijframework.content.constants.Constants.NAME;
import static com.brijframework.content.constants.Constants.TYPE;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EOGlobalItem extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name=IDEN_NO)
	private String idenNo;
	
	@Column(name=NAME)
	private String name;
	
	@Column(name=LOGO_URL , columnDefinition = "LONGTEXT")
	@Lob
	private String logoUrl;
		
	@Column(name=DESCRIPTION, columnDefinition = "LONGTEXT")
	@Lob
	private String description;
	
	@Lob
	@Column(name=INSTRUCTIONS, columnDefinition = "LONGTEXT")
	private String instructions;

	@Column(name=COLOR)
	private String color;
	
	@Column(name=TYPE)
	public String type;

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
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
