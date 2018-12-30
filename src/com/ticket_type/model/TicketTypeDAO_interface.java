package com.ticket_type.model;

import java.util.List;
import java.util.Set;

import com.seating_area.model.SeatingAreaVO;

public interface TicketTypeDAO_interface {

	public String insert(TicketTypeVO ticketTypeVO);
    public void update(TicketTypeVO ticketTypeVO);
    public void delete(String ticketTypeVO);
    public TicketTypeVO findByPrimaryKey(String tictype_no);
    public List<TicketTypeVO> getAll();
    
    public Set<SeatingAreaVO> getSeatingAreasByTicketType(String tictype_no);
    
}
