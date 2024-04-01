package com.brijframework.content.client.entites;

import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.CUST_BUSINESS_APP_ID;
import static com.brijframework.content.constants.Constants.EOCUST_CATEGORY_ITEM;
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
@Table(name = EOCUST_CATEGORY_ITEM, uniqueConstraints = {
		@UniqueConstraint(columnNames = { CUST_BUSINESS_APP_ID, GROUP_ID, NAME }) })
public class EOCustCategoryItem extends EOCustItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLOR)
	private String color;

	@JoinColumn(name = CUST_BUSINESS_APP_ID, nullable = false)
	@ManyToOne
	private EOCustBusinessApp custBusinessApp;

	@ManyToOne
	@JoinColumn(name = GROUP_ID, nullable = false)
	private EOCustCategoryGroup custCategoryGroup;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}

	public EOCustCategoryGroup getCustCategoryGroup() {
		return custCategoryGroup;
	}

	public void setCustCategoryGroup(EOCustCategoryGroup custCategoryGroup) {
		this.custCategoryGroup = custCategoryGroup;
	}

}
