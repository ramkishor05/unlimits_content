package com.brijframework.content.global.entities;

import static com.brijframework.content.constants.Constants.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOGLOBAL_MINDSET_ITEM)
public class EOGlobalMindSetItem extends EOGlobalItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = RESOURCE_ID)
	private Long resourceId;

	@Column(name = MUSIC_URL)
	@Lob
	private String musicUrl;

	@Column(name = POSTER_URL)
	@Lob
	private String posterUrl;
	
	@ManyToOne()
	@JoinColumn(name = "MINDSET_LIBARARY_ID")
	private EOGlobalMindSetLibarary mindSetLibarary;
	
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

	public EOGlobalMindSetLibarary getMindSetLibarary() {
		return mindSetLibarary;
	}

	public void setMindSetLibarary(EOGlobalMindSetLibarary mindSetLibarary) {
		this.mindSetLibarary = mindSetLibarary;
	}
}
