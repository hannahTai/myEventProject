package com.VENUE.model;

import java.util.List;

public class VenueService {

	
	private VenueDAO_interface venueDAO;

	public VenueService() {
		venueDAO = new VenueDAO();
	}

	public VenueVO addVenue(String venue_name, String address,
			Double latitude, Double longitude, String venue_info, byte[] venue_locationPic) {
		VenueVO venueVO = new VenueVO();
		venueVO.setVenue_name(venue_name);
		venueVO.setAddress(address);
		venueVO.setLatitude(latitude);
		venueVO.setLongitude(longitude);
		venueVO.setVenue_info(venue_info);
		venueVO.setVenue_locationPic(venue_locationPic);		
		venueDAO.insert(venueVO);
		return venueVO;
	}
	
	public VenueVO updateVenue(String venue_no, String venue_name, String address,
			Double latitude, Double longitude, String venue_info, byte[] venue_locationPic) {
		VenueVO venueVO = new VenueVO();
		venueVO.setVenue_no(venue_no);
		venueVO.setVenue_name(venue_name);
		venueVO.setAddress(address);
		venueVO.setLatitude(latitude);
		venueVO.setLongitude(longitude);
		venueVO.setVenue_info(venue_info);
		venueVO.setVenue_locationPic(venue_locationPic);	
		venueDAO.update(venueVO);
		return venueVO;
	}

	public void deleteVenue(String venue_no) {
		venueDAO.delete(venue_no);
	}

	public VenueVO getOneEventTitle(String venue_no) {
		return venueDAO.findByPrimaryKey(venue_no);
	}

	public List<VenueVO> getAll() {
		return venueDAO.getAll();
	}
	
	
	
}
