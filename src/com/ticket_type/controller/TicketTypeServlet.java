package com.ticket_type.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.seating_area.model.SeatingAreaService;
import com.seating_area.model.SeatingAreaVO;
import com.ticket_type.model.TicketTypeService;
import com.ticket_type.model.TicketTypeVO;

@WebServlet("/ticket_type/TicketTypeServlet.do")
public class TicketTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TicketTypeServlet() {
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
		if ("updateTicketType".equals(action)) {
			
			String tictype_name = null;
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String tictype_no = request.getParameter("tictype_no");
				String tictype_color = request.getParameter("tictype_color");
				tictype_name = request.getParameter("tictype_name");
				Integer tictype_price = new Integer(request.getParameter("tictype_price"));
				 
				/****************************** 2.開始修改資料 **************************************************/
				TicketTypeService ticketTypeService = new TicketTypeService();
				ticketTypeService.updateTicketType(tictype_no, tictype_color, tictype_name, tictype_price);

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				out.println("  ### " + tictype_name + " 更新成功 !  ");

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ### " + tictype_name + " 更新失敗 : " +  e.getMessage());
			}
			
		}
	

		
		
		
		// 請求來源 : backend -> updateEvent.jsp
		if ("deleteTicketType".equals(action)) {
			
			String tictype_name = null;
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String tictype_no = request.getParameter("tictype_no");
				tictype_name = request.getParameter("tictype_name");
				 
				/****************************** 2.開始修改資料 **************************************************/
				TicketTypeService ticketTypeService = new TicketTypeService();
				ticketTypeService.deleteTicketType(tictype_no);

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				out.println("  ### " + tictype_name + " 刪除成功 !  ");

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ### " + tictype_name + " 刪除失敗 : " +  e.getMessage());
			}
			
		}
		
		
		
		
		
		// 請求來源 : backend -> updateEvent.jsp
		if ("addTicketType".equals(action)) {
			
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String eve_no = request.getParameter("eve_no");
				 
				/****************************** 2.開始修改資料 **************************************************/
				TicketTypeService ticketTypeService = new TicketTypeService();
				TicketTypeVO ticketTypeVO = ticketTypeService.addTicketType(eve_no);
				Gson gson = new Gson();				
				String ticketTypeVOjsonStr = gson.toJson(ticketTypeVO);
				
				/****************************** 3.修改完成,準備轉交 ***************************************************/
				out.println(ticketTypeVOjsonStr);
				
				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ###" + " 新增失敗 : " +  e.getMessage());
			}
			
		}
		
		
		
		
		
		// 請求來源 : backend -> updateEvent.jsp
		if ("copyTicketType".equals(action)) {
			
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/				
				String tictype_no_forCopy = request.getParameter("tictype_no_forCopy");
				 
				/****************************** 2.開始修改資料 **************************************************/
				TicketTypeService ticketTypeService = new TicketTypeService();
				String tictype_no = ticketTypeService.copyInsertWithSeatingArea(tictype_no_forCopy);
				List<Object> list = new ArrayList<>();
				TicketTypeVO ticketTypeVO = ticketTypeService.getOneTicketType(tictype_no);
				list.add(ticketTypeVO);
				Set<SeatingAreaVO> seatingAreaVoSet = ticketTypeService.getSeatingAreasByTicketType(tictype_no);
				for(SeatingAreaVO aSeatingAreaVO : seatingAreaVoSet) {
					list.add(aSeatingAreaVO);
				}
				
				Gson gson = new Gson();				
				String listStr = gson.toJson(list);
				
				/****************************** 3.修改完成,準備轉交 ***************************************************/
				out.println(listStr);
				
				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ###" + " 複製失敗 : " +  e.getMessage());
			}
			
		}
		
	}

}
