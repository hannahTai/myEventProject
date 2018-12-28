package com.FAVORITE_EVENT.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.EVENT_TITLE.model.EventTitleService;
import com.EVENT_TITLE.model.EventTitleVO;
import com.FAVORITE_EVENT.model.*;

@WebServlet("/FAVORITE_EVENT/FavoriteEventServlet.do")
public class FavoriteEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FavoriteEventServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String action = request.getParameter("action");
		
		
		
		
		
		
		
		
		
		
		// 請求來源 : front-end -> listAllEventTitle.jsp
		if ("getOneFavoriteEvent_For_Display".equals(action)) {

			Map<String, String> favoriteEventErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("FavoriteEventErrorMsgs", favoriteEventErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String member_no = request.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					favoriteEventErrorMsgs.put("member_no", "無法取得會員資料");
				}
				
				String evetit_no = request.getParameter("evetit_no");
				if (evetit_no == null || evetit_no.trim().length() == 0) {
					favoriteEventErrorMsgs.put("evetit_no", "無法取得活動主題資料");
				}
				
				if (!favoriteEventErrorMsgs.isEmpty()) {
//					RequestDispatcher failureView = request.getRequestDispatcher("/front-end/EVENT_TITLE/listOneEventTitle.jsp");
//					failureView.forward(request, response);
					return;
				}

				/****************************** 2.開始查詢資料 **************************************************/
				FavoriteEventService favoriteEventService = new FavoriteEventService();
//				FavoriteEventVO favoriteEventVO = new FavoriteEventVO();
//				favoriteEventVO = 
				favoriteEventService.addFavoriteEvent(member_no, evetit_no);

				/****************************** 3.查詢完成,準備轉交 **************************************************/
//				FavoriteEventService favoriteEventService = new FavoriteEventService();
////				FavoriteEventVO favoriteEventVO = new FavoriteEventVO();
////				favoriteEventVO = 
//				favoriteEventService.addFavoriteEvent(member_no, evetit_no);

				/****************************** 其他可能的錯誤處理 ******************************/
			} catch (Exception e) {
				favoriteEventErrorMsgs.put("Exception", "查詢資料失敗 : " + e.getMessage());
//				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
//				failureView.forward(request, response);
			}
			return;
		}
		
		
		
		
		
		
		
		
		
		
		// 請求來源 : front-end -> listOneEventTitle.jsp
		if ("addFavoriteEvent".equals(action)) {

			Map<String, String> favoriteEventErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("FavoriteEventErrorMsgs", favoriteEventErrorMsgs);


			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				
				String member_no = request.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					favoriteEventErrorMsgs.put("member_no", "無法取得會員資料");
				}
				
				String evetit_no = request.getParameter("evetit_no");
				if (evetit_no == null || evetit_no.trim().length() == 0) {
					favoriteEventErrorMsgs.put("evetit_no", "無法取得活動主題資料");
				}
				
				if (!favoriteEventErrorMsgs.isEmpty()) {
//					RequestDispatcher failureView = request.getRequestDispatcher("/front-end/EVENT_TITLE/listOneEventTitle.jsp");
//					failureView.forward(request, response);
					return;
				}
	
				/****************************** 2.開始新增資料 **************************************************/
				FavoriteEventService favoriteEventService = new FavoriteEventService();
//				FavoriteEventVO favoriteEventVO = new FavoriteEventVO();
//				favoriteEventVO = 
				favoriteEventService.addFavoriteEvent(member_no, evetit_no);		
				
				/****************************** 3.修改完成,準備轉交 ***************************************************/
//				request.setAttribute("eventTitleVO", eventTitleVO);
//				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/listOneEventTitle.jsp");
//				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				favoriteEventErrorMsgs.put("Exception", "新增資料失敗 : " + e.getMessage());
//				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
//				failureView.forward(request, response);
			} 
				
		}
		
		
		
		
		
		
		
		
		
		
		// 請求來源 : front-end -> listAllEventTitle.jsp
		else if ("deleteFavoriteEvent".equals(action)) {

			Map<String, String> favoriteEventErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("FavoriteEventErrorMsgs", favoriteEventErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String member_no = request.getParameter("member_no");
				if (member_no == null || member_no.trim().length() == 0) {
					favoriteEventErrorMsgs.put("member_no", "無法取得會員資料");
				}
				
				String evetit_no = request.getParameter("evetit_no");
				if (evetit_no == null || evetit_no.trim().length() == 0) {
					favoriteEventErrorMsgs.put("evetit_no", "無法取得活動主題資料");
				}
				
				if (!favoriteEventErrorMsgs.isEmpty()) {
//					RequestDispatcher failureView = request.getRequestDispatcher("/front-end/EVENT_TITLE/listOneEventTitle.jsp");
//					failureView.forward(request, response);
					return;
				}

				/****************************** 2.開始刪除資料 **************************************************/
				FavoriteEventService favoriteEventService = new FavoriteEventService();
//				FavoriteEventVO favoriteEventVO = new FavoriteEventVO();
//				favoriteEventVO = 
				favoriteEventService.deleteFavoriteEvent(member_no, evetit_no);		

				/****************************** 3.刪除完成,準備轉交 **************************************************/
//				request.setAttribute("eventTitleVO", eventTitleVO);
//				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/listOneEventTitle.jsp");
//				successView.forward(request, response);
				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				favoriteEventErrorMsgs.put("Exception", "新增資料失敗 : " + e.getMessage());
//				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
//				failureView.forward(request, response);
			}
			return;
		}
		
	}
	
	
	
	
	
	
	
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
