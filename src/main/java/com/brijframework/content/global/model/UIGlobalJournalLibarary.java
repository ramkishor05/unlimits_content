package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.Date;

public class UIGlobalJournalLibarary extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date journalDate;

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}
}
