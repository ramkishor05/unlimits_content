package com.brijframework.content.global.service;

import java.util.List;

import com.brijframework.content.global.entities.EOGlobalTagLibarary;
import com.brijframework.content.global.model.UIGlobalTagLibarary;


public interface GlobalTagResourceService{
	
	void init(List<EOGlobalTagLibarary> eoGlobalTagItemJson);

	public List<UIGlobalTagLibarary>  importCsv(String csvData);

	public String  exportCsv();

	
}
