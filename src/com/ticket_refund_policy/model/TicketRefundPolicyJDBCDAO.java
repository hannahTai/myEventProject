package com.ticket_refund_policy.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.event_classification.model.EventClassificationJDBCDAO;



public class TicketRefundPolicyJDBCDAO implements TicketRefundPolicyDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, ticketRefundPolicyVO.getTicRefPolicy_no());
			pstmt.setString(2, ticketRefundPolicyVO.getTicRefPolicy_name());
			pstmt.setString(3, ticketRefundPolicyVO.getTicRefPolicy_content());
			
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
	public void update(TicketRefundPolicyVO ticketRefundPolicyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, ticketRefundPolicyVO.getTicRefPolicy_name());
			pstmt.setString(2, ticketRefundPolicyVO.getTicRefPolicy_content());
			pstmt.setString(3, ticketRefundPolicyVO.getTicRefPolicy_no());
			
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
	public void delete(String ticRefPolicy_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ticRefPolicy_no);

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
	public TicketRefundPolicyVO findByPrimaryKey(String ticRefPolicy_no) {
		
		TicketRefundPolicyVO ticketRefundPolicyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		return list;
	}
	
	
	public static void main(String[] args) {
		
		TicketRefundPolicyJDBCDAO dao = new TicketRefundPolicyJDBCDAO();

		
//		TicketRefundPolicyDAO_interface
		
		
		// 新增
//		TicketRefundPolicyVO TicketRefundPolicyVO1 = new TicketRefundPolicyVO();
//		TicketRefundPolicyVO1.setTicRefPolicy_no("TRP5");
//		TicketRefundPolicyVO1.setTicRefPolicy_name("方案五");
//		TicketRefundPolicyVO1.setTicRefPolicy_content("拉拉拉拉拉");
//		dao.insert(TicketRefundPolicyVO1);
		
		// 修改
//		TicketRefundPolicyVO TicketRefundPolicyVO2 = new TicketRefundPolicyVO();
//		TicketRefundPolicyVO2.setTicRefPolicy_no("TRP2");
//		TicketRefundPolicyVO2.setTicRefPolicy_name("方案五");
//		TicketRefundPolicyVO2.setTicRefPolicy_content("拉拉拉拉拉");
//		dao.update(TicketRefundPolicyVO2);
		
		// 刪除
//		dao.delete("TRP5");
//		System.out.println("------------------------------");
		
		// 查詢一個
//		TicketRefundPolicyVO TicketRefundPolicyVO3 = dao.findByPrimaryKey("TRP1");
//		System.out.println(TicketRefundPolicyVO3.getTicRefPolicy_no());
//		System.out.println(TicketRefundPolicyVO3.getTicRefPolicy_name());
//		System.out.println(TicketRefundPolicyVO3.getTicRefPolicy_content());
//		System.out.println("------------------------------");
		
		// 查詢全部
//		List<TicketRefundPolicyVO> list = dao.getAll();
//		for (TicketRefundPolicyVO aTicketRefundPolicyVO : list) {
//			System.out.println(aTicketRefundPolicyVO.getTicRefPolicy_no());
//			System.out.println(aTicketRefundPolicyVO.getTicRefPolicy_name());
//			System.out.println(aTicketRefundPolicyVO.getTicRefPolicy_content());
//			System.out.println("------------------------------");
//		}
	}
}
