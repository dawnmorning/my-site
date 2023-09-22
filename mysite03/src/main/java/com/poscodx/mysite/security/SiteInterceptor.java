package com.poscodx.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	private ServletContext sc;
	@Autowired
	private SiteService siteService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		sc = request.getServletContext();
		SiteVo siteVo = siteService.getSite();
		sc.setAttribute("siteVo", siteVo);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
}
