<%@page import="com.mem.model.MemVO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.article.model.*"%>
<%@ page import="com.article_rate.model.*"%>
<%@ page import="java.util.ArrayList"%>

<!-- 取得當前是否登入會員 -->
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="memVO" scope="session" class="com.mem.model.MemVO"/> 

<%	
	ArticleService articleSvc = new ArticleService();
    List<ArticleVO> list = articleSvc.getAllSeen(); // 拿出上架文章
    pageContext.setAttribute("list",list);
	
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	pageContext.setAttribute("df",df);
%>   
 
<!DOCTYPE html>
	
	<html>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
	<meta name="generator" content="Jekyll v3.8.5">
	<title>(前台)論壇 - ListAllArticle.jsp</title>

    <!-- Bootstrap core CSS -->
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/index.css" rel="stylesheet">
	<!-- Custom styles for this template -->
    <link href="<%=request.getContextPath()%>/css/article/blog.css" rel="stylesheet">
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
      
       article section { 
      		height:auto; 
       } 
       		section.article-section.self-article{  /*自己的文章背景色 */
       			background-color : #ace4aea6
       		}
    	
       article .card{
         width: 100%;
         color : #8B8080;
         box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
         transition: all 0.3s cubic-bezier(.25,.8,.25,1);
       }
	      article .card:hover {
	        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	      }
      
     .catergory-item {margin-left: .7rem;}
		
		.catergory-item a{
			color: #FFFFFF;
		    background-color: #937c6f;
		    border: 1px solid #937c6f;
		    border-radius: .25rem;
		}
		.catergory-item a:hover{
			transition: all .5s;
			border: 1px solid ;
			background: #FFFFFF;
			color: #937c6f;
		}
		.catergory-item a.onCheckedBtn{
			box-shadow:5px 5px 5px gray;
			position:relative;
			top:-3px;
		}
		
	
		
	.btnStyle{
	  	font-size:16px;
	  	color : #fff;
	  	background: #937c6f;
	  	border: 1px solid #937c6f;
	  	font-weight:900;
	 }
	
		.btnStyle.addBtn {
			position :fixed;
			top:85%;
			left:15%;
		}
		.btnStyle:hover{
		  	color : #937c6f;
		  	background-color: #fff;
		  	border: 1px solid #937c6f;
		 }

	#myflashModal {
		overflow:hidden; 
		height:40px;
	}	 
	#myflashModal .arrowPic {
		background: transparent;
	    width: 5%;
	    position: fixed;
	    animation: hightlight .5s infinite alternate linear
	}
	@keyframes hightlight{
		from{opacity:0}
		to{opacity:1}
	}
		#myflashModal .arrowPic.left{
			left: 5vw;
		}
		#myflashModal .arrowPic.right{
			right : 5vw;
			transform: rotateY(180deg)
		}
		#myflashModal .hintText {
			position:fixed;
			top:50vh;
			animation: hightlight .5s infinite alternate linear;
			text-shadow:5px 7px 7px #101010;
			font-weight:900;
		}
		
		#myflashModal .doNotShow {
			padding:.5rem;
			position:fixed;
			top:70vh;
			font-size:15px;
		}
		
	.container .arrowPic2 {
		background: transparent;
	    width: 4%;
	    top:49.5%;
	    position: fixed;
	    pointer:cursor;
	}
		.container .arrowPic2.left{
			left: 5vw;
		}
		.container .arrowPic2.right{
			right : 5vw;
			transform: rotateY(180deg);
		}
		
    </style>
    
</head>
<body>

	<jsp:include page="/front-end/Header.jsp"/>
  
<div class="container my-5">
	<hr width=80%>
	<div class="row">
	  <div class="col-6">
	    <div id="demo-2">
	      <input class="mb-2 searchBar" type="search" placeholder="Search">
	    </div>
	 </div>
	  
	 <div class="col-6 catergory">
	    <ul class="nav justify-content-end">
	      <c:if test="${account!=null}">
		      <li class="nav-item catergory-item">
		        <a class="nav-link catergory-link" href="#" catergory="${memVO.mem_no}">我的文章</a>
		      </li>
	      </c:if>
	      <li class="nav-item catergory-item">
	        <a class="nav-link catergory-link current" href="#" catergory="all">ALL</a>
	      </li>
	      <li class="nav-item catergory-item ">
	        <a class="nav-link catergory-link" href="#" catergory="0">閒聊</a>
	      </li>
	      <li class="nav-item catergory-item">
	        <a class="nav-link catergory-link" href="#" catergory="1">心得</a>
	      </li>
	      <li class="nav-item catergory-item">
	        <a class="nav-link catergory-link" href="#" catergory="2">詢問</a>
	      </li>
	      <li class="nav-item catergory-item">
	        <a class="nav-link catergory-link" href="#" catergory="3">公告</a>
	      </li>
	    </ul>
	  </div>
	 </div>	
	 
	<article class="text-cetner article">
	<c:forEach var="articleVO" items="${list}">                            <%-- 屬該會員就塞入 --%>
	  <section class="article-section <c:if test="${articleVO.mem_no == memVO.mem_no}">${memVO.mem_no} self-article</c:if>" catergory="${articleVO.article_type}" article_no="${articleVO.article_no}" > 
	    
	    <div class="card article-card">
		    <strong class="d-inline-block mb-2">${articleTypeMap.get(articleVO.article_type.toString())}</strong>
		    <div class="card-body article-card-body">
		      <h5 class="card-title">${articleVO.article_title}</h5>
		      <span><i>觀看次數 : ${articleVO.article_seen}</i></span><br>
			  <span><i>評分 : ${articleVO.article_rate}</i></span>
		      <c:if test="${articleVO.article_content.length()<20}">
		      	<p class="card-text">${articleVO.article_content}</p>
		      </c:if>
		      <p class="lead mb-0 hoverUnderLine">continue read....</p>
	    	</div>
	    	 <form style="display: none" method="post" action="<%=request.getContextPath()%>/article/article.do">
				<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
				<input type="hidden" name="lastPagePath" value="<%=request.getServletPath()%>">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input class="showOneArticle" type="submit" name="article_no" value="${articleVO.article_no}">
		     </form>
	 	 </div>
	  </section>
	</c:forEach>
	</article>
	<c:if test="${account!=null}">
		<a class="btn btnStyle addBtn" href='<%=request.getContextPath()%>/front-end/article/RaddArticle.jsp' 
			style="font-size:24px; z-index:20" data-toggle="tooltip" data-placement="top" title="ADD"><i class="fas fa-plus"></i></a>
	</c:if>
	
	<c:if test="${doNotShow==null}">
	<div class="modal fade"  id="myflashModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content" style="background:transparent;border:0">
	      <div class="modal-body">
	      	<h1 class="text-white hintText">Please Swipe Left or Right</h1>
	        <img class="arrowPic left" src="<%=request.getContextPath()%>/front-end/article/images/achor.png" style="background:transparent">
	        <img class="arrowPic right" src="<%=request.getContextPath()%>/front-end/article/images/achor.png" style="background:transparent">
	      </div>
	      <div class="modal-footer">
	      	<label for="doNotShow" class="text-white doNotShow"><input style="margin:0.4rem 0.5rem 0 0" id="doNotShow" type="checkbox">Do Not Show it Again</label>
      	  </div>
	    </div>
	  </div>
	</div>
	</c:if>
	
	<div class="text-center moveArrow" style="display:none">
		<img class="arrowPic2 left" src="<%=request.getContextPath()%>/front-end/article/images/achor.png" style="background:transparent">
		<img class="arrowPic2 right" src="<%=request.getContextPath()%>/front-end/article/images/achor.png" style="background:transparent">
	</div>
	
</div>

<jsp:include page="/footer.jsp"/>

<script type="text/javascript">
	
	var data22=null;
	$(document).ready(function(){
		
		// 起始位置
		skipJumbtron(259);
		adjustMoveText("show"," GRAB & PIN YOUR STORY");
		
		$("#myflashModal").modal();
		$(".modal-backdrop.fade.show").click(function(){
			$("#myflashModal").modal("hide");
			<c:if test="${doNotShow==null}">
				$("#myflashModal").on("hidden.bs.modal",function(){
					$(".moveArrow").fadeIn();
				})
			</c:if>
		});
		<c:if test="${doNotShow!=null}">
			$(".moveArrow").fadeIn();
		</c:if>
		
		$(".doNotShow").click(doNotShowModal);
		$("#doNotShow").change(doNotShowModal);
		
		var addFlag = "${addFlag}";
		// 判斷查有無文章的動作-----------------
		var list = [<%
		    List<String> list_Aritlce_no = (List<String>)request.getAttribute("list_Aritlce_no");
		    if(list_Aritlce_no!=null && !list_Aritlce_no.isEmpty()){
				for(String eachNo : list_Aritlce_no){
					out.print("\"" + eachNo + "\",");
				}
		    }
		%>];
		if(list.length>0){
			$('.article-section').each(function(){
	            if(!list.includes($(this).attr("article_no"))){
	                $(this).hide().addClass('hidden');
	            } else {
	                $(this).fadeIn('slow').removeClass('hidden');
	            }
	         });
		}else{
			<c:if test="${noArticle}">
				Swal.fire({
				  icon: 'error',
			 	 title: '查無此文章',
				})
			</c:if>
		}
		// 有無選擇類別-------------------------
		<c:if test="${choosen_article_type!=null}"> 
			$('.catergory-item').find(".catergory-link[catergory='${choosen_article_type}']").addClass('current').addClass("onCheckedBtn");
		</c:if>
		
	    // filter
	    $('.catergory-item').on('click', function(event){
	        event.preventDefault();
	        // current class
	        $('.catergory-item .catergory-link.current').removeClass('current').removeClass("onCheckedBtn");
	        $(this).find(".catergory-link").addClass('current').addClass("onCheckedBtn");

	        // filter link text
	        var catergory = $(this).find(".catergory-link").attr("catergory");
	        
	        // remove hidden class if "all" is selected
	        if(catergory == 'all'){
	            $('.article-section').fadeIn('slow').removeClass('hidden');
	        } else if(catergory == "${memVO.mem_no}") {
	        	$('.article-section').each(function(){
	               if($(this).hasClass(catergory)){
	            	   $(this).fadeIn('slow').removeClass('hidden');
	               } else {
	            	  $(this).hide().addClass('hidden');
	               }
	            });	        	
	    	} else {
	            $('.article-section').each(function(){
	               if($(this).attr("catergory")!=catergory){
	                   $(this).hide().addClass('hidden');
	               } else {
	                   $(this).fadeIn('slow').removeClass('hidden');
	               }
	            });
	        }
	        return false;        
	    });
	});
	
	// 判斷是否有show過提示modal
	function doNotShowModal(){
		console.log("checked or not " + $("#doNotShow").prop("checked"));
		$.ajax({
			url:"<%=request.getContextPath()%>/article/article.do",
			type:"POST",
			data:{
				doNotShow : $("#doNotShow").prop("checked"),
				action : "doNotShow"
			},
			success : function(data){
				console.log("check result " + data);					
			}
		})
	}
	
	// 搜尋 有點bug
	$(".searchBar").focus(function(){
		$(this).keypress(function(e){
			if(e.keyCode==13){  // enter
// 				console.log("searchBar " + $(".searchBar").val());
// 				console.log("current " +  $('.catergory-item .catergory-link.current').attr("catergory"));
								
				$.ajax({
					url:"<%= request.getContextPath()%>/article/article.do",
					type:"POST",
					dataType:"json",
					data:{"action":"getCompositeQuery_For_Ajax",
						  "article_type": function(){
							  var val = $('.catergory-item .catergory-link.current').attr("catergory");
							  if(val=="all"){
								  return null;
							  }else{
								  return val;
							  }
						  },
						  "article_title":$(".searchBar").val(),
					},
					success: function(data){
						if(data.errorMsgs==null){
							var list = new Array();
							data.result.forEach(e => list.push(e.article_no));
							if(list.length==0){
								Swal.fire({
								  icon: 'error',
								  title: '查無此文章',
								});
							}else{
								$('.article-section').each(function(){
					               if(!list.includes($(this).attr("article_no"))){
					                   $(this).hide().addClass('hidden');
					               } else {
					                   $(this).fadeIn('slow').removeClass('hidden');
					               }
					            });
							}
						}
					}
				});
				
			}
		});
	});

	$(".article-card").click(function(){
	   $(this).find("input.showOneArticle").click();
    });
	
	var slideTimer;
	var whoClickSilde;
	// 移動文章 - 1
	$(".arrowPic2").mousedown(function(){
		whoClickSilde = $(this);
		console.log("in mouseDown");
		slideTimer = setInterval(slidePage,50);
	});
	
	$(".arrowPic2").mouseup(function(){
		clearInterval(slideTimer);
	});
	
	function slidePage(){
		console.log("in slidePage");
		var x = $(".article").offset().left;  // 當前X位置
        var oriMove = $(".article").scrollLeft();  // 初始位置
		console.log("x " + x);
		console.log("oriMove " + oriMove);
		console.log($(this));
        if(whoClickSilde.hasClass("left")){
        	$(".article").scrollLeft(oriMove-15);
        	return;
        }else if(whoClickSilde.hasClass("right")){
        	$(".article").scrollLeft(oriMove+15);
        	return;
        }
	}
	
	// 移動文章 - 2 
	$(".article").mousedown(function(e){
		var x = e.pageX;
		var moveX = 0;
		var move = 0;
        var oriMove = $(this).scrollLeft();
		$(this).mousemove(function(e2){
			moveX=e2.pageX;
			move = (x-moveX);
			$(this).scrollLeft(move+oriMove);
		});
	});

	$(".article").mouseup(function(e){
		$(this).off("mousemove");
	});
	
</script>
<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery-migrate-3.0.0.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/awesomeFont.js"></script>
<script src="<%=request.getContextPath()%>/js/article/sweetalert2@9.js"></script>

</body>
</html>