package com.brijframework.content.forgin.model;

import java.util.List;

public class DirContent implements MediaContent {
	private String id;
	private String name;
	private String type;

	private List<MediaContent> files;

	public DirContent(String id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MediaContent> getFiles() {
		return files;
	}

	public void setFiles(List<MediaContent> files) {
		this.files = files;
	}
}
