package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

// role이 admin인 사람만 들어올 수 있다.
@Auth(Role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SiteService siteService;

	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpdate(@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "welcome", required = true, defaultValue = "") String welcome,
			@RequestParam(value = "description", required = true, defaultValue = "") String description) {

		SiteVo vo = new SiteVo();
		vo.setTitle(title);
		vo.setWelcome(welcome);
		vo.setDescription(description);

		siteService.UpdateSite(vo);
		return "admin/main";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
