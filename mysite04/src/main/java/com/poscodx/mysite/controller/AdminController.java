package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

// role이 admin인 사람만 들어올 수 있다.
@Auth(Role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private SiteService siteService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		servletContext.setAttribute("siteVo", siteVo);
		System.out.println("---->" + servletContext);
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpdate(SiteVo vo, @RequestParam("profileImage") MultipartFile profileImage) {
		if (!profileImage.isEmpty()) {
			String imageUrl = fileUploadService.restore(profileImage);
			vo.setProfile(imageUrl);
		} else {
			SiteVo currentSiteVo = siteService.getSite();
			vo.setProfile(currentSiteVo.getProfile());
		}
		SiteVo site = applicationContext.getBean(SiteVo.class);

		siteService.UpdateSite(vo);
		servletContext.setAttribute("siteVo", vo);

//		site.setTitle(vo.getTitle());
//		site.setWelcome(vo.getWelcome());
//		site.setProfile(vo.getProfile());
//		site.setDescription(vo.getDescription());
		BeanUtils.copyProperties(vo, site);

		return "redirect:/admin";
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