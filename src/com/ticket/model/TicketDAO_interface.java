package com.ticket.model;
import java.util.*;

public interface TicketDAO_interface {
	public void insert(TicketVO ticketVO);
    public void update(TicketVO ticketVO);
    public void delete(String ticket_no);
    public TicketVO findByPrimaryKey(String ticket_no);
    
//    public List<TicketVO> findByTicket_order_status(String ticket_order_status);
    
    public List<TicketVO> getAll();	
    public void insertTickets (TicketVO ticketVO , java.sql.Connection con);
}
