package com.event_classification.model;

import java.io.Serializable;

public class EventClassificationVO implements Serializable {
	
	private static final long serialVersionUID = -6837368763882702894L;
	private String eveclass_no;
	private String eveclass_name;
	
	public EventClassificationVO() {
		super();
	}
	
	public EventClassificationVO(String eveclass_no,String eveclass_name) {
		this.eveclass_no = eveclass_no;
		this.eveclass_name = eveclass_name;
	}
	
	public String getEveclass_no() {
		return eveclass_no;
	}
	public void setEveclass_no(String eveclass_no) {
		this.eveclass_no = eveclass_no;
	}
	public String getEveclass_name() {
		return eveclass_name;
	}
	public void setEveclass_name(String eveclass_name) {
		this.eveclass_name = eveclass_name;
	}
}
