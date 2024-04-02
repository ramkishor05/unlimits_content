package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.EOGLOBAL_TAG_ITEM;
import static com.brijframework.content.constants.Constants.GROUP_ID;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TAG_ITEM ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalTagItem extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;
	
	@ManyToOne
	@JoinColumn(name = GROUP_ID, nullable = false)
	private EOGlobalTagGroup globalTagGroup;

	public EOGlobalTagGroup getGlobalTagGroup() {
		return globalTagGroup;
	}

	public void setGlobalTagGroup(EOGlobalTagGroup globalTagGroup) {
		this.globalTagGroup = globalTagGroup;
	}

}
