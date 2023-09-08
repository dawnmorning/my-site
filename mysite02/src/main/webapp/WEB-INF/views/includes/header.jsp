<%@page import="com.poscodx.mysite.dao.UserDao"%>
<%@page import="com.poscodx.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% 
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	Long userNo = null;
	UserVo userInfo = null;
	if(authUser != null) {
	    userNo = authUser.getNo();
	    UserDao userDao = new UserDao();
	    userInfo = userDao.GetUserByNo(userNo);
	}
	%>
<div id="header">
	<h1>MySite</h1>
	<ul>
	<% if (authUser == null) {%>
		<li><a href="<%=request.getContextPath()%>/user?a=loginform">로그인</a><li>
		<li><a href="<%=request.getContextPath()%>/user?a=joinform">회원가입</a><li>
		<% } else { %>
		<li><a href="<%=request.getContextPath()%>/user?a=updateform">회원정보수정</a><li>
		<li><a href="<%=request.getContextPath()%>/user?a=logout">">로그아웃</a><li>
		<li><%= userInfo.getName() %>님 안녕하세요 ^^;</li>
		<% } %>
	</ul>
</div>