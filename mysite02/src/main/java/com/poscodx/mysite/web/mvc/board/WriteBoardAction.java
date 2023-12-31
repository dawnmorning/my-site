package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class WriteBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=login");
			return;
		} else {
			request.setAttribute("authUser", authUser);
		}
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String reg_Date = formatter.format(currentDate);

		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setReg_date(reg_Date);

		long user_no = authUser.getNo();
		vo.setUser_no(user_no);

		String no = request.getParameter("no");

		if (no == null || "".equals(no)) {
			vo.setG_no(new BoardDao().getNextGroupNo());
			vo.setDepth(1);
			vo.setO_no(1);
		} else {
			BoardVo existedVo = new BoardDao().getBoardByNo(no);
			vo.setReg_date(reg_Date);
			vo.setG_no(existedVo.getG_no());
			vo.setO_no(existedVo.getO_no() + 1);
			vo.setDepth(existedVo.getDepth() + 1);
			new BoardDao().Update(existedVo.getG_no(),existedVo.getO_no()+1);
		}
		new BoardDao().insert(vo);
		System.out.println(title + contents + currentDate + reg_Date + user_no + no + " " + vo.getG_no() + " " + vo.getO_no() + " " + vo.getDepth());
		response.sendRedirect("/mysite02/board");
	}

}
