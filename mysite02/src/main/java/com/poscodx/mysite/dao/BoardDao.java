package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<BoardVo>();
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
			String sql = "select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, u.name "
					+ "from board b " + "join user u " + "on b.user_no = u.no" + " order by g_no DESC, o_no ASC";
			pstmt = conn.prepareStatement(sql);

			// 5. sql 실행

			rs = pstmt.executeQuery();

			// 6. 결과처리
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_day = rs.getString(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				long user_no = rs.getLong(9);
				String userName = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setBoardNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setDate(reg_day);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setUserName(userName);

				result.add(vo);

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

	public BoardVo getBoardByNo(String no) {
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC Driver Class 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.0.174:3307/webdb?chraset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. sql 준비
			String sql = "SELECT * FROM board WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);

			// 4. sql 실행
			rs = pstmt.executeQuery();

			// 5. 결과처리
			if (rs.next()) {
				result = new BoardVo();
				result.setBoardNo(rs.getInt("no"));
				result.setTitle(rs.getString("title"));
				result.setContents(rs.getString("contents"));
				result.setHit(rs.getInt("hit"));
				result.setDate(rs.getString("reg_date"));
				result.setG_no(rs.getInt("g_no"));
				result.setO_no(rs.getInt("o_no"));
				result.setDepth(rs.getInt("depth"));
				result.setUser_no(rs.getLong("user_no"));

			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				// 6. 자원 처리
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

	public int getNextGroupNo() {
		int result = 1;
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
			String sql = "SELECT COALESCE(MAX(g_no), 0) + 1 AS nextGroupNo FROM board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// 6. 결과처리
			while (rs.next()) {
				result = rs.getInt("nextGroupNo");
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

	public int getNextObjectNo() {
		int result = 1; // 기본적으로 1로 시작
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
			// 3. sql 준비
			String sql = "SELECT MAX(o_no) + 1 AS nextOrderNo FROM board WHERE g_no = ?";
			pstmt = conn.prepareStatement(sql);
			BoardVo vo = new BoardVo();
			pstmt.setInt(1, vo.getG_no());
			rs = pstmt.executeQuery();
			// 6. 결과처리
			if (rs.next()) {
				result = rs.getInt("nextOrderNo");
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

	public int getNextDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			// 3. sql 준비
			String sql = "insert into board values (null,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			// 4. binding
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, 1);
			pstmt.setString(4, vo.getDate());
			pstmt.setInt(5, vo.getG_no());
			pstmt.setInt(6, vo.getO_no());
			pstmt.setInt(7, vo.getDepth());
			pstmt.setLong(8, vo.getUser_no());

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

	public void Update(int g_no, int o_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "update board" + " set o_no = o_no + 1" + " where g_no = ? and o_no >= ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, g_no);
			pstmt.setInt(2, o_no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void modifyBoardByNo(String no, String title, String contents) {
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
			// 3. sql 준비
			String sql = "update board " + " set title=?, contents=?, reg_date=current_time() " + " where no=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setInt(3, Integer.parseInt(no));
			pstmt.executeUpdate();
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
	}

	public void deleteBoardByNo(String no) {
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
			// 3. sql 준비
			String sql = "delete from board where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(no));
			pstmt.executeUpdate();
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
	}

	public void updateHit(String no) {
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
			// 3. sql 준비
			String sql = "update board "
					+ " set hit = hit + 1 "
					+ " where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(no));
			pstmt.executeUpdate();
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
	}
}
