package com.favorite_event.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.favorite_event.model.FavoriteEventService;

@WebServlet("/favorite_event/FavoriteEventServlet.do")
public class FavoriteEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FavoriteEventServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		
		
		
		
		
		// 請求來源 : front-end -> listOneEventTitle.jsp
		if ("getOneFavoriteEvent_For_Display".equals(action)) {
			try {
				/****************************** 1.接收請求參數 **************************************************/
				String member_no = request.getParameter("member_no");
				String evetit_no = request.getParameter("evetit_no");

				/****************************** 2.開始查詢資料 **************************************************/
				FavoriteEventService favoriteEventService = new FavoriteEventService();
				boolean result = favoriteEventService.getOneFavoriteEvent(member_no, evetit_no);

				/****************************** 3.查詢完成,準備轉交 **************************************************/
				out.println(result);

				/****************************** 其他可能的錯誤處理 ******************************/
			} catch (Exception e) {
				out.println("  ###" + " 查詢失敗 : " +  e.getMessage());
			}
		}

		
		
		
		
		// 請求來源 : front-end -> listOneEventTitle.jsp
		if ("addFavoriteEvent".equals(action)) {
			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				String member_no = request.getParameter("member_no");
				String evetit_no = request.getParameter("evetit_no");

				/****************************** 2.開始新增資料 **************************************************/
				FavoriteEventService favoriteEventService = new FavoriteEventService();
				favoriteEventService.addFavoriteEvent(member_no, evetit_no);		
				
				/****************************** 3.修改完成,準備轉交 ***************************************************/
				out.println("  ###" + " 新增成功");
				
				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ###" + " 新增失敗 : " +  e.getMessage());
			} 
		}
		
		
		
		
		
		// 請求來源 : front-end -> listOneEventTitle.jsp
		else if ("deleteFavoriteEvent".equals(action)) {
			try {
				/****************************** 1.接收請求參數 **************************************************/
				String member_no = request.getParameter("member_no");
				String evetit_no = request.getParameter("evetit_no");

				/****************************** 2.開始刪除資料 **************************************************/
				FavoriteEventService favoriteEventService = new FavoriteEventService();
				favoriteEventService.deleteFavoriteEvent(member_no, evetit_no);		

				/****************************** 3.刪除完成,準備轉交 **************************************************/
				out.println("  ###" + " 刪除成功");
				
				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				out.println("  ###" + " 刪除失敗 : " +  e.getMessage());
			}
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
