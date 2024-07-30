package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_PROMPT_LIBARARY;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_PROMPT_LIBARARY ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalPromptLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TYPE")
	private String type;

	@JoinColumn(name = "TENURE_ID" , nullable = true)
	@OneToOne
	private EOGlobalTenure tenure;
	
	@JoinColumn(name = "SUB_CATEGORY_ID", nullable = true)
	@OneToOne
	private EOGlobalSubCategory subCategory;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EOGlobalTenure getTenure() {
		return tenure;
	}

	public void setTenure(EOGlobalTenure tenure) {
		this.tenure = tenure;
	}

	public EOGlobalSubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(EOGlobalSubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
}
