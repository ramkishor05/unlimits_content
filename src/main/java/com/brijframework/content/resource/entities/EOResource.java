/**
 * 
 */
package com.brijframework.content.resource.entities;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *  @author omnie
 */
@Entity
@Table(name = "EORESOURCE")
public class EOResource extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String folderName;

	private String fileName;

	private String fileContent;
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	/**
	 * 
	 */
	public String getFileUrl() {
		return getFolderName()+"/"+getFileName();

	}

}
