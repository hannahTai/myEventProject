package com.TICKET_TYPE.model;

public class TicketTypeVO implements java.io.Serializable{
	private static final long serialVersionUID = -4198828877298907164L;
	
	private String tictype_no;
	private String eve_no;
	private String tictype_color;
	private String tictype_name;
	private Integer tictype_price;
	
	
	
	public TicketTypeVO() {
		super();
	}
	public TicketTypeVO(String tictype_no, String eve_no, String tictype_color,
			String tictype_name, Integer tictype_price) {
		super();
		this.tictype_no = tictype_no;
		this.eve_no = eve_no;
		this.tictype_color = tictype_color;
		this.tictype_name = tictype_name;
		this.tictype_price = tictype_price;
	}
	
	
	
	public String getTictype_no() {
		return tictype_no;
	}
	public void setTictype_no(String tictype_no) {
		this.tictype_no = tictype_no;
	}
	public String getEve_no() {
		return eve_no;
	}
	public void setEve_no(String eve_no) {
		this.eve_no = eve_no;
	}
	public String getTictype_color() {
		return tictype_color;
	}
	public void setTictype_color(String tictype_color) {
		this.tictype_color = tictype_color;
	}
	public String getTictype_name() {
		return tictype_name;
	}
	public void setTictype_name(String tictype_name) {
		this.tictype_name = tictype_name;
	}
	public Integer getTictype_price() {
		return tictype_price;
	}
	public void setTictype_price(Integer tictype_price) {
		this.tictype_price = tictype_price;
	}
	
	
	
}
