package com.FAVORITE_EVENT.model;

import java.util.List;

public class FavoriteEventService {
	
	private FavoriteEventDAO_interface favoriteEventDAO;

	public FavoriteEventService() {
		favoriteEventDAO = new FavoriteEventDAO();
	}

	public FavoriteEventVO addFavoriteEvent(String member_no, String evetit_no) {
		FavoriteEventVO ticketRefundPolicyVO = new FavoriteEventVO();
		ticketRefundPolicyVO.setMember_no(member_no);
		ticketRefundPolicyVO.setEvetit_no(evetit_no);
		favoriteEventDAO.insert(ticketRefundPolicyVO);
		return ticketRefundPolicyVO;
	}

	public void deleteFavoriteEvent(String member_no, String evetit_no) {
		favoriteEventDAO.delete(member_no, evetit_no);
	}

	public List<FavoriteEventVO> findFavoriteEventByMember(String member_no) {
		return favoriteEventDAO.findByMember(member_no);
	}
	
	
}
