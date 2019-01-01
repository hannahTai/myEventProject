package com.favorite_event.model;

import java.util.List;

public class FavoriteEventService {
	
	private FavoriteEventDAO_interface favoriteEventDAO;

	public FavoriteEventService() {
		favoriteEventDAO = new FavoriteEventDAO();
	}

	public FavoriteEventVO addFavoriteEvent(String member_no, String evetit_no) {
		FavoriteEventVO favoriteEventVO = new FavoriteEventVO();
		favoriteEventVO.setMember_no(member_no);
		favoriteEventVO.setEvetit_no(evetit_no);
		favoriteEventDAO.insert(favoriteEventVO);
		return favoriteEventVO;
	}

	public void deleteFavoriteEvent(String member_no, String evetit_no) {
		favoriteEventDAO.delete(member_no, evetit_no);
	}

	public List<FavoriteEventVO> findFavoriteEventByMember(String member_no) {
		return favoriteEventDAO.findByMember(member_no);
	}
	
	public boolean getOneFavoriteEvent(String member_no, String evetit_no) {
		return favoriteEventDAO.getOneFavoriteEvent(member_no, evetit_no);
	}
	
}
