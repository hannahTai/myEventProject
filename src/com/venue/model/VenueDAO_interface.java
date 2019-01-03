package com.venue.model;

import java.util.List;

public interface VenueDAO_interface {
	public String insert (VenueVO venueVO);
	public void update (VenueVO venueVO);
	public void delete (String venue_no);
	public VenueVO findByPrimaryKey (String venue_no);
	public List<VenueVO> getAll();	
	
	public void update_withoutLocationPic(VenueVO venueVO);
}
