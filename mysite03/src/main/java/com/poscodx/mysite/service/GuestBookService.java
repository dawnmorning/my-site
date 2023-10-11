package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestBookRepository;

import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookRepository guestbookRepository;

	public List<GuestBookVo> getContentsList() {
		return guestbookRepository.findAll();
	}

	public Boolean deleteContents(Long no, String password) {
		return guestbookRepository.deleteByNoAndPassword(no, password);
	}

	public Boolean addContents(GuestBookVo vo) {
		return guestbookRepository.insert(vo);
	}
}
