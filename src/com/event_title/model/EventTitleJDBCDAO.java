package com.event_title.model;


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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.event.model.EventVO;
 
public class EventTitleJDBCDAO implements EventTitleDAO_interface{
	
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
	
	private static final String GET_ALL_STMT_LAUNCHED  = "SELECT evetit_no, eveclass_no, ticrefpolicy_no, evetit_name, evetit_startdate, evetit_enddate, "
			+ "evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions, evetit_status, launchdate, offdate, promotionranking "
			+ "FROM EVENT_TITLE WHERE (evetit_status = 'confirmed') and (CURRENT_DATE BETWEEN launchdate and offdate) ORDER BY promotionranking DESC";
	
	private static final String UPDATE_STMT_withoutPoster = 
			"UPDATE EVENT_TITLE SET eveclass_no=?, ticrefpolicy_no=?, evetit_name=?, evetit_startdate=?, evetit_enddate=?, "
			+ "info=?, notices=?, eticpurchaserules=?, eticrules=?, refundrules=?, evetit_sessions=?, evetit_status=?, launchdate=?, offdate=?, promotionranking=? "
			+ "WHERE evetit_no=?";

	
	
	private static final String GET_Event_ByEventTitle_STMT = "SELECT eve_no, evetit_no, venue_no, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status "
			+ "FROM EVENT WHERE evetit_no=? order by eve_no";
	
	
	
	
	
	private static final String GET_EventTitle_NotInTheAdvertisement_STMT = 
			"SELECT evetit_no, eveclass_no, ticrefpolicy_no, evetit_name, evetit_startdate, evetit_enddate, "
			+ "evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions, evetit_status, launchdate, offdate, promotionranking "
			+ "from event_title where evetit_no not in (select evetit_no from advertisement) and (evetit_status = 'confirmed') and (CURRENT_DATE BETWEEN launchdate and offdate) ORDER BY evetit_no";

	
	
	
		
	@Override
	public String insert(EventTitleVO evetitVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String evetit_no = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String[] cols = { "evetit_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

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
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				evetit_no = rs.getString(1);
			}
			
			System.out.println("----------Inserted----------");

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
		return evetit_no;
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
	public void delete(String evetit_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, evetit_no);

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
				
				list.add(eventTitleVO);
				
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
	
	
	
	
	
	
	
	
	@Override
	public List<EventTitleVO> getAllLaunched() {
		
		List<EventTitleVO> list = new ArrayList<EventTitleVO>();
		EventTitleVO eventTitleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BufferedReader br = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT_LAUNCHED);
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
				
				list.add(eventTitleVO);
				
			}
			
			System.out.println("----------getAllLaunched finished----------");
			
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
	public void update_withoutPoster(EventTitleVO evetitVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT_withoutPoster);

			pstmt.setString(1, evetitVO.getEveclass_no()); 
			pstmt.setString(2, evetitVO.getTicrefpolicy_no()); 
			pstmt.setString(3, evetitVO.getEvetit_name());
			pstmt.setDate(4, evetitVO.getEvetit_startdate());
			pstmt.setDate(5, evetitVO.getEvetit_enddate());			
			pstmt.setCharacterStream(6, new StringReader(evetitVO.getInfo()));							
			pstmt.setCharacterStream(7, new StringReader(evetitVO.getNotices()));		
			pstmt.setCharacterStream(8, new StringReader(evetitVO.getEticpurchaserules())); 				
			pstmt.setCharacterStream(9, new StringReader(evetitVO.getEticrules()));			
			pstmt.setCharacterStream(10, new StringReader(evetitVO.getRefundrules()));			
			pstmt.setInt(11, evetitVO.getEvetit_sessions());
			pstmt.setString(12, evetitVO.getEvetit_status());
			pstmt.setDate(13, evetitVO.getLaunchdate());
			pstmt.setDate(14, evetitVO.getOffdate());
			pstmt.setInt(15, evetitVO.getPromotionranking());
			pstmt.setString(16, evetitVO.getEvetit_no());
		
			pstmt.executeUpdate();
			
			System.out.println("----------update_withoutPoster----------");

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
	public List<EventTitleVO> getAllLaunched(Map<String, String[]> map) {

		List<EventTitleVO> list = new ArrayList<EventTitleVO>();
		EventTitleVO eventTitleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BufferedReader br = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			String finalSQL = "SELECT * FROM EVENT_TITLE WHERE (evetit_status = 'confirmed') AND (CURRENT_DATE BETWEEN launchdate AND offdate) "
			          + CompositeQuery_EventTitle_Launched.get_WhereCondition(map)
			          + "ORDER BY promotionranking DESC, launchdate";
			System.out.println("getAllLaunched_EventTitle_CompositeQuery_EventTitle_Launched" + finalSQL);
			
			pstmt = con.prepareStatement(finalSQL);
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
				
				list.add(eventTitleVO);
				
			}
			
			System.out.println("----------getAllLaunched:CompositeQuery_EventTitle_Launched finished----------");
			
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
	public Set<EventVO> getEventsByEventTitle(String evetit_no){
		Set<EventVO> set = new LinkedHashSet<>();
		EventVO eventVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_Event_ByEventTitle_STMT);
			pstmt.setString(1, evetit_no);
			rs = pstmt.executeQuery();
	
			while(rs.next()) {
				eventVO = new EventVO();				
				eventVO.setEve_no(rs.getString("eve_no"));
				eventVO.setEvetit_no(rs.getString("evetit_no"));
				eventVO.setVenue_no(rs.getString("venue_no"));
				eventVO.setEve_sessionname(rs.getString("eve_sessionname"));				
				eventVO.setEve_seatmap(rs.getBytes("eve_seatmap"));
				eventVO.setEve_startdate(rs.getTimestamp("eve_startdate"));
				eventVO.setEve_enddate(rs.getTimestamp("eve_enddate"));				
				eventVO.setEve_onsaledate(rs.getTimestamp("eve_onsaledate"));
				eventVO.setEve_offsaledate(rs.getTimestamp("eve_offsaledate"));
				eventVO.setTiclimit(rs.getInt("ticlimit"));
				eventVO.setFullrefundenddate(rs.getTimestamp("fullrefundenddate"));
				eventVO.setEve_status(rs.getString("eve_status"));
				set.add(eventVO);
			}
			
			System.out.println("----------getEventsByEventTitle finished----------");
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
		return set;
	}
	
	
	@Override
	public List<EventTitleVO> getNotInTheAdvertisement() {
		
		List<EventTitleVO> list = new ArrayList<EventTitleVO>();
		EventTitleVO eventTitleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BufferedReader br = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_EventTitle_NotInTheAdvertisement_STMT);
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
				
				list.add(eventTitleVO);
				
			}
			
			System.out.println("----------getNotInTheAdvertisement finished----------");
			
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
		
		
		
		EventTitleJDBCDAO dao = new EventTitleJDBCDAO();
		
		
		
		// 新增		
//		FileInputStream fis1 = null;
//		ByteArrayOutputStream baos1 = null;	
//		try {
//			EventTitleVO EventTitleVO1 = new EventTitleVO();
//			
//			EventTitleVO1.setEveclass_no("A"); 
//			EventTitleVO1.setTicrefpolicy_no("TRP2");
//			EventTitleVO1.setEvetit_name("SingAround");
//			EventTitleVO1.setEvetit_startdate(java.sql.Date.valueOf("2019-01-31"));
//			EventTitleVO1.setEvetit_enddate(java.sql.Date.valueOf("2019-01-31"));
//			
//			fis1 = new FileInputStream("writeImgJDBC/tomcat.jpg");		  //B	
//			baos1 = new ByteArrayOutputStream();			
//			int i;
//			while ((i = fis1.read()) != -1)
//				baos1.write(i);			
//			EventTitleVO1.setEvetit_poster(baos1.toByteArray());
//						
//			EventTitleVO1.setInfo("This is INFO.");		
//			EventTitleVO1.setNotices("This is NOTICES.");
//			EventTitleVO1.setEticpurchaserules("This is ETICPURCHASERULES.");
//			EventTitleVO1.setEticrules("This is ETICRULES.");
//			EventTitleVO1.setRefundrules("This is REFUNDRULES.");
//			EventTitleVO1.setEvetit_sessions(new Integer(1));
//			EventTitleVO1.setEvetit_status("temporary");
//			EventTitleVO1.setLaunchdate(java.sql.Date.valueOf("2019-01-31"));
//			EventTitleVO1.setOffdate(java.sql.Date.valueOf("2019-01-31"));
//			EventTitleVO1.setPromotionranking(new Integer(1));
//			
//			String evetit_no = dao.insert(EventTitleVO1);
//			
//			System.out.println(evetit_no);
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
//			EventTitleVO EventTitleVO2 = new EventTitleVO();
//			EventTitleVO2.setEvetit_no("E0001");
//			EventTitleVO2.setEveclass_no("B"); 
//			EventTitleVO2.setTicrefpolicy_no("TRP3");
//			EventTitleVO2.setEvetit_name("SingAround2");
//			EventTitleVO2.setEvetit_startdate(java.sql.Date.valueOf("2018-12-31"));
//			EventTitleVO2.setEvetit_enddate(java.sql.Date.valueOf("2018-12-31"));
//			
//			fis2 = new FileInputStream("writeImgJDBC/java.jpg");			
//			baos2 = new ByteArrayOutputStream();			
//			int i;
//			while ((i = fis2.read()) != -1)
//				baos2.write(i);		
//			EventTitleVO2.setEvetit_poster(baos2.toByteArray());
//						 
//			EventTitleVO2.setInfo("This is INFO.2");		
//			EventTitleVO2.setNotices("This is NOTICES.2");
//			EventTitleVO2.setEticpurchaserules("This is ETICPURCHASERULES.2");
//			EventTitleVO2.setEticrules("This is ETICRULES.2");
//			EventTitleVO2.setRefundrules("This is REFUNDRULES.2");
//			EventTitleVO2.setEvetit_sessions(new Integer(2));
//			EventTitleVO2.setEvetit_status("temporary");
//			EventTitleVO2.setLaunchdate(java.sql.Date.valueOf("2018-12-31"));
//			EventTitleVO2.setOffdate(java.sql.Date.valueOf("2018-12-31"));
//			EventTitleVO2.setPromotionranking(new Integer(2));
//			
//			dao.update(EventTitleVO2);
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
//		dao.delete("E0005");
//		System.out.println("------------------------------");
		
		
		
		// 查詢一個
//		EventTitleVO EventTitleVO3 = dao.findByPrimaryKey("E0051");
//		System.out.println(EventTitleVO3.getEvetit_no());
//		System.out.println(EventTitleVO3.getEveclass_no());
//		System.out.println(EventTitleVO3.getTicrefpolicy_no());
//		System.out.println(EventTitleVO3.getEvetit_name());
//		System.out.println(EventTitleVO3.getEvetit_startdate());
//		System.out.println(EventTitleVO3.getEvetit_enddate());
//																							//B
//		try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/eventTitleTest.jpg"), true)){
//			if(EventTitleVO3.getEvetit_poster() == null) {
//				
//			} else {
//				ps.write(EventTitleVO3.getEvetit_poster());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(EventTitleVO3.getInfo());
//		System.out.println(EventTitleVO3.getNotices());
//		System.out.println(EventTitleVO3.getEticpurchaserules());
//		System.out.println(EventTitleVO3.getEticrules());
//		System.out.println(EventTitleVO3.getRefundrules());
//		
//		System.out.println(EventTitleVO3.getEvetit_sessions());
//		System.out.println(EventTitleVO3.getEvetit_status());
//		System.out.println(EventTitleVO3.getLaunchdate());
//		System.out.println(EventTitleVO3.getOffdate());
//		System.out.println(EventTitleVO3.getPromotionranking());		
//		System.out.println("------------------------------");
		
		
		
		// 查詢全部
//		List<EventTitleVO> list = dao.getAll();
//		for (EventTitleVO aEventTitleVO : list) {
//			System.out.println(aEventTitleVO.getEvetit_no());
//			System.out.println(aEventTitleVO.getEveclass_no());
//			System.out.println(aEventTitleVO.getTicrefpolicy_no());
//			System.out.println(aEventTitleVO.getEvetit_name());
//			System.out.println(aEventTitleVO.getEvetit_startdate());
//			System.out.println(aEventTitleVO.getEvetit_enddate());
//					
//			try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/" + aEventTitleVO.getEvetit_no() + ".jpg"), true)){
//				ps.write(aEventTitleVO.getEvetit_poster());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			System.out.println(aEventTitleVO.getInfo());
//			System.out.println(aEventTitleVO.getNotices());
//			System.out.println(aEventTitleVO.getEticpurchaserules());
//			System.out.println(aEventTitleVO.getEticrules());
//			System.out.println(aEventTitleVO.getRefundrules());
//			
//			System.out.println(aEventTitleVO.getEvetit_sessions());
//			System.out.println(aEventTitleVO.getEvetit_status());
//			System.out.println(aEventTitleVO.getLaunchdate());
//			System.out.println(aEventTitleVO.getOffdate());
//			System.out.println(aEventTitleVO.getPromotionranking());		
//			System.out.println("------------------------------");		
//		}
		
		
		

		
		// 查詢全部上架的
//		List<EventTitleVO> list = dao.getAllLaunched();
//		for (EventTitleVO aEventTitleVO : list) {
//			System.out.println(aEventTitleVO.getEvetit_no());
//			System.out.println(aEventTitleVO.getEveclass_no());
//			System.out.println(aEventTitleVO.getTicrefpolicy_no());
//			System.out.println(aEventTitleVO.getEvetit_name());
//			System.out.println(aEventTitleVO.getEvetit_startdate());
//			System.out.println(aEventTitleVO.getEvetit_enddate());
//					
//			try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/Launched" + aEventTitleVO.getEvetit_no() + ".jpg"), true)){
//				ps.write(aEventTitleVO.getEvetit_poster());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			System.out.println(aEventTitleVO.getInfo());
//			System.out.println(aEventTitleVO.getNotices());
//			System.out.println(aEventTitleVO.getEticpurchaserules());
//			System.out.println(aEventTitleVO.getEticrules());
//			System.out.println(aEventTitleVO.getRefundrules());
//			
//			System.out.println(aEventTitleVO.getEvetit_sessions());
//			System.out.println(aEventTitleVO.getEvetit_status());
//			System.out.println(aEventTitleVO.getLaunchdate());
//			System.out.println(aEventTitleVO.getOffdate());
//			System.out.println(aEventTitleVO.getPromotionranking());		
//			System.out.println("------------------------------");		
//		}
			

		
		
		
		// 修改_withoutPoster
//		EventTitleVO EventTitleVO8 = new EventTitleVO();
//		EventTitleVO8.setEvetit_no("E0003");
//		EventTitleVO8.setEveclass_no("B"); 
//		EventTitleVO8.setTicrefpolicy_no("TRP3");
//		EventTitleVO8.setEvetit_name("SingAround2");
//		EventTitleVO8.setEvetit_startdate(java.sql.Date.valueOf("2018-12-31"));
//		EventTitleVO8.setEvetit_enddate(java.sql.Date.valueOf("2018-12-31"));
//		EventTitleVO8.setInfo("This is INFO.2");		
//		EventTitleVO8.setNotices("This is NOTICES.2");
//		EventTitleVO8.setEticpurchaserules("This is ETICPURCHASERULES.2");
//		EventTitleVO8.setEticrules("This is ETICRULES.2");
//		EventTitleVO8.setRefundrules("This is REFUNDRULES.2");
//		EventTitleVO8.setEvetit_sessions(new Integer(2));
//		EventTitleVO8.setEvetit_status("temporary");
//		EventTitleVO8.setLaunchdate(java.sql.Date.valueOf("2018-12-31"));
//		EventTitleVO8.setOffdate(java.sql.Date.valueOf("2018-12-31"));
//		EventTitleVO8.setPromotionranking(new Integer(2));		
//		dao.update_withoutPoster(EventTitleVO8);
//	
	
		// 用活動主題查活動場次
//		Set<EventVO> set = dao.getEventsByEventTitle("E0001");
//		for (EventVO aEventVO : set) {
//			System.out.println(aEventVO.getEve_no());
//			System.out.println(aEventVO.getEvetit_no());
//			System.out.println(aEventVO.getVenue_no());
//			System.out.println(aEventVO.getEve_sessionname());
//			
//			try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/" + aEventVO.getEve_no() + ".jpg"), true)){
//				ps.write(aEventVO.getEve_seatmap());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			System.out.println(aEventVO.getEve_startdate());
//			System.out.println(aEventVO.getEve_enddate());
//			System.out.println(aEventVO.getEve_onsaledate());
//			System.out.println(aEventVO.getEve_offsaledate());
//			System.out.println(aEventVO.getTiclimit());
//			
//			System.out.println(aEventVO.getFullrefundenddate());
//			System.out.println(aEventVO.getEve_status());	
//			System.out.println("------------------------------");	
//		}
		
		
		
		
		
		// 查詢沒有被廣告且狀態為確定
//		List<EventTitleVO> list = dao.getNotInTheAdvertisement();
//		for (EventTitleVO aEventTitleVO : list) {
//			System.out.println(aEventTitleVO.getEvetit_no());
//			System.out.println(aEventTitleVO.getEveclass_no());
//			System.out.println(aEventTitleVO.getTicrefpolicy_no());
//			System.out.println(aEventTitleVO.getEvetit_name());
//			System.out.println(aEventTitleVO.getEvetit_startdate());
//			System.out.println(aEventTitleVO.getEvetit_enddate());
//					
//			try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/" + aEventTitleVO.getEvetit_no() + ".jpg"), true)){
//				ps.write(aEventTitleVO.getEvetit_poster());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			System.out.println(aEventTitleVO.getInfo());
//			System.out.println(aEventTitleVO.getNotices());
//			System.out.println(aEventTitleVO.getEticpurchaserules());
//			System.out.println(aEventTitleVO.getEticrules());
//			System.out.println(aEventTitleVO.getRefundrules());
//			
//			System.out.println(aEventTitleVO.getEvetit_sessions());
//			System.out.println(aEventTitleVO.getEvetit_status());
//			System.out.println(aEventTitleVO.getLaunchdate());
//			System.out.println(aEventTitleVO.getOffdate());
//			System.out.println(aEventTitleVO.getPromotionranking());		
//			System.out.println("------------------------------");		
//		}
		
		
		
	}

	

	
	
}
