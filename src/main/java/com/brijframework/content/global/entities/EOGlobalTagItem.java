package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_TAG;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TAG ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalTagItem extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
