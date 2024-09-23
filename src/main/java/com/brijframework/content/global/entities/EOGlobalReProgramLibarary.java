/**
 * 
 */
package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.POSTER_URL;
import static com.brijframework.content.constants.Constants.RESOURCE_ID;

import java.util.List;

import static com.brijframework.content.constants.Constants.MUSIC_URL;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOGLOBAL_REPROGRAM_LIBARARY")
public class EOGlobalReProgramLibarary extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name=RESOURCE_ID)
	private Long resourceId;
	
	@Column(name=MUSIC_URL)
	@Lob
	private String musicUrl;
	
	@Column(name=POSTER_URL)
	@Lob
	private String posterUrl;

	@OneToMany(mappedBy = "reProgramLibarary", cascade = CascadeType.ALL)
	private List<EOGlobalReProgramItem> reProgramItems;
	
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public List<EOGlobalReProgramItem> getReProgramItems() {
		return reProgramItems;
	}

	public void setReProgramItems(List<EOGlobalReProgramItem> reProgramItems) {
		this.reProgramItems = reProgramItems;
	}
}
