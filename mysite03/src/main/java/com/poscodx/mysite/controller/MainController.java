package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.mysite.service.SiteService;

@Controller
public class MainController {
	// 가져와서 뿌려야함
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("/")
	public String main() {
		return "/main/index";
	}
}
