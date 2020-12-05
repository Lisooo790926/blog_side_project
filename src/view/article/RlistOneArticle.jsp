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
<jsp:useBean id="article_rateSvc" scope="request" class="com.article_rate.model.Article_rateService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="replySvc" scope="page" class="com.reply.model.ReplyService" />

<jsp:useBean id="articleVO" scope="session" class="com.article.model.ArticleVO" />
<jsp:useBean id="memVO" scope="session" class="com.mem.model.MemVO" />

<% 
	// 拿作者
	MemVO author = memSvc.getOneMem(articleVO.getMem_no());
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	pageContext.setAttribute("df",df);
	pageContext.setAttribute("author", author);
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
	<!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/css/article/blog.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/article/star.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/css/article/hoverCard.css" rel="stylesheet" />

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
		  .btnStyle.addAccuse {
			position :fixed;
			top:85%;
			left:15%;
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
	  
	  .eachReply{
	  	width:90%;
	  	margin:0 auto;
	  }
	  
	  .reply h3{
	  	font-weight : 900;
	  	color: #fff;
	  	width:100%;
	  	border: 1px solid #937c6f;
	  	padding:1rem;
	  	background:#937c6f;
	  	border-radius:.5rem;
	  }
	  
	  .reply .replyArea{
	  	width:100%;
	  	height:80px;
	  	border-color: #937c6f;
	  }
	  	.reply textArea.replyArea:focus{
	  	  transition: box-shadow .5s;
		  border: 2px solid #E8B216;
		  box-shadow:5px 5px 5px #937c6f;
		}
		
	  .reply .confirmReply{
	  	position:relative;
	  	font-size : 26px;
	  	padding:4px;
	  	border-radius:4px;
	  	top:-4rem;
	  	left:95%;
	  }
	  
	  .reply .showChange{
	  	text-align :center;
	  	vertical-align:middle;
	  	background : rgba(0,0,0,0.2);
	  	width:50%;
	  	height:fit-content;
	  	display:none;
	  	position:absolute;
	  	margin:1rem
	  }
	  
	  .reply .showChange input{
	  	margin:1rem
	  }
	  
	  .thumbupIcon{  
	  	font-size : 26px;
	  	color: #DF9292;
	  }
	  
		  .thumbupIcon:hover {  
		  	font-size : 26px;
		  	color: red;
		  }
	  
	  .thumbDownIcon{
	  	font-size : 26px;
	  	color: #84BF04;
	  }
	  
		  .thumbDownIcon:hover{
		  	font-size : 26px;
		  	color: #53733D;
		  }
	  
	  .backIcon{
	  	position:relative;
	  }
	  
	  .hideAll{
	  	display:none;
	  }
	  
    </style>
    
</head>
<body>
	<jsp:include page="/front-end/Header.jsp"/>

<div class="container mt-5">
<main role="main" class="container-lg">
  <div class="row mb-1">
    <aside class="col-md-3 blog-sidebar">
      <div class="p-3 mb-3 bg-light rounded text-center">
        <h4>文章作者</h4>
        <hr>
        <p class="mb-0 text-center"><b>${author.mem_name}</b></p>
        <img width=200px src="<%=request.getContextPath()%>/myUtil/PictureReader?mem_no=${author.mem_no}">
        <c:if test="${memVO.mem_no!=articleVO.mem_no}">
        
	        <div class="rating m-0 w-100 pr-5" data-vote="0">  <!-- ranking area --> 
			  <div class="star hidden">
			    <span class="full"data-value="0"></span>
			  </div>
			<c:forEach varStatus="score" begin="1" end="5">
			  <div class="star">
			    <span class="full" data-value="${score.index}"></span>
			    <span class="selected"></span>
			  </div>
			</c:forEach>
			  <div class="score mt-1">
			    <span class="score-rating js-score">
			    <% 
					String article_no = articleVO.getArticle_no();
					String mem_no = memVO.getMem_no();
					Integer article_mem_rate = article_rateSvc.getOneArticle_rate(article_no, mem_no);
					if(article_mem_rate==null){
						out.print(0);
					}else{
						out.print(article_mem_rate);
					}
				%>
			    </span>
			    <span>/</span>
			    <span class="total">5</span>
			  </div>
			</div>
		</c:if>
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

       <%   // 列出精華文章
   			// List<ArticleVO> greatList = articleSvc.getNthGreatArticle(articleVO.getArticle_type()); // 真的
   			List<ArticleVO> greatList = articleSvc.getNthGreatArticle(articleVO.getArticle_type());
	   		pageContext.setAttribute("greatList", greatList);
  	   %>
		 <c:if test="${greatList!=null}">
		  <h4 class="text-center pt-1">推薦文章</h4>
		  <hr>
		  <div class="row mb-2">
		   <c:forEach var="greatArticleVO" items="${greatList}" >
		   		<div class="col-12 greatArticle">
			    	<section class="article-section" catergory="${greatArticleVO.article_type}" article_no="${greatArticleVO.article_no}" >
					    <div class="card article-card">
						    <strong class="d-inline-block mb-2">${articleTypeMap.get(greatArticleVO.article_type.toString())}</strong>
						    <div class="card-body article-card-body">
						      <h5 class="card-title">${greatArticleVO.article_title}</h5>
						      <span><i>觀看次數 : ${greatArticleVO.article_seen}</i></span><br>
						      <span><i>評分 : ${greatArticleVO.article_rate}</i></span>
						      <c:if test="${greatArticleVO.article_content.length()<20}">
						      	<p class="card-text">${greatArticleVO.article_content}</p>
						      </c:if>
						      <p class="lead mb-0 hoverUnderLine">continue read....</p>
					    	</div>
					 	  </div>
					 	 <form style="display: none" method="post" action="<%=request.getContextPath()%>/article/article.do">
							<input type="hidden" name="lastArticle_no" value="${articleVO.article_no}">
							<input type="hidden" name="action" value="getOne_For_Display">
							<input class="showOneArticle" type="submit" name="article_no" value="${greatArticleVO.article_no}">
					     </form>
					    
				   </section>
				</div>
		    </c:forEach>
			</div>
		  </c:if>
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

    <div class="col-9 blog-main ">
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/article/article.do" name="form1">
      	<h3 class="pb-3 mb-4 border-bottom" style="font-weight:900; ">
	        <p class="display">${articleTypeMap.get(articleVO.article_type.toString())}</p>
	        <p><select class="hideAll" size="1" name="article_type">
				<c:forEach var="typeA" items="${articleTypeMap.values()}" varStatus="tArt">
					<option value="${tArt.count-1}" ${(articleVO.article_type==(tArt.count-1))?'selected':""} >${typeA}
				</c:forEach>
			</select></p>
	    </h3>
      <div class="blog-post w-100"> <!-- main body  -->
		<div class="row">
			<div class="col-8">
				<h2 class="blog-post-title ">
					<p class="display">${articleVO.article_title}</p>
					<textarea class="hideAll" style="width:100%" name="article_title" size="30"  placeholder="New_Title"
					 row="3"><%=articleVO.getArticle_title()%></textarea>
				</h2>
			</div>      	
        	<div class="col-4">
	        	<div class="row">
	        		<a class="btn btnStyle mr-2" style="font-size:24px" href="
				       	<c:if test="${lastArticle_no==null}">
				       		<%=request.getContextPath()%>${lastPagePath}?whichPage=${whichPage}
				       	</c:if>
				       	<c:if test="${lastArticle_no!=null}">
				       		<%=request.getContextPath()%>/article/article.do?action=getOne_For_Display&article_no=${lastArticle_no}
				       	</c:if>
				       	" data-toggle="tooltip" data-placement="bottom" title="上一頁"><i class="fas fa-arrow-circle-left"></i></a>
			     	<a class="btn  btnStyle mr-2" style="font-size:24px"  href="<%=request.getContextPath()%>/front-end/article/RlistAllArticle.jsp" data-toggle="tooltip" data-placement="bottom" title="首頁"><i class="fas fa-home"></i></a>
	        		<c:if test="${account!=null && (memVO.mem_no==articleVO.mem_no)}">
			        	<button class="btn btnStyle mr-2 callUpdate" type="button" style="font-size:24px" ><i class="fas fa-pencil-alt"></i></button>
	        			<button class="btn btnStyle hideAll" style="font-size:24px" type="submit"><i class="far fa-check-circle"></i></button>
	        		</c:if>
				</div>
	    		<div class="row mt-2">
	        	  <i>觀看次數 :　</i><span>${articleVO.article_seen}</span>　
				  <i>評分 :　</i><span class="showRate">${articleVO.article_rate}</span>
	        	</div>
	    	</div>
		</div>
        
        <p class="blog-post-meta mb-1">
        	${df.format(articleVO.article_date)} by 
        	<a href="">${author.mem_name}</a>
        	<hr style="margin:0;width:25%">
        </p>
       	<div class="display">
       		${articleVO.article_content}
       	</div>
		<p class="hideAll" >
			<textarea contenteditable="true" name="article_content" id="article_content">${articleVO.article_content}</textarea>
		</p>
		
      </div>
      	<input type="hidden" name="action" value="update">
		<input type="hidden" name="article_no" value="<%=articleVO.getArticle_no()%>">
		<input type="hidden" name="mem_no" value="<%=articleVO.getMem_no()%>">
	  </FORM>	
	
      <!-- /.blog-post -->
    </div><!-- /.blog-main -->
  </div><!-- /.row -->

  
  <!-- reply area -->
  <div class="row reply mt-2" > 
  	<div class="col-4" style="height:0"></div>
  	<div class="col-8" style="height:0;position:relative;top:-4rem;">
  	 <nav class=" text-center mb-2 backIcon">
        <a class="btn btnStyle mr-5" style="font-size:24px" href="
        	<c:if test="${lastArticle_no==null}">
        		<%=request.getContextPath()%>${lastPagePath}?whichPage=${whichPage}
        	</c:if>
        	<c:if test="${lastArticle_no!=null}">
        		<%=request.getContextPath()%>/article/article.do?action=getOne_For_Display&article_no=${lastArticle_no}
        	</c:if>
        	" data-toggle="tooltip" data-placement="bottom" title="上一頁"><i class="fas fa-arrow-circle-left"></i></a>
        <a class="btn  btnStyle mr-5" style="font-size:24px"  href="<%=request.getContextPath()%>/front-end/article/RlistAllArticle.jsp" data-toggle="tooltip" data-placement="bottom" title="首頁"><i class="fas fa-home"></i></a>
      </nav>
    </div>
  	
  	<hr width="80%">
  	<div class="alert w-100 text-center"><h3>留言區</h3></div>
   	<ul class="list-unstyled eachReply">
   	
	 <c:forEach var="replyVO" items="${replySvc.findByArticleno(articleVO.article_no)}">
	  <li class="media p-1 realReply">
	    <img width=70 src="<%=request.getContextPath()%>/myUtil/PictureReader?mem_no=${replyVO.mem_no}" class="mr-3">
	    <div class="media-body">
	      <h4 class="mt-0 mb-1">${memSvc.getOneMem(replyVO.mem_no).mem_name}</h4>
	      <p style="font-size:16px">${replyVO.reply_content}</p>
	      <div class="text-right">
	      	<span style="color:#C0B9B9">${df.format(replyVO.reply_time)}
	      	　<i class="far fa-thumbs-up thumbupIcon"></i>　<i class="fas fa-heart-broken thumbDownIcon"></i></span>
	      </div>
	    </div>
	       <div class="showChange" member="${replyVO.mem_no}">
			  <FORM style="display:inline-block;margin: 0px;" METHOD="post" class="changeReply"
			  		ACTION="<%=request.getContextPath()%>/reply/reply.do" >
			     <input type="hidden" name="reply_no" value="${replyVO.reply_no}">
			     <input type="hidden" name="article_no" value="${articleVO.article_no}">
			     <input type="hidden" name="action" value="delete">
			     <input class="btnStyle changeBtn" type="submit" value="刪除">
			  </FORM><br>
		  </div>
	  </li>
	 </c:forEach>
	 
	 <!-- Prepare reply block! -->
	  <li class="media p-1 prepareReply" style="display:none">
	    <img width=70 src="" class="mr-3 prepareImg">
	    <div class="media-body">
	      <h4 class="mt-0 mb-1 prepareMem_no"></h4>
	      <p style="font-size:16px" class="prepareContent"></p>
	      <div class="text-right">
	      	<span class="prepareDate" style="color:#C0B9B9">
	      	　<i class="far fa-thumbs-up thumbupIcon"></i>　<i class="fas fa-heart-broken thumbDownIcon"></i></span>
	      </div>
	    </div>
	    <div class="showChange" >
		  <FORM style="display:inline-block;margin: 0px;" METHOD="post" class="changeReply"
		  		ACTION="<%=request.getContextPath()%>/reply/reply.do" >
		     <input type="hidden" name="reply_no">
		     <input type="hidden" name="article_no">
		     <input type="hidden" name="action" value="delete">
		     <input class="btnStyle" type="submit" value="刪除">
		  </FORM><br>
	  </li>
	  <!-- Prepare reply block! end -->
	 
	 <li class="media p-1">
	    <img width=70 src="<%=request.getContextPath()%>/myUtil/PictureReader?mem_no=${(memVO.mem_no==null)?"":memVO.mem_no}" class="mr-3">
	 	<div class="media-body">
	      <h5 class="mt-0 mb-1">${memSvc.getOneMem(replyVO.mem_no).mem_name}</h5>
	      <textArea class="replyArea" row="5" type="text" name="reply_content"  placeholder="put your reply Here" ></textArea>
	      <i class="fas fa-external-link-alt confirmReply btnStyle"></i>
	 	</div>
	 </li>
	 
	 </ul>
  </div>
  <c:if test="${memVO.mem_no != articleVO.mem_no}"> 
		<button class="btn btnStyle addAccuse" style="font-size:24px ;z-index:20" data-toggle="tooltip" data-placement="top"  title="ACCUSE"><i class="fas fa-times-circle"></i></button>
		
		<!-- Accuse Area -->
		<div class="modal fade" id="addAccuseModal" tabindex="-1" role="dialog" aria-labelledby="addAccuseModalLable" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="addAccuseModalLable"><b>新增檢舉</b></h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <form class="addAccuseForm" action="<%=request.getContextPath() %>/accuse/accuse.do" method="POST">
		          <div class="form-group w-100">
		            <b>檢舉種類</b>
		            <select class="form-control accuse_type" name="accuse_type" size="1">
		            	<option selected disabled>Choose	
						<c:forEach var="accuse_type" items="${accuseTypeMap.values()}" varStatus="acc">
							<option value="${acc.count-1}">${accuse_type}					
						</c:forEach>
					</select>
					 <div class="invalid-feedback hideAll">
				       Please enter a message in the textarea.
				     </div>
		          </div>
		          <div>
		            <label for="recipient-name" class="col-form-label">檢舉原因:</label>
		            <textarea class="accuse_content form-group w-100" name="accuse_content" placeholder="put your accuse Here" class="form-control" id="recipient-name"></textarea>
		          	<div class="invalid-feedback hideAll"></div>
		          </div>
		          <input type="hidden" name="article_no" value="${articleVO.article_no}">
		          <input type="hidden" name="action" value="insert">
		          <div class="modal-footer">
			        <button type="button" type="button" class="btn btnStyle mr-2" data-dismiss="modal">Close</button>
			        <button class="accuseSubmit btn btnStyle" type="button">sumbit</button>
			      </div>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
  </c:if>
  
</main><!-- /.container -->
</div>

<jsp:include page="/footer.jsp"/>

<script type="text/javascript">
	
	var data2 = null;
	
	// 暫存每個Input的位置
	$(document).ready(function(){
		skipJumbtron(0);
		adjustMoveText("show"," GRAB & PIN YOUR STORY");
		// inline 文章 只有可以編輯才有用
		CKEDITOR.disableAutoInline = true;
		var editor = CKEDITOR.inline('article_content');
		
	 	// 起始視窗下滑
		var x = '${param.position}';
		if(x==""){skipJumbtron(150);}
		else{skipJumbtron(x);}
		
		// 側邊文章連結
		$(".article-card").click(function(){
		   $(this).next().find("input.showOneArticle").click();
	    });
		
		// 檢舉modal
		<c:if test="${account==null}">
			$(".addAccuse").click(validate);
		</c:if>
		<c:if test="${account!=null}">
			$(".addAccuse").click(function(){
				$("#addAccuseModal").modal();
			});
		</c:if>
		
		// 回傳判斷新增成功或失敗
		<c:if test="${accuseUpdate==\"OK\"}">
			Swal.fire({
				icon: 'success',
				title: '新增成功',
			})
		</c:if>
		<c:if test="${accuseUpdate==\"repeat\"}">
			Swal.fire({
				 icon: 'error',
				 title: '不得重複申請',
			})
		</c:if>
	});
	
	// 會員登入才能評分
	<c:if test="${account==null}">
		$(".full").click(validate);
	</c:if>
	
	// 會員登入後Ajax評分
	<c:if test="${account!=null}">
		$(".full").click(function(e){
			console.log($(this).attr("data-value"));
			$.ajax({
				url:"<%= request.getContextPath()%>/article_rate/article_rate.do",
				type:"POST",
				data:{"action":"update_Rate_Ajax",
					  "Content-Type":"text/html",
					  "mem_no": "${memVO.mem_no}",
					  "article_no" : "${articleVO.article_no}",
					  "article_mem_rate" : $(this).attr("data-value")
				},
				success: function(data){
					console.log(data);
					$(".blog-post").find(".showRate").text(data);
				}
			});
		});
	</c:if>
	
	// 按壓後產生紅色與綠色
	$(".thumbupIcon").click(function(e){e.stopPropagation();$(this).css("color","red")});
	$(".thumbDownIcon").click(function(){e.stopPropagation();$(this).css("color","#53733D")});
	
	// 確認留言 / Ajax建立留言
	$(".confirmReply").click(function(){
		console.log($(".replyArea"));
		$.ajax({
			url:"<%= request.getContextPath()%>/reply/reply.do",
			type:"POST",
			dataType:"json",
			data:{
				"action" : "insert_ForAjax",
				"reply_content" : $(".replyArea").val(),
				"article_no" : "${articleVO.article_no}",
				"mem_no" : "${memVO.mem_no}"
			},
			success : function(data){
				console.log(data); 
				if(data.errorMsgs==null){
					// 建立新的等待留言窗格
					var li = $("<li>").html($(".prepareReply").html())
									  .addClass("media p-1 realReply newReply");
					$(".prepareReply").before(li);
					
					var url = "<%=request.getContextPath()%>/myUtil/PictureReader?mem_no=${memVO.mem_no}";
					$(".newReply .prepareImg").attr("src",url);
					$(".newReply .prepareMem_no").text("${memVO.mem_name}");
					$(".newReply .prepareContent").text(data.reply_content);
					$(".newReply .prepareDate").prepend(new Date().Format("yyyy-MM-dd hh:mm"));
					$(".replyArea").val("");
					$(".newReply").click(toggleUpdate);
					$(".newReply .showChange").attr("member",data.mem_no);
					$(".newReply .changeReply input[name='reply_no']").val(data.reply_no);
					$(".newReply .changeReply input[name='article_no']").val(data.article_no);
					$(".newReply .changeReply .changeContent").val(data.reply_content);
					$(".newReply").fadeIn("slow").removeClass("newReply");
					
				}else if(data.errorMsgs.includes("login")){
					alert("請先登入");
				}
			}
		});
	});
	
	// 刪除留言 ,做toggle彈出
	$(".realReply").click(toggleUpdate);
	
	function toggleUpdate(){
		var member = $(this).find(".showChange").attr("member");
		if(member=="${memVO.mem_no}"){
			var showChange = $(this).find(".showChange");
			showChange.fadeToggle("slow");
			if(showChange.attr("alreadyChecked")==null){
				var input = $("<input>").attr("type","hidden")
								        .attr("name","position")
								        .attr("value",$(this).offset().top)
				showChange.attr("alreadyChecked",1);					
				showChange.find(".changeReply").append(input);
			}
		}
	}
	
	$(".callUpdate").click(function(){
		
		var checked = $(".display").css("display");
		console.log("result" + (checked=="none"));
		if(checked=="none"){
			$(".display").fadeIn();
			$(".hideAll").hide();
		}else{
			$(".display").hide();
			$(".hideAll").fadeIn();
		}
	});
	
	
	$(".accuseSubmit").click(function(){
		// 判斷檢舉內容
		var accuseType = $(".addAccuseForm .accuse_type");
		var accuseContent = $(".addAccuseForm .accuse_content");
		if(accuseType.val()==null){
			accuseType.next().text("選擇類別").show();
			return;
		}
		if(accuseContent.val().length==0){
			accuseContent.next().text("輸入檢舉原因").show();
			return;
		}
		$(this).attr("type","submit").click();
	});
	
	
</script>
<script src="<%=request.getContextPath()%>/js/awesomeFont.js"></script>
<script src="<%=request.getContextPath()%>/js/article/star.js"></script>
<script src="<%=request.getContextPath()%>/js/article/ckeditor/ckeditor.js"></script>
<script src="<%=request.getContextPath()%>/js/article/sweetalert2@9.js"></script>

</body>
</html>