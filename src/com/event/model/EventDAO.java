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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ticket_type.model.TicketTypeVO;

public class EventDAO implements EventDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ETIckeTsDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
	
	
	
	@Override
	public String insert(EventVO eventVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String eve_no = null;
		
		try {
			con = ds.getConnection();

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
			con = ds.getConnection();
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
			con = ds.getConnection();
			
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
	};

	
	@Override
	public void update_withoutSeatmap(EventVO eventVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
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
			con = ds.getConnection();

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
	
	
}
