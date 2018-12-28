package com.seating_area.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatingAreaJDBCDAO implements SeatingAreaDAO_interface{
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT=
			"INSERT INTO seating_area (ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber) "
			+ "VALUES ('ES'||LPAD(to_char(TICAREA_SEQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE seating_area set ticarea_color=?, ticarea_name=?, tictotalnumber=?, ticbookednumber=? "
			+ "WHERE ticarea_no=?";
			
	private static final String DELETE_STMT = 
			"DELETE FROM seating_area WHERE ticarea_no=?";
		 
	private static final String GET_ONE_STMT=
			"SELECT ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber "
			+ "FROM seating_area WHERE ticarea_no=?";
	
	private static final String GET_ALL_STMT=
			"SELECT ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber "
			+ "FROM seating_area ORDER BY ticarea_no";
	
	
	
	@Override
	public String insert(SeatingAreaVO seatingareaVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ticarea_no = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String[] cols = { "ticarea_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, seatingareaVO.getEve_no());
			pstmt.setString(2, seatingareaVO.getTictype_no());
			pstmt.setString(3, seatingareaVO.getTicarea_color());
			pstmt.setString(4, seatingareaVO.getTicarea_name());
			pstmt.setInt(5, seatingareaVO.getTictotalnumber());
			pstmt.setInt(6, seatingareaVO.getTicbookednumber());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ticarea_no = rs.getString(1);
			}
			
			System.out.println("----------Inserted : " + ticarea_no + "----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return ticarea_no;
	}
	
	@Override
	public void update(SeatingAreaVO seatingareaVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, seatingareaVO.getTicarea_color());
			pstmt.setString(2, seatingareaVO.getTicarea_name());
			pstmt.setInt(3, seatingareaVO.getTictotalnumber());
			pstmt.setInt(4, seatingareaVO.getTicbookednumber());
			pstmt.setString(5, seatingareaVO.getTicarea_no());

			pstmt.executeUpdate();
			
			System.out.println("----------Updated----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String ticarea_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ticarea_no);

			pstmt.executeUpdate();
			
			System.out.println("----------Deleted----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	} 

	@Override
	public SeatingAreaVO findByPrimaryKey(String ticarea_no) {
		
		SeatingAreaVO seatingareaVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ticarea_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				seatingareaVO = new SeatingAreaVO();
				seatingareaVO.setTicarea_no(rs.getString("ticarea_no"));
				seatingareaVO.setEve_no(rs.getString("eve_no"));
				seatingareaVO.setTictype_no(rs.getString("tictype_no"));
				seatingareaVO.setTicarea_color(rs.getString("ticarea_color"));
				seatingareaVO.setTicarea_name(rs.getString("ticarea_name"));
				seatingareaVO.setTictotalnumber(rs.getInt("tictotalnumber"));
				seatingareaVO.setTicbookednumber(rs.getInt("ticbookednumber"));
			}
			
			System.out.println("----------findByPrimaryKey finished----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return seatingareaVO;
	}

	@Override
	public List<SeatingAreaVO> getAll() {
		
		List<SeatingAreaVO> list = new ArrayList<SeatingAreaVO>();
		SeatingAreaVO seatingareaVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				seatingareaVO = new SeatingAreaVO();
				seatingareaVO.setTicarea_no(rs.getString("ticarea_no"));
				seatingareaVO.setEve_no(rs.getString("eve_no"));
				seatingareaVO.setTictype_no(rs.getString("tictype_no"));
				seatingareaVO.setTicarea_color(rs.getString("ticarea_color"));
				seatingareaVO.setTicarea_name(rs.getString("ticarea_name"));
				seatingareaVO.setTictotalnumber(rs.getInt("tictotalnumber"));
				seatingareaVO.setTicbookednumber(rs.getInt("ticbookednumber"));
				list.add(seatingareaVO);
			}

			System.out.println("----------getAll finished----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	
	

	
	
	
	
	public static void main (String[] args) {
		
		
		
		SeatingAreaJDBCDAO dao = new SeatingAreaJDBCDAO();
		
		
		
		// 新增	
//		SeatingAreaVO seatingareaVO1 = new SeatingAreaVO();	
//		seatingareaVO1.setEve_no("EV00002");
//		seatingareaVO1.setTictype_no("ET000008");	
//		seatingareaVO1.setTicarea_color("#EE7700");
//		seatingareaVO1.setTicarea_name("搖滾A3區");
//		seatingareaVO1.setTictotalnumber(new Integer(200));
//		seatingareaVO1.setTicbookednumber(new Integer(5));
//		dao.insert(seatingareaVO1);
		
		
		
		// 修改	
//		SeatingAreaVO seatingareaVO2 = new SeatingAreaVO();
//		seatingareaVO2.setTicarea_no("ES00000010");
//		seatingareaVO2.setTicarea_color("#EEEEEE");
//		seatingareaVO2.setTicarea_name("搖滾搖滾區");
//		seatingareaVO2.setTictotalnumber(new Integer(250));
//		seatingareaVO2.setTicbookednumber(new Integer(200));
//		dao.update(seatingareaVO2);
		
		
		
		// 刪除
//		dao.delete("ES00000010"); 
//		System.out.println("------------------------------");

		
		
		// 查詢一個
//		SeatingAreaVO seatingareaVO3 = dao.findByPrimaryKey("ES00000001");
//		System.out.println(seatingareaVO3.getTicarea_no());
//		System.out.println(seatingareaVO3.getEve_no());
//		System.out.println(seatingareaVO3.getTictype_no());
//		System.out.println(seatingareaVO3.getTicarea_color());
//		System.out.println(seatingareaVO3.getTicarea_name());
//		System.out.println(seatingareaVO3.getTictotalnumber());
//		System.out.println(seatingareaVO3.getTicbookednumber());
//		System.out.println("------------------------------");
		
		
		
		// 查詢全部
//		List<SeatingAreaVO> list = dao.getAll();
//		for(SeatingAreaVO aSeatingareaVO :list) {
//			System.out.println(aSeatingareaVO.getTicarea_no());
//			System.out.println(aSeatingareaVO.getEve_no());
//			System.out.println(aSeatingareaVO.getTictype_no());
//			System.out.println(aSeatingareaVO.getTicarea_color());
//			System.out.println(aSeatingareaVO.getTicarea_name());
//			System.out.println(aSeatingareaVO.getTictotalnumber());
//			System.out.println(aSeatingareaVO.getTicbookednumber());
//			System.out.println("------------------------------");
//		}
		
		
		
	}
	
	
	
}
