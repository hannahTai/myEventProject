package com.advertisement.model;

import java.util.List;

public interface AdvertisementDAO_interface {
	public String insert (AdvertisementVO advertisementVO);
	public void update (AdvertisementVO advertisementVO);
	public void delete (String ad_no);
	public AdvertisementVO findByPrimaryKey (String ad_no);
	public List<AdvertisementVO> getAll();	
	public List<AdvertisementVO> getLaunched();
}
