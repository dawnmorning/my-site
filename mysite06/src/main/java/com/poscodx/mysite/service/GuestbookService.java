package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getContentsList() {
		return guestbookRepository.findAll();
	}
	
	public Boolean deleteContents(Long no, String password) {
		return guestbookRepository.deleteByNoAndPassword(no, password);
	}
	
	public Boolean addContents(GuestbookVo vo) {
		return guestbookRepository.insert(vo);
	}

	public GuestbookVo addEntry(GuestbookVo guestbookVo) {
		if(guestbookRepository.insert(guestbookVo)) {
            return guestbookVo; // 성공적으로 추가된 경우
        }
        return null; // 추가하지 못한 경우
	}

}
