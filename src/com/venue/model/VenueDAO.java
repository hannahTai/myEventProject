package com.venue.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
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

public class VenueDAO implements VenueDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ETIckeTsDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO VENUE (VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC)"
			+ "VALUES ('V'||LPAD(to_char(VENUE_SEQ.NEXTVAL), 3, '0'), ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = "UPDATE VENUE SET VENUE_NAME=?,ADDRESS=?,LATITUDE=?,LONGITUDE=?,VENUE_INFO=?,VENUE_LOCATIONPIC=? WHERE VENUE_NO=?";

	private static final String DELETE_STMT = "DELETE FROM VENUE WHERE VENUE_NO=?";

	private static final String GET_ONE_STMT = "SELECT venue_no, venue_name,address,latitude,longitude,venue_info,venue_locationPic FROM VENUE WHERE venue_no = ?";

	private static final String GET_ALL_STMT = "SELECT VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC FROM VENUE";
	
	private static final String UPDATE_STMT_withoutLocationPic = "UPDATE VENUE SET VENUE_NAME=?,ADDRESS=?,LATITUDE=?,LONGITUDE=?,VENUE_INFO=? WHERE VENUE_NO=?";


	@Override
	public String insert(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String venue_no = null;

		try {
			con = ds.getConnection();
			
			String[] cols = { "venue_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, venueVO.getVenue_name());
			pstmt.setString(2, venueVO.getAddress());
			pstmt.setDouble(3, venueVO.getLatitude());
			pstmt.setDouble(4, venueVO.getLongitude());
			pstmt.setCharacterStream(5, new StringReader(venueVO.getVenue_info()));
			pstmt.setBytes(6, venueVO.getVenue_locationPic());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				venue_no = rs.getString(1);
			}

			System.out.println("----------Inserted----------");

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
		return venue_no;
	}

	@Override
	public void update(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, venueVO.getVenue_name());
			pstmt.setString(2, venueVO.getAddress());
			pstmt.setDouble(3, venueVO.getLatitude());
			pstmt.setDouble(4, venueVO.getLongitude());
			pstmt.setCharacterStream(5, new StringReader(venueVO.getVenue_info()));
			pstmt.setBytes(6, venueVO.getVenue_locationPic());
			pstmt.setString(7, venueVO.getVenue_no());

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
	public void delete(String venue_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, venue_no);

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
	public VenueVO findByPrimaryKey(String venue_no) {
		VenueVO venueVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BufferedReader br = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, venue_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				venueVO = new VenueVO();
				venueVO.setVenue_no(rs.getString("venue_no"));
				venueVO.setVenue_name(rs.getString("venue_name"));
				venueVO.setAddress(rs.getString("address"));
				venueVO.setLatitude(rs.getDouble("latitude"));
				venueVO.setLongitude(rs.getDouble("longitude"));

				if (rs.getCharacterStream("venue_info") == null) {
					venueVO.setVenue_info("");
				} else {
					br = new BufferedReader(rs.getCharacterStream("venue_info"));
					StringBuilder infoSb = new StringBuilder();
					String infoStr = null;
					while ((infoStr = br.readLine()) != null)
						infoSb.append(infoStr).append("\n");
					venueVO.setVenue_info(infoSb.toString());
				}

				venueVO.setVenue_locationPic(rs.getBytes("venue_locationPic"));
			}

			System.out.println("----------findByPrimaryKey finished----------");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (IOException ie) {
			throw new RuntimeException("An IO error occured. " + ie.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					ioe.printStackTrace(System.err);
				}
			}
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
		BufferedReader br = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				venueVO = new VenueVO();
				venueVO.setVenue_no(rs.getString("VENUE_NO"));
				venueVO.setVenue_name(rs.getString("VENUE_NAME"));
				venueVO.setAddress(rs.getString("ADDRESS"));
				venueVO.setLatitude(rs.getDouble("LATITUDE"));
				venueVO.setLongitude(rs.getDouble("LONGITUDE"));
				if (rs.getCharacterStream("VENUE_INFO") == null) {
					venueVO.setVenue_info("");
				} else {
					br = new BufferedReader(rs.getCharacterStream("VENUE_INFO"));
					StringBuilder infoSb = new StringBuilder();
					String infoStr = null;
					while ((infoStr = br.readLine()) != null)
						infoSb.append(infoStr).append("\n");
					venueVO.setVenue_info(infoSb.toString());
				}
				venueVO.setVenue_locationPic(rs.getBytes("VENUE_LOCATIONPIC"));
				list.add(venueVO);
			}

			System.out.println("----------getAll finished----------");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (IOException ie) {
			throw new RuntimeException("An IO error occured. " + ie.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ioe) {
					ioe.printStackTrace(System.err);
				}
			}
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
	
	
	@Override
	public void update_withoutLocationPic(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT_withoutLocationPic);

			pstmt.setString(1, venueVO.getVenue_name());
			pstmt.setString(2, venueVO.getAddress());
			pstmt.setDouble(3, venueVO.getLatitude());
			pstmt.setDouble(4, venueVO.getLongitude());
			pstmt.setCharacterStream(5, new StringReader(venueVO.getVenue_info()));
			pstmt.setString(6, venueVO.getVenue_no());

			pstmt.executeUpdate();

			System.out.println("----------update_withoutLocationPic done----------");

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
}
