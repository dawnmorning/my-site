package com.poscodx.mysite.web.mvc.guestbook;

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
