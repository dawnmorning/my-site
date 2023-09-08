package com.poscodx.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.web.mvc.Action;

public class GuestbookDelete implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int no = Integer.parseInt(request.getParameter("no"));
		String password = request.getParameter("password");

		GuestBookDao dao = new GuestBookDao();
		dao.guestBookDeleteByPassWord(no, password);
		// form 으로 유저가 왔고, 내가 뭘 해줄까?를 생각
		response.sendRedirect("/mysite02/guestbook");
	}

}
