package com.brijframework.content.client.entites;

import static com.brijframework.content.constants.Constants.CATEGORY_ITEM_ID;
import static com.brijframework.content.constants.Constants.COLOR;
import static com.brijframework.content.constants.Constants.CUST_BUSINESS_APP_ID;
import static com.brijframework.content.constants.Constants.EOCUST_TAG_GROUP;
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
@Table(name = EOCUST_TAG_GROUP, uniqueConstraints = {
		@UniqueConstraint(columnNames = { CUST_BUSINESS_APP_ID, NAME, CATEGORY_ITEM_ID }) })
public class EOCustTagGroup extends EOCustItem {
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
	@JoinColumn(name = CATEGORY_ITEM_ID, nullable = false)
	private EOCustCategoryItem custCategoryItem;

	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}

	public EOCustCategoryItem getCustCategoryItem() {
		return custCategoryItem;
	}

	public void setCustCategoryItem(EOCustCategoryItem custCategoryItem) {
		this.custCategoryItem = custCategoryItem;
	}

}
