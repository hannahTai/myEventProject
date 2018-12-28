package com.ticket_refund_policy.model;

import java.util.List;

public class TicketRefundPolicyService {
	
	private TicketRefundPolicyDAO_interface ticketRefundPolicyDAO;

	public TicketRefundPolicyService() {
		ticketRefundPolicyDAO = new TicketRefundPolicyDAO();
	}

	public TicketRefundPolicyVO addTicketRefundPolicy(String ticRefPolicy_no, String ticRefPolicy_name, String ticRefPolicy_content) {
		TicketRefundPolicyVO ticketRefundPolicyVO = new TicketRefundPolicyVO();
		ticketRefundPolicyVO.setTicRefPolicy_no(ticRefPolicy_no);
		ticketRefundPolicyVO.setTicRefPolicy_name(ticRefPolicy_name);
		ticketRefundPolicyVO.setTicRefPolicy_content(ticRefPolicy_content);
		ticketRefundPolicyDAO.insert(ticketRefundPolicyVO);
		return ticketRefundPolicyVO;
	}

	public TicketRefundPolicyVO updateTicketRefundPolicy(String ticRefPolicy_no, String ticRefPolicy_name, String ticRefPolicy_content) {
		TicketRefundPolicyVO ticketRefundPolicyVO = new TicketRefundPolicyVO();
		ticketRefundPolicyVO.setTicRefPolicy_no(ticRefPolicy_no);
		ticketRefundPolicyVO.setTicRefPolicy_name(ticRefPolicy_name);
		ticketRefundPolicyVO.setTicRefPolicy_content(ticRefPolicy_content);
		ticketRefundPolicyDAO.update(ticketRefundPolicyVO);
		return ticketRefundPolicyVO;
	}

	public void deleteTicketRefundPolicy(String ticRefPolicy_no) {
		ticketRefundPolicyDAO.delete(ticRefPolicy_no);
	}

	public TicketRefundPolicyVO getOneEventTitle(String ticRefPolicy_no) {
		return ticketRefundPolicyDAO.findByPrimaryKey(ticRefPolicy_no);
	}

	public List<TicketRefundPolicyVO> getAll() {
		return ticketRefundPolicyDAO.getAll();
	}
}
