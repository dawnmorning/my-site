package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class ReadBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String currentPage = request.getParameter("curPage");
		BoardVo board = new BoardDao().getBoardByNo(no);
		new BoardDao().updateHit(no);
		request.setAttribute("board", board);
		request.setAttribute("currentPage", currentPage);

		request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
	}

}
