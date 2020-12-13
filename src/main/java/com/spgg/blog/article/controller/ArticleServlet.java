package com.article.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.article.model.ArticleService;
import com.article.model.ArticleVO;
import com.mem.model.MemVO;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

public class ArticleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		// show one
		if ("getOne_For_Display".equals(action)) {
			
			// 建立ErrorMessage
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs); // 先設再加

			try {
				
				// 上一頁資訊
				String lastArticle_no = request.getParameter("lastArticle_no");
				String lastPagePath = request.getParameter("lastPagePath");
				String whichPage = request.getParameter("whichPage");
				
				session.setAttribute("lastArticle_no", lastArticle_no); // 為了給倒回首頁或多篇文章專用
				if(lastPagePath!=null) {
					session.setAttribute("lastPagePath", lastPagePath); // 為了給倒回首頁或多篇文章專用
					if(whichPage==null) {
						whichPage="1";
					}
					session.setAttribute("whichPage", whichPage);
				}
				
				MemVO memVO = (MemVO)session.getAttribute("memVO");
				ArrayList<String> seenList = (ArrayList)session.getAttribute("seenList");
				String mem_no = null;
				
//				System.out.println("seenList " + seenList);
				if(seenList==null) {  // 代表是空的
					seenList = new ArrayList<String>();
					// 第二筆紀錄會員或非會員
					if(memVO==null) {
						seenList.add("null"); 
					}else{
						mem_no = memVO.getMem_no();  // 將會員換成現有會員
						seenList.clear();
						seenList.add(mem_no); 
					}
				}
				
				// 一定是查完有才看到
				String article_no = request.getParameter("article_no");
				ArticleService articleSvc = new ArticleService();
				// 追加觀看次數
				if(!seenList.contains(article_no)) {
					seenList.add(article_no);
					articleSvc.updateArticleSeen(article_no);
				}
				ArticleVO articleVO = articleSvc.getOneArticle(article_no);
				
				// 轉交
				String url = "/front-end/article/RlistOneArticle.jsp";
				session.setAttribute("seenList", seenList);
				session.setAttribute("location", request.getContextPath() + url);
				session.setAttribute("articleVO", articleVO);
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);
				
			} catch (Exception e) {
				// 有錯誤跳轉回首頁
				errorMsgs.add("內部錯誤 in getOneArticle " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/article/RlistAllArticle.jsp");
				failureView.forward(request, response);
			}
		}

		// composite search
		if ("getCompositeQuery_For_Display".equals(action)) {

			List<String> errorMsgs = new ArrayList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				request.setAttribute("noArticle",false); // 搜尋前
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map_For_ArticleMultiSelect");
				if (request.getParameter("whichPage") == null){
					HashMap<String, String[]> map1 = new HashMap<String, String[]>(request.getParameterMap());
					session.setAttribute("map_For_ArticleMultiSelect",map1);
					map = map1;
				} 
				
				ArticleService articleSvc = new ArticleService();
				List<ArticleVO> articleList = articleSvc.getAll(map);
				
//				System.out.println(articleList);
				
				// 搜尋文章
				List<String> list = null;
				if(articleList!=null && !articleList.isEmpty()) {  // 找到才做相應動作
					list = getListArticle_no(articleList);
					request.setAttribute("list_Aritlce_no", list);
					String article_type = request.getParameter("article_type");
					if(article_type!=null) {
						request.setAttribute("choosen_article_type", article_type);
					}
				}else {
					request.setAttribute("noArticle",true); // 如果找不到 轉false
				}
				
				RequestDispatcher successView = request.getRequestDispatcher("/front-end/article/RlistAllArticle.jsp");
				successView.forward(request, response);
				
			} catch (Exception e) {
				errorMsgs.add("內部錯誤 in getCompositeQuery" + e.getMessage());
				System.out.println("內部錯誤 in getCompositeQuery" + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/article/RlistAllArticle.jsp");
				failureView.forward(request, response);
			}
		}
		
		// ajax
		if ("getCompositeQuery_For_Ajax".equals(action)) {
			
			response.setContentType("text/html;charset=UTF8");
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonAry = new JSONArray();
			PrintWriter out = response.getWriter();
			
			try {
				
				HashMap<String, String[]> map = new HashMap<String, String[]>(request.getParameterMap());
				
				ArticleService articleSvc = new ArticleService();
				List<ArticleVO> articleList = articleSvc.getAll(map);
				
//				System.out.println(articleList);
				
				for(ArticleVO article : articleList) {
					JSONObject tempJSON = new JSONObject(article);
					jsonAry.put(tempJSON);
				}
				if(jsonAry!=null) {
					jsonObj.put("result", jsonAry);
				}
//				System.out.println(jsonObj);
				out.print(jsonObj);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				jsonObj.put("errorMsgs", "內部錯誤 in getCompositeQuery");
				out.print(jsonObj.toString());
			}
		}

		// insert
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				boolean addArticleFlag = (Boolean)session.getAttribute("addArticleFlag");
				if(addArticleFlag) { // 取出 flag 先辨認, 如果為true 代表上次更新的, 直接跳轉回首頁
					RequestDispatcher faliureView = request.getRequestDispatcher("/front-end/article/RlistAllArticle.jsp");
					faliureView.forward(request, response);
					return;
				}
				// 從session中取出登入會員的資料, 因為已經經過濾器, 所以不需要做驗證
				MemVO memVO = (MemVO)session.getAttribute("memVO"); 
				String mem_no = memVO.getMem_no(); 
				
				String article_title = request.getParameter("article_title");
				if (article_title == null || article_title.trim().length() == 0) {
					errorMsgs.add("article_title");
				}else {
					article_title = article_title.trim();
				}
				String article_content = request.getParameter("article_content");
				System.out.println(article_content);
				if (article_content == null || article_content.trim().length() == 0) {
					errorMsgs.add("article_content");
				}

				Integer article_type = -1;
				try {
					article_type = new Integer(request.getParameter("article_type"));
					System.out.println("article_type " +article_type);
				} catch (NumberFormatException nfe) {
					errorMsgs.add("article_type");
				}

				ArticleVO articleVO = new ArticleVO();
				articleVO.setArticle_content(article_content);
				articleVO.setArticle_title(article_title);
				articleVO.setArticle_type(article_type);
				
				if (!errorMsgs.isEmpty()) { // 一旦新增過, flag就轉true
					System.out.println("errorMsgs" + errorMsgs);
					request.setAttribute("articleVO", articleVO);
					RequestDispatcher faliureView = request.getRequestDispatcher("/front-end/article/RaddArticle.jsp");
					faliureView.forward(request, response);
					return;
				}
				
				session.setAttribute("addArticleFlag", true);  // 已新增
				ArticleService articleSvc = new ArticleService();
				articleSvc.addArticle(mem_no, article_title, article_content, article_type);
				
				RequestDispatcher successView = request.getRequestDispatcher("/front-end/article/RlistAllArticle.jsp");
				successView.forward(request, response);

			} catch (Exception e) {
				errorMsgs.add("內部錯誤 in insert" + e.getMessage());
				System.out.println(errorMsgs);
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/article/RaddArticle.jsp");
				failureView.forward(request, response);
			}
		}

		// update
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String article_no = request.getParameter("article_no");
				if (article_no == null) {
					article_no = "";
					errorMsgs.add("頁面的value打錯 - article_no");
				}

				String article_title = request.getParameter("article_title");
				if (article_title == null || article_title.trim().length() == 0) {
					article_title = "";
					errorMsgs.add("請輸入標題");
				}else {
					article_title = article_title.trim();
				}
				String article_content = request.getParameter("article_content");
				if (article_content == null || article_content.trim().length() == 0) {
					article_content = "";
					errorMsgs.add("文章沒有內容");
				}

				Integer article_type = null;
				try {
					article_type = new Integer(request.getParameter("article_type"));
				} catch (NumberFormatException nfe) {
					article_type = 0;
					errorMsgs.add("新增Type出問題");
				}
				
				System.out.println("here1");
				
				ArticleVO articleVO = new ArticleVO();
				articleVO.setArticle_no(article_no);
				articleVO.setArticle_content(article_content);
				articleVO.setArticle_title(article_title);
				articleVO.setArticle_type(article_type);
				if (!errorMsgs.isEmpty()) {
					session.setAttribute("articleVO", articleVO); // 因為不會進會員登入頁面, 放request即可
					RequestDispatcher faliureView = request.getRequestDispatcher("/front-end/article/RlistOneArticle.jsp");
					faliureView.forward(request, response);
					return;
				}
				ArticleService articleSvc = new ArticleService();
				articleSvc.updateArticle(article_no, article_title, article_content, article_type);
				System.out.println("here2");
				session.setAttribute("articleVO", articleSvc.getOneArticle(article_no));
				
				RequestDispatcher successView = request.getRequestDispatcher("/front-end/article/RlistOneArticle.jsp");
				successView.forward(request, response);
				
			} catch (Exception e) {
				errorMsgs.add("內部錯誤 in update" + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/article/RlistOneArticle.jsp");
				failureView.forward(request, response);
			}
		}

		// delete
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			String lastPagePath = request.getParameter("lastPagePath");
			try {
				
				String article_no = request.getParameter("article_no");
				if(article_no==null || article_no.trim().length()==0 ) {
					errorMsgs.add("請確認文章編號或傳入編號");
				}else {
					article_no = article_no.trim();
				}
				
				ArticleService articleSvc = new ArticleService();
				articleSvc.updateArticleStatus(article_no,2);
				
				RequestDispatcher successView = request.getRequestDispatcher(lastPagePath);
				successView.forward(request, response);
				
			}catch(Exception e) {
				errorMsgs.add("內部錯誤 in delete " + e.getMessage()); // 有可能找不到
				RequestDispatcher failureView = request.getRequestDispatcher(lastPagePath);
				failureView.forward(request, response);
			}
			
		}
		
		// ------------------------------------ 後台 ------------------------------------------------

		if("update_article_status".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String whichPage = request.getParameter("whichPage");
				if(whichPage==null) {
					whichPage="1";
				}
				
				String article_no = request.getParameter("article_no");
				String status = request.getParameter("article_status");
				
				if(article_no==null || status==null) {
					errorMsgs.add("頁面字打錯");
					RequestDispatcher failureView = request.getRequestDispatcher("/back-end/article/RBlistAllArticle.jsp");
					failureView.forward(request, response);
					return;
				}
				// 下拉選單
				Integer article_status = Integer.parseInt(status);
				
				ArticleService articleSvc = new ArticleService();
				articleSvc.updateArticleStatus(article_no, article_status);
				
				request.setAttribute("whichPage", whichPage);
				String url = "/back-end/article/RBlistAllArticle.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);
			}catch(Exception e) {
				errorMsgs.add("內部錯誤 in updateStatus " + e.getMessage()); 
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/article/RBlistAllArticle.jsp");
				failureView.forward(request, response);
			}
		}
		
		if("getDeleteArticle_For_Display".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				ArticleService articleSvc = new ArticleService();
				List<ArticleVO> articleList = articleSvc.findByStatus(2); // 直接查詢
				
				session.setAttribute("articleList",articleList);
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/article/RBlistMultiArticle.jsp");
				successView.forward(request, response);
				
			}catch(Exception e) {
				errorMsgs.add("內部錯誤 in updateStatus " + e.getMessage()); 
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/article/RBlistMultiArticle.jsp");
				failureView.forward(request, response);
			}
		}
		
		if("recover_status".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String article_no = request.getParameter("article_no");
				if(article_no==null) {
					errorMsgs.add("article_no 頁面打錯了");
					RequestDispatcher failureView = request.getRequestDispatcher("/back-end/article/RBlistMultiArticle.jsp");
					failureView.forward(request, response);
				}
				
				ArticleService articleSvc = new ArticleService();
				articleSvc.updateArticleStatus(article_no, 0); // 回到下架狀態
				List<ArticleVO> articleList = articleSvc.findByStatus(2); // 更新查詢
				
				session.setAttribute("articleList",articleList);
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/article/RBlistMultiArticle.jsp");
				successView.forward(request, response);
				
			}catch(Exception e) {
				errorMsgs.add("內部錯誤 in updateStatus " + e.getMessage()); 
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/article/RBlistMultiArticle.jsp");
				failureView.forward(request, response);
			}
		}
		
		if("doNotShow".equals(action)) {
			PrintWriter out = response.getWriter();
			try {
				String check = request.getParameter("doNotShow");
				System.out.println(check);
				if(check.equals("true")){
					session.setAttribute("doNotShow", true);
				}
				out.print(check);
			}catch(Exception e) {
				out.print("error" + e.getMessage());
			}
		}
		
	}
	
	private List<String> getListArticle_no(List<ArticleVO> articleList){
		List<String> list = new ArrayList<>();
		
		if(!articleList.isEmpty()) {
			for(ArticleVO articleVO :articleList) {
				list.add(articleVO.getArticle_no());
			}
			return list;
		}
		return null;
	}
}

@Controller
class ArticleController {

	// 寫一條龍 DAO model service controller front隨便
	// update
	// insert
	// composite search ajax
	// composite search
	// show all
	// show one
	// delete

	@RequestMapping(value = "delete-article")
	public String deleteTheArticle(final String title) {

		// use service delete article

		return ""; // return path
	}
}