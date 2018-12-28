package com.ticket_refund_policy.model;

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

import com.event_classification.model.EventClassificationJDBCDAO;



public class TicketRefundPolicyJNDIDAO implements TicketRefundPolicyDAO_interface {
	
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
			"INSERT INTO TICKET_REFUND_POLICY (TICREFPOLICY_NO,TICREFPOLICY_NAME,TICREFPOLICY_CONTENT) VALUES (?,?,?)";

	private static final String UPDATE_STMT = 
			"UPDATE TICKET_REFUND_POLICY SET TICREFPOLICY_NAME=?,TICREFPOLICY_CONTENT=? WHERE TICREFPOLICY_NO=?";
	
	private static final String DELETE_STMT = 
			"DELETE FROM TICKET_REFUND_POLICY WHERE TICREFPOLICY_NO=?";

	private static final String GET_ONE_STMT = 
			"SELECT TICREFPOLICY_NO,TICREFPOLICY_NAME,TICREFPOLICY_CONTENT FROM TICKET_REFUND_POLICY WHERE TICREFPOLICY_NO = ?";

	private static final String GET_ALL_STMT = 
			"SELECT TICREFPOLICY_NO,TICREFPOLICY_NAME,TICREFPOLICY_CONTENT FROM TICKET_REFUND_POLICY";
	
	@Override
	public void insert(TicketRefundPolicyVO ticketRefundPolicyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, ticketRefundPolicyVO.getTicRefPolicy_no());
			pstmt.setString(2, ticketRefundPolicyVO.getTicRefPolicy_name());
			pstmt.setString(3, ticketRefundPolicyVO.getTicRefPolicy_content());
			
			pstmt.executeUpdate();
			System.out.println("----------Inserted----------");
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
	public void update(TicketRefundPolicyVO ticketRefundPolicyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, ticketRefundPolicyVO.getTicRefPolicy_name());
			pstmt.setString(2, ticketRefundPolicyVO.getTicRefPolicy_content());
			pstmt.setString(3, ticketRefundPolicyVO.getTicRefPolicy_no());
			
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
	public void delete(String ticRefPolicy_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ticRefPolicy_no);

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
	public TicketRefundPolicyVO findByPrimaryKey(String ticRefPolicy_no) {
		
		TicketRefundPolicyVO ticketRefundPolicyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1,ticRefPolicy_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticketRefundPolicyVO = new TicketRefundPolicyVO();
				ticketRefundPolicyVO.setTicRefPolicy_no(rs.getString("TICREFPOLICY_NO"));
				ticketRefundPolicyVO.setTicRefPolicy_name(rs.getString("TICREFPOLICY_NAME"));
				ticketRefundPolicyVO.setTicRefPolicy_content(rs.getString("TICREFPOLICY_CONTENT"));
			}
			
			System.out.println("----------findByPrimaryKey finished----------");

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
		return ticketRefundPolicyVO;
	}

	@Override
	public List<TicketRefundPolicyVO> getAll() {
		List<TicketRefundPolicyVO> list = new ArrayList<TicketRefundPolicyVO>();
		TicketRefundPolicyVO ticketRefundPolicyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticketRefundPolicyVO = new TicketRefundPolicyVO();
				ticketRefundPolicyVO.setTicRefPolicy_no(rs.getString("TICREFPOLICY_NO"));
				ticketRefundPolicyVO.setTicRefPolicy_name(rs.getString("TICREFPOLICY_NAME"));
				ticketRefundPolicyVO.setTicRefPolicy_content(rs.getString("TICREFPOLICY_CONTENT"));
				list.add(ticketRefundPolicyVO);
			}
			
			System.out.println("----------getAll finished----------");

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
		return list;
	}

}
