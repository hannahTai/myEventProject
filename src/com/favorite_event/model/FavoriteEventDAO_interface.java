package com.favorite_event.model;

import java.util.List;

public interface FavoriteEventDAO_interface {
	public void insert (FavoriteEventVO favoriteEventVO);
	public void delete (String member_no, String evetit_no);
	public List<FavoriteEventVO> findByMember (String member_no);
	public void insertbyNo (String member_no, String evetit_no);
	
	public boolean getOneFavoriteEvent(String member_no, String evetit_no);
}
