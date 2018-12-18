package com.EVENT_CLASSIFICATION.model;

import java.util.List;


public interface EventClassificationDAO_interface {
	public void insert(EventClassificationVO eventClassificationVO);
    public void update(EventClassificationVO eventClassificationVO);
    public void delete(String eveclass_no);
    public EventClassificationVO findByPrimaryKey(String eveclass_no);
    public List<EventClassificationVO> getAll();
}
