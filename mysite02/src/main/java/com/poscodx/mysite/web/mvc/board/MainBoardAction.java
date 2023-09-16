package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;

public class MainBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int currentPage = 1;
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		String searchKeyword = request.getParameter("kwd");
		if (searchKeyword == null) {
			searchKeyword = "";
		}

		int itemsPerPage = 10; // 한 페이지에 표시될 게시물 수
		int pagesToShow = 5; // 한 번에 표시할 페이지 수

		// DAO를 통해 검색 기능과 페이지네이션 기능을 지원하는 메서드 필요
		List<BoardVo> boardList = new BoardDao().findPageByKeyword(currentPage, itemsPerPage, searchKeyword);

		int totalItems = new BoardDao().getTotalCountByKeyword(searchKeyword); // 검색어에 해당하는 총 게시물 수를 가져와야 함
		int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

		// 시작 페이지 및 종료 페이지 계산
		int startPage = ((currentPage - 1) / pagesToShow) * pagesToShow + 1;
		int endPage = startPage + pagesToShow - 1;

		if (endPage > totalPages) {
			endPage = totalPages;
		}

		request.setAttribute("list", boardList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("kwd", searchKeyword);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
