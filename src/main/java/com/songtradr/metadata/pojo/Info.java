package com.songtradr.metadata.pojo;

public class Info {
	private String year;

	private String content;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ClassPojo [year = " + year + ", content = " + content + "]";
	}
}
