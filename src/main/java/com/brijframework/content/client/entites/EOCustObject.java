package com.brijframework.content.client.entites;

import static com.brijframework.content.constants.Constants.ACTIVE;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
public abstract class EOCustObject extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = ACTIVE)
	private Boolean active;
	
	@PrePersist
	public void init() {
		this.active=true;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
