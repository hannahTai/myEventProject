package com.seating_area.model;

import java.util.List;
import java.util.Set;

import com.ticket.model.TicketVO;
import com.ticketorder.model.TicketOrderVO;

public class SeatingAreaService {

	private SeatingAreaDAO_interface seatingAreaDAO;

	public SeatingAreaService() {
		seatingAreaDAO = new SeatingAreaDAO();
	}

	public SeatingAreaVO addSeatingArea(String eve_no, String tictype_no,
			String ticarea_color, String ticarea_name, Integer tictotalnumber) {
		SeatingAreaVO seatingAreaVO = new SeatingAreaVO();		
		seatingAreaVO.setEve_no(eve_no);
		seatingAreaVO.setTictype_no(tictype_no);					
		seatingAreaVO.setTicarea_color(ticarea_color);
		seatingAreaVO.setTicarea_name(ticarea_name);		
		seatingAreaVO.setTictotalnumber(tictotalnumber);	
		seatingAreaVO.setTicbookednumber(new Integer(0));	
		String ticarea_no = seatingAreaDAO.insert(seatingAreaVO);
		seatingAreaVO.setTicarea_no(ticarea_no);	
		return seatingAreaVO;
	}
	
	public SeatingAreaVO addSeatingArea(String eve_no, String tictype_no) {
		SeatingAreaVO seatingAreaVO = new SeatingAreaVO();		
		seatingAreaVO.setEve_no(eve_no);
		seatingAreaVO.setTictype_no(tictype_no);					
		seatingAreaVO.setTicarea_color("#3399ff");
		seatingAreaVO.setTicarea_name("票區名");		
		seatingAreaVO.setTictotalnumber(new Integer(0));	
		seatingAreaVO.setTicbookednumber(new Integer(0));	
		String ticarea_no = seatingAreaDAO.insert(seatingAreaVO);
		seatingAreaVO.setTicarea_no(ticarea_no);	
		return seatingAreaVO;
	}
	
	public SeatingAreaVO updateSeatingArea(String ticarea_no,
			 String ticarea_color, String ticarea_name, Integer tictotalnumber) {
		SeatingAreaVO seatingAreaVO = new SeatingAreaVO();		
		seatingAreaVO.setTicarea_no(ticarea_no);				
		seatingAreaVO.setTicarea_color(ticarea_color);
		seatingAreaVO.setTicarea_name(ticarea_name);		
		seatingAreaVO.setTictotalnumber(tictotalnumber);	
		seatingAreaDAO.update(seatingAreaVO);
		return seatingAreaVO;
	}

	public void deleteSeatingArea(String ticarea_no) {
		seatingAreaDAO.delete(ticarea_no);
	}

	public SeatingAreaVO getOneSeatingArea(String ticarea_no) {
		return seatingAreaDAO.findByPrimaryKey(ticarea_no);
	}

	public List<SeatingAreaVO> getAll() {
		return seatingAreaDAO.getAll();
	}
	
	public Set<TicketOrderVO> getTicketOrders_BySeatingArea(String ticarea_no) {
		return seatingAreaDAO.getTicketOrders_BySeatingArea(ticarea_no);
	}
	
	public Set<TicketVO> getTickets_BySeatingArea(String ticarea_no) {
		return seatingAreaDAO.getTickets_BySeatingArea(ticarea_no);
	}
	
	
	//========== BEAR begin ======================================================================
	public List<SeatingAreaVO> getAllSeatingAreaByEveNo(String eve_no) {
		return seatingAreaDAO.getAllWithEve_No(eve_no);
	}
	//========== BEAR end ======================================================================

	
	
}
