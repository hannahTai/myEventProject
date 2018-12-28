package com.favorite_event.model;

import java.io.Serializable;

public class FavoriteEventVO implements Serializable{
	private static final long serialVersionUID = -3422393396874605780L;
	
	private String member_no;
	private String evetit_no;
	
	public FavoriteEventVO() {
		super();
	}
	
	public FavoriteEventVO(String member_no, String evetit_no) {
		super();
		this.member_no = member_no;
		this.evetit_no = evetit_no;
	}

	public String getMember_no() {
		return member_no;
	}

	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}

	public String getEvetit_no() {
		return evetit_no;
	}

	public void setEvetit_no(String evetit_no) {
		this.evetit_no = evetit_no;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
