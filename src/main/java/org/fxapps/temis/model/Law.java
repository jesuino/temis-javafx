package org.fxapps.temis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("author")
public class Law {
	
	private String code;
	private String desc;
	private String date;
	private String title;
	private String projectLawNumber;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProjectLawNumber() {
		return projectLawNumber;
	}

	public void setProjectLawNumber(String projectLawNumber) {
		this.projectLawNumber = projectLawNumber;
	}
	
	@Override
	public String toString() {
		return this.getTitle();
	}

}
