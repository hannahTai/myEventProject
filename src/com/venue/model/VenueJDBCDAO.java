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

public class VenueJDBCDAO implements VenueDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO VENUE (VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC)"
			+ "VALUES ('V'||LPAD(to_char(VENUE_SEQ.NEXTVAL), 3, '0'), ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = "UPDATE VENUE SET VENUE_NAME=?,ADDRESS=?,LATITUDE=?,LONGITUDE=?,VENUE_INFO=?,VENUE_LOCATIONPIC=? WHERE VENUE_NO=?";

	private static final String DELETE_STMT = "DELETE FROM VENUE WHERE VENUE_NO=?";

	private static final String GET_ONE_STMT = "SELECT venue_no, venue_name,address,latitude,longitude,venue_info,venue_locationPic FROM VENUE WHERE venue_no = ?";

	private static final String GET_ALL_STMT = "SELECT VENUE_NO,VENUE_NAME,ADDRESS,LATITUDE,LONGITUDE,VENUE_INFO,VENUE_LOCATIONPIC FROM VENUE";

	@Override
	public void insert(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, venueVO.getVenue_name());
			pstmt.setString(2, venueVO.getAddress());
			pstmt.setDouble(3, venueVO.getLatitude());
			pstmt.setDouble(4, venueVO.getLongitude());
			pstmt.setCharacterStream(5, new StringReader(venueVO.getVenue_info()));
			pstmt.setBytes(6, venueVO.getVenue_locationPic());

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
	public void update(VenueVO venueVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		BufferedReader br = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {

		VenueJDBCDAO dao = new VenueJDBCDAO();

		// 新增
//		FileInputStream fis1 = null;
//		ByteArrayOutputStream baos1 = null;	
//		try {
//			VenueVO VenueVO1 = new VenueVO();
//			
//			VenueVO1.setVenue_name("A"); 
//			VenueVO1.setAddress("This is address");
//			VenueVO1.setLatitude(10.5666666);
//			VenueVO1.setLongitude(134.555555);
//			VenueVO1.setVenue_info("This is venue_info");
//			
//			fis1 = new FileInputStream("writeImgJDBC/tomcat.jpg");		  //B	
//			baos1 = new ByteArrayOutputStream();			
//			int i;
//			while ((i = fis1.read()) != -1)
//				baos1.write(i);			
//			VenueVO1.setVenue_locationPic(baos1.toByteArray());
//			
//			dao.insert(VenueVO1);
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (baos1 != null) {
//				try {
//					baos1.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (fis1 != null) {
//				try {
//					fis1.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		
		
		
		
		// 修改
//		FileInputStream fis2 = null;
//		ByteArrayOutputStream baos2 = null;	
//		try {
//			VenueVO VenueVO2 = new VenueVO();
//			VenueVO2.setVenue_no("V001"); 
//			VenueVO2.setVenue_name("CCC"); 
//			VenueVO2.setAddress("This is address2");
//			VenueVO2.setLatitude(10.5666666);
//			VenueVO2.setLongitude(134.555555);
//			VenueVO2.setVenue_info("This is venue_info");
//			
//			fis2 = new FileInputStream("writeImgJDBC/tomcat.jpg");		  //B	
//			baos2 = new ByteArrayOutputStream();			
//			int i;
//			while ((i = fis2.read()) != -1)
//				baos2.write(i);			
//			VenueVO2.setVenue_locationPic(baos2.toByteArray());
//			
//			dao.update(VenueVO2);
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (baos2 != null) {
//				try {
//					baos2.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (fis2 != null) {
//				try {
//					fis2.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		
		
		
		// 刪除
//		dao.delete("V002");
//		System.out.println("------------------------------");
		
		
		// 查詢一個
//		VenueVO VenueVO3 = dao.findByPrimaryKey("V003");
//		System.out.println(VenueVO3.getVenue_no());
//		System.out.println(VenueVO3.getVenue_name());
//		System.out.println(VenueVO3.getAddress());
//		System.out.println(VenueVO3.getLatitude());
//		System.out.println(VenueVO3.getLongitude());
//		System.out.println(VenueVO3.getVenue_info());
//																							//B
//		try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/venueTest.jpg"), true)){
//			if(VenueVO3.getVenue_locationPic() == null) {
//				
//			} else {
//				ps.write(VenueVO3.getVenue_locationPic());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//		System.out.println("------------------------------");
		
		
		
		// 查詢全部
		List<VenueVO> list = dao.getAll();
		for (VenueVO aVenueVO : list) {
			System.out.println(aVenueVO.getVenue_no());
			System.out.println(aVenueVO.getVenue_name());
			System.out.println(aVenueVO.getAddress());
			System.out.println(aVenueVO.getLatitude());
			System.out.println(aVenueVO.getLongitude());
			System.out.println(aVenueVO.getVenue_info());
																								//B
			try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/venueTest.jpg"), true)){
				if(aVenueVO.getVenue_locationPic() == null) {
					
				} else {
					ps.write(aVenueVO.getVenue_locationPic());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.println("------------------------------");
		}
		
	}

}
