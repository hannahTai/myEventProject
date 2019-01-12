package com.ticketorder.model;
import java.util.*;

import com.seating_area.model.SeatingAreaVO;
import com.ticket.model.TicketDAOJDBC;
import com.ticket.model.TicketVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TicketOrderDAOJDBC implements TicketOrderDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA105G2";
	String passwd = "123456";
	private static final String INSERT_STMT=
			"INSERT INTO ticket_order (ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status) VALUES ('TO_'||(TO_CHAR(SYSDATE,'YYYYMMDD'))||'_'||LPAD(to_char(TICKET_ORDER_SEQ.NEXTVAL), 6, '0'),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT=
			"SELECT ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status FROM ticket_order order by ticket_order_no";
	private static final String GET_ONE_STMT=
			"SELECT ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status FROM ticket_order where ticket_order_no=?";
	private static final String DELETE = 
			"DELETE FROM ticket_order where ticket_order_no = ?";
	private static final String GET_Tickets_ByTicketOrder_STMT = 
			"SELECT ticket_no,ticarea_no,ticket_order_no,member_no,ticket_status,ticket_create_time,ticket_resale_status,ticket_resale_price,is_from_resale FROM ticket where ticket_order_no = ? order by ticket_no";
	private static final String UPDATE = 
			"UPDATE ticket_order set member_no=?, ticarea_no=?, total_price=?, total_amount=?, ticket_order_time=?, payment_method=?,ticket_order_status=? where ticket_order_no=?";
	
	@Override
	public void insert(TicketOrderVO ticketorderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, ticketorderVO.getMember_no());
			pstmt.setString(2, ticketorderVO.getTicarea_no());
			pstmt.setInt(3, ticketorderVO.getTotal_price());
			pstmt.setInt(4, ticketorderVO.getTotal_amount());
			pstmt.setTimestamp(5, ticketorderVO.getTicket_order_time());
			pstmt.setString(6, ticketorderVO.getPayment_method());
			pstmt.setString(7, ticketorderVO.getTicket_order_status());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public void update(TicketOrderVO ticketorderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, ticketorderVO.getMember_no());
			pstmt.setString(2, ticketorderVO.getTicarea_no());
			pstmt.setInt(3, ticketorderVO.getTotal_price());
			pstmt.setInt(4, ticketorderVO.getTotal_amount());
			pstmt.setTimestamp(5, ticketorderVO.getTicket_order_time());
			pstmt.setString(6, ticketorderVO.getPayment_method());
			pstmt.setString(7, ticketorderVO.getTicket_order_status());
			pstmt.setString(8, ticketorderVO.getTicket_order_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public void delete(String ticket_order_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ticket_order_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public TicketOrderVO findByPrimaryKey(String ticket_order_no) {
		TicketOrderVO ticketorderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ticket_order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				ticketorderVO = new TicketOrderVO();
				ticketorderVO.setTicket_order_no(rs.getString("ticket_order_no"));
				ticketorderVO.setMember_no(rs.getString("member_no"));
				ticketorderVO.setTicarea_no(rs.getString("ticarea_no"));
				ticketorderVO.setTotal_price(rs.getInt("total_price"));
				ticketorderVO.setTotal_amount(rs.getInt("total_amount"));
				ticketorderVO.setTicket_order_time(rs.getTimestamp("ticket_order_time"));
				ticketorderVO.setPayment_method(rs.getString("payment_method"));
				ticketorderVO.setTicket_order_status(rs.getString("ticket_order_status"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return ticketorderVO;
	}

	@Override
	public List<TicketOrderVO> getAll() {
		List<TicketOrderVO> list = new ArrayList<TicketOrderVO>();
		TicketOrderVO ticketorderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				ticketorderVO = new TicketOrderVO();
				ticketorderVO.setTicket_order_no(rs.getString("ticket_order_no"));
				ticketorderVO.setMember_no(rs.getString("member_no"));
				ticketorderVO.setTicarea_no(rs.getString("ticarea_no"));
				ticketorderVO.setTotal_price(rs.getInt("total_price"));
				ticketorderVO.setTotal_amount(rs.getInt("total_amount"));
				ticketorderVO.setTicket_order_time(rs.getTimestamp("ticket_order_time"));
				ticketorderVO.setPayment_method(rs.getString("payment_method"));
				ticketorderVO.setTicket_order_status(rs.getString("ticket_order_status"));
				list.add(ticketorderVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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

	@Override
	public Set<TicketVO> getTicketsByTicketOrder(String ticket_order_no) {
		Set<TicketVO> set = new HashSet<TicketVO>();
		TicketVO ticketVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_Tickets_ByTicketOrder_STMT);
			pstmt.setString(1, ticket_order_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
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
				set.add(ticketVO); // Store the row in the vector
			}
	
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void insertWithTickets(TicketOrderVO ticketorderVO, List<TicketVO> list) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先新增部門
			String cols[] = {"TICKET_ORDER_NO"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);			
			pstmt.setString(1, ticketorderVO.getMember_no());
			pstmt.setString(2, ticketorderVO.getTicarea_no());
			pstmt.setInt(3, ticketorderVO.getTotal_price());
			pstmt.setInt(4, ticketorderVO.getTotal_amount());
			pstmt.setTimestamp(5, ticketorderVO.getTicket_order_time());
			pstmt.setString(6, ticketorderVO.getPayment_method());
			pstmt.setString(7, ticketorderVO.getTicket_order_status());
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String next_ticket_order_no = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_ticket_order_no = rs.getString(1);
				System.out.println("自增主鍵值= " + next_ticket_order_no +"(剛新增成功的訂票訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增票券
			TicketDAOJDBC dao = new TicketDAOJDBC();
			System.out.println("list.size()-A="+list.size());
			for (TicketVO aTicket : list) {
				aTicket.setTicket_order_no(next_ticket_order_no);
				dao.insertTickets(aTicket, con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+list.size());
			System.out.println("新增訂票訂單編號" + next_ticket_order_no + "時,共有幾張票券:" + list.size());
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-TicketOrderDAO");
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	public static void main (String[] args) {
		TicketOrderDAOJDBC dao = new TicketOrderDAOJDBC();
		//ADD
//		TicketOrderVO ticketorderVO = new TicketOrderVO();
//		ticketorderVO.setMember_no("M000001");
//		ticketorderVO.setTicarea_no("E000101A01");
//		ticketorderVO.setTotal_price(9999);
//		ticketorderVO.setTotal_amount(2);
//		long Ltime = new Date().getTime();
//		Timestamp times = new Timestamp(Ltime);
//		ticketorderVO.setTicket_order_time(times);
//		ticketorderVO.setPayment_method("CREDITCARD");
//		ticketorderVO.setTicket_order_status("WAITPAY1");
//		dao.insert(ticketorderVO);
//		
//		//SELECT WITH LIST
//		System.out.println("------selectAll after add start--------");
//		List<TicketOrderVO> list = dao.getAll();
//		for(TicketOrderVO aVO :list) {
//			System.out.print(aVO.getTicket_order_no() + ",");
//			System.out.print(aVO.getMember_no() + ",");
//			System.out.print(aVO.getTicarea_no() + ",");
//			System.out.print(aVO.getTotal_price() + ",");
//			System.out.print(aVO.getTotal_amount() + ",");
//			System.out.print(aVO.getTicket_order_time() + ",");
//			System.out.print(aVO.getPayment_method() + ",");
//			System.out.print(aVO.getTicket_order_status() + ",");
//			System.out.println();
//		}
//		System.out.println("---------------------");
//		
//		//DELETE
//		dao.delete("TO_20181214_000004"); 
//		
//		//SELECT WITH PK
//		TicketOrderVO VO2 = dao.findByPrimaryKey("TO_20181225_000001");
//		System.out.println("------select1 start--------");
//		System.out.print(VO2.getTicket_order_no() + ",");
//		System.out.print(VO2.getMember_no() + ",");
//		System.out.print(VO2.getTicarea_no() + ",");
//		System.out.print(VO2.getTotal_price() + ",");
//		System.out.print(VO2.getTotal_amount() + ",");
//		System.out.print(VO2.getTicket_order_time() + ",");
//		System.out.print(VO2.getPayment_method() + ",");
//		System.out.print(VO2.getTicket_order_status() + ",");
//		System.out.println("---------------------");
//		
//		//UPDATE
//		VO2.setTotal_price(9870); //update VO2.price to 9000;
////		dao.update(VO2);
//		
//		//SELECT WITH LIST
//		System.out.println("------Final selectAll start--------");
//		List<TicketOrderVO> list2 = dao.getAll();
//		for(TicketOrderVO aVO :list2) {
//			System.out.print(aVO.getTicket_order_no() + ",");
//			System.out.print(aVO.getMember_no() + ",");
//			System.out.print(aVO.getTicarea_no() + ",");
//			System.out.print(aVO.getTotal_price() + ",");
//			System.out.print(aVO.getTotal_amount() + ",");
//			System.out.print(aVO.getTicket_order_time() + ",");
//			System.out.print(aVO.getPayment_method() + ",");
//			System.out.print(aVO.getTicket_order_status() + ",");
//			System.out.println();
//		}
//		System.out.println("---------------------");
		
//		Set<TicketVO> tSet = dao.getTicketsByTicketOrder("TO_20181225_000001"); 
//		System.out.println(tSet);
//		for (TicketVO aTVO : tSet) {
//			System.out.println(aTVO.getTicket_no());
//		}
//		TicketOrderVO toVO = new TicketOrderVO(); add one to and 2 t here manually
		
	}

	@Override
	public String insertTicketOrderAndUpdateTicArea(TicketOrderVO ticketorderVO, SeatingAreaVO seatingareaVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateTicketOrderAndInsertTickets(TicketOrderVO ticketorderVO, List<TicketVO> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String cancelTicketOrderByServlet(String ticket_order_no) {
		// TODO Auto-generated method stub
		return null;
	}

}
