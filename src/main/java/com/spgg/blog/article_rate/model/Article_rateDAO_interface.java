package com.article_rate.model;

import java.util.*;

public interface Article_rateDAO_interface {
	// 有評分才新增或修改, 沒有所謂刪除
	void insert(Article_rateVO article_rateVO);
	void update(Article_rateVO article_rateVO);
	// 這只是來顯示該文章該名會員的評分
	Integer findByPrimaryKey(String article_no,String mem_no);
	
	// 會員評分同時, 直接對article做分數操作 用updateArticleRate(String, Integer) 不OK改
	// 改成排程器去定時更新評分!!!
	Double findByArticleno(String article_no);
	
}
