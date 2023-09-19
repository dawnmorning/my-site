package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestBookRepository;
import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {

	// 서비스가 레포지토리 사용
	@Autowired
	private GuestBookRepository guestBookRepository;

	public List<GuestBookVo> getContentsList() {
		return guestBookRepository.guestBookfindAll();
	}

	public void deleteContents(int no, String password) {
		guestBookRepository.guestBookDeleteByPassWord(no, password);
	}

	public void insertContents(GuestBookVo vo) {
		guestBookRepository.guestBookInsert(vo);
	}
}