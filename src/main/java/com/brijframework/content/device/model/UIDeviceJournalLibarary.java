package com.brijframework.content.device.model;

import java.io.Serializable;

public class UIDeviceJournalLibarary extends UIDeviceItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String journalDate;

	public String getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(String journalDate) {
		this.journalDate = journalDate;
	}
}
