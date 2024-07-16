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
	
	private String posterName;

	private String posterContent;

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
	
	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public String getPosterContent() {
		return posterContent;
	}

	public void setPosterContent(String posterContent) {
		this.posterContent = posterContent;
	}

	public String getFileUrl() {
		return "/resource/"+getFolderName()+"/"+getFileName();
	}

	public String getPosterUrl() {
		return "/resource/"+getFolderName()+"/"+getPosterName();

	}

}
