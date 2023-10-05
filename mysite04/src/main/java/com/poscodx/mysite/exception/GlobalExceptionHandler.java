package com.poscodx.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception e, Model model) {
		// 1. 로깅(Logging)
//		System.out.println(e);
		StringWriter error = new StringWriter();
		e.printStackTrace(new PrintWriter(error));
		System.out.println(error.toString());
		logger.error(error.toString());
		
		// 2. 사과 페이지
		model.addAttribute("error", error);
		return "error/exception";
	}
}
