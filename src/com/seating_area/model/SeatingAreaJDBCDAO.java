package com.seating_area.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ticket.model.TicketVO;
import com.ticketorder.model.TicketOrderVO;


public class SeatingAreaJDBCDAO implements SeatingAreaDAO_interface{
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "CA105G2";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT=
			"INSERT INTO seating_area (ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber) "
			+ "VALUES ('ES'||LPAD(to_char(TICAREA_SEQ.NEXTVAL), 8, '0'), ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
			"UPDATE seating_area set ticarea_color=?, ticarea_name=?, tictotalnumber=? "
			+ "WHERE ticarea_no=?";
			
	private static final String DELETE_STMT = 
			"DELETE FROM seating_area WHERE ticarea_no=?";
		 
	private static final String GET_ONE_STMT=
			"SELECT ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber "
			+ "FROM seating_area WHERE ticarea_no=?";
	
	private static final String GET_ALL_STMT=
			"SELECT ticarea_no, eve_no, tictype_no, ticarea_color, ticarea_name, tictotalnumber, ticbookednumber "
			+ "FROM seating_area ORDER BY ticarea_no";

	
	
	private static final String GET_TicketOrders_BySeatingArea_STMT=
			"SELECT ticket_order_no,member_no,ticarea_no,total_price,total_amount,ticket_order_time,payment_method,ticket_order_status FROM ticket_order WHERE ticarea_no=? order by ticket_order_no";

	private static final String GET_Tickets_BySeatingArea_STMT=
			"SELECT ticket_no,ticarea_no,ticket_order_no,member_no,ticket_status,ticket_create_time,ticket_resale_status,ticket_resale_price,is_from_resale FROM ticket WHERE ticarea_no=? order by ticket_no";


	
	@Override
	public String insert(SeatingAreaVO seatingareaVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ticarea_no = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String[] cols = { "ticarea_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, seatingareaVO.getEve_no());
			pstmt.setString(2, seatingareaVO.getTictype_no());
			pstmt.setString(3, seatingareaVO.getTicarea_color());
			pstmt.setString(4, seatingareaVO.getTicarea_name());
			pstmt.setInt(5, seatingareaVO.getTictotalnumber());
			pstmt.setInt(6, seatingareaVO.getTicbookednumber());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ticarea_no = rs.getString(1);
			}
			
			System.out.println("----------Inserted : " + ticarea_no + "----------");

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
		return ticarea_no;
	}
	
	@Override
	public void update(SeatingAreaVO seatingareaVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, seatingareaVO.getTicarea_color());
			pstmt.setString(2, seatingareaVO.getTicarea_name());
			pstmt.setInt(3, seatingareaVO.getTictotalnumber());
			pstmt.setString(4, seatingareaVO.getTicarea_no());

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
	public void delete(String ticarea_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ticarea_no);

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
	public SeatingAreaVO findByPrimaryKey(String ticarea_no) {
		
		SeatingAreaVO seatingareaVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ticarea_no);

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
		return seatingareaVO;
	}

	@Override
	public List<SeatingAreaVO> getAll() {
		
		List<SeatingAreaVO> list = new ArrayList<SeatingAreaVO>();
		SeatingAreaVO seatingareaVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
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
				list.add(seatingareaVO);
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
	public void insertFromTicketType(SeatingAreaVO seatingareaVO , Connection con) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ticarea_no = null;

		try {
			
			String[] cols = { "ticarea_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, seatingareaVO.getEve_no());
			pstmt.setString(2, seatingareaVO.getTictype_no());
			pstmt.setString(3, seatingareaVO.getTicarea_color());
			pstmt.setString(4, seatingareaVO.getTicarea_name());
			pstmt.setInt(5, seatingareaVO.getTictotalnumber());
			pstmt.setInt(6, seatingareaVO.getTicbookednumber());

			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ticarea_no = rs.getString(1);
			}
			
			System.out.println("----------insertFromTicketType : " + ticarea_no + "----------");

		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back from ---SeatingArea---");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}	
	}
    
    
    
	@Override
	public Set<TicketOrderVO> getTicketOrders_BySeatingArea(String ticarea_no) {
		
		Set<TicketOrderVO> ticketOrderVOset = new LinkedHashSet<TicketOrderVO>();
		TicketOrderVO ticketorderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_TicketOrders_BySeatingArea_STMT);
			pstmt.setString(1, ticarea_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ticketorderVO = new TicketOrderVO();
				ticketorderVO.setTicket_order_no(rs.getString("ticket_order_no"));
				ticketorderVO.setMember_no(rs.getString("member_no"));
				ticketorderVO.setTicarea_no(rs.getString("ticarea_no"));
				ticketorderVO.setTotal_price(rs.getInt("total_price"));
				ticketorderVO.setTotal_amount(rs.getInt("total_amount"));
				ticketorderVO.setTicket_order_time(rs.getTimestamp("ticket_order_time"));
				ticketorderVO.setPayment_method(rs.getString("payment_method"));
				ticketorderVO.setTicket_order_status(rs.getString("ticket_order_status"));
				ticketOrderVOset.add(ticketorderVO);
			}

			System.out.println("----------getTicketOrders_BySeatingArea finished----------");

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
		return ticketOrderVOset;
	}
	
	
	
	@Override
	public Set<TicketVO> getTickets_BySeatingArea(String ticarea_no) {
		
		Set<TicketVO> ticketOrderVOset = new LinkedHashSet<TicketVO>();
		TicketVO ticketVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_Tickets_BySeatingArea_STMT);
			pstmt.setString(1, ticarea_no);
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
				ticketOrderVOset.add(ticketVO);
			}

			System.out.println("----------getTickets_BySeatingArea finished----------");

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
		return ticketOrderVOset;
	}
    
	
	
	//========== BEAR begin ======================================================================
	@Override
	public SeatingAreaVO findByPrimaryKeyWithCon(String ticarea_no, Connection con) {
		return null;
	}
	@Override
	public void updateSeatingAreaVOBecauseTicketOrderCreated(SeatingAreaVO seatingareaVO, Connection con) {
		
	}
	@Override
	public void updateSeatingAreaVOBecauseTicketOrderCancelledByServlet(SeatingAreaVO seatingareaVO, Connection con) {
		
	}
	@Override
	public List<SeatingAreaVO> getAllWithEve_No(String eve_no) {
		return null;
	}    
	//========== BEAR end ======================================================================
	

	
	
	
	
	public static void main (String[] args) {
		
		
		
		SeatingAreaJDBCDAO dao = new SeatingAreaJDBCDAO();
		
		
		
		// 新增	
//		SeatingAreaVO seatingareaVO1 = new SeatingAreaVO();	
//		seatingareaVO1.setEve_no("EV00002");
//		seatingareaVO1.setTictype_no("ET000008");	
//		seatingareaVO1.setTicarea_color("#EE7700");
//		seatingareaVO1.setTicarea_name("搖滾A3區");
//		seatingareaVO1.setTictotalnumber(new Integer(200));
//		seatingareaVO1.setTicbookednumber(new Integer(5));
//		dao.insert(seatingareaVO1);
		
		
		
		// 修改	
//		SeatingAreaVO seatingareaVO2 = new SeatingAreaVO();
//		seatingareaVO2.setTicarea_no("ES00000001");
//		seatingareaVO2.setTicarea_color("#EEEEEE");
//		seatingareaVO2.setTicarea_name("搖滾搖滾區");
//		seatingareaVO2.setTictotalnumber(new Integer(250));
//		dao.update(seatingareaVO2);
		
		
		
		// 刪除
//		dao.delete("ES00000010"); 
//		System.out.println("------------------------------");

		
		
		// 查詢一個
//		SeatingAreaVO seatingareaVO3 = dao.findByPrimaryKey("ES00000001");
//		System.out.println(seatingareaVO3.getTicarea_no());
//		System.out.println(seatingareaVO3.getEve_no());
//		System.out.println(seatingareaVO3.getTictype_no());
//		System.out.println(seatingareaVO3.getTicarea_color());
//		System.out.println(seatingareaVO3.getTicarea_name());
//		System.out.println(seatingareaVO3.getTictotalnumber());
//		System.out.println(seatingareaVO3.getTicbookednumber());
//		System.out.println("------------------------------");
		
		
		
		// 查詢全部
//		List<SeatingAreaVO> list = dao.getAll();
//		for(SeatingAreaVO aSeatingareaVO :list) {
//			System.out.println(aSeatingareaVO.getTicarea_no());
//			System.out.println(aSeatingareaVO.getEve_no());
//			System.out.println(aSeatingareaVO.getTictype_no());
//			System.out.println(aSeatingareaVO.getTicarea_color());
//			System.out.println(aSeatingareaVO.getTicarea_name());
//			System.out.println(aSeatingareaVO.getTictotalnumber());
//			System.out.println(aSeatingareaVO.getTicbookednumber());
//			System.out.println("------------------------------");
//		}
		
		
		
		//用票區找訂票訂單
//		Set<TicketOrderVO> ticketOrderVolist = dao.getTicketOrders_BySeatingArea("ES00000001");
//		for(TicketOrderVO ticketOrderVO : ticketOrderVolist) {
//			System.out.print(ticketOrderVO.getTicket_order_no() + ",");
//			System.out.print(ticketOrderVO.getMember_no() + ",");
//			System.out.print(ticketOrderVO.getTicarea_no() + ",");
//			System.out.print(ticketOrderVO.getTotal_price() + ",");
//			System.out.print(ticketOrderVO.getTotal_amount() + ",");
//			System.out.print(ticketOrderVO.getTicket_order_time() + ",");
//			System.out.print(ticketOrderVO.getPayment_method() + ",");
//			System.out.print(ticketOrderVO.getTicket_order_status() + ",");
//			System.out.println();
//		}
//		System.out.println("---------------------");
		
		//用票區找票券
		Set<TicketVO> ticketVolist = dao.getTickets_BySeatingArea("ES00000001");
		for(TicketVO aTicketVO : ticketVolist) {
			System.out.print(aTicketVO.getTicarea_no() + ",");
			System.out.print(aTicketVO.getTicket_order_no() + ",");
			System.out.print(aTicketVO.getMember_no() + ",");
			System.out.print(aTicketVO.getTicket_status() + ",");
			System.out.print(aTicketVO.getTicket_create_time() + ",");
			System.out.print(aTicketVO.getTicket_resale_status() + ",");
			System.out.print(aTicketVO.getTicket_resale_price() + ",");
			System.out.print(aTicketVO.getIs_from_resale() + ",");
			System.out.println();
		}
		System.out.println("---------------------");
		
		
		
	}
	
	
	
}
