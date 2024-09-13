package com.brijframework.content.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.brijframework.content.constants.RecordStatus;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
public abstract class EOEntityObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CREATED_UID")
	private String createdUid;
	
	@Column(name = "CREATED_AT")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "UPDATED_UID")
	private String updatedUid;

	@Column(name = "UPDATED_AT")
	@UpdateTimestamp
	private Date updatedAt;
	
	@Column(name = "RECORD_STATUS")
	private String recordState;
	
	@Column(name = "ORDER_SEQUENCE")
	private Double orderSequence;
	
	@PrePersist
	public void init() {
		this.recordState=RecordStatus.ACTIVETED.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedUid() {
		return createdUid;
	}

	public void setCreatedUid(String createdUid) {
		this.createdUid = createdUid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedUid() {
		return updatedUid;
	}

	public void setUpdatedUid(String updatedUid) {
		this.updatedUid = updatedUid;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getRecordState() {
		return recordState;
	}

	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}

	public Double getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(Double orderSequence) {
		this.orderSequence = orderSequence;
	}

	@Override
	public int hashCode() {
		return id==null ?0 :id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EOEntityObject other = (EOEntityObject) obj;
		return id.equals(other.id);
	}
	
}
