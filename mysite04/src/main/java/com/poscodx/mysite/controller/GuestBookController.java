package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.GuestBookService;
import com.poscodx.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	// 컨트롤러가 서비스 사용
	@Autowired
	private GuestBookService guestBookService;
	
//	@Auth(Role = "USER")
//	@Auth
	@RequestMapping("")
	public String main(Model model) {
		// 넣어서 보내줘야지
		List<GuestBookVo> list = guestBookService.getContentsList();
		model.addAttribute("list",list);
		return "guestbook/index";
	}
	
	// deleteform을 보여주는 함수
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	// 넘겨야 써먹을거 아니여?
	public String delete(@PathVariable("no") int no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") int no, @RequestParam(value="password",required=true, defaultValue="") String password) {
		guestBookService.deleteContents(no, password);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String insert(GuestBookVo vo) {
		guestBookService.insertContents(vo);
		return "redirect:/guestbook";
	}
}
