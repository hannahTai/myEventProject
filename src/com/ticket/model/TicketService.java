package com.ticket.model;

import java.sql.Timestamp;
import java.util.List;

public class TicketService {
	private TicketDAO_interface dao;
	public TicketService() {
//		dao = new TicketDAOJDBC();
		dao = new TicketDAO();
	}
	public TicketVO addTicket(String ticarea_no, String ticket_order_no, String member_no, 
			String ticket_status, Timestamp ticket_create_time, String ticket_resale_status, 
			Integer ticket_resale_price, String is_from_resale) {
		TicketVO tVO = new TicketVO();
		tVO.setTicarea_no(ticarea_no);
		tVO.setTicket_order_no(ticket_order_no);
		tVO.setMember_no(member_no);
		tVO.setTicket_status(ticket_status);
		tVO.setTicket_create_time(ticket_create_time);
		tVO.setTicket_resale_status(ticket_resale_status);
		tVO.setTicket_resale_price(ticket_resale_price);
		tVO.setIs_from_resale(is_from_resale);
		dao.insert(tVO);
		
		return tVO;
	}
	
	public TicketVO updateTicket(String ticket_no, String ticarea_no, String ticket_order_no, 
			String member_no, String ticket_status, Timestamp ticket_create_time, 
			String ticket_resale_status,Integer ticket_resale_price, String is_from_resale) {
		TicketVO tVO = new TicketVO();
		tVO.setTicket_no(ticket_no);
		tVO.setTicarea_no(ticarea_no);
		tVO.setTicket_order_no(ticket_order_no);
		tVO.setMember_no(member_no);
		tVO.setTicket_status(ticket_status);
		tVO.setTicket_create_time(ticket_create_time);
		tVO.setTicket_resale_status(ticket_resale_status);
		tVO.setTicket_resale_price(ticket_resale_price);
		tVO.setIs_from_resale(is_from_resale);
		dao.update(tVO);
		
		return tVO;
	}
	
	public void deleteTicket(String ticket_no) {
		dao.delete(ticket_no);
	}
	
	public TicketVO getOneTicket(String ticket_no) {
		return dao.findByPrimaryKey(ticket_no);
	}
	
	public List<TicketVO> getAll(){
		return dao.getAll();
	}
}
