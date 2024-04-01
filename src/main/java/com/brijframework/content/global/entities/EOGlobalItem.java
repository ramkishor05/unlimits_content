package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.DESCRIPTION;
import static com.brijframework.content.constants.Constants.IDEN_NO;
import static com.brijframework.content.constants.Constants.INSTRUCTIONS;
import static com.brijframework.content.constants.Constants.LOGO_URL;
import static com.brijframework.content.constants.Constants.NAME;
import static com.brijframework.content.constants.Constants.TYPE_ID;

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
	
	@Column(name=LOGO_URL)
	@Lob
	private String logoUrl;
	
	@Column(name=DESCRIPTION)
	@Lob
	private String description;
	
	@Column(name=TYPE_ID)
	private String typeId;
	
	@Lob
	@Column(name=INSTRUCTIONS)
	private String instructions;

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoUrl() {
		return this.logoUrl;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}
