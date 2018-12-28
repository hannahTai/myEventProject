package com.venue.model;

public class VenueVO implements java.io.Serializable{
	private static final long serialVersionUID = -818002754699471683L;
	
	private String venue_no;
	private String venue_name;
	private String address;
	private Double latitude;
	private Double longitude;
	private String venue_info;
	private byte[] venue_locationPic;
	
	public VenueVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VenueVO(String venue_no, String venue_name, String address, Double latitude, Double longitude,
			String venue_info, byte[] venue_locationPic) {
		super();
		this.venue_no = venue_no;
		this.venue_name = venue_name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.venue_info = venue_info;
		this.venue_locationPic = venue_locationPic;
	}

	public String getVenue_no() {
		return venue_no;
	}
	public void setVenue_no(String venue_no) {
		this.venue_no = venue_no;
	}
	public String getVenue_name() {
		return venue_name;
	}
	public void setVenue_name(String venue_name) {
		this.venue_name = venue_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getVenue_info() {
		return venue_info;
	}
	public void setVenue_info(String venue_info) {
		this.venue_info = venue_info;
	}
	public byte[] getVenue_locationPic() {
		return venue_locationPic;
	}
	public void setVenue_locationPic(byte[] venue_locationPic) {
		this.venue_locationPic = venue_locationPic;
	}
}
