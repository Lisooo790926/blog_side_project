package com.article_rate.model;

public class Article_rateService {
	
	private Article_rateDAO_interface dao;
	public Article_rateService() {
//		dao = new Article_rateDAO();
		dao = new Article_rateJNDIDAO();
	}
	
	// 會員對文章做評分
	public void addArticleRate(String article_no,String mem_no, Integer article_mem_rate) {
		Article_rateVO article_rateVO = new Article_rateVO();
		article_rateVO.setMem_no(mem_no);
		article_rateVO.setArticle_no(article_no);
		article_rateVO.setArticle_mem_rate(article_mem_rate);
		dao.insert(article_rateVO);
	}
	
	public void updateArticleRate(String article_no,String mem_no, Integer article_mem_rate) {
		Article_rateVO article_rateVO = new Article_rateVO();
		article_rateVO.setMem_no(mem_no);
		article_rateVO.setArticle_no(article_no);
		article_rateVO.setArticle_mem_rate(article_mem_rate);
		dao.update(article_rateVO);
	}
	
	// 當會員評分完, 將評分同步更新到Article表格中
	public Integer getOneArticle_rate(String article_no, String mem_no) {
		return dao.findByPrimaryKey(article_no, mem_no);
	}
	
	public Double getArticle_rate(String article_no) {
		return dao.findByArticleno(article_no);
	}
}
