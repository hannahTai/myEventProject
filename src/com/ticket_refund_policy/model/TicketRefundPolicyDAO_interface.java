package com.ticket_refund_policy.model;

import java.util.List;


public interface TicketRefundPolicyDAO_interface {
	public void insert(TicketRefundPolicyVO ticketRefundPolicyVO);
    public void update(TicketRefundPolicyVO ticketRefundPolicyVO);
    public void delete(String ticRefPolicy_no);
    public TicketRefundPolicyVO findByPrimaryKey(String ticRefPolicy_no);
    public List<TicketRefundPolicyVO> getAll();
}
