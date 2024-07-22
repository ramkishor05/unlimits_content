/**
 * 
 */
package com.brijframework.content.resource.entities;

import com.brijframework.content.entities.EOEntityObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
	
	@Column(name = "FOLDER_NAME")
	@Lob
	private String folderName;

	@Column(name = "FILE_NAME")
	@Lob
	private String fileName;

	@Column(name = "FILE_CONTENT")
	@Lob
	@Transient
	private String fileContent;
	
	@Column(name = "POSTER_NAME")
	@Lob
	private String posterName;

	@Column(name = "POSTER_CONTENT")
	@Lob
	@Transient
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
}
