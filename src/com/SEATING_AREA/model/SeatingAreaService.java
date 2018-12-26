package com.SEATING_AREA.model;

import java.util.List;

public class SeatingAreaService {

	private SeatingAreaDAO_interface seatingAreaDAO;

	public SeatingAreaService() {
		seatingAreaDAO = new SeatingAreaDAO();
	}

	public SeatingAreaVO addSeatingArea(String eve_no, String tictype_no,
			String ticarea_color, String ticarea_name, Integer tictotalnumber, Integer ticbookednumber) {
		SeatingAreaVO seatingAreaVO = new SeatingAreaVO();		
		seatingAreaVO.setEve_no(eve_no);
		seatingAreaVO.setTictype_no(tictype_no);					
		seatingAreaVO.setTicarea_color(ticarea_color);
		seatingAreaVO.setTicarea_name(ticarea_name);		
		seatingAreaVO.setTictotalnumber(tictotalnumber);	
		seatingAreaVO.setTicbookednumber(ticbookednumber);	
		String ticarea_no = seatingAreaDAO.insert(seatingAreaVO);
		seatingAreaVO.setTicarea_no(ticarea_no);	
		return seatingAreaVO;
	}
	
	public SeatingAreaVO updateSeatingArea(String ticarea_no,
			 String ticarea_color, String ticarea_name, Integer tictotalnumber, Integer ticbookednumber) {
		SeatingAreaVO seatingAreaVO = new SeatingAreaVO();		
		seatingAreaVO.setTicarea_no(ticarea_no);				
		seatingAreaVO.setTicarea_color(ticarea_color);
		seatingAreaVO.setTicarea_name(ticarea_name);		
		seatingAreaVO.setTictotalnumber(tictotalnumber);	
		seatingAreaVO.setTicbookednumber(ticbookednumber);	
		seatingAreaDAO.update(seatingAreaVO);
		return seatingAreaVO;
	}

	public void deleteSeatingArea(String ticarea_no) {
		seatingAreaDAO.delete(ticarea_no);
	}

	public SeatingAreaVO getOneSeatingArea(String ticarea_no) {
		return seatingAreaDAO.findByPrimaryKey(ticarea_no);
	}

	public List<SeatingAreaVO> getAll() {
		return seatingAreaDAO.getAll();
	}

	
	
}
