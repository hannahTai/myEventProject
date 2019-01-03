package com.event_title.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.event.model.EventVO;

public interface EventTitleDAO_interface {
	
	 public String insert(EventTitleVO evetitVO);
     public void update(EventTitleVO evetitVO);
     public void delete(String evetit_no);
     public EventTitleVO findByPrimaryKey(String evetit_no);
     public List<EventTitleVO> getAll();
     
     
     
     public List<EventTitleVO> getAllLaunched(Map<String, String[]> map);
     
     
     
     public List<EventTitleVO> getAllLaunched();    
     public void update_withoutPoster(EventTitleVO evetitVO);

     
     
     public Set<EventVO> getEventsByEventTitle(String evetit_no);
     
     
     public List<EventTitleVO> getNotInTheAdvertisement();
}
