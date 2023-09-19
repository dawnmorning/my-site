package com.poscodx.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception e, Model model) {
		// 1. 로깅(Logging)
//		System.out.println(e);
		StringWriter error = new StringWriter();
		e.printStackTrace(new PrintWriter(error));
		System.out.println(error.toString());
		model.addAttribute("error", error);
		// 2. 사과 페이지
		return "error/exception";
	}
}
