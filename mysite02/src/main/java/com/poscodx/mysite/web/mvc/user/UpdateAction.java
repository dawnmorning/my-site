package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String userNo = request.getParameter("userNo");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		UserDao userDao = new UserDao();
		UserVo userVo = userDao.GetUserByNo(Long.parseLong(userNo));
		
		userVo.setName(name);
		userVo.setPassword(password);
		userVo.setGender(gender);
		
		userDao.update(userVo);
		
		response.sendRedirect(request.getContextPath());

	}

}
