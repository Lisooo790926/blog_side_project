package com.article.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ArticleService {
	
	private ArticleDAO_interface dao;
	public ArticleService() {
//		dao = new ArticleDAO();
		dao = new ArticleJNDIDAO();
	}
	
	// 修改文章 (不會動到評分, 查看次數)
	public ArticleVO updateArticle(String article_no,String article_title,
			 String article_content,Integer article_type) {
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticle_content(article_content);
		articleVO.setArticle_no(article_no);
		articleVO.setArticle_title(article_title);
		articleVO.setArticle_type(article_type);
		
		dao.update(articleVO);
		
		return articleVO;
	}
	
	// 更新文章標題暫時不寫進Service, 不一定會用到
	
	// 更新文章狀態
	public void updateArticleStatus(String article_no, Integer article_status) {
		dao.updateArticleStatus(article_no, article_status);
	}
	
	// 更新文章分數 (當有人去評分該篇文章時, 目前是每評一筆就存一筆 --> 占資源 ((改)
	public void updateArticleRate(String article_no, Double rate) {
		dao.updateArticleRate(article_no, rate);
	}
	
	// 更新文章觀看數
	public void updateArticleSeen(String article_no) {
		dao.updateArticleSeen(article_no);
	}
	
	// 新增文章 
	public ArticleVO addArticle(String mem_no,String article_title,
			String article_content,Integer article_type) {
		ArticleVO articleVO = new ArticleVO();
		articleVO.setArticle_content(article_content);
		articleVO.setArticle_title(article_title);
		articleVO.setArticle_type(article_type);
		articleVO.setMem_no(mem_no);
		dao.insert(articleVO);
		
		return articleVO;
	}
	
	// 刪除文章
	public void delete(String article_no) {
		dao.delete(article_no);
	}
	
	// 搜尋by 類別
	public List<ArticleVO> findByType(Integer type) {
		return dao.findByType(type);
	}
	
	// 搜尋by 會員 (員工上下架皆搜)
	public List<ArticleVO> findByMemno(String mem_no) {
		return dao.findByMemno(mem_no);
	}
	// 搜尋by 會員 (only 上架)
	public List<ArticleVO> findByMemnoSeen(String mem_no) {
		return dao.findByMemnoSeen(mem_no);
	}
	
	// 搜尋by keyword
	public List<ArticleVO> findByKeyword(String keyword) {
		return dao.findByKeyword(keyword);
	}
	
	// 搜尋by keyword
	public List<ArticleVO> findByStatus(Integer article_status) {
		return dao.findByStatus(article_status);
	}
	
	// 進去看文章
	public ArticleVO getOneArticle(String article_no) {
		return dao.findByPrimaryKey(article_no);
	}
	
	// 取得需管理文章 (後台)
	public List<ArticleVO> getAll(){
		return dao.getAll();
	} 
	// 取得刪除文章 (後台)
	public List<ArticleVO> getAllDelete(){
		return dao.getAllDelete();
	}
	
	// 取得全部上架文章
	public List<ArticleVO> getAllSeen(){
		return dao.getAllSeen();
	}

	// 指定特定風格, 評分由大到小排列
	public List<ArticleVO> getGreatArticle(Integer type) {
		return dao.getGreatArticle(type);
	}
	
	// 指定特定風格, 評分由大到小排列
	public List<ArticleVO> getNthGreatArticle(Integer type) {
		return dao.getNthArticle(type);
	}
	
	public List<ArticleVO> getAll(Map<String,String[]> map){
		return dao.getCompositeQuery(map);
	}
}
