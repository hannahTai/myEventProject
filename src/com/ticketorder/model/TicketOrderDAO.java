package com.ticketorder.model;
import java.util.*;
import java.util.Date;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.seating_area.model.SeatingAreaDAO;
import com.seating_area.model.SeatingAreaService;
import com.seating_area.model.SeatingAreaVO;
import com.ticket.model.TicketDAO;
import com.ticket.model.TicketDAOJDBC;
import com.ticket.model.TicketVO;

public class TicketOrderDAO implements TicketOrderDAO_interface{
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
			"INSERT INTO ticket_order (ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status) VALUES ('TO_'||(TO_CHAR(SYSDATE,'YYYYMMDD'))||'_'||LPAD(to_char(TICKET_ORDER_SEQ.NEXTVAL), 6, '0'),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT=
			"SELECT ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status FROM ticket_order order by ticket_order_no";
	private static final String GET_ONE_STMT=
			"SELECT ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status FROM ticket_order where ticket_order_no=?";
	private static final String DELETE = 
			"DELETE FROM ticket_order where ticket_order_no = ?";
	private static final String UPDATE = 
			"UPDATE ticket_order set member_no=?, ticarea_no=?, total_price=?, total_amount=?, ticket_order_time=?, payment_method=?,ticket_order_status=? where ticket_order_no=?";
	
	@Override
	public void insert(TicketOrderVO ticketorderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ticket_order_no);

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
	public TicketOrderVO findByPrimaryKey(String ticket_order_no) {
		TicketOrderVO ticketorderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
//		TicketOrderDAO dao = new TicketOrderDAO();
//		//ADD
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
////		dao.delete("TO_20181214_000004"); 
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
//	}

	@Override
	public Set<TicketVO> getTicketsByTicketOrder(String ticket_order_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertWithTickets(TicketOrderVO ticketorderVO, List<TicketVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
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
			TicketDAO dao = new TicketDAO();
			System.out.println("list.size()-A="+list.size());
			System.out.println("list.toString="+list);
			for (TicketVO aTicketVO : list) {
				aTicketVO.setTicket_order_no(next_ticket_order_no);
				System.out.println("aTicketVO.getTicket_no()="+aTicketVO.getTicket_no());
				System.out.println("aTicketVO.getTicarea_no()="+aTicketVO.getTicarea_no());
				System.out.println("aTicketVO.getTicket_order_no()="+aTicketVO.getTicket_order_no());
				System.out.println("aTicketVO.getMember_no()="+aTicketVO.getMember_no());
				System.out.println("aTicketVO.getTicket_status()="+aTicketVO.getTicket_status());
				System.out.println("aTicketVO.getTicket_create_time()="+aTicketVO.getTicket_create_time());
				System.out.println("aTicketVO.getTicket_resale_status()="+aTicketVO.getTicket_resale_status());
				System.out.println("aTicketVO.getTicket_resale_price()="+aTicketVO.getTicket_resale_price());
				System.out.println("aTicketVO.getIs_from_resale()="+aTicketVO.getIs_from_resale());
				
				dao.insertTickets(aTicketVO, con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+list.size());
			System.out.println("新增訂票訂單編號" + next_ticket_order_no + "時,共有幾張票券:" + list.size());
			
			// Handle any driver errors
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

	@Override
	public String insertTicketOrderAndUpdateTicArea(TicketOrderVO ticketorderVO, SeatingAreaVO seatingareaVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String strReturn = null;
		try {
			con = ds.getConnection();
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先新增ticket_order
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
			
			//get nextSeq value
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				strReturn = rs.getString(1);
				System.out.println("自增主鍵值= " + strReturn +"(剛新增成功的訂票訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			
			// 再同時update tic_area's ticbookednumber
			SeatingAreaDAO seatingareaDAO = new SeatingAreaDAO();
//			seatingareaDAO.updateSeatingAreaVOBecauseTicketOrderCreated(seatingareaVO, con);
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			
			// Handle any driver errors
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
		return strReturn;
	}

	@Override
	public void updateTicketOrderAndInsertTickets(TicketOrderVO ticketorderVO, List<TicketVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先update ticket_order
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
			
			// 再同時新增票券
			TicketDAO dao = new TicketDAO();
			System.out.println("list.size()-A="+list.size());
			for (TicketVO aTicketVO : list) {
				aTicketVO.setTicket_order_no(ticketorderVO.getTicket_order_no());
				dao.insertTickets(aTicketVO, con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+list.size());
			System.out.println("UPDATE訂票訂單時,共有幾張票券:" + list.size());
			
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-TicketOrderDAO updateTicketOrderAndInsertTickets");
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

	@Override
	public String cancelTicketOrderByServlet(String ticket_order_no) {
		TicketOrderVO ticketorderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先get TicketOrderVO
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
			//check out the status , only cancel the 'WAITTOPAY'
			String toStatus = ticketorderVO.getTicket_order_status();
			if(!toStatus.contains("WAIT")) {
				throw new SQLException("status don't contain WAIT,cant cancel");
			}
			
    		this.cancelOneTicketOrderByServlet(ticketorderVO, con);
    		
    		//after update TicketOrderStatus to outdate4, then update the seatingarea's bookednumber
    		String ticarea_no = ticketorderVO.getTicarea_no();
    		Integer total_amount_thisTicketOrder = ticketorderVO.getTotal_amount();
    		
			//同時delete tic_area's bookednumber
			SeatingAreaDAO seatingareaDAO = new SeatingAreaDAO();
//			SeatingAreaVO sVO = seatingareaDAO.findByPrimaryKeyWithCon(ticarea_no, con);
//			Integer currentBookedNum = sVO.getTicbookednumber();
//			sVO.setTicbookednumber(currentBookedNum-total_amount_thisTicketOrder);
//			seatingareaDAO.updateSeatingAreaVOBecauseTicketOrderCancelledByServlet(sVO, con);
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-TicketOrderDAO cancelTicketOrderByServlet");
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
		return ticket_order_no;
	}
	public void cancelOneTicketOrderByServlet(TicketOrderVO ticketorderVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, ticketorderVO.getMember_no());
			pstmt.setString(2, ticketorderVO.getTicarea_no());
			pstmt.setInt(3, ticketorderVO.getTotal_price());
			pstmt.setInt(4, ticketorderVO.getTotal_amount());
			pstmt.setTimestamp(5, ticketorderVO.getTicket_order_time());
			pstmt.setString(6, ticketorderVO.getPayment_method());
			pstmt.setString(7, "OUTDATE4");
			pstmt.setString(8, ticketorderVO.getTicket_order_no());

			pstmt.executeUpdate();
			
			System.out.println("----------Updated----------");

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-cancelOneTicketOrderByServlet at TicketOrderDAO.java");
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
