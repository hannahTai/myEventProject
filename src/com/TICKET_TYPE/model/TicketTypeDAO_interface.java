package com.TICKET_TYPE.model;

import java.util.List;

public interface TicketTypeDAO_interface {

	public String insert(TicketTypeVO ticketTypeVO);
    public void update(TicketTypeVO ticketTypeVO);
    public void delete(String ticketTypeVO);
    public TicketTypeVO findByPrimaryKey(String tictype_no);
    public List<TicketTypeVO> getAll();
    
}
