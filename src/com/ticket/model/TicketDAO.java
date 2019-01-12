package com.ticket.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ticketorder.model.TicketOrderDAOJDBC;
import com.ticketorder.model.TicketOrderVO;

public class TicketDAO implements TicketDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ETIckeTsDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT=
			"INSERT INTO ticket (ticket_no,ticarea_no,ticket_order_no,member_no,ticket_status,ticket_create_time,ticket_resale_status,ticket_resale_price,is_from_resale) VALUES ('T_'||(TO_CHAR(SYSDATE,'YYYYMMDD'))||'_'||LPAD(to_char(TICKET_SEQ.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT=
			"SELECT ticket_no,ticarea_no,ticket_order_no,member_no,ticket_status,ticket_create_time,ticket_resale_status,ticket_resale_price,is_from_resale FROM ticket order by ticket_no";
	private static final String GET_ONE_STMT=
			"SELECT ticket_no,ticarea_no,ticket_order_no,member_no,ticket_status,ticket_create_time,ticket_resale_status,ticket_resale_price,is_from_resale FROM ticket where ticket_no=?";
	private static final String DELETE = 
			"DELETE FROM ticket where ticket_no = ?";
	private static final String UPDATE = 
			"UPDATE ticket set ticarea_no=?, ticket_order_no=?, member_no=?, ticket_status=?, ticket_create_time=?, ticket_resale_status=?,ticket_resale_price=?,is_from_resale=? where ticket_no=?";

	@Override
	public void insert(TicketVO ticketVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, ticketVO.getTicarea_no());
			pstmt.setString(2, ticketVO.getTicket_order_no());
			pstmt.setString(3, ticketVO.getMember_no());
			pstmt.setString(4, ticketVO.getTicket_status());
			pstmt.setTimestamp(5, ticketVO.getTicket_create_time());
			pstmt.setString(6, ticketVO.getTicket_resale_status());
			pstmt.setInt(7, ticketVO.getTicket_resale_price());
			pstmt.setString(8, ticketVO.getIs_from_resale());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(TicketVO ticketVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, ticketVO.getTicarea_no());
			pstmt.setString(2, ticketVO.getTicket_order_no());
			pstmt.setString(3, ticketVO.getMember_no());
			pstmt.setString(4, ticketVO.getTicket_status());
			pstmt.setTimestamp(5, ticketVO.getTicket_create_time());
			pstmt.setString(6, ticketVO.getTicket_resale_status());
			pstmt.setInt(7, ticketVO.getTicket_resale_price());
			pstmt.setString(8, ticketVO.getIs_from_resale());
			pstmt.setString(9, ticketVO.getTicket_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void delete(String ticket_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ticket_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public TicketVO findByPrimaryKey(String ticket_no) {
		TicketVO ticketVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ticket_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				ticketVO = new TicketVO();
				ticketVO.setTicket_no(rs.getString("ticket_no"));
				ticketVO.setTicarea_no(rs.getString("ticarea_no"));
				ticketVO.setTicket_order_no(rs.getString("ticket_order_no"));
				ticketVO.setMember_no(rs.getString("member_no"));
				ticketVO.setTicket_status(rs.getString("ticket_status"));
				ticketVO.setTicket_create_time(rs.getTimestamp("ticket_create_time"));
				ticketVO.setTicket_resale_status(rs.getString("ticket_resale_status"));
				ticketVO.setTicket_resale_price(rs.getInt("ticket_resale_price"));
				ticketVO.setIs_from_resale(rs.getString("is_from_resale"));
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return ticketVO;
	}

	@Override
	public List<TicketVO> getAll() {
		List<TicketVO> list = new ArrayList<TicketVO>();
		TicketVO ticketVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				ticketVO = new TicketVO();
				ticketVO.setTicket_no(rs.getString("ticket_no"));
				ticketVO.setTicarea_no(rs.getString("ticarea_no"));
				ticketVO.setTicket_order_no(rs.getString("ticket_order_no"));
				ticketVO.setMember_no(rs.getString("member_no"));
				ticketVO.setTicket_status(rs.getString("ticket_status"));
				ticketVO.setTicket_create_time(rs.getTimestamp("ticket_create_time"));
				ticketVO.setTicket_resale_status(rs.getString("ticket_resale_status"));
				ticketVO.setTicket_resale_price(rs.getInt("ticket_resale_price"));
				ticketVO.setIs_from_resale(rs.getString("is_from_resale"));
				list.add(ticketVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
//	public static void main (String[] args) {
//		TicketDAO dao = new TicketDAO();
//		//ADD
////		TicketVO tVO = new TicketVO();
////		tVO.setTicarea_no("E000101A01");
////		tVO.setTicket_order_no("TO_20181225_000001");
////		tVO.setMember_no("M000001");
////		tVO.setTicket_status("ACTIVE1");
////		long Ltime = new Date().getTime();
////		Timestamp times = new Timestamp(Ltime);
////		tVO.setTicket_create_time(times);
////		tVO.setTicket_resale_status("NONE1");
////		tVO.setTicket_resale_price(0);
////		tVO.setIs_from_resale("NO");
////		dao.insert(tVO);
////		//DELETE
////		dao.delete("T_20181214_000004");
//		
//		//SELECT WITH PK
//		TicketVO tVO2 = dao.findByPrimaryKey("T_20181225_000001");
//		System.out.println("------select1 start--------");
//		System.out.print(tVO2.getTicket_no() + ",");
//		System.out.print(tVO2.getTicarea_no() + ",");
//		System.out.print(tVO2.getTicket_order_no() + ",");
//		System.out.print(tVO2.getMember_no() + ",");
//		System.out.print(tVO2.getTicket_status() + ",");
//		System.out.print(tVO2.getTicket_create_time() + ",");
//		System.out.print(tVO2.getTicket_resale_status() + ",");
//		System.out.print(tVO2.getTicket_resale_price() + ",");
//		System.out.print(tVO2.getIs_from_resale() + ",");
//		System.out.println("---------------------");
//		//UPDATE
////		tVO2.setTicket_status("USED2");
////		dao.update(tVO2);
//		
//		//SELECT WTIH LIST
//		List<TicketVO> list = dao.getAll();
//		for(TicketVO aVO :list) {
//			System.out.println("------selectALL start--------");
//			System.out.print(aVO.getTicket_no() + ",");
//			System.out.print(aVO.getTicarea_no() + ",");
//			System.out.print(aVO.getTicket_order_no() + ",");
//			System.out.print(aVO.getMember_no() + ",");
//			System.out.print(aVO.getTicket_status() + ",");
//			System.out.print(aVO.getTicket_create_time() + ",");
//			System.out.print(aVO.getTicket_resale_status() + ",");
//			System.out.print(aVO.getTicket_resale_price() + ",");
//			System.out.print(aVO.getIs_from_resale() + ",");
//			System.out.println("---------------------");
//		}
//	}

	@Override
	public void insertTickets(TicketVO ticketVO, Connection con) {
		System.out.println("ticketVO.toString()="+ticketVO);
		PreparedStatement pstmt = null;
		try {
     		pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, ticketVO.getTicarea_no());
			pstmt.setString(2, ticketVO.getTicket_order_no());
			pstmt.setString(3, ticketVO.getMember_no());
			pstmt.setString(4, ticketVO.getTicket_status());
			pstmt.setTimestamp(5, ticketVO.getTicket_create_time());
			pstmt.setString(6, ticketVO.getTicket_resale_status());
			pstmt.setInt(7, ticketVO.getTicket_resale_price());
			pstmt.setString(8, ticketVO.getIs_from_resale());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-ticket");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}
}
