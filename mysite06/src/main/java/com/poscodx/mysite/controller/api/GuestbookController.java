package com.poscodx.mysite.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poscodx.mysite.dto.JsonResult;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;

@RestController("gbApiController")
@RequestMapping("/api/gb")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping
	public JsonResult getGuestBook() {
		List<GuestbookVo> list = guestbookService.getContentsList();
		return JsonResult.success(list);
	}
	
	@PostMapping
	public JsonResult addGuestBook(@RequestBody GuestbookVo guestbookVo) {
		GuestbookVo createdVo = guestbookService.addEntry(guestbookVo);
		return JsonResult.success(createdVo);
	}
	
	@DeleteMapping("/{no}")
	public JsonResult deleteGuestBook(@PathVariable("no") Long no, @RequestBody Map<String, String> params) {
	    String password = params.get("password");
	    // 비밀번호 확인 및 내용 삭제
	    Boolean success = guestbookService.deleteContents(no, password);
	    System.out.println(no +  password);
	    if (success) {
	        return JsonResult.success(null);
	    } else {
	        return JsonResult.fail("비밀번호가 틀렸거나 삭제할 게시물이 없습니다.");
	    }
	}
}
