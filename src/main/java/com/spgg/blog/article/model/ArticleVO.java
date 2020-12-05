package com.article.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ArticleVO implements Serializable{
	private String article_no;
	private String mem_no;
	private String article_title;
	private Timestamp article_date;
	private String article_content;
	private Double article_rate;
	private Integer article_type;
	private Integer article_seen;
	private Integer article_status;
	
	
	public ArticleVO() {}
	
	public String getArticle_no() {
		return article_no;
	}
	public void setArticle_no(String article_no) {
		this.article_no = article_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public Timestamp getArticle_date() {
		return article_date;
	}
	public void setArticle_date(Timestamp article_date) {
		this.article_date = article_date;
	}
	public String getArticle_content() {
		return article_content;
	}
	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}
	public Double getArticle_rate() {
		return article_rate;
	}
	public void setArticle_rate(Double article_rate) {
		this.article_rate = article_rate;
	}
	public Integer getArticle_type() {
		return article_type;
	}
	public void setArticle_type(Integer article_type) {
		this.article_type = article_type;
	}
	public Integer getArticle_seen() {
		return article_seen;
	}
	public void setArticle_seen(Integer article_seen) {
		this.article_seen = article_seen;
	}
	public Integer getArticle_status() {
		return article_status;
	}
	public void setArticle_status(Integer article_status) {
		this.article_status = article_status;
	}
	
	
}
