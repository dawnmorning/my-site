package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscodx.mysite.vo.UserVo;

public class UserDao {
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			// 3. sql 준비
			String sql = "insert into user values (null,?,?,password(?),?, current_date())";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

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

	public UserVo findByEamilAndPassword(String email, String password) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. sql 준비
			String sql = "select no, name" 
					+ " from user" 
					+ " where email = ? and password=password(?)";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			// 5. sql 실행
			rs = pstmt.executeQuery();

			// 6. 결과처리
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
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
		return userVo;
	}
	public UserVo GetUserByNo(Long no) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 3. sql 준비
			String sql = "select *" 
					+ " from user" 
					+ " where no=?";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setLong(1, no);

			// 5. sql 실행
			rs = pstmt.executeQuery();

			// 6. 결과처리
			if(rs.next()) {
				Long userNo = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				String gender = rs.getString(5);				
				userVo = new UserVo();
				userVo.setNo(userNo);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setPassword(password);
				userVo.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
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
		return userVo;
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
