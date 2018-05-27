package com.newsfeeder.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class News {
	
	@EmbeddedId
	private NewsId id;

	private String title;
	@Column(length=10000)
	private String description;
	private Date publicationDate;
	private String comments;
	private String link;
	@Column(length=300)
	private String imagePath;
	private String author;
	private Date updatedDate;
	
	@JsonInclude(value=Include.ALWAYS)  // just use this field for JSON
	@Transient    // marked as TRANSIENT in order NOT to save the image data in DB , imagePath column has the local path of the file
	private String imageBase64;

	public News() {		
	}
	
	
	public News(NewsId id, String title, String description, Date publicationDate, String comments, String link,
			String imagePath, String author, Date updatedDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.publicationDate = publicationDate;
		this.comments = comments;
		this.link = link;
		this.imagePath = imagePath;
		this.author = author;
		this.updatedDate = updatedDate;
	}



	public NewsId getId() {
		return id;
	}



	public void setId(NewsId id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Date getPublicationDate() {
		return publicationDate;
	}



	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getComments() {
		return comments;
	}



	public void setComments(String comments) {
		this.comments = comments;
	}



	public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}


	@JsonIgnore
	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public Date getUpdatedDate() {
		return updatedDate;
	}



	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	
	public String getImageBase64() {
		return imageBase64;
	}


	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}


	@Embeddable
	public static class NewsId implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Integer newsSourceId; // ID of source of news, mapping can be found in NewsSource table
	    private String GUID;	    
	    
		public NewsId() {
		}

		public NewsId(Integer newsSourceId, String gUID) {
			super();
			this.newsSourceId = newsSourceId;
			GUID = gUID;
		}

		public Integer getNewsSourceId() {
			return newsSourceId;
		}
		public void setNewsSourceId(Integer newsSourceId) {
			this.newsSourceId = newsSourceId;
		}
		public String getGUID() {
			return GUID;
		}
		public void setGUID(String gUID) {
			GUID = gUID;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((GUID == null) ? 0 : GUID.hashCode());
			result = prime * result + ((newsSourceId == null) ? 0 : newsSourceId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NewsId other = (NewsId) obj;
			if (GUID == null) {
				if (other.GUID != null)
					return false;
			} else if (!GUID.equals(other.GUID))
				return false;
			if (newsSourceId == null) {
				if (other.newsSourceId != null)
					return false;
			} else if (!newsSourceId.equals(other.newsSourceId))
				return false;
			return true;
		} 
		
	}
	
	
}
