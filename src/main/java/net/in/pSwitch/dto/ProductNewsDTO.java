package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

public class ProductNewsDTO {

	private String id;

	@NotNull(message = "NEWS cannot be blank")
	private String news;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

}
