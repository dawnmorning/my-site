<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		</div>
		<div id="wrapper">
			<div id="content">
				<div id="board">
					<form id="search_form"
						action="${pageContext.request.contextPath }/board" method="get">
						<input type="text" id="kwd" name="kwd" value="${kwd}"> <input
							type="submit" value="찾기">
					</form>
					<table class="tbl-ex">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>조회수</th>
							<th>작성일</th>
							<th>&nbsp;</th>
						</tr>
						<c:forEach var="item" items="${list }" varStatus="status">
							<c:set var="idx" value="${fn:length(list) }" />
							<tr>
								<td>${item.no }</td>
								<!-- padding 계산해야 함  vo.depth-1 -->
								<td style="padding-left: ${(item.depth-1)* 30}px "><c:if
										test="${item.depth >= 2 }">
										<img
											src="${pageContext.request.contextPath }/assets/images/reply.png" />
									</c:if> <a
									href="${pageContext.request.contextPath }/board?b=view&no=${item.no }&curPage=${currentPage}">${item.title }</a>
								</td>
								<td>${item.userName }</td>
								<td>${item.hit }</td>
								<td>${item.reg_date }</td>
								<td><a
									href="${pageContext.request.contextPath }/board?b=delete&no=${item.no}"
									class="del">삭제</a></td>
							</tr>
						</c:forEach>
					</table>
					<!-- pager 추가 -->
					<div class="pager">
						<ul>
							<!-- 이전 페이지 그룹 링크 -->
							<c:if test="${startPage > 1}">
								<li><a href="?page=${startPage - 1}&kwd=${kwd}">◀</a></li>
							</c:if>

							<!-- 페이지 번호 출력 -->
							<c:forEach var="i" begin="${startPage}" end="${endPage}">
								<c:choose>
									<c:when test="${i == currentPage}">
										<li class="selected">${i}</li>
									</c:when>
									<c:otherwise>
										<li><a href="?page=${i}&kwd=${kwd}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<!-- 다음 페이지 그룹 링크 -->
							<c:if test="${endPage < totalPages}">
								<li><a href="?page=${endPage + 1}&kwd=${kwd}">▶</a></li>
							</c:if>
						</ul>
					</div>
					<!-- pager 추가 -->
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?b=writeform"
							id="new-book">글쓰기</a>
					</div>
				</div>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>