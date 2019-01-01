package com.event.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.event_title.model.EventTitleService;
import com.event_title.model.EventTitleVO;
import com.ticket_type.model.TicketTypeVO;

public class EventService {

	private EventDAO_interface eventDao;

	public EventService() {
		eventDao = new EventDAO();
	}

	public EventVO addEvent(String evetit_no, String venue_no, String eve_sessionname,
			byte[] eve_seatmap, Timestamp eve_startdate, Timestamp eve_enddate, Timestamp eve_onsaledate,
			Timestamp eve_offsaledate, Integer ticlimit, Timestamp fullrefundenddate, String eve_status) {

		EventVO eventVO = new EventVO();

		eventVO.setEvetit_no(evetit_no);
		eventVO.setVenue_no(venue_no);
		eventVO.setEve_sessionname(eve_sessionname);
		eventVO.setEve_seatmap(eve_seatmap);
		eventVO.setEve_startdate(eve_startdate);
		eventVO.setEve_enddate(eve_enddate);
		eventVO.setEve_onsaledate(eve_onsaledate);
		eventVO.setEve_offsaledate(eve_offsaledate);
		eventVO.setTiclimit(ticlimit);
		eventVO.setFullrefundenddate(fullrefundenddate);
		eventVO.setEve_status(eve_status);

		String eve_no = eventDao.insert(eventVO);
		eventVO.setEve_no(eve_no);

		return eventVO;
	}
	
	public EventVO addEvent(String evetit_no) {
		EventTitleService eventTitleService = new EventTitleService();
		EventTitleVO EventTitleVO = eventTitleService.getOneEventTitle(evetit_no);
		Integer evetit_sessions = EventTitleVO.getEvetit_sessions();
		String eve_no = eventDao.insert(evetit_no, evetit_sessions);
		EventVO eventVO = getOneEvent(eve_no);
		return eventVO;
	}

	public EventVO updateEvent(String eve_no, String venue_no, String eve_sessionname, byte[] eve_seatmap,
			Timestamp eve_startdate, Timestamp eve_enddate, Timestamp eve_onsaledate, Timestamp eve_offsaledate,
			Integer ticlimit, Timestamp fullrefundenddate, String eve_status) {

		EventVO eventVO = new EventVO();

		eventVO.setEve_no(eve_no);
		eventVO.setVenue_no(venue_no);
		eventVO.setEve_sessionname(eve_sessionname);
		eventVO.setEve_seatmap(eve_seatmap);
		eventVO.setEve_startdate(eve_startdate);
		eventVO.setEve_enddate(eve_enddate);
		eventVO.setEve_onsaledate(eve_onsaledate);
		eventVO.setEve_offsaledate(eve_offsaledate);
		eventVO.setTiclimit(ticlimit);
		eventVO.setFullrefundenddate(fullrefundenddate);
		eventVO.setEve_status(eve_status);

		if(eve_seatmap == null || eve_seatmap.length == 0) {
			eventDao.update_withoutSeatmap(eventVO);
		} else {
			eventDao.update(eventVO);
		}

		return eventVO;
	}

	public void deleteEvent(String eve_no, String evetit_no) {
		EventTitleService eventTitleService = new EventTitleService();
		EventTitleVO EventTitleVO = eventTitleService.getOneEventTitle(evetit_no);
		Integer evetit_sessions = EventTitleVO.getEvetit_sessions();
		eventDao.delete(eve_no, evetit_no, evetit_sessions);
	}

	public EventVO getOneEvent(String eve_no) {
		return eventDao.findByPrimaryKey(eve_no);
	}

	public List<EventVO> getAll() {
		return eventDao.getAll();
	}
	
	public Set<TicketTypeVO> getTicketTypesByEvent(String eve_no) {
		return eventDao.getTicketTypesByEvent(eve_no);
	}
}
