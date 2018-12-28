package com.ticket_type.model;

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

public class TicketTypeJNDIDAO implements TicketTypeDAO_interface{

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
			"INSERT INTO TICKET_TYPE (tictype_no, eve_no, tictype_color, tictype_name, tictype_price) "
			+ "VALUES ('ET'||LPAD(to_char(TICTYPE_SEQ.NEXTVAL), 6, '0'), ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE TICKET_TYPE SET tictype_color=?, tictype_name=?, tictype_price=? "
			+ "WHERE tictype_no=?";
	
	private static final String DELETE_STMT = 
			"DELETE FROM TICKET_TYPE WHERE tictype_no=?";
	
	private static final String GET_ONE_STMT = 
			"SELECT tictype_no, eve_no, tictype_color, tictype_name, tictype_price "
			+ "FROM TICKET_TYPE WHERE tictype_no=?";
	
	private static final String GET_ALL_STMT = 
			"SELECT tictype_no, eve_no, tictype_color, tictype_name, tictype_price "
			+ "FROM TICKET_TYPE ORDER BY tictype_no";
	
	
	
	@Override
	public String insert(TicketTypeVO ticketTypeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String tictype_no = null;
		
		try {
			con = ds.getConnection();
			
			String[] cols = { "tictype_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, ticketTypeVO.getEve_no()); 
			pstmt.setString(2, ticketTypeVO.getTictype_color());
			pstmt.setString(3, ticketTypeVO.getTictype_name());
			pstmt.setInt(4, ticketTypeVO.getTictype_price());				
		
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				tictype_no = rs.getString(1);
			}
			
			System.out.println("----------Inserted : " + tictype_no + "----------");

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
		return tictype_no;
	}

	@Override
	public void update(TicketTypeVO ticketTypeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, ticketTypeVO.getTictype_color());
			pstmt.setString(2, ticketTypeVO.getTictype_name());
			pstmt.setInt(3, ticketTypeVO.getTictype_price());	
			pstmt.setString(4, ticketTypeVO.getTictype_no()); 
		
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
	public void delete(String ticketTypeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ticketTypeVO);

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
	public TicketTypeVO findByPrimaryKey(String tictype_no) {
		
		TicketTypeVO ticketTypeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, tictype_no); 
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticketTypeVO = new TicketTypeVO();				
				ticketTypeVO.setTictype_no(rs.getString("tictype_no"));
				ticketTypeVO.setEve_no(rs.getString("eve_no"));
				ticketTypeVO.setTictype_color(rs.getString("tictype_color"));
				ticketTypeVO.setTictype_name(rs.getString("tictype_name"));				
				ticketTypeVO.setTictype_price(rs.getInt("tictype_price"));				
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
		return ticketTypeVO;
	}

	@Override
	public List<TicketTypeVO> getAll() {
		
		List<TicketTypeVO> list = new ArrayList<TicketTypeVO>();
		TicketTypeVO ticketTypeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;		
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {				
				ticketTypeVO = new TicketTypeVO();				
				ticketTypeVO.setTictype_no(rs.getString("tictype_no"));
				ticketTypeVO.setEve_no(rs.getString("eve_no"));
				ticketTypeVO.setTictype_color(rs.getString("tictype_color"));
				ticketTypeVO.setTictype_name(rs.getString("tictype_name"));				
				ticketTypeVO.setTictype_price(rs.getInt("tictype_price"));
				list.add(ticketTypeVO);				
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
