package com.EVENT_TITLE.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.EVENT_TITLE.model.EventTitleService;
import com.EVENT_TITLE.model.EventTitleVO;


@WebServlet("/EVENT_TITLE/EventTitleServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class EventTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventTitleServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		
		String action = request.getParameter("action");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if ("insertEventTitle".equals(action)) {
			
			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);
			
			ByteArrayOutputStream baos = null;	
//			try {
			
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String eveclass_no = request.getParameter("eveclass_no");				
				String ticrefpolicy_no = request.getParameter("ticrefpolicy_no");
				
				
				String evetit_name = request.getParameter("evetit_name");				
				if (evetit_name == null || evetit_name.trim().length() == 0) {
					eventTitleErrorMsgs.add("活動主題名稱請勿空白");
				}
				
				java.sql.Date evetit_startdate = null;
				try {
					evetit_startdate = java.sql.Date.valueOf(request.getParameter("evetit_startdate").trim());
				} catch (IllegalArgumentException e) {
					
				}
				java.sql.Date evetit_enddate = null;
				try {
					evetit_enddate = java.sql.Date.valueOf(request.getParameter("evetit_enddate").trim());
				} catch (IllegalArgumentException e) {
					
				}
				
				Part part = request.getPart("evetit_poster");
				InputStream in = part.getInputStream();
				baos = new ByteArrayOutputStream();
				int i;
				while ((i = in.read()) != -1)
					baos.write(i);			
				byte[] evetit_poster = baos.toByteArray();
				
				String info = request.getParameter("info");			
				String notices = request.getParameter("notices");				
				String eticpurchaserules = request.getParameter("eticpurchaserules");				
				String eticrules = request.getParameter("eticrules");				
				String refundrules = request.getParameter("refundrules");
	
				EventTitleVO eventTitleVO = new EventTitleVO();				
				eventTitleVO.setEveclass_no(eveclass_no); 
				eventTitleVO.setTicrefpolicy_no(ticrefpolicy_no);
				eventTitleVO.setEvetit_name(evetit_name);
				eventTitleVO.setEvetit_startdate(evetit_startdate);
				eventTitleVO.setEvetit_enddate(evetit_enddate);
				eventTitleVO.setEvetit_poster(evetit_poster);							
				eventTitleVO.setInfo(info);		
				eventTitleVO.setNotices(notices);
				eventTitleVO.setEticpurchaserules(eticpurchaserules);
				eventTitleVO.setEticrules(eticrules);
				eventTitleVO.setRefundrules(refundrules);
				
				
				
				
				if (!eventTitleErrorMsgs.isEmpty()) {
					request.setAttribute("eventTitleVO", eventTitleVO);
					RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}
				


				/*************************** 2.開始新增資料 ***************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleVO = eventTitleService.addEventTitle(eveclass_no, ticrefpolicy_no, evetit_name,
						evetit_startdate, evetit_enddate, evetit_poster, info,
						notices, eticpurchaserules, eticrules, refundrules);
				
				request.setAttribute("eventTitleVO", eventTitleVO);
				

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
				successView.forward(request, response);


				
				
				
				
				//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/emp/addEmp.jsp");
//				failureView.forward(req, res);


//			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		else if ("updateEventTitle".equals(action)) {
			
			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);
			
			ByteArrayOutputStream baos = null;	
//			try {
			
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				String evetit_no = null;
				if((EventTitleVO) request.getAttribute("eventTitleVO") == null) {
					
				} else {
					EventTitleVO eventTitleVOfromClient = (EventTitleVO) request.getAttribute("eventTitleVO");
					evetit_no = eventTitleVOfromClient.getEvetit_no();
				}
			
				
				
				String eveclass_no = request.getParameter("eveclass_no");				
				String ticrefpolicy_no = request.getParameter("ticrefpolicy_no");
				
				
				String evetit_name = request.getParameter("evetit_name");				
				if (evetit_name == null || evetit_name.trim().length() == 0) {
					eventTitleErrorMsgs.add("活動主題名稱請勿空白");
				}
				
				java.sql.Date evetit_startdate = null;
				try {
					evetit_startdate = java.sql.Date.valueOf(request.getParameter("evetit_startdate").trim());
				} catch (IllegalArgumentException e) {
					
				}
				java.sql.Date evetit_enddate = null;
				try {
					evetit_enddate = java.sql.Date.valueOf(request.getParameter("evetit_enddate").trim());
				} catch (IllegalArgumentException e) {
					
				}
				
				Part part = request.getPart("evetit_poster");
				InputStream in = part.getInputStream();
				baos = new ByteArrayOutputStream();
				int i;
				while ((i = in.read()) != -1)
					baos.write(i);			
				byte[] evetit_poster = baos.toByteArray();
				
				String info = request.getParameter("info");			
				String notices = request.getParameter("notices");				
				String eticpurchaserules = request.getParameter("eticpurchaserules");				
				String eticrules = request.getParameter("eticrules");				
				String refundrules = request.getParameter("refundrules");
	
				EventTitleVO eventTitleVO = new EventTitleVO();				
				eventTitleVO.setEveclass_no(eveclass_no); 
				eventTitleVO.setTicrefpolicy_no(ticrefpolicy_no);
				eventTitleVO.setEvetit_name(evetit_name);
				eventTitleVO.setEvetit_startdate(evetit_startdate);
				eventTitleVO.setEvetit_enddate(evetit_enddate);
				eventTitleVO.setEvetit_poster(evetit_poster);							
				eventTitleVO.setInfo(info);		
				eventTitleVO.setNotices(notices);
				eventTitleVO.setEticpurchaserules(eticpurchaserules);
				eventTitleVO.setEticrules(eticrules);
				eventTitleVO.setRefundrules(refundrules);
				
				
				
				
				if (!eventTitleErrorMsgs.isEmpty()) {
					request.setAttribute("eventTitleVO", eventTitleVO);
					RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}
				


				/*************************** 2.開始新增資料 ***************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleVO = eventTitleService.updateEventTitle(evetit_no, eveclass_no, ticrefpolicy_no, evetit_name,
						evetit_startdate, evetit_enddate, evetit_poster, info,
						notices, eticpurchaserules, eticrules, refundrules);
				
				request.setAttribute("eventTitleVO", eventTitleVO);
				

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
				successView.forward(request, response);


				
				
				
				
				//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/emp/addEmp.jsp");
//				failureView.forward(req, res);


//			}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
		}
	}
		

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
