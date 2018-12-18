package com.EVENT_CLASSIFICATION.model;

import java.util.List;

public class EventClassificationService {

	private EventClassificationDAO_interface eventClassificationDAO;
	
	
	public EventClassificationService() {
		eventClassificationDAO = new EventClassificationDAO();
	}
		
	public EventClassificationVO addEventClassification(String eveclass_no, String eveclass_name) {		
		EventClassificationVO eventClassificationVO = new EventClassificationVO();				
		eventClassificationVO.setEveclass_no(eveclass_no); 
		eventClassificationVO.setEveclass_name(eveclass_name);
		eventClassificationDAO.insert(eventClassificationVO);
		return eventClassificationVO;
	}
	
	public EventClassificationVO updateEventClassification(String eveclass_no, String eveclass_name) {		
		EventClassificationVO eventClassificationVO = new EventClassificationVO();				
		eventClassificationVO.setEveclass_no(eveclass_no); 
		eventClassificationVO.setEveclass_name(eveclass_name);
		eventClassificationDAO.update(eventClassificationVO);
		return eventClassificationVO;
	}
	
	public void deleteEventClassification(String eveclass_no) {			
		eventClassificationDAO.delete(eveclass_no);
	}	
	
	public EventClassificationVO getOneAdvertisement(String eveclass_no) {			
		return eventClassificationDAO.findByPrimaryKey(eveclass_no);
	}
	
	public List<EventClassificationVO> getAll() {
		return eventClassificationDAO.getAll();
	}
	
}
