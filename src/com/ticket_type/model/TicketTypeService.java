package com.ticket_type.model;

import java.util.List;
import java.util.Set;

import com.seating_area.model.SeatingAreaVO;

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
	
	public TicketTypeVO addTicketType(String eve_no) {
		TicketTypeVO ticketTypeVO = new TicketTypeVO();		
		ticketTypeVO.setEve_no(eve_no);	
		ticketTypeVO.setTictype_color("#3399ff");
		ticketTypeVO.setTictype_name("票種名");
		ticketTypeVO.setTictype_price(new Integer(0));		
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

	public Set<SeatingAreaVO> getSeatingAreasByTicketType(String tictype_no) {
		return ticketTypeDAO.getSeatingAreasByTicketType(tictype_no);
	}
	
	public String copyInsertWithSeatingArea(String tictype_no_forCopy) {
		return ticketTypeDAO.copyInsertWithSeatingArea(tictype_no_forCopy);
	}
	
}
