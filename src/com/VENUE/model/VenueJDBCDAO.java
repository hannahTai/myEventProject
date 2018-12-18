package com.VENUE.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VenueJDBCDAO implements VenueDAO_interface{

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO VENUE (VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC)"
					+"VALUES ('V'||LPAD(to_char(VENUE_SEQ.NEXTVAL), 3, '0'), ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = 
			"UPDATE VENUE SET VENUE_NAME=?,ADDRESS=?,LATITUDE=?,LONGITUDE=?,VENUE_INFO=?,VENUE_LOCATIONPIC=? WHERE VENUE_NO=?,";
	
	private static final String DELETE_STMT = 
			"DELETE FROM VENUE WHERE VENUE_NO=?";

	private static final String GET_ONE_STMT = 
			"SELECT VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC FROM VENUE WHERE VENUE_NO = ?";

	private static final String GET_ALL_STMT = 
			"SELECT VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC FROM VENUE";
	
	@Override
	public void insert(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,venueVO.getVenue_name());
			pstmt.setString(2,venueVO.getAddress());
			pstmt.setFloat(3,venueVO.getLatitude());
			pstmt.setFloat(4,venueVO.getLongitude());
			pstmt.setString(5,venueVO.getVenue_info());
			pstmt.setBytes(6,venueVO.getVenue_locationPic());
			
			pstmt.executeUpdate();
			
			System.out.println("Inserted");

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
	public void update(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1,venueVO.getVenue_name());
			pstmt.setString(2,venueVO.getAddress());
			pstmt.setFloat(3,venueVO.getLatitude());
			pstmt.setFloat(4,venueVO.getLongitude());
			pstmt.setString(5,venueVO.getVenue_info());
			pstmt.setBytes(6,venueVO.getVenue_locationPic());
			pstmt.setString(7,venueVO.getVenue_no());
		
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
	public void delete(String venue_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, venue_no);

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
	public VenueVO findByPrimaryKey(String venue_no) {
		VenueVO venueVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, venue_no); 
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				venueVO = new VenueVO();
				venueVO.setVenue_no(rs.getString("VENUE_NO"));
				venueVO.setVenue_name(rs.getString("VENUE_NAME"));
				venueVO.setAddress(rs.getString("ADDRESS"));
				venueVO.setLatitude(rs.getFloat("LATITUDE"));
				venueVO.setLongitude(rs.getFloat("LONGITUDE"));
				venueVO.setVenue_info(rs.getString("VENUE_INFO"));
				venueVO.setVenue_locationPic(rs.getBytes("VENUE_LOCATIONPIC"));
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
		return venueVO;
	}

	@Override
	public List<VenueVO> getAll() {
		List<VenueVO> list = new ArrayList<>();
		VenueVO venueVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				venueVO = new VenueVO();
				venueVO.setVenue_no(rs.getString("VENUE_NO"));
				venueVO.setVenue_name(rs.getString("VENUE_NAME"));
				venueVO.setAddress(rs.getString("ADDRESS"));
				venueVO.setLatitude(rs.getFloat("LATITUDE"));
				venueVO.setLongitude(rs.getFloat("LONGITUDE"));
				venueVO.setVenue_info(rs.getString("VENUE_INFO"));
				venueVO.setVenue_locationPic(rs.getBytes("VENUE_LOCATIONPIC"));
				list.add(venueVO);
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
		return list;
	}

}
