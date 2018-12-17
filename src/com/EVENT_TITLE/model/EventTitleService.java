package com.EVENT_TITLE.model;

import java.util.List;

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

		eventTitleDao.insert(eventTitleVO);

		return eventTitleVO;
	}
	
	public EventTitleVO addEventTitle(String eveclass_no, String ticrefpolicy_no, String evetit_name,
			java.sql.Date evetit_startdate, java.sql.Date evetit_enddate, byte[] evetit_poster, String info,
			String notices, String eticpurchaserules, String eticrules, String refundrules) {

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

		String evetit_no = eventTitleDao.insert2_Basic(eventTitleVO);		
		eventTitleVO = getOneEventTitle(evetit_no);

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

		eventTitleDao.update(eventTitleVO);

		return eventTitleVO;
	}
	
	public EventTitleVO updateEventTitle(String evetit_no, String eveclass_no, String ticrefpolicy_no,
			String evetit_name, java.sql.Date evetit_startdate, java.sql.Date evetit_enddate, byte[] evetit_poster,
			String info, String notices, String eticpurchaserules, String eticrules, String refundrules) {

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
		
		eventTitleDao.update2_Basic(eventTitleVO);		
		eventTitleVO = getOneEventTitle(evetit_no);

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

}
