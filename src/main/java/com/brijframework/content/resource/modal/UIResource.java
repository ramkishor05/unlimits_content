/**
 * 
 */
package com.brijframework.content.resource.modal;

/**
 * @author omnie
 */
public class UIResource {

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
		return "/resource/"+getFolderName()+"/"+getFileName();

	}


}
