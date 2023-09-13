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
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
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
								<td>${idx - status.index }</td>
								<!-- padding 계산해야 함  vo.depth-1 -->
								<td style="padding-left: ${(item.depth-1)* 30}px ">
									<c:if test="${item.depth >= 2 }">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png"/>
									</c:if>
									<a href="${pageContext.request.contextPath }/board?b=view&no=${item.boardNo }">${item.title }</a>
								</td>
								<td>${item.userName }</td>
								<td>${item.hit }</td>
								<td>${item.date }</td>
								<td><a href="${pageContext.request.contextPath }/board?b=delete&no=${item.boardNo}" class="del">삭제</a></td>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>
				<!-- pager 추가 -->
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?b=writeform" id="new-book">글쓰기</a>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>