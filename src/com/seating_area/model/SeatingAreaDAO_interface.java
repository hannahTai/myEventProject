package com.seating_area.model;
import java.util.*;

public interface SeatingAreaDAO_interface {
	
	public String insert(SeatingAreaVO seatingareaVO);
    public void update(SeatingAreaVO seatingareaVO);
    public void delete(String ticarea_no);
    public SeatingAreaVO findByPrimaryKey(String ticarea_no);
    public List<SeatingAreaVO> getAll();
    
}
