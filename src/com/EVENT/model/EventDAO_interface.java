package com.EVENT.model;

import java.util.List;

public interface EventDAO_interface {
	
	public String insert(EventVO eventVO);
    public void update(EventVO eventVO);
    public void delete(String eve_no);
    public EventVO findByPrimaryKey(String eve_no);
    public List<EventVO> getAll();

}
