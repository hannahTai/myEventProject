package com.seating_area.model;
import java.util.*;

import com.ticket.model.TicketVO;
import com.ticketorder.model.TicketOrderVO;

public interface SeatingAreaDAO_interface {
	
	public String insert(SeatingAreaVO seatingareaVO);
    public void update(SeatingAreaVO seatingareaVO);
    public void delete(String ticarea_no);
    public SeatingAreaVO findByPrimaryKey(String ticarea_no);
    public List<SeatingAreaVO> getAll();
    
    
    public void insertFromTicketType(SeatingAreaVO seatingareaVO , java.sql.Connection con);
    public Set<TicketOrderVO> getTicketOrders_BySeatingArea(String ticarea_no);
    public Set<TicketVO> getTickets_BySeatingArea(String ticarea_no);
    
}
