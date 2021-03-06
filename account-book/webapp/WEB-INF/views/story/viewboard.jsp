<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 해더 //최상단 메뉴 -->
<c:import url="/WEB-INF/views/include/login_top.jsp" />

<!-- 각페이지에서 사용하는 css, js 링크영역 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/story/story.css">
<script src="${pageContext.request.contextPath}/assets/js/story/viewboard.js"></script>

    <div class="container">
    	<div class="row mt">
					<div class="col-md-12">
						
						<!-- 결산월 이야기 -->
						<h1 align="center">결산월 이야기</h1>
						<form action="${pageContext.request.contextPath}/story" method="post">
							<input type="search" name="search" class="searchsize">
							<button type="submit" class="fa fa-search fa-2x sr-contact"></button>
						</form>
					    <table class="table">
					      <thead>
					        <tr>
					          <th>결산월</th>
					          <th>제목</th>
					          <th>작성자</th>
					          <th>작성일</th>
					          <th>추천</th>
					          <th>조회</th>
					        </tr>
					      </thead>
					      <tbody>
					      	<c:forEach var="board" items="${board.list}">
					      		<script>
						      		arrays.push("${board.boardId}");
					      		</script>
						        <tr>
						          <td>${board.month}</td>
						          <td><label id="${board.boardId}">${board.title}</label></td>
						          <td>${board.name}</td>
						          <td>${board.day}</td>
						          <td>${board.good}</td>
						          <td>${board.hit}</td>
						        </tr>
					     	</c:forEach>
					      </tbody>
					    </table>
					    
					    <!-- 페이징처리 -->
					    <div class="pager">
							<ul>
								<c:if test="${board.prevPage > 0}" >
									<li><a href="${pageContext.request.contextPath}/story?p=${board.prevPage}&search=${board.keyword}">◀</a></li>
								</c:if>
								
								<c:forEach begin="${board.beginPage}" end="${board.beginPage + board.listSize - 1}" var="page">
									<c:choose>
										<c:when test="${board.endPage < page}">
											<li>${page}</li> 
										</c:when> 
										<c:when test="${board.currentPage == page}">
											<li class="selected">${page}</li>
										</c:when>
										<c:otherwise> 
											<li><a href="${pageContext.request.contextPath}/story?p=${page}&search=${board.keyword}">${page}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								
								<c:if test="${board.nextPage > 0}" >
									<li><a href="${pageContext.request.contextPath}/story?p=${board.nextPage}&search=${board.keyword}">▶</a></li>
								</c:if>
							</ul>
						</div>
						<!-- /페이징처리 -->
					</div>
					<!-- /게시판 -->
				</div>
	    	</div>
<c:import url="/WEB-INF/views/include/main_bottom.jsp"/>
<c:import url="/WEB-INF/views/story/import_main_popup.jsp"/>