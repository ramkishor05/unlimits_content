package com.brijframework.content.constants;

import java.util.Arrays;
import java.util.List;

public enum DataStatus {
  
	ACTIVETED("ACTIVETED", Arrays.asList(1)), DACTIVETED("DACTIVETED",Arrays.asList(0)), ALL("All", Arrays.asList(0,1));
	
	String status;
	List<Integer> statusIds;

	DataStatus(String status, List<Integer> statusIds) {
		this.status=status;
		this.statusIds=statusIds;
	}

	public String getStatus() {
		return status;
	}

	public List<Integer> getStatusIds() {
		return statusIds;
	}
	
}
