<%@page import="com.mem.model.MemVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.article.model.*"%>
<%@ page import="com.article_rate.model.*"%>
<%@ page import="java.util.ArrayList"%>

<jsp:useBean id="articleSvc" scope="page" class="com.article.model.ArticleService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="memVO" scope="session" class="com.mem.model.MemVO" />

<% 
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	pageContext.setAttribute("df",df);
	
	session.setAttribute("addArticleFlag",false); // 一旦進頁面就會刷flag, 但不要按上一頁
%>
    
<!DOCTYPE html>
	
	<html>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
	<meta name="generator" content="Jekyll v3.8.5">
	<title>(前台)文章資料 - ListOneArticle.jsp</title>

    <!-- Bootstrap core CSS -->
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/index.css" rel="stylesheet">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
      
      .addArticleContainer{
      	display:none;
      }
      
      .greatArticle .card{
       width: 100%;
       color : #8B8080;
       box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
       transition: all 0.3s cubic-bezier(.25,.8,.25,1);
      }
	      .greatArticle .card:hover {
	        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	      }
      
	  .btnStyle{
	  	font-size:16px;
	  	color : #fff;
	  	background: #937c6f;
	  	border: 1px solid #937c6f;
	  	font-weight:900;
	  }
		  .btnStyle:hover{
		  	color : #937c6f;
		  	background-color: #fff;
		  	border: 1px solid #937c6f;
		  }
		  
	  .searchBtn{
	  	border: 1px solid #937c6f;
		background: #937c6f;
		color:#fff;
	  }
	  
	  .searchBtn:hover{
	  	transition:all .5s;
	  	border: 1px solid #937c6f;
		background: #fff;
		color:#937c6f;
	  }
	  
    </style>
    <!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/css/article/blog.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/article/star.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/css/article/hoverCard.css" rel="stylesheet" />
</head>
<body>

	<jsp:include page="/front-end/Header.jsp"/>

<div class="container addArticleContainer mt-5">
<main role="main" class="container-lg ">
  <div class="row mb-1">
    <aside class="col-md-3 blog-sidebar">
      <div class="p-3 mb-3 bg-light rounded text-center">
        <h4>我的文章</h4>
        <hr>
        <p class="mb-0 text-center"><b>${memVO.mem_name}</b></p>
        <img width=200px src="<%=request.getContextPath()%>/myUtil/PictureReader?mem_no=${memVO.mem_no}">
      </div>
		
      <div class="p-1">
       <form class="articleSearch" METHOD="post" ACTION="<%=request.getContextPath()%>/article/article.do" name="ArticleForm2">
		  <h4 class="text-center">文章搜尋</h4>
		  <hr>
		  <div class="form-row">
		    <div class="col-12 mb-1 keywordSearch">
		      <label for="validationDefault01">文章關鍵字</label>
		      <input type="text" class="form-control" id="validationDefault01" name="article_title"
		      	 placeholder="Input your Keyword">
		    </div>
		  </div>
		
		  <label class="my-1 mr-2" for="inlineFormCustomSelectPref">類別搜尋</label>
		  <select class="custom-select my-1 mr-sm-2 typeSearch" id="inlineFormCustomSelectPref" name="article_type">
		    <option selected disabled>Choose</option>
		    <c:forEach var="typeA" items="${articleTypeMap.values()}" varStatus="tArt">
				<option value="${tArt.count-1}">${typeA}
			</c:forEach>
		  </select>
		
		  <button class="btn mt-2 w-100 searchBtn" type="submit">search</button>
		  <input type="hidden" name="action" value="getCompositeQuery_For_Display">
		</form>
      </div>

 
	     <div class="p-3  text-center fakeLink">
	        <h4 class="text-center">ELSEWHERE</h4>
	        <hr>
	        <ol class="list-unstyled">
	          <a href="" class="btn btnStyle"><i class="fab fa-twitter"></i></a>
	          <a href="" class="btn btnStyle"><i class="fab fa-facebook-f"></i></a>
	          <a href="" class="btn btnStyle"><i class="fab fa-instagram"></i></a>
	          <a href="" class="btn btnStyle"><i class="fab fa-pinterest-p"></i></a>
	          <a href="" class="btn btnStyle"><i class="fab fa-youtube"></i></a>
	        </ol>
	      </div>  
    </aside><!-- /.blog-sidebar -->

    <div class="col-9 blog-main">
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/article/article.do">
      	
      	<h3 class="text-center">
      		新增文章
      		<a class="btn btnStyle mr-2" style="font-size:24px"  href="<%=request.getContextPath()%>/front-end/article/RlistAllArticle.jsp" data-toggle="tooltip" data-placement="bottom" title="首頁"><i class="fas fa-home"></i></a>
      	</h3>
      	<hr>
	    
                      文章種類 
      	<select class="insert article_type w-100 form-control" size="1" name="article_type">
			<option selected disabled>Choose
			<c:forEach var="typeA" items="${articleTypeMap.values()}" varStatus="tArt">
				<option value="${tArt.index}">${typeA}
			</c:forEach>
	    </select>
	   	<hr>
                     文章標題
		<textarea class="insert article_title form-control w-100 h-100" name="article_title" size="30"  placeholder="New_Title"
		 row="3"></textarea>
	 	<hr>
	 	<div class=" insert article_content form-control h-100" required>
		 文章內容
			<textarea style="width:100%;height:500px;border:2px solid red" name="article_content" id="article_content"></textarea>
      	</div>
      	<input type="hidden" name="mem_no" value="${memVO.mem_no}"/>
		<input type="hidden" name="action" value="insert">
		
		<div class="my-4 text-center">
			<button class="btn btnStyle ml-4 addSubmitBtn" style="font-size:24px;width:40%" type="submit"><i class="far fa-check-circle"></i></button>
			<button class="btn btnStyle ml-4 magic_btn" style="font-size:24px;width:40%" type="button">點點我</button>
		</div>
	  </FORM>	
    </div><!-- /.blog-main -->
  </div><!-- /.row -->
  
</main><!-- /.container -->
</div>

<jsp:include page="/footer.jsp"/>

<script type="text/javascript">
	
	var data2 = null;
	var data3 = null;
	// 暫存每個Input的位置
	$(document).ready(function(){
		skipJumbtron(0);
		adjustMoveText("show"," GRAB & PIN YOUR STORY");
		checkedArticle();
		
// 		$('html, body').animate({scrollTop: 290},500);
		$(".addArticleContainer").fadeIn(1000);
		
		CKEDITOR.replace('article_content',{width:"100%",height:"700px"});
		
	});
	
	
	// 調整 錯誤驗證移到前台
	function checkedArticle(){
		var result = new Array();
		var index = 0;
		var result = [<%
			List<String> errorMsgs = (List<String>)request.getAttribute("errorMsgs");
			if(errorMsgs!=null){
				for(String error :errorMsgs){
					switch(error){
						case "article_title":
							out.print("\"" + error +"\"," );
							break;
						case "article_content":
							out.print("\"" + error +"\"," );
							break;
						case "article_type":
							out.print("\"" + error +"\"," );
							break;
					}
				}
			}
 		%>]; 
			
		if(result.length!=0){
			for(let each of result){
				console.log(each);
				$(".form-control").filter("."+each).addClass("is-invalid")
			}			
		}
	}
	
	$(".insert.form-control").click(function(){
		$(this).removeClass("is-invalid");
	});
	
	$(".magic_btn").click(function(){
		$(".article_type option").removeAttr("selected");
		$(".article_title").text("Patya 打鐵仔家具使用心得");
	});
	
	
</script>

<script src="<%=request.getContextPath()%>/js/awesomeFont.js"></script>
<script src="<%=request.getContextPath()%>/js/article/ckeditor/ckeditor.js"></script>
</body>
</html>