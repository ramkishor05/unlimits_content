package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_EXAMPLE_VISUALIZE;

import java.util.Date;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_EXAMPLE_VISUALIZE, uniqueConstraints = {@UniqueConstraint(columnNames = { "TENURE_ID" , "EXAMPLE_LIBARARY_ID"})})
public class EOGlobalExampleVisualize extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name="TENURE_ID")
	@ManyToOne(optional = true)
	public EOGlobalTenure tenure;
	
	@Column(name="VISUALIZE_DATE")
	@Temporal(TemporalType.DATE)
	private Date visualizeDate;
	
	@Column(name="VISUALIZE_REQUEST")
	private String visualizeRequest;
	
	@Column(name="VISUALIZE_RESPONSE")
	private String visualizeResponse;
	
	@JoinColumn(name="EXAMPLE_LIBARARY_ID", referencedColumnName = "id")
	@ManyToOne(optional = true)
	public EOGlobalExampleLibarary exampleLibarary;

	public EOGlobalTenure getTenure() {
		return tenure;
	}

	public void setTenure(EOGlobalTenure tenure) {
		this.tenure = tenure;
	}

	public Date getVisualizeDate() {
		return visualizeDate;
	}

	public void setVisualizeDate(Date visualizeDate) {
		this.visualizeDate = visualizeDate;
	}

	public String getVisualizeRequest() {
		return visualizeRequest;
	}

	public void setVisualizeRequest(String visualizeRequest) {
		this.visualizeRequest = visualizeRequest;
	}

	public String getVisualizeResponse() {
		return visualizeResponse;
	}

	public void setVisualizeResponse(String visualizeResponse) {
		this.visualizeResponse = visualizeResponse;
	}

	public EOGlobalExampleLibarary getExampleLibarary() {
		return exampleLibarary;
	}

	public void setExampleLibarary(EOGlobalExampleLibarary exampleLibarary) {
		this.exampleLibarary = exampleLibarary;
	}

	@Override
	public String toString() {
		return "EOGlobalExampleVisualize [tenure=" + tenure + ", visualizeDate=" + visualizeDate + ", visualizeRequest="
				+ visualizeRequest + ", visualizeResponse=" + visualizeResponse + ", exampleLibarary=" + exampleLibarary
				+ "]";
	}
	
	
}
