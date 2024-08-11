package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_TENURE;
import static com.brijframework.content.constants.Constants.NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TENURE ,  uniqueConstraints = { @UniqueConstraint (columnNames = {NAME})} )
public class EOGlobalTenure extends EOGlobalItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "YEAR")
	private Integer year;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
}
