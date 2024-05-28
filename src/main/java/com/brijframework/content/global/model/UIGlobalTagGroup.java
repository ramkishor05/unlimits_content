package com.brijframework.content.global.model;

import java.io.Serializable;
import java.util.List;

public class UIGlobalTagGroup extends UIGlobalItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long subCategoryId;
	
	private List<UIGlobalPrompt> prompts;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	
	public List<UIGlobalPrompt> getPrompts() {
		return prompts;
	}

	public void setPrompts(List<UIGlobalPrompt> prompts) {
		this.prompts = prompts;
	}
	
}
