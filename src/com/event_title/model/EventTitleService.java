package com.event_title.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.advertisement.model.AdvertisementService;
import com.advertisement.model.AdvertisementVO;
import com.event.model.EventService;
import com.event.model.EventVO;
import com.seating_area.model.SeatingAreaService;
import com.seating_area.model.SeatingAreaVO;
import com.ticket.model.TicketVO;

public class EventTitleService {

	private EventTitleDAO_interface eventTitleDao;

	public EventTitleService() {
		eventTitleDao = new EventTitleDAO();
	}

	public EventTitleVO addEventTitle(String eveclass_no, String ticrefpolicy_no, String evetit_name,
			java.sql.Date evetit_startdate, java.sql.Date evetit_enddate, byte[] evetit_poster, String info,
			String notices, String eticpurchaserules, String eticrules, String refundrules, Integer evetit_sessions,
			String evetit_status, java.sql.Date launchdate, java.sql.Date offdate, Integer promotionranking) {

		EventTitleVO eventTitleVO = new EventTitleVO();

		eventTitleVO.setEveclass_no(eveclass_no);
		eventTitleVO.setTicrefpolicy_no(ticrefpolicy_no);
		eventTitleVO.setEvetit_name(evetit_name);
		eventTitleVO.setEvetit_startdate(evetit_startdate);
		eventTitleVO.setEvetit_enddate(evetit_enddate);
		eventTitleVO.setEvetit_poster(evetit_poster);
		eventTitleVO.setInfo(info);
		eventTitleVO.setNotices(notices);
		eventTitleVO.setEticpurchaserules(eticpurchaserules);
		eventTitleVO.setEticrules(eticrules);
		eventTitleVO.setRefundrules(refundrules);
		eventTitleVO.setEvetit_sessions(evetit_sessions);
		eventTitleVO.setEvetit_status(evetit_status);
		eventTitleVO.setLaunchdate(launchdate);
		eventTitleVO.setOffdate(offdate);
		eventTitleVO.setPromotionranking(promotionranking);

		String evetit_no = eventTitleDao.insert(eventTitleVO);
		eventTitleVO.setEvetit_no(evetit_no);

		return eventTitleVO;
	}
	


	public EventTitleVO updateEventTitle(String evetit_no, String eveclass_no, String ticrefpolicy_no,
			String evetit_name, java.sql.Date evetit_startdate, java.sql.Date evetit_enddate, byte[] evetit_poster,
			String info, String notices, String eticpurchaserules, String eticrules, String refundrules,
			Integer evetit_sessions, String evetit_status, java.sql.Date launchdate, java.sql.Date offdate,
			Integer promotionranking) {

		EventTitleVO eventTitleVO = new EventTitleVO();

		eventTitleVO.setEvetit_no(evetit_no);
		eventTitleVO.setEveclass_no(eveclass_no);
		eventTitleVO.setTicrefpolicy_no(ticrefpolicy_no);
		eventTitleVO.setEvetit_name(evetit_name);
		eventTitleVO.setEvetit_startdate(evetit_startdate);
		eventTitleVO.setEvetit_enddate(evetit_enddate);
		eventTitleVO.setEvetit_poster(evetit_poster);
		eventTitleVO.setInfo(info);
		eventTitleVO.setNotices(notices);
		eventTitleVO.setEticpurchaserules(eticpurchaserules);
		eventTitleVO.setEticrules(eticrules);
		eventTitleVO.setRefundrules(refundrules);
		eventTitleVO.setEvetit_sessions(evetit_sessions);
		eventTitleVO.setEvetit_status(evetit_status);
		eventTitleVO.setLaunchdate(launchdate);
		eventTitleVO.setOffdate(offdate);
		eventTitleVO.setPromotionranking(promotionranking);
		
		
		if(evetit_poster == null || evetit_poster.length == 0) {
			eventTitleDao.update_withoutPoster(eventTitleVO);
		} else {
			eventTitleDao.update(eventTitleVO);
		}

		return eventTitleVO;
	}

	public void deleteEventTitle(String evetit_no) {
		eventTitleDao.delete(evetit_no);
	}

	public EventTitleVO getOneEventTitle(String evetit_no) {
		return eventTitleDao.findByPrimaryKey(evetit_no);
	}

	public List<EventTitleVO> getAll() {
		return eventTitleDao.getAll();
	}
	
	public List<EventTitleVO> getAllLaunched() {
		return eventTitleDao.getAllLaunched();
	}
	
	public List<EventTitleVO> getAllLaunched(Map<String, String[]> map) {
		return eventTitleDao.getAllLaunched(map);
	}
	
	public Set<EventVO> getEventsByEventTitle(String evetit_no) {
		return eventTitleDao.getEventsByEventTitle(evetit_no);
	}
	
	
	public List<EventTitleVO> getNotInTheAdvertisement() {
		List<EventTitleVO> eventTitleList = eventTitleDao.getNotInTheAdvertisement();
		System.out.println(eventTitleList);
		return eventTitleList;
	}
	
	public Set<TicketVO> getTicketsByEventTitle(String evetit_no) {		
		
		Set<EventVO> eventVOset = getEventsByEventTitle(evetit_no);
		
		Set<TicketVO> ticketVO_ByEvent = new LinkedHashSet<>();
		
		EventService eventService = new EventService();
		SeatingAreaService seatingAreaService = new SeatingAreaService();
		
		for(EventVO eventVO : eventVOset) {
			Set<SeatingAreaVO> SeatingAreaVoSet = eventService.getSeatingAreasByEvent(eventVO.getEve_no());
			for(SeatingAreaVO aSeatingAreaVO : SeatingAreaVoSet) {
				Set<TicketVO> ticketVOset = seatingAreaService.getTickets_BySeatingArea(aSeatingAreaVO.getTicarea_no());
				for(TicketVO aTicketVO : ticketVOset) {
					ticketVO_ByEvent.add(aTicketVO);
				}
			}
		}
		return ticketVO_ByEvent;
	}
	

}
