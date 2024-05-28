package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_TAGE_PROMPT_MAPPING;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_TAGE_PROMPT_MAPPING ,  uniqueConstraints = { @UniqueConstraint (columnNames = {"TAG_ID", "PROMPT_ID"})} )
public class EOGlobalTagPromptMapping extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name = "TAG_ID")
	@ManyToOne
	private EOGlobalTagGroup eoGlobalTagGroup;
	
	@JoinColumn(name = "PROMPT_ID")
	@ManyToOne
	private EOGlobalPrompt eoGlobalPrompt;

	public EOGlobalTagGroup getEoGlobalTagGroup() {
		return eoGlobalTagGroup;
	}

	public void setEoGlobalTagGroup(EOGlobalTagGroup eoGlobalTagGroup) {
		this.eoGlobalTagGroup = eoGlobalTagGroup;
	}

	public EOGlobalPrompt getEoGlobalPrompt() {
		return eoGlobalPrompt;
	}

	public void setEoGlobalPrompt(EOGlobalPrompt eoGlobalPrompt) {
		this.eoGlobalPrompt = eoGlobalPrompt;
	}

}
