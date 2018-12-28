package com.ticket_type.model;

import java.util.List;

public class TicketTypeService {

	private TicketTypeDAO_interface ticketTypeDAO;

	public TicketTypeService() {
		ticketTypeDAO = new TicketTypeDAO();
	}

	public TicketTypeVO addTicketType(String eve_no, String tictype_color, String tictype_name, Integer tictype_price) {
		TicketTypeVO ticketTypeVO = new TicketTypeVO();		
		ticketTypeVO.setEve_no(eve_no);	
		ticketTypeVO.setTictype_color(tictype_color);
		ticketTypeVO.setTictype_name(tictype_name);
		ticketTypeVO.setTictype_price(tictype_price);		
		String tictype_no = ticketTypeDAO.insert(ticketTypeVO);
		ticketTypeVO.setTictype_no(tictype_no);	
		return ticketTypeVO;
	}
	
	public TicketTypeVO updateTicketType(String tictype_no, String tictype_color, String tictype_name, Integer tictype_price) {
		TicketTypeVO ticketTypeVO = new TicketTypeVO();		
		ticketTypeVO.setTictype_no(tictype_no);		
		ticketTypeVO.setTictype_color(tictype_color);
		ticketTypeVO.setTictype_name(tictype_name);
		ticketTypeVO.setTictype_price(tictype_price);	
		ticketTypeDAO.update(ticketTypeVO);
		return ticketTypeVO;
	}

	public void deleteTicketType(String tictype_no) {
		ticketTypeDAO.delete(tictype_no);
	}

	public TicketTypeVO getOneTicketType(String tictype_no) {
		return ticketTypeDAO.findByPrimaryKey(tictype_no);
	}

	public List<TicketTypeVO> getAll() {
		return ticketTypeDAO.getAll();
	}

	
	
}
