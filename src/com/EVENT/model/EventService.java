package com.EVENT.model;

import java.sql.Timestamp;
import java.util.List;

public class EventService {

	private EventDAO_interface eventDao;

	public EventService() {
		eventDao = new EventDAO();
	}

	public EventVO addEvent(String evetit_no, String venue_no, String eve_session, String eve_sessionname,
			byte[] eve_seatmap, Timestamp eve_startdate, Timestamp eve_enddate, Timestamp eve_onsaledate,
			Timestamp eve_offsaledate, Integer ticlimit, Timestamp fullrefundenddate, String eve_status) {

		EventVO eventVO = new EventVO();

		eventVO.setEvetit_no(evetit_no);
		eventVO.setVenue_no(venue_no);
		eventVO.setEve_session(eve_session);
		eventVO.setEve_no(eventVO.getEvetit_no() + eventVO.getEve_session());
		eventVO.setEve_sessionname(eve_sessionname);
		eventVO.setEve_seatmap(eve_seatmap);
		eventVO.setEve_startdate(eve_startdate);
		eventVO.setEve_enddate(eve_enddate);
		eventVO.setEve_onsaledate(eve_onsaledate);
		eventVO.setEve_offsaledate(eve_offsaledate);
		eventVO.setTiclimit(ticlimit);
		eventVO.setFullrefundenddate(fullrefundenddate);
		eventVO.setEve_status(eve_status);

		eventDao.insert(eventVO);

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

		eventDao.update(eventVO);

		return eventVO;
	}

	public void deleteEvent(String eve_no) {
		eventDao.delete(eve_no);
	}

	public EventVO getOneEventTitle(String eve_no) {
		return eventDao.findByPrimaryKey(eve_no);
	}

	public List<EventVO> getAll() {
		return eventDao.getAll();
	}
}
