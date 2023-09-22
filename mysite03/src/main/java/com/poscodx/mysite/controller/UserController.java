package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if (result.hasErrors()) {
//			System.out.println(result);
//			List<ObjectError> list = result.getAllErrors();
//			for (ObjectError error : list) {
//				System.out.println(error);
//			}
			//getmodel -> map
			model.addAllAttributes(result.getModel());
//			model.addAttribute("userVo",userVo); @ModelAttribute UserVo(객체)를 가지고 감
			return "user/join";
		}
		System.out.println(userVo);
		userService.addUser(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String auth(HttpSession session,
//			@RequestParam(value = "email", required = true, defaultValue = "") String email,
//			@RequestParam(value = "password", required = true, defaultValue = "") String password, Model model) {
//		UserVo authUser = userService.getUser(email, password);
//		if (authUser == null) {
//			model.addAttribute("email", email);
//			return "user/login";
//		} else {
//			/**
//			 * 인증 성공
//			 */
//			session.setAttribute("authUser", authUser);
//			return "redirect:/";
//		}
//
//	}
//
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}
	
	// form으로 이동
	@Auth
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		// Access Controll(접근 제어)
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if (authUser == null) {
//			return "redirect:/user/login";
//		}
		System.out.println("#####" + authUser);
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo", userVo);
		return "user/update";
	}
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(HttpSession session, UserVo userVo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		userVo.setNo(authUser.getNo());
		userService.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
}
