package com.poscodx.mysite.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	@Autowired
	private SqlSession sqlSession;

	public List<GuestBookVo> guestBookfindAll() {
		List<GuestBookVo> result = sqlSession.selectList("guestbook.guestBookfindAll");
		return result;
	}

	public Boolean guestBookInsert(GuestBookVo vo) {
		int count = sqlSession.insert("guestbook.guestBookInsert", vo);
		return count == 1;
	}

	public Boolean guestBookDeleteByPassWord(int no, String pw) {
		Map<String, Object >map = new HashMap<String, Object>(); 
		map.put("no", no);
		map.put("password", pw);
		int count = sqlSession.delete("guestbook.guestBookDeleteByPassWord", map);
		return count == 1;
	}
}
