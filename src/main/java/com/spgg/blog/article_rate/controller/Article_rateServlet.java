package com.article_rate.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.article.model.ArticleService;
import com.article.model.ArticleVO;
import com.article_rate.model.Article_rateService;
import com.mem.model.MemVO;

public class Article_rateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if("update_Rate_Ajax".equals(action)) {
			// 不做會員驗證
			response.setContentType("text/html;charset=UTF8");
			PrintWriter out = response.getWriter();
			
			try {
				
				ArticleService articleSvc = new ArticleService();
				String article_no = request.getParameter("article_no");
				String mem_no = request.getParameter("mem_no");
				// 因為是選單, 所以不會有超過分數的問題
				String mem_rate = request.getParameter("article_mem_rate");
				Integer article_mem_rate = null;
				try {
					article_mem_rate = new Integer(mem_rate);
				}catch(NumberFormatException nfe) {
					System.out.println(mem_rate + " 前者轉換錯誤" + nfe.getMessage());
					return;
				}
				// 先確認有無評分過, 再決定update or insert
				Article_rateService article_rateSvc = new Article_rateService();
				Integer last_article_rate = article_rateSvc.getOneArticle_rate(article_no, mem_no);
				if(last_article_rate==0) {
					article_rateSvc.addArticleRate(article_no, mem_no, article_mem_rate);
				}else if(last_article_rate>0 && last_article_rate<=5) {
					article_rateSvc.updateArticleRate(article_no, mem_no, article_mem_rate);
				}
				Double resultScore = articleSvc.getOneArticle(article_no).getArticle_rate();
				out.print(resultScore);
				
			}catch(Exception e) {
				System.out.println("內部錯誤 in article_rate " + e.getMessage());
				out.print("內部錯誤 in article_rate " + e.getMessage());
			}
		}
		
	}
	
}
