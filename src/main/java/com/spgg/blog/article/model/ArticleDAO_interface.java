package com.article.model;

import java.util.List;
import java.util.Map;

public interface ArticleDAO_interface {
	// 1增 1刪 3修 6查
	
	// update the article from the member
	void update(ArticleVO articleVO);
	void updateArticleTitle(String article_no, String new_title);
	void updateArticleStatus(String article_no, Integer status);
	void updateArticleRate(String article_no, Double rate);
	void updateArticleSeen(String article_no);
	
	// add an article to forum
	void insert(ArticleVO articleVO);
	
	// members delete their own article
	void delete(String article_no);
	
	// member will search articles by each type
	List<ArticleVO> findByType(Integer type);
	List<ArticleVO> findByMemno(String mem_no);
	List<ArticleVO> findByMemnoSeen(String mem_no);
	List<ArticleVO> findByKeyword(String keyword);
	List<ArticleVO> findByStatus(Integer article_status);
	ArticleVO findByPrimaryKey(String article_no);
	
	List<ArticleVO> getAll();
	List<ArticleVO> getAllDelete();
	List<ArticleVO> getAllSeen();
	List<ArticleVO> getGreatArticle(Integer type);
	List<ArticleVO> getNthArticle(Integer type);
	
	List<ArticleVO> getCompositeQuery(Map<String, String[]> map);
	
}
