package com.seating_area.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SeatingAreaDAO implements SeatingAreaDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ETIckeTsDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT=
			"INSERT INTO seating_area (ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber) "
			+ "VALUES ('ES'||LPAD(to_char(TICAREA_SEQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE seating_area set ticarea_color=?, ticarea_name=?, tictotalnumber=? "
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
			con = ds.getConnection();
			
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, seatingareaVO.getTicarea_color());
			pstmt.setString(2, seatingareaVO.getTicarea_name());
			pstmt.setInt(3, seatingareaVO.getTictotalnumber());
			pstmt.setString(4, seatingareaVO.getTicarea_no());

			pstmt.executeUpdate();
			
			System.out.println("----------Updated----------");

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ticarea_no);

			pstmt.executeUpdate();
			
			System.out.println("----------Deleted----------");

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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
	
	
	
}
