package com.ticket_refund_policy.model;

public class TicketRefundPolicyVO implements java.io.Serializable{
	

	private static final long serialVersionUID = 8395089140628808093L;
	private String ticRefPolicy_no;
	private String ticRefPolicy_name;
	private String ticRefPolicy_content;
		
	public TicketRefundPolicyVO() {
		super();
	}
	
	public TicketRefundPolicyVO(String ticRefPolicy_no, String ticRefPolicy_name, String ticRefPolicy_content) {
		super();
		this.ticRefPolicy_no = ticRefPolicy_no;
		this.ticRefPolicy_name = ticRefPolicy_name;
		this.ticRefPolicy_content = ticRefPolicy_content;
	}
	public String getTicRefPolicy_no() {
		return ticRefPolicy_no;
	}
	public void setTicRefPolicy_no(String ticRefPolicy_no) {
		this.ticRefPolicy_no = ticRefPolicy_no;
	}
	public String getTicRefPolicy_name() {
		return ticRefPolicy_name;
	}
	public void setTicRefPolicy_name(String ticRefPolicy_name) {
		this.ticRefPolicy_name = ticRefPolicy_name;
	}
	public String getTicRefPolicy_content() {
		return ticRefPolicy_content;
	}
	public void setTicRefPolicy_content(String ticRefPolicy_content) {
		this.ticRefPolicy_content = ticRefPolicy_content;
	}
}
