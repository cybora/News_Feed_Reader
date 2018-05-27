package com.newsfeeder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="news_source")
public class NewsSource {
	
	@Id
	private Integer id;
	
	@NotBlank
	@Column(name="source_name")
	private String name;
	@NotBlank
	private String URL;
	@Column(name="active_flag")
	private Boolean activeFlag;
	
	public NewsSource() {
	}

	public NewsSource(String name, @NotBlank String uRL, @NotBlank Boolean activeFlag) {
		super();
		this.name = name;
		URL = uRL;
		this.activeFlag = activeFlag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
}
