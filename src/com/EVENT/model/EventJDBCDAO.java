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

public class EventJDBCDAO implements EventDAO_interface{

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
	
	
	
	
	

	
	
	
	
	public static void main(String[] args) {
		
		
		
		EventJDBCDAO dao = new EventJDBCDAO();
		
		
		
		// 新增		
		FileInputStream fis1 = null;
		ByteArrayOutputStream baos1 = null;	
		try {
			EventVO eventVO1 = new EventVO();			
			
			eventVO1.setEvetit_no("E0001"); 
			eventVO1.setVenue_no("V001");
			eventVO1.setEve_session("03");
			eventVO1.setEve_no(eventVO1.getEvetit_no() + eventVO1.getEve_session()); 			
			eventVO1.setEve_sessionname("第三場");					
			
			fis1 = new FileInputStream("writeImgJDBC/tomcat.jpg");			
			baos1 = new ByteArrayOutputStream();			
			int i;
			while ((i = fis1.read()) != -1)
				baos1.write(i);			
			eventVO1.setEve_seatmap(baos1.toByteArray());	
			
			eventVO1.setEve_startdate(java.sql.Timestamp.valueOf("2018-08-20 12:00:00"));							
			eventVO1.setEve_enddate(java.sql.Timestamp.valueOf("2019-03-10 14:30:00"));		
			eventVO1.setEve_onsaledate(java.sql.Timestamp.valueOf("2018-09-01 10:00:00")); 				
			eventVO1.setEve_offsaledate(java.sql.Timestamp.valueOf("2018-03-09 24:00:00"));				
			eventVO1.setTiclimit(new Integer(4));			
			eventVO1.setFullrefundenddate(java.sql.Timestamp.valueOf("2018-09-10 10:00:00"));
			eventVO1.setEve_status("normal");
			
			dao.insert(eventVO1);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baos1 != null) {
				try {
					baos1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis1 != null) {
				try {
					fis1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
		
		// 修改		
		FileInputStream fis2 = null;
		ByteArrayOutputStream baos2 = null;	
		try {
			EventVO eventVO2 = new EventVO();
			
			eventVO2.setEve_no("E000101"); 			
			eventVO2.setVenue_no("V001");
			eventVO2.setEve_sessionname("第一場");					
			
			fis2 = new FileInputStream("writeImgJDBC/java.jpg");			
			baos2 = new ByteArrayOutputStream();			
			int i;
			while ((i = fis2.read()) != -1)
				baos2.write(i);			
			eventVO2.setEve_seatmap(baos2.toByteArray());	
			
			eventVO2.setEve_startdate(java.sql.Timestamp.valueOf("2017-08-22 12:00:00"));							
			eventVO2.setEve_enddate(java.sql.Timestamp.valueOf("2018-03-12 14:30:00"));		
			eventVO2.setEve_onsaledate(java.sql.Timestamp.valueOf("2017-09-01 10:00:00")); 				
			eventVO2.setEve_offsaledate(java.sql.Timestamp.valueOf("2017-03-31 24:00:00"));				
			eventVO2.setTiclimit(new Integer(4));			
			eventVO2.setFullrefundenddate(null);
			eventVO2.setEve_status("cancel");
			
			dao.update(eventVO2);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baos1 != null) {
				try {
					baos1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis1 != null) {
				try {
					fis1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		// 刪除
		dao.delete("E000103");
		System.out.println("------------------------------");
		
		
		
		// 查詢一個
		EventVO EventVO03 = dao.findByPrimaryKey("E000101");
		System.out.println(EventVO03.getEve_no());
		System.out.println(EventVO03.getEvetit_no());
		System.out.println(EventVO03.getVenue_no());
		System.out.println(EventVO03.getEve_session());
		System.out.println(EventVO03.getEve_sessionname());
		
		try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/eventTest.jpg"), true)){
			ps.write(EventVO03.getEve_seatmap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(EventVO03.getEve_startdate());
		System.out.println(EventVO03.getEve_enddate());
		System.out.println(EventVO03.getEve_onsaledate());
		System.out.println(EventVO03.getEve_offsaledate());
		System.out.println(EventVO03.getTiclimit());
		
		System.out.println(EventVO03.getFullrefundenddate());
		System.out.println(EventVO03.getEve_status());	
		System.out.println("------------------------------");

		
		
		// 查詢全部
		List<EventVO> list = dao.getAll();	
		for(EventVO aEventVO : list) {
			System.out.println(aEventVO.getEve_no());
			System.out.println(aEventVO.getEvetit_no());
			System.out.println(aEventVO.getVenue_no());
			System.out.println(aEventVO.getEve_session());
			System.out.println(aEventVO.getEve_sessionname());
			
			try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/" + aEventVO.getEve_no() + ".jpg"), true)){
				ps.write(aEventVO.getEve_seatmap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(aEventVO.getEve_startdate());
			System.out.println(aEventVO.getEve_enddate());
			System.out.println(aEventVO.getEve_onsaledate());
			System.out.println(aEventVO.getEve_offsaledate());
			System.out.println(aEventVO.getTiclimit());
			
			System.out.println(aEventVO.getFullrefundenddate());
			System.out.println(aEventVO.getEve_status());	
			System.out.println("------------------------------");
		}
		
		
		
	}

	
	
}
