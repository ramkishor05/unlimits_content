package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.EOGLOBAL_JOURNAL_LIBARARY;
import static com.brijframework.content.constants.Constants.NAME;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name=EOGLOBAL_JOURNAL_LIBARARY ,  uniqueConstraints = { 
		@UniqueConstraint (columnNames = {NAME,"JOURNAL_DATE"})} )
public class EOGlobalJournalLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "JOURNAL_DATE")
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date journalDate;

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}
}
