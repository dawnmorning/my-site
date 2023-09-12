package com.poscodx.mysite.web.mvc.board;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class BoardActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if ("writeform".equals(actionName)) {
			action = new WriteBoardFormAction();
		} else if("write".equals(actionName)) {
			action = new WriteBoardAction();
		}else if("view".equals(actionName)) {
			action = new ReadBoardAction();
		}
		else {
			action = new MainBoardAction();			
		}
		return action;
	}

}
