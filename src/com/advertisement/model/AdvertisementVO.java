package com.advertisement.model;

import java.sql.Date;

public class AdvertisementVO implements java.io.Serializable{
	private static final long serialVersionUID = -2836918843160702803L;
	
	private String ad_no;
	private String evetit_no;
	private Date ad_startdate;
	private Date ad_enddate;
	
	public AdvertisementVO() {
		super();
	}
	
	public AdvertisementVO(String ad_no, String evetit_no, Date ad_startdate, Date ad_enddate) {
		super();
		this.ad_no = ad_no;
		this.evetit_no = evetit_no;
		this.ad_startdate = ad_startdate;
		this.ad_enddate = ad_enddate;
	}
	public String getAd_no() {
		return ad_no;
	}
	public void setAd_no(String ad_no) {
		this.ad_no = ad_no;
	}
	public String getEvetit_no() {
		return evetit_no;
	}
	public void setEvetit_no(String evetit_no) {
		this.evetit_no = evetit_no;
	}
	public Date getAd_startdate() {
		return ad_startdate;
	}
	public void setAd_startdate(Date ad_startdate) {
		this.ad_startdate = ad_startdate;
	}
	public Date getAd_enddate() {
		return ad_enddate;
	}
	public void setAd_enddate(Date ad_enddate) {
		this.ad_enddate = ad_enddate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
