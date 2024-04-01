package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.DESCRIPTION;
import static com.brijframework.content.constants.Constants.EOGLOBAL_PROMPT;
import static com.brijframework.content.constants.Constants.NAME;
import static com.brijframework.content.constants.Constants.TYPE_ID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_PROMPT ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalPromptItem extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = NAME)
	private String name;

	@Column(name = DESCRIPTION)
	private String desc;

	@Column(name = TYPE_ID)
	private String typeId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
