/**
 * 
 */
package com.brijframework.content.resource.modal;

import org.unlimits.rest.model.UIModel;

/**
 * @author omnie
 */
public class UIResource extends UIModel{

	private Boolean includeId=false;

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

	public Boolean getIncludeId() {
		return includeId;
	}

	public void setIncludeId(Boolean includeId) {
		this.includeId = includeId;
	}

	public String getFileUrl() {
		if(getIncludeId() !=null && getIncludeId()) {
		    return "/resource/"+getFolderName()+"/"+getId()+"/"+getFileName();
		}else {
			return "/resource/"+getFolderName()+"/"+getFileName();
		}
	}

	public String getPosterUrl() {
		if(getIncludeId() !=null && getIncludeId()) {
			return "/resource/"+getFolderName()+"/"+getId()+"/"+getPosterName();
		}else {
			return "/resource/"+getFolderName()+"/"+getPosterName();
		}
	}

	@Override
	public String toString() {
		return "UIResource [folderName=" + folderName + ", fileName=" + fileName + ", fileContent=" + fileContent
				+ ", posterName=" + posterName + ", posterContent=" + posterContent + "]";
	}

	
}
