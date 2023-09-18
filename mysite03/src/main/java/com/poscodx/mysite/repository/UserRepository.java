package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}

	public UserVo findByEamilAndPassword(String email, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.findByEamilAndPassword", map);
	}

	public UserVo GetUserByNo(Long no) {
		return sqlSession.selectOne("user.GetUserByNo", no);
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		// 1. JDBC Driver Class 로딩
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.174:3307/webdb?charset=utf8";
			// getConnection (url, 계정이름, 비밀번호)
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void update(UserVo userVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			// sql preparation
			String sql = "update user set name=?, password=password(?), gender=? where no=?";
			pstmt = conn.prepareStatement(sql);

			// binding
			pstmt.setString(1, userVo.getName());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getGender());
			pstmt.setLong(4, userVo.getNo());

			// sql execution
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
