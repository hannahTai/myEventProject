package com.ADVERTISEMENT.model;

import java.sql.Date;
import java.util.List;

public class AdvertisementService {

	private AdvertisementDAO_interface advertisementDAO;

	public AdvertisementService() {
		advertisementDAO = new AdvertisementDAO();
	}
		
	public AdvertisementVO addAdvertisement(String evetit_no, Date ad_startdate, Date ad_enddate) {		
		AdvertisementVO advertisementVO = new AdvertisementVO();				
		advertisementVO.setEvetit_no(evetit_no); 
		advertisementVO.setAd_startdate(ad_startdate);
		advertisementVO.setAd_enddate(ad_enddate);		
		advertisementDAO.insert(advertisementVO);		
		return advertisementVO;
	}
	
	public AdvertisementVO updateAdvertisement(String ad_no, String evetit_no, Date ad_startdate, Date ad_enddate) {		
		AdvertisementVO advertisementVO = new AdvertisementVO();		
		advertisementVO.setAd_no(ad_no);
		advertisementVO.setEvetit_no(evetit_no); 
		advertisementVO.setAd_startdate(ad_startdate);
		advertisementVO.setAd_enddate(ad_enddate);		
		advertisementDAO.update(advertisementVO);
		return advertisementVO;
	}	
	
	public void deleteAdvertisement(String ad_no) {			
		advertisementDAO.delete(ad_no);
	}	
	
	public AdvertisementVO getOneAdvertisement(String ad_no) {			
		return advertisementDAO.findByPrimaryKey(ad_no);
	}
	
	public List<AdvertisementVO> getAll() {
		return advertisementDAO.getAll();
	}
	
}
