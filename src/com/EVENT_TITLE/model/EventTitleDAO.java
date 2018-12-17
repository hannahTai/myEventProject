package com.EVENT_TITLE.model;


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

public class EventTitleDAO implements EventTitleDAO_interface{
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO EVENT_TITLE (evetit_no, eveclass_no, ticrefpolicy_no, evetit_name, evetit_startdate, evetit_enddate, "
			+ "evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions, evetit_status, launchdate, offdate, promotionranking) "
			+ "VALUES ('E'||LPAD(to_char(EVETIT_SEQ.NEXTVAL), 4, '0'), ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE EVENT_TITLE SET eveclass_no=?, ticrefpolicy_no=?, evetit_name=?, evetit_startdate=?, evetit_enddate=?, "
			+ "evetit_poster=?, info=?, notices=?, eticpurchaserules=?, eticrules=?, refundrules=?, evetit_sessions=?, evetit_status=?, launchdate=?, offdate=?, promotionranking=? "
			+ "WHERE evetit_no=?";
	
	private static final String DELETE_STMT = 
			"DELETE FROM EVENT_TITLE WHERE evetit_no=?";

	private static final String GET_ONE_STMT = 
			"SELECT evetit_no, eveclass_no, ticrefpolicy_no, evetit_name, evetit_startdate, evetit_enddate, "
			+ "evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions, evetit_status, launchdate, offdate, promotionranking "
			+ "FROM EVENT_TITLE WHERE evetit_no=?";

	private static final String GET_ALL_STMT = 
			"SELECT evetit_no, eveclass_no, ticrefpolicy_no, evetit_name, evetit_startdate, evetit_enddate, "
			+ "evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions, evetit_status, launchdate, offdate, promotionranking "
			+ "FROM EVENT_TITLE ORDER BY evetit_no";
	
	private static final String INSERT2_STMT_Basic = 
			"INSERT INTO EVENT_TITLE (evetit_no, eveclass_no, ticrefpolicy_no, evetit_name, evetit_startdate, evetit_enddate, "
			+ "evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules) "
			+ "VALUES ('E'||LPAD(to_char(EVETIT_SEQ.NEXTVAL), 4, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
	private static final String UPDATE2_STMT_Basic = 
			"UPDATE EVENT_TITLE SET eveclass_no=?, ticrefpolicy_no=?, evetit_name=?, evetit_startdate=?, evetit_enddate=?, "
			+ "evetit_poster=?, info=?, notices=?, eticpurchaserules=?, eticrules=?, refundrules=? "
			+ "WHERE evetit_no=?";

	
		
	@Override
	public void insert(EventTitleVO evetitVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, evetitVO.getEveclass_no()); 
			pstmt.setString(2, evetitVO.getTicrefpolicy_no()); 
			pstmt.setString(3, evetitVO.getEvetit_name());
			pstmt.setDate(4, evetitVO.getEvetit_startdate());
			pstmt.setDate(5, evetitVO.getEvetit_enddate());
			pstmt.setBytes(6, evetitVO.getEvetit_poster());		  //B		
			pstmt.setCharacterStream(7, new StringReader(evetitVO.getInfo()));							
			pstmt.setCharacterStream(8, new StringReader(evetitVO.getNotices()));		
			pstmt.setCharacterStream(9, new StringReader(evetitVO.getEticpurchaserules())); 				
			pstmt.setCharacterStream(10, new StringReader(evetitVO.getEticrules()));			
			pstmt.setCharacterStream(11, new StringReader(evetitVO.getRefundrules()));			
			pstmt.setInt(12, evetitVO.getEvetit_sessions());
			pstmt.setString(13, evetitVO.getEvetit_status());
			pstmt.setDate(14, evetitVO.getLaunchdate());
			pstmt.setDate(15, evetitVO.getOffdate());
			pstmt.setInt(16, evetitVO.getPromotionranking());
		
			pstmt.executeUpdate();

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
	public void update(EventTitleVO evetitVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, evetitVO.getEveclass_no()); 
			pstmt.setString(2, evetitVO.getTicrefpolicy_no()); 
			pstmt.setString(3, evetitVO.getEvetit_name());
			pstmt.setDate(4, evetitVO.getEvetit_startdate());
			pstmt.setDate(5, evetitVO.getEvetit_enddate());
			pstmt.setBytes(6, evetitVO.getEvetit_poster());				
			pstmt.setCharacterStream(7, new StringReader(evetitVO.getInfo()));							
			pstmt.setCharacterStream(8, new StringReader(evetitVO.getNotices()));		
			pstmt.setCharacterStream(9, new StringReader(evetitVO.getEticpurchaserules())); 				
			pstmt.setCharacterStream(10, new StringReader(evetitVO.getEticrules()));			
			pstmt.setCharacterStream(11, new StringReader(evetitVO.getRefundrules()));			
			pstmt.setInt(12, evetitVO.getEvetit_sessions());
			pstmt.setString(13, evetitVO.getEvetit_status());
			pstmt.setDate(14, evetitVO.getLaunchdate());
			pstmt.setDate(15, evetitVO.getOffdate());
			pstmt.setInt(16, evetitVO.getPromotionranking());
			pstmt.setString(17, evetitVO.getEvetit_no());
		
			pstmt.executeUpdate();

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
	public void delete(String evetit_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, evetit_no);

			pstmt.executeUpdate();

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
	public EventTitleVO findByPrimaryKey(String evetit_no) {
		
		EventTitleVO eventTitleVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		BufferedReader br = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, evetit_no); 
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventTitleVO = new EventTitleVO();
				eventTitleVO.setEvetit_no(rs.getString("evetit_no"));
				
				eventTitleVO.setEveclass_no(rs.getString("eveclass_no"));
				eventTitleVO.setTicrefpolicy_no(rs.getString("ticrefpolicy_no"));
				eventTitleVO.setEvetit_name(rs.getString("evetit_name"));
				eventTitleVO.setEvetit_startdate(rs.getDate("evetit_startdate"));
				eventTitleVO.setEvetit_enddate(rs.getDate("evetit_enddate"));
				
				eventTitleVO.setEvetit_poster(rs.getBytes("evetit_poster"));	  //B
				
				if(rs.getCharacterStream("info") == null) {
					eventTitleVO.setInfo("");
				} else {
					br = new BufferedReader(rs.getCharacterStream("info"));
					StringBuilder infoSb = new StringBuilder();
					String infoStr = null;			
					while((infoStr = br.readLine()) != null)
						infoSb.append(infoStr).append("\n");				
					eventTitleVO.setInfo(infoSb.toString());
				}
				
				if(rs.getCharacterStream("notices") == null) {
					eventTitleVO.setNotices("");
				} else {
					br = new BufferedReader(rs.getCharacterStream("notices"));
					StringBuilder noticesSb = new StringBuilder();
					String noticesStr = null;			
					while((noticesStr = br.readLine()) != null)
						noticesSb.append(noticesStr).append("\n");				
					eventTitleVO.setNotices(noticesSb.toString());
				}
				
				if(rs.getCharacterStream("eticpurchaserules") == null) {
					eventTitleVO.setEticpurchaserules("");
				} else {
				br = new BufferedReader(rs.getCharacterStream("eticpurchaserules"));
				StringBuilder eticpurchaserulesSb = new StringBuilder();
				String eticpurchaserulesStr = null;			
				while((eticpurchaserulesStr = br.readLine()) != null)
					eticpurchaserulesSb.append(eticpurchaserulesStr).append("\n");				
				eventTitleVO.setEticpurchaserules(eticpurchaserulesSb.toString());
				}
				
				if(rs.getCharacterStream("eticrules") == null) {
					eventTitleVO.setEticrules("");
				} else {
					br = new BufferedReader(rs.getCharacterStream("eticrules"));
					StringBuilder eticrulesSb = new StringBuilder();
					String eticrulesStr = null;			
					while((eticrulesStr = br.readLine()) != null)
						eticrulesSb.append(eticrulesStr).append("\n");				
					eventTitleVO.setEticrules(eticrulesSb.toString());
				}

				if(rs.getCharacterStream("refundrules") == null) {
					eventTitleVO.setRefundrules("");
				} else {
					br = new BufferedReader(rs.getCharacterStream("refundrules"));
					StringBuilder refundrulesSb = new StringBuilder();
					String refundrulesStr = null;			
					while((refundrulesStr = br.readLine()) != null)
						refundrulesSb.append(refundrulesStr).append("\n");				
					eventTitleVO.setRefundrules(refundrulesSb.toString());
				}		
				
				eventTitleVO.setEvetit_sessions(rs.getInt("evetit_sessions"));
				eventTitleVO.setEvetit_status(rs.getString("evetit_status"));				
				eventTitleVO.setLaunchdate(rs.getDate("launchdate"));
				eventTitleVO.setOffdate(rs.getDate("offdate"));
				eventTitleVO.setPromotionranking(rs.getInt("promotionranking"));
				
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
		return eventTitleVO;
	}

	@Override
	public List<EventTitleVO> getAll() {
		
		List<EventTitleVO> list = new ArrayList<EventTitleVO>();
		EventTitleVO eventTitleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BufferedReader br = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventTitleVO = new EventTitleVO();
				eventTitleVO.setEvetit_no(rs.getString("evetit_no"));
				
				eventTitleVO.setEveclass_no(rs.getString("eveclass_no"));
				eventTitleVO.setTicrefpolicy_no(rs.getString("ticrefpolicy_no"));
				eventTitleVO.setEvetit_name(rs.getString("evetit_name"));
				eventTitleVO.setEvetit_startdate(rs.getDate("evetit_startdate"));
				eventTitleVO.setEvetit_enddate(rs.getDate("evetit_enddate"));
				
				eventTitleVO.setEvetit_poster(rs.getBytes("evetit_poster"));  //B
							
				br = new BufferedReader(rs.getCharacterStream("info"));
				StringBuilder infoSb = new StringBuilder();
				String infoStr = null;			
				while((infoStr = br.readLine()) != null)
					infoSb.append(infoStr).append("\n");				
				eventTitleVO.setInfo(infoSb.toString());
				
				br = new BufferedReader(rs.getCharacterStream("notices"));
				StringBuilder noticesSb = new StringBuilder();
				String noticesStr = null;			
				while((noticesStr = br.readLine()) != null)
					noticesSb.append(noticesStr).append("\n");				
				eventTitleVO.setNotices(noticesSb.toString());
				
				br = new BufferedReader(rs.getCharacterStream("eticpurchaserules"));
				StringBuilder eticpurchaserulesSb = new StringBuilder();
				String eticpurchaserulesStr = null;			
				while((eticpurchaserulesStr = br.readLine()) != null)
					eticpurchaserulesSb.append(eticpurchaserulesStr).append("\n");				
				eventTitleVO.setEticpurchaserules(eticpurchaserulesSb.toString());
				
				br = new BufferedReader(rs.getCharacterStream("eticrules"));
				StringBuilder eticrulesSb = new StringBuilder();
				String eticrulesStr = null;			
				while((eticrulesStr = br.readLine()) != null)
					eticrulesSb.append(eticrulesStr).append("\n");				
				eventTitleVO.setEticrules(eticrulesSb.toString());

				br = new BufferedReader(rs.getCharacterStream("refundrules"));
				StringBuilder refundrulesSb = new StringBuilder();
				String refundrulesStr = null;			
				while((refundrulesStr = br.readLine()) != null)
					refundrulesSb.append(refundrulesStr).append("\n");				
				eventTitleVO.setRefundrules(refundrulesSb.toString());
						
				eventTitleVO.setEvetit_sessions(rs.getInt("evetit_sessions"));
				eventTitleVO.setEvetit_status(rs.getString("evetit_status"));				
				eventTitleVO.setLaunchdate(rs.getDate("launchdate"));
				eventTitleVO.setOffdate(rs.getDate("offdate"));
				eventTitleVO.setPromotionranking(rs.getInt("promotionranking"));
				
				list.add(eventTitleVO);
				
			}
			
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

	@Override
	public String insert2_Basic(EventTitleVO evetitVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String evetit_no = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String[] cols = { "evetit_no" };
			pstmt = con.prepareStatement(INSERT2_STMT_Basic, cols);

			pstmt.setString(1, evetitVO.getEveclass_no()); 
			pstmt.setString(2, evetitVO.getTicrefpolicy_no()); 
			pstmt.setString(3, evetitVO.getEvetit_name());
			pstmt.setDate(4, evetitVO.getEvetit_startdate());
			pstmt.setDate(5, evetitVO.getEvetit_enddate());
			pstmt.setBytes(6, evetitVO.getEvetit_poster());		  //B		
			pstmt.setCharacterStream(7, new StringReader(evetitVO.getInfo()));							
			pstmt.setCharacterStream(8, new StringReader(evetitVO.getNotices()));		
			pstmt.setCharacterStream(9, new StringReader(evetitVO.getEticpurchaserules())); 				
			pstmt.setCharacterStream(10, new StringReader(evetitVO.getEticrules()));			
			pstmt.setCharacterStream(11, new StringReader(evetitVO.getRefundrules()));			
		
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				evetit_no = rs.getString(1);
				//System.out.println(evetit_no);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
		return evetit_no;
	}

	@Override
	public String update2_Basic(EventTitleVO evetitVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE2_STMT_Basic);

			pstmt.setString(1, evetitVO.getEveclass_no()); 
			pstmt.setString(2, evetitVO.getTicrefpolicy_no()); 
			pstmt.setString(3, evetitVO.getEvetit_name());
			pstmt.setDate(4, evetitVO.getEvetit_startdate());
			pstmt.setDate(5, evetitVO.getEvetit_enddate());
			pstmt.setBytes(6, evetitVO.getEvetit_poster());				
			pstmt.setCharacterStream(7, new StringReader(evetitVO.getInfo()));							
			pstmt.setCharacterStream(8, new StringReader(evetitVO.getNotices()));		
			pstmt.setCharacterStream(9, new StringReader(evetitVO.getEticpurchaserules())); 				
			pstmt.setCharacterStream(10, new StringReader(evetitVO.getEticrules()));			
			pstmt.setCharacterStream(11, new StringReader(evetitVO.getRefundrules()));			
			pstmt.setString(12, evetitVO.getEvetit_no());
		
			pstmt.executeUpdate();
			
			System.out.println("----------Updated2_Basic----------");

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
		return evetitVO.getEvetit_no();
	}
	
}
