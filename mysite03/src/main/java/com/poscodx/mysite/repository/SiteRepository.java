package com.poscodx.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public void update(SiteVo siteVo) {
		sqlSession.update("admin.update", siteVo);
	}

	public SiteVo getSite() {
		return sqlSession.selectOne("admin.getSite");
	}
	

	
}
