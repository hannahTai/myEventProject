package com.event.model;

import java.util.List;
import java.util.Set;

import com.event_title.model.EventTitleVO;
import com.seating_area.model.SeatingAreaVO;
import com.ticket_type.model.TicketTypeVO;

public interface EventDAO_interface {
	
	public String insert(EventVO eventVO);
    public void update(EventVO eventVO);
    public void delete(String eve_no, String evetit_no, Integer evetit_sessions);
    public EventVO findByPrimaryKey(String eve_no);
    public List<EventVO> getAll();
    
    public void update_withoutSeatmap(EventVO eventVO);
    
    public Set<TicketTypeVO> getTicketTypesByEvent(String eve_no);
	public String insert(String evetit_no, Integer evetit_sessions);
	
	public Set<SeatingAreaVO> getSeatingAreasByEvent(String eve_no);
	
	public EventVO copyEvent_withTicketTypeAndSeatingArea(String eve_no, String eve_no_forCopy);

}
