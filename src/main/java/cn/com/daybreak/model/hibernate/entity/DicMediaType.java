package cn.com.daybreak.model.hibernate.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DicMediaType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1251871147260191455L;
	
	private int mediaTypeID;
	private String mediaTypeName;
	private String mediaTypeDisplayName;
	private Set<Media> medias = new HashSet<Media>();
	
	public final static String MEDIA_TYPE_NAME_AUDIO = "audio";
	public final static String MEDIA_TYPE_NAME_VEDIO = "vedio";
	public final static String MEDIA_TYPE_NAME_IMAGE = "image";
	
	public int getMediaTypeID() {
		return mediaTypeID;
	}
	
	public void setMediaTypeID(int mediaTypeID) {
		this.mediaTypeID = mediaTypeID;
	}
	
	public String getMediaTypeName() {
		return mediaTypeName;
	}
	
	public void setMediaTypeName(String mediaTypeName) {
		this.mediaTypeName = mediaTypeName;
	}
	
	public String getMediaTypeDisplayName() {
		return mediaTypeDisplayName;
	}
	
	public void setMediaTypeDisplayName(String mediaTypeDisplayName) {
		this.mediaTypeDisplayName = mediaTypeDisplayName;
	}
	
	@JsonIgnore
	public Set<Media> getMedias() {
		return medias;
	}

	public void setMedias(Set<Media> medias) {
		this.medias = medias;
	}
	
}
