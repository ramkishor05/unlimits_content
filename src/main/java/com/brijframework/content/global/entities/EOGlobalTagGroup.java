package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.EOGLOBAL_TAG_GROUP;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TAG_GROUP ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalTagGroup extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
