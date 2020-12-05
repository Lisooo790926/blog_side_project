package com.article_rate.model;

import java.io.Serializable;

public class Article_rateVO implements Serializable {
	private String article_no;
	private String mem_no;
	private Integer article_mem_rate;
	
	public Article_rateVO() {}
	
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
	public Integer getArticle_mem_rate() {
		return article_mem_rate;
	}
	public void setArticle_mem_rate(Integer article_mem_rate) {
		this.article_mem_rate = article_mem_rate;
	}
	
	
}
