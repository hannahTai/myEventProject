package com.SEATING_AREA.model;

public class SeatingAreaVO implements java.io.Serializable{
	private static final long serialVersionUID = 2720753249365728108L;
	
	private String ticarea_no;
	private String eve_no;
	private String tictype_no;
	private String ticarea_ename;
	private String ticarea_color;
	private String ticarea_name;
	private Integer tictotalnumber;
	private Integer ticbookednumber;
	
	
	
	public SeatingAreaVO() {
		super();
	}
	
	public SeatingAreaVO(String ticarea_no, String eve_no, String tictype_no, String ticarea_ename,
			String ticarea_color, String ticarea_name, Integer tictotalnumber, Integer ticbookednumber) {
		super();
		this.ticarea_no = ticarea_no;
		this.eve_no = eve_no;
		this.tictype_no = tictype_no;
		this.ticarea_ename = ticarea_ename;
		this.ticarea_color = ticarea_color;
		this.ticarea_name = ticarea_name;
		this.tictotalnumber = tictotalnumber;
		this.ticbookednumber = ticbookednumber;
	}
	
	
	
	public String getTicarea_no() {
		return ticarea_no;
	}
	public void setTicarea_no(String ticarea_no) {
		this.ticarea_no = ticarea_no;
	}
	public String getEve_no() {
		return eve_no;
	}
	public void setEve_no(String eve_no) {
		this.eve_no = eve_no;
	}
	public String getTictype_no() {
		return tictype_no;
	}
	public void setTictype_no(String tictype_no) {
		this.tictype_no = tictype_no;
	}
	public String getTicarea_ename() {
		return ticarea_ename;
	}
	public void setTicarea_ename(String ticarea_ename) {
		this.ticarea_ename = ticarea_ename;
	}
	public String getTicarea_color() {
		return ticarea_color;
	}
	public void setTicarea_color(String ticarea_color) {
		this.ticarea_color = ticarea_color;
	}
	public String getTicarea_name() {
		return ticarea_name;
	}
	public void setTicarea_name(String ticarea_name) {
		this.ticarea_name = ticarea_name;
	}
	public Integer getTictotalnumber() {
		return tictotalnumber;
	}
	public void setTictotalnumber(Integer tictotalnumber) {
		this.tictotalnumber = tictotalnumber;
	}
	public Integer getTicbookednumber() {
		return ticbookednumber;
	}
	public void setTicbookednumber(Integer ticbookednumber) {
		this.ticbookednumber = ticbookednumber;
	}
	
	
	
}
