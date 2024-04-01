package com.brijframework.content.client.entites;

import static com.brijframework.content.constants.Constants.APP_ID;
import static com.brijframework.content.constants.Constants.BUSINESS_ID;
import static com.brijframework.content.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.content.constants.Constants.CUST_ID;
import static com.brijframework.content.constants.Constants.EOCUST_BUSINESS_APP;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOCUST_BUSINESS_APP, uniqueConstraints = {
		@UniqueConstraint(columnNames = { APP_ID, CUST_ID, BUSINESS_ID }) })
public class EOCustBusinessApp extends EOCustObject {

	private static final long serialVersionUID = 1L;

	@Column(name = APP_ID, nullable = false)
	private long appId;

	@Column(name = CUST_ID, nullable = false)
	private long custId;

	@Column(name = BUSINESS_ID, nullable = false)
	private long businessId;

	@OneToMany(mappedBy = CUST_BUSINESS_APP)
	public Set<EOCustCategoryGroup> custCategoryGroups;

	@OneToMany(mappedBy = CUST_BUSINESS_APP)
	public Set<EOCustCategoryItem> custCategoryList;

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	public Set<EOCustCategoryGroup> getCustCategoryGroups() {
		return custCategoryGroups;
	}

	public void setCustCategoryGroups(Set<EOCustCategoryGroup> custCategoryGroups) {
		this.custCategoryGroups = custCategoryGroups;
	}

	public Set<EOCustCategoryItem> getCustCategoryList() {
		return custCategoryList;
	}

	public void setCustCategoryList(Set<EOCustCategoryItem> custCategoryList) {
		this.custCategoryList = custCategoryList;
	}

}
