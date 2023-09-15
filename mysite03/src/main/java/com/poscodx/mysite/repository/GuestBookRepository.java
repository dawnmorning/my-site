package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	public List<GuestBookVo> guestBookfindAll() {
		List<GuestBookVo> result = new ArrayList<GuestBookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.174:3307/webdb?chraset=utf8";
			// getConnection (url, 계정이름, 비밀번호)
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결 성공");
			// 3. sql 준비
			String sql = "select * from guestbook";
			pstmt = conn.prepareStatement(sql);

			// 5. sql 실행

			rs = pstmt.executeQuery();

			// 6. 결과처리
			while (rs.next()) {
				int no = rs.getInt(1);
				String password = rs.getString(2);
				String name = rs.getString(3);
				String contents = rs.getString(4);
				String reg_day = rs.getString(5);

				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setPassword(password);
				vo.setName(name);
				vo.setContents(contents);
				vo.setDate(reg_day);

				result.add(vo);
//				System.out.println(empNo + " : " +  firstName + lastName);

			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 7. 자원 처리
				if (rs != null) {
					rs.close();
				}
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
		return result;
	}

	public void guestBookInsert(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			// 3. sql 준비
			String sql = "insert into guestbook values (null,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());

			// 5. sql 실행
			pstmt.executeQuery();

			// 6. 결과처리 생략

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
	public void guestBookDeleteByPassWord(int no, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.174:3307/webdb?chraset=utf8";
			// getConnection (url, 계정이름, 비밀번호)
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			System.out.println("연결 성공");
			// 3. sql 준비
			String sql = "delete from guestbook where no=? and password=?";
			pstmt = conn.prepareStatement(sql);
			// 4. binding
			pstmt.setInt(1, no);
	        pstmt.setString(2, pw);
			// 5. sql 실행
			pstmt.executeQuery();

			// 6. 결과처리 생략
//			while (rs.next()) {
//				Long no = rs.getLong(1);
//				String firstName = rs.getString(2);
//				String lastName = rs.getString(3);
//				String email = rs.getString(4);
//
//				EmailListVo vo = new EmailListVo();
//				vo.setNo(no);
//				vo.setFirstName(firstName);
//				vo.setLastName(lastName);
//				vo.setEmail(email);
//
//				result.add(vo);
////						System.out.println(empNo + " : " +  firstName + lastName);
//
//			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 7. 자원 처리
//				if (rs != null) {
//					rs.close();
//				}
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
