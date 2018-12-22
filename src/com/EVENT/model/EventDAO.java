package com.EVENT.model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements EventDAO_interface{

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO EVENT (eve_no, evetit_no, venue_no, eve_session, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE EVENT SET venue_no=?, eve_sessionname=?, eve_seatmap=?, "
			+ "eve_startdate=?, eve_enddate=?, eve_onsaledate=?, eve_offsaledate=?, ticlimit=?, fullrefundenddate=?, eve_status=? "
			+ "WHERE eve_no=?";

	private static final String DELETE_STMT = 
			"DELETE FROM EVENT WHERE eve_no=?";
	
	private static final String GET_ONE_STMT = 
			"SELECT eve_no, evetit_no, venue_no, eve_session, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status "
			+ "FROM EVENT WHERE eve_no=?";
	
	private static final String GET_ALL_STMT = 
			"SELECT eve_no, evetit_no, venue_no, eve_session, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status "
			+ "FROM EVENT ORDER BY eve_no";


	
	@Override
	public void insert(EventVO eventVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eventVO.getEve_no()); 
			pstmt.setString(2, eventVO.getEvetit_no()); 
			pstmt.setString(3, eventVO.getVenue_no());
			pstmt.setString(4, eventVO.getEve_session());
			pstmt.setString(5, eventVO.getEve_sessionname());		
			pstmt.setBytes(6, eventVO.getEve_seatmap());	
			pstmt.setTimestamp(7, eventVO.getEve_startdate());							
			pstmt.setTimestamp(8, eventVO.getEve_enddate());		
			pstmt.setTimestamp(9, eventVO.getEve_onsaledate()); 				
			pstmt.setTimestamp(10, eventVO.getEve_offsaledate());			
			pstmt.setInt(11, eventVO.getTiclimit());			
			pstmt.setTimestamp(12, eventVO.getFullrefundenddate());
			pstmt.setString(13, eventVO.getEve_status());
		
			pstmt.executeUpdate();
			
			System.out.println("----------Inserted----------");

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
	public void update(EventVO eventVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, eventVO.getVenue_no());			
			pstmt.setString(2, eventVO.getEve_sessionname());		
			pstmt.setBytes(3, eventVO.getEve_seatmap());	
			pstmt.setTimestamp(4, eventVO.getEve_startdate());							
			pstmt.setTimestamp(5, eventVO.getEve_enddate());		
			pstmt.setTimestamp(6, eventVO.getEve_onsaledate()); 				
			pstmt.setTimestamp(7, eventVO.getEve_offsaledate());			
			pstmt.setInt(8, eventVO.getTiclimit());			
			pstmt.setTimestamp(9, eventVO.getFullrefundenddate());
			pstmt.setString(10, eventVO.getEve_status());
			pstmt.setString(11, eventVO.getEve_no()); 
		
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
	public void delete(String eve_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, eve_no);

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
	public EventVO findByPrimaryKey(String eve_no) {
		
		EventVO eventVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, eve_no); 
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventVO = new EventVO();				
				eventVO.setEve_no(rs.getString("eve_no"));
				eventVO.setEvetit_no(rs.getString("evetit_no"));
				eventVO.setVenue_no(rs.getString("venue_no"));
				eventVO.setEve_session(rs.getString("eve_session"));
				eventVO.setEve_sessionname(rs.getString("eve_sessionname"));				
				eventVO.setEve_seatmap(rs.getBytes("eve_seatmap"));
				eventVO.setEve_startdate(rs.getTimestamp("eve_startdate"));
				eventVO.setEve_enddate(rs.getTimestamp("eve_enddate"));				
				eventVO.setEve_onsaledate(rs.getTimestamp("eve_onsaledate"));
				eventVO.setEve_offsaledate(rs.getTimestamp("eve_offsaledate"));
				eventVO.setTiclimit(rs.getInt("ticlimit"));
				eventVO.setFullrefundenddate(rs.getTimestamp("fullrefundenddate"));
				eventVO.setEve_status(rs.getString("eve_status"));
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
		return eventVO;
	}

	@Override
	public List<EventVO> getAll() {
		
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventVO = new EventVO();				
				eventVO.setEve_no(rs.getString("eve_no"));
				eventVO.setEvetit_no(rs.getString("evetit_no"));
				eventVO.setVenue_no(rs.getString("venue_no"));
				eventVO.setEve_session(rs.getString("eve_session"));
				eventVO.setEve_sessionname(rs.getString("eve_sessionname"));				
				eventVO.setEve_seatmap(rs.getBytes("eve_seatmap"));
				eventVO.setEve_startdate(rs.getTimestamp("eve_startdate"));
				eventVO.setEve_enddate(rs.getTimestamp("eve_enddate"));				
				eventVO.setEve_onsaledate(rs.getTimestamp("eve_onsaledate"));
				eventVO.setEve_offsaledate(rs.getTimestamp("eve_offsaledate"));
				eventVO.setTiclimit(rs.getInt("ticlimit"));
				eventVO.setFullrefundenddate(rs.getTimestamp("fullrefundenddate"));
				eventVO.setEve_status(rs.getString("eve_status"));
				list.add(eventVO);
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

}
