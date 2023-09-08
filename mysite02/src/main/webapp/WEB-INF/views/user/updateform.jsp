<%@page import="com.poscodx.mysite.dao.UserDao"%>
<%@page import="com.poscodx.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
UserVo authUser = (UserVo) session.getAttribute("authUser");
Long userNo = authUser.getNo();
UserDao userDao = new UserDao();
UserVo userInfo = userDao.GetUserByNo(userNo);
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/user.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post"
					action="<%=request.getContextPath()%>/user">
					<input type="hidden" name="a" value="update"> 
					<input type="hidden" name="userNo" value="<%=userInfo.getNo() %>">
					<label class="block-label" for="name">이름</label> 
					<input id="name" name="name" type="text" value="<%=userInfo.getName()%>">
						
					<label class ="block-label" for="email">이메일</label>
					<h4><%= userInfo.getEmail() %></h4>

					<label class="block-label">패스워드</label> <input name="password"
						type="password" value="">

					<fieldset>
						<legend>성별</legend>
						<%
						if ("female".equals(userInfo.getGender())) {
						%>
						<label>여</label> <input type="radio" name="gender" value="female"
							checked="checked"> <label>남</label> <input type="radio"
							name="gender" value="male">
						<%
						} else {
						%>
						<label>여</label> <input type="radio" name="gender" value="female">
						<label>남</label> <input type="radio" name="gender" value="male"
							checked="checked">
						<%
						}
						%>
					</fieldset>


					<input type="submit" value="수정">
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>