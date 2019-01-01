package com.seating_area.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.seating_area.model.SeatingAreaService;
import com.seating_area.model.SeatingAreaVO;

@WebServlet("/seating_area/SeatingAreaServlet.do")
public class SeatingAreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SeatingAreaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");
		
		
		
		
		
		// 請求來源 : backend -> updateEvent.jsp
		if ("updateSeatingArea".equals(action)) {
			
			String ticarea_name = null;
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String ticarea_no = request.getParameter("ticarea_no");
				String ticarea_color = request.getParameter("ticarea_color");
				ticarea_name = request.getParameter("ticarea_name");
				Integer tictotalnumber = new Integer(request.getParameter("tictotalnumber"));
				 
				/****************************** 2.開始修改資料 **************************************************/
				SeatingAreaService ticketAreaService = new SeatingAreaService();
				ticketAreaService.updateSeatingArea(ticarea_no, ticarea_color, ticarea_name, tictotalnumber);

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				out.println("  ### " + ticarea_name + " 更新成功 !  ");

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ### " + ticarea_name + " 更新失敗 : " +  e.getMessage());
			}
			
		}
		
		
		
		
		
		// 請求來源 : backend -> updateEvent.jsp
		if ("deleteSeatingArea".equals(action)) {
			
			String ticarea_name = null;
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String ticarea_no = request.getParameter("ticarea_no");
				ticarea_name = request.getParameter("ticarea_name");
				
				/****************************** 2.開始刪除資料 **************************************************/
				SeatingAreaService ticketAreaService = new SeatingAreaService();
				ticketAreaService.deleteSeatingArea(ticarea_no);

				/****************************** 3.刪除完成,準備轉交 ***************************************************/
				out.println("  ### " + ticarea_name + " 刪除成功 !  ");

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ### " + ticarea_name + " 刪除失敗 : " +  e.getMessage());
			}
			
		}
		
		
		
		
		
		// 請求來源 : backend -> updateEvent.jsp
		if ("addSeatingArea".equals(action)) {
			
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String eve_no = request.getParameter("eve_no");
				String tictype_no = request.getParameter("tictype_no");
				
				/****************************** 2.開始刪除資料 **************************************************/
				SeatingAreaService ticketAreaService = new SeatingAreaService();
				SeatingAreaVO seatingAreaVO = ticketAreaService.addSeatingArea(eve_no, tictype_no);
				
				Gson gson = new Gson();				
				String seatingAreaVOjsonStr = gson.toJson(seatingAreaVO);
				
				/****************************** 3.刪除完成,準備轉交 ***************************************************/
				out.println(seatingAreaVOjsonStr);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ###" + " 新增失敗 : " +  e.getMessage());
			}
			
		}
		
	}

}
