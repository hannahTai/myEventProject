package com.event.model;

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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.seating_area.model.SeatingAreaDAO;
import com.seating_area.model.SeatingAreaJDBCDAO;
import com.seating_area.model.SeatingAreaVO;
import com.ticket_type.model.TicketTypeJDBCDAO;
import com.ticket_type.model.TicketTypeVO;
import com.ticketorder.model.TicketOrderVO;

public class EventJDBCDAO implements EventDAO_interface{

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO EVENT (eve_no, evetit_no, venue_no, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status) "
			+ "VALUES ('EV'||LPAD(to_char(EVE_SEQ.NEXTVAL), 5, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE EVENT SET venue_no=?, eve_sessionname=?, eve_seatmap=?, "
			+ "eve_startdate=?, eve_enddate=?, eve_onsaledate=?, eve_offsaledate=?, ticlimit=?, fullrefundenddate=?, eve_status=? "
			+ "WHERE eve_no=?";

	private static final String DELETE_STMT = 
			"DELETE FROM EVENT WHERE eve_no=?";
	
	private static final String GET_ONE_STMT = 
			"SELECT eve_no, evetit_no, venue_no, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status "
			+ "FROM EVENT WHERE eve_no=?";
	
	private static final String GET_ALL_STMT = 
			"SELECT eve_no, evetit_no, venue_no, eve_sessionname, eve_seatmap, "
			+ "eve_startdate, eve_enddate, eve_onsaledate, eve_offsaledate, ticlimit, fullrefundenddate, eve_status "
			+ "FROM EVENT ORDER BY eve_no";

	
	private static final String GET_TicketType_ByEvent_STMT = 
			"SELECT tictype_no, eve_no, tictype_color, tictype_name, tictype_price "
			+ "FROM TICKET_TYPE WHERE eve_no=? ORDER BY tictype_no";
	
	
	private static final String UPDATE_STMT_withoutSeatmap = 
			"UPDATE EVENT SET venue_no=?, eve_sessionname=?, "
			+ "eve_startdate=?, eve_enddate=?, eve_onsaledate=?, eve_offsaledate=?, ticlimit=?, fullrefundenddate=?, eve_status=? "
			+ "WHERE eve_no=?";
	
	private static final String INSERT_STMT_init = 
			"INSERT INTO EVENT (eve_no, evetit_no, venue_no) "
			+ "VALUES ('EV'||LPAD(to_char(EVE_SEQ.NEXTVAL), 5, '0'), ?, ?)";
	
	
	
	private static final String DELETE_TicketTypes_ByEvent_STMT = 
			"DELETE FROM ticket_type WHERE eve_no=?";
	
	private static final String DELETE_SeatingAreas_ByEvent_STMT = 
			"DELETE FROM seating_area WHERE eve_no=?";
	
	
	
	private static final String UPDATE_evetit_sessions_ByEvent_STMT = 
			"UPDATE EVENT_TITLE SET evetit_sessions=? "
			+ "WHERE evetit_no=?";
	
	
	private static final String GET_SeatingArea_ByEvent_STMT = 
			"SELECT ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber "
			+ "FROM seating_area WHERE eve_no=?";
	
	

	@Override
	public String insert(EventVO eventVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String eve_no = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			String[] cols = { "eve_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, eventVO.getEvetit_no()); 
			pstmt.setString(2, eventVO.getVenue_no());
			pstmt.setString(3, eventVO.getEve_sessionname());		
			pstmt.setBytes(4, eventVO.getEve_seatmap());	
			
			pstmt.setTimestamp(5, eventVO.getEve_startdate());							
			pstmt.setTimestamp(6, eventVO.getEve_enddate());		
			pstmt.setTimestamp(7, eventVO.getEve_onsaledate()); 				
			pstmt.setTimestamp(8, eventVO.getEve_offsaledate());			
			pstmt.setInt(9, eventVO.getTiclimit());			
			pstmt.setTimestamp(10, eventVO.getFullrefundenddate());
			pstmt.setString(11, eventVO.getEve_status());
		
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				eve_no = rs.getString(1);
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
		return eve_no;
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
	public void delete(String eve_no, String evetit_no, Integer evetit_sessions) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(DELETE_SeatingAreas_ByEvent_STMT);
			pstmt.setString(1, eve_no);
			pstmt.executeUpdate();
			
			
			pstmt = con.prepareStatement(DELETE_TicketTypes_ByEvent_STMT);
			pstmt.setString(1, eve_no);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, eve_no);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(UPDATE_evetit_sessions_ByEvent_STMT);
			evetit_sessions -= 1;
			pstmt.setInt(1, evetit_sessions);
			pstmt.setString(2, evetit_no);
			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			
			System.out.println("----------Deleted seatingArea, ticketType, event----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	
	
	
	@Override
	public Set<TicketTypeVO> getTicketTypesByEvent(String eve_no){
		Set<TicketTypeVO> set = new LinkedHashSet<>();
		TicketTypeVO ticketTypeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;		
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_TicketType_ByEvent_STMT);
			pstmt.setString(1, eve_no);
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {				
				ticketTypeVO = new TicketTypeVO();				
				ticketTypeVO.setTictype_no(rs.getString("tictype_no"));
				ticketTypeVO.setEve_no(rs.getString("eve_no"));
				ticketTypeVO.setTictype_color(rs.getString("tictype_color"));
				ticketTypeVO.setTictype_name(rs.getString("tictype_name"));				
				ticketTypeVO.setTictype_price(rs.getInt("tictype_price"));
				set.add(ticketTypeVO);				
			}
			
			System.out.println("----------getTicketTypesByEvent finished----------");

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
	public void update_withoutSeatmap(EventVO eventVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT_withoutSeatmap);
			
			pstmt.setString(1, eventVO.getVenue_no());			
			pstmt.setString(2, eventVO.getEve_sessionname());			
			pstmt.setTimestamp(3, eventVO.getEve_startdate());							
			pstmt.setTimestamp(4, eventVO.getEve_enddate());		
			pstmt.setTimestamp(5, eventVO.getEve_onsaledate()); 				
			pstmt.setTimestamp(6, eventVO.getEve_offsaledate());			
			pstmt.setInt(7, eventVO.getTiclimit());			
			pstmt.setTimestamp(8, eventVO.getFullrefundenddate());
			pstmt.setString(9, eventVO.getEve_status());
			pstmt.setString(10, eventVO.getEve_no()); 
		
			pstmt.executeUpdate();
			
			System.out.println("----------update_withoutPoster done----------");

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
	public String insert(String evetit_no, Integer evetit_sessions) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String eve_no = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			con.setAutoCommit(false);
			
			String[] cols = { "eve_no" };
			pstmt = con.prepareStatement(INSERT_STMT_init, cols);			
			pstmt.setString(1, evetit_no); 
			pstmt.setString(2, "V001"); 		
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				eve_no = rs.getString(1);
			}
			
			pstmt = con.prepareStatement(UPDATE_evetit_sessions_ByEvent_STMT);
			evetit_sessions += 1;
			pstmt.setInt(1, evetit_sessions);
			pstmt.setString(2, evetit_no);
			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			
			System.out.println("----------Inserted_init with evetit_no and evetit_sessions----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		return eve_no;
	}
	
	
	
	@Override
	public Set<SeatingAreaVO> getSeatingAreasByEvent(String eve_no){
		Set<SeatingAreaVO> set = new LinkedHashSet<>();
		SeatingAreaVO seatingareaVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;		
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_SeatingArea_ByEvent_STMT);
			pstmt.setString(1, eve_no);
					
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
				set.add(seatingareaVO);
			}
			
			System.out.println("----------getSeatingAreasByEvent finished----------");

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
	public EventVO copyEvent_withTicketTypeAndSeatingArea(String eve_no, String eve_no_forCopy) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		EventVO eventVO = null;	
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);			
			
			con.setAutoCommit(false);

			// 修改複製場次
			pstmt = con.prepareStatement(UPDATE_STMT);			
			eventVO = findByPrimaryKey(eve_no_forCopy);			
			eventVO.setEve_no(eve_no);
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
			eventVO = findByPrimaryKey(eve_no);
			
			// 刪除原票種票區
			TicketTypeJDBCDAO ticketTypeDAO = new TicketTypeJDBCDAO();
			SeatingAreaJDBCDAO seatingAreaDAO = new SeatingAreaJDBCDAO();
			
			Set<SeatingAreaVO> seatingAreaVOSet = getSeatingAreasByEvent(eve_no);
			for(SeatingAreaVO aSeatingAreaVO : seatingAreaVOSet) {
				seatingAreaDAO.delete(aSeatingAreaVO.getTicarea_no());
			}			
			Set<TicketTypeVO> ticketTypeVoSet = getTicketTypesByEvent(eve_no);
			for(TicketTypeVO aTicketTypeVO : ticketTypeVoSet) {
				ticketTypeDAO.delete(aTicketTypeVO.getTictype_no());
			}			
			
			// 新增複製票種
			Set<TicketTypeVO> ticketTypeVoSet_forCopy = getTicketTypesByEvent(eve_no_forCopy);
			for(TicketTypeVO aTicketTypeVO : ticketTypeVoSet_forCopy) {
				Set<SeatingAreaVO> seatingAreaVoSet_forCopy = ticketTypeDAO.getSeatingAreasByTicketType(aTicketTypeVO.getTictype_no());
				aTicketTypeVO.setTictype_no(null);
				aTicketTypeVO.setEve_no(eve_no);				
				ticketTypeDAO.copyInsertFromEvent(aTicketTypeVO, seatingAreaVoSet_forCopy, con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			
			System.out.println("----------copyEvent_withTicketTypeAndSeatingArea----------");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back from ---Event---");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		return eventVO;
	}

	
	
	//========== BEAR begin ======================================================================
	@Override
	public List<EventVO> findByEveTit_no(String evetit_no) {
		return null;
	}
	//========== BEAR end ======================================================================

	
	
	public static void main(String[] args) {
		
		
		
		EventJDBCDAO dao = new EventJDBCDAO();
		
		
		
		// 新增		
//		FileInputStream fis1 = null;
//		ByteArrayOutputStream baos1 = null;	
//		try {
//			EventVO eventVO1 = new EventVO();			
//			
//			eventVO1.setEvetit_no("E0001"); 
//			eventVO1.setVenue_no("V001");		
//			eventVO1.setEve_sessionname("第三場");					
//			
//			fis1 = new FileInputStream("writeImgJDBC/tomcat.jpg");			
//			baos1 = new ByteArrayOutputStream();			
//			int i;
//			while ((i = fis1.read()) != -1)
//				baos1.write(i);			
//			eventVO1.setEve_seatmap(baos1.toByteArray());	
//			
//			eventVO1.setEve_startdate(java.sql.Timestamp.valueOf("2018-08-20 12:00:00"));							
//			eventVO1.setEve_enddate(java.sql.Timestamp.valueOf("2019-03-10 14:30:00"));		
//			eventVO1.setEve_onsaledate(java.sql.Timestamp.valueOf("2018-09-01 10:00:00")); 				
//			eventVO1.setEve_offsaledate(java.sql.Timestamp.valueOf("2018-03-09 24:00:00"));				
//			eventVO1.setTiclimit(new Integer(4));			
//			eventVO1.setFullrefundenddate(java.sql.Timestamp.valueOf("2018-09-10 10:00:00"));
//			eventVO1.setEve_status("normal");
//			
//			dao.insert(eventVO1);
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
//			EventVO eventVO2 = new EventVO();
//			
//			eventVO2.setEve_no("EV00001"); 			
//			eventVO2.setVenue_no("V001");
//			eventVO2.setEve_sessionname("第一場");					
//			
//			fis2 = new FileInputStream("writeImgJDBC/java.jpg");			
//			baos2 = new ByteArrayOutputStream();			
//			int i;
//			while ((i = fis2.read()) != -1)
//				baos2.write(i);			
//			eventVO2.setEve_seatmap(baos2.toByteArray());	
//			
//			eventVO2.setEve_startdate(java.sql.Timestamp.valueOf("2017-08-22 12:00:00"));							
//			eventVO2.setEve_enddate(java.sql.Timestamp.valueOf("2018-03-12 14:30:00"));		
//			eventVO2.setEve_onsaledate(java.sql.Timestamp.valueOf("2017-09-01 10:00:00")); 				
//			eventVO2.setEve_offsaledate(java.sql.Timestamp.valueOf("2017-03-31 24:00:00"));				
//			eventVO2.setTiclimit(new Integer(4));			
//			eventVO2.setFullrefundenddate(null);
//			eventVO2.setEve_status("cancel");
//			
//			dao.update(eventVO2);
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
//		dao.delete("EV00006", "E0003", 6);
//		System.out.println("------------------------------");
		
		
		
		// 查詢一個
//		EventVO EventVO03 = dao.findByPrimaryKey("EV00001");
//		System.out.println(EventVO03.getEve_no());
//		System.out.println(EventVO03.getEvetit_no());
//		System.out.println(EventVO03.getVenue_no());
//		System.out.println(EventVO03.getEve_sessionname());
//		
//		try (PrintStream ps = new PrintStream(new FileOutputStream("readImgJDBC/eventTest.jpg"), true)){
//			ps.write(EventVO03.getEve_seatmap());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(EventVO03.getEve_startdate());
//		System.out.println(EventVO03.getEve_enddate());
//		System.out.println(EventVO03.getEve_onsaledate());
//		System.out.println(EventVO03.getEve_offsaledate());
//		System.out.println(EventVO03.getTiclimit());
//		
//		System.out.println(EventVO03.getFullrefundenddate());
//		System.out.println(EventVO03.getEve_status());	
//		System.out.println("------------------------------");

		
		
		// 查詢全部
//		List<EventVO> list = dao.getAll();	
//		for(EventVO aEventVO : list) {
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
		
		
		
		// 用活動查票種
//		Set<TicketTypeVO> set = dao.getTicketTypesByEvent("EV00001");	
//		for(TicketTypeVO aTicketTypeVO : set) {
//			System.out.println(aTicketTypeVO.getTictype_no());
//			System.out.println(aTicketTypeVO.getEve_no());
//			System.out.println(aTicketTypeVO.getTictype_color());
//			System.out.println(aTicketTypeVO.getTictype_name());
//			System.out.println(aTicketTypeVO.getTictype_price());		
//			System.out.println("------------------------------");
//		}
		
		
		
		// 無圖片修改			
//		EventVO eventVO4 = new EventVO();		
//		eventVO4.setEve_no("EV00002"); 			
//		eventVO4.setVenue_no("V001");
//		eventVO4.setEve_sessionname("第一場!!!!!!!!!!!!!!!!");								
//		eventVO4.setEve_startdate(java.sql.Timestamp.valueOf("2022-08-22 12:00:00"));							
//		eventVO4.setEve_enddate(java.sql.Timestamp.valueOf("2022-03-12 14:30:00"));		
//		eventVO4.setEve_onsaledate(java.sql.Timestamp.valueOf("2022-09-01 10:00:00")); 				
//		eventVO4.setEve_offsaledate(java.sql.Timestamp.valueOf("2022-03-31 24:00:00"));				
//		eventVO4.setTiclimit(new Integer(3));			
//		eventVO4.setFullrefundenddate(null);
//		eventVO4.setEve_status("cancel");		
//		dao.update_withoutSeatmap(eventVO4);
			
		
		
		// 初始化新增			
//		dao.insert("E0003", 5);
		
		
		
		// 用活動查票區
//		Set<SeatingAreaVO> set = dao.getSeatingAreasByEvent("EV00001");
//		for(SeatingAreaVO aSeatingareaVO :set) {
//			System.out.println(aSeatingareaVO.getTicarea_no());
//			System.out.println(aSeatingareaVO.getEve_no());
//			System.out.println(aSeatingareaVO.getTictype_no());
//			System.out.println(aSeatingareaVO.getTicarea_color());
//			System.out.println(aSeatingareaVO.getTicarea_name());
//			System.out.println(aSeatingareaVO.getTictotalnumber());
//			System.out.println(aSeatingareaVO.getTicbookednumber());
//			System.out.println("------------------------------");
//		}
		
		
		
		// 複製場次票種票區					
//		dao.copyEvent_withTicketTypeAndSeatingArea("EV00014", "EV00001");
		
		
	}

	
	
}
