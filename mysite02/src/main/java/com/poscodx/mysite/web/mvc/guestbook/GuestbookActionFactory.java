package com.poscodx.mysite.web.mvc.guestbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class GuestbookActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if ("insert".equals(actionName)) {
			action = new InsertGuestbook();
		
		} else if ("deleteform".equals(actionName)) {
			action = new GuestbookDeleteForm();
		} else if ("delete".equals(actionName)) {
			action = new GuestbookDelete();
		} 
		else {
			action = new MainGuestbookAction();
		}
		
		return action;
	}

}
