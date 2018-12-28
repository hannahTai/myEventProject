package com.event.model;

import java.sql.Timestamp;

public class EventVO implements java.io.Serializable{
	private static final long serialVersionUID = -5468464951341681154L;
	
	private String eve_no;
	private String evetit_no;
	private String venue_no;
	private String eve_sessionname;
	private byte[] eve_seatmap;
	private Timestamp eve_startdate;
	private Timestamp eve_enddate;
	private Timestamp eve_onsaledate;
	private Timestamp eve_offsaledate;
	private Integer ticlimit;
	private Timestamp fullrefundenddate;
	private String eve_status;
	
	public EventVO() {
		super();
	}

	public EventVO(String eve_no, String evetit_no, String venue_no, String eve_sessionname, byte[] eve_seatmap,
			Timestamp eve_startdate, Timestamp eve_enddate, Timestamp eve_onsaledate, Timestamp eve_offsaledate,
			Integer ticlimit, Timestamp fullrefundenddate, String eve_status) {
		super();
		this.eve_no = eve_no;
		this.evetit_no = evetit_no;
		this.venue_no = venue_no;
		this.eve_sessionname = eve_sessionname;
		this.eve_seatmap = eve_seatmap;
		this.eve_startdate = eve_startdate;
		this.eve_enddate = eve_enddate;
		this.eve_onsaledate = eve_onsaledate;
		this.eve_offsaledate = eve_offsaledate;
		this.ticlimit = ticlimit;
		this.fullrefundenddate = fullrefundenddate;
		this.eve_status = eve_status;
	}

	public String getEve_no() {
		return eve_no;
	}

	public void setEve_no(String eve_no) {
		this.eve_no = eve_no;
	}

	public String getEvetit_no() {
		return evetit_no;
	}

	public void setEvetit_no(String evetit_no) {
		this.evetit_no = evetit_no;
	}

	public String getVenue_no() {
		return venue_no;
	}

	public void setVenue_no(String venue_no) {
		this.venue_no = venue_no;
	}

	public String getEve_sessionname() {
		return eve_sessionname;
	}

	public void setEve_sessionname(String eve_sessionname) {
		this.eve_sessionname = eve_sessionname;
	}

	public byte[] getEve_seatmap() {
		return eve_seatmap;
	}

	public void setEve_seatmap(byte[] eve_seatmap) {
		this.eve_seatmap = eve_seatmap;
	}

	public Timestamp getEve_startdate() {
		return eve_startdate;
	}

	public void setEve_startdate(Timestamp eve_startdate) {
		this.eve_startdate = eve_startdate;
	}

	public Timestamp getEve_enddate() {
		return eve_enddate;
	}

	public void setEve_enddate(Timestamp eve_enddate) {
		this.eve_enddate = eve_enddate;
	}

	public Timestamp getEve_onsaledate() {
		return eve_onsaledate;
	}

	public void setEve_onsaledate(Timestamp eve_onsaledate) {
		this.eve_onsaledate = eve_onsaledate;
	}

	public Timestamp getEve_offsaledate() {
		return eve_offsaledate;
	}

	public void setEve_offsaledate(Timestamp eve_offsaledate) {
		this.eve_offsaledate = eve_offsaledate;
	}

	public Integer getTiclimit() {
		return ticlimit;
	}

	public void setTiclimit(Integer ticlimit) {
		this.ticlimit = ticlimit;
	}

	public Timestamp getFullrefundenddate() {
		return fullrefundenddate;
	}

	public void setFullrefundenddate(Timestamp fullrefundenddate) {
		this.fullrefundenddate = fullrefundenddate;
	}

	public String getEve_status() {
		return eve_status;
	}

	public void setEve_status(String eve_status) {
		this.eve_status = eve_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
