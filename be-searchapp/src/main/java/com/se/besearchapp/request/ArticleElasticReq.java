package com.se.besearchapp.request;

import java.util.List;

import com.se.besearchapp.entities.Article;

public class ArticleElasticReq {
	private String Index;
	private List<Article> Articles;

	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
