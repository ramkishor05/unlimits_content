package com.brijframework.content.device.model;

import java.io.Serializable;
import java.util.Date;

public class UIDeviceJournalLibarary extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date journalDate;

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}
}
