package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.repository.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=login");
			return;
		}
		String no = request.getParameter("no");
		BoardVo list = new BoardDao().getBoardByNo(no);
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
		rd.forward(request, response);
	}
}
