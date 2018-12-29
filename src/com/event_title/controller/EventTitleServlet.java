package com.event_title.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.event.model.EventVO;
import com.event_title.model.EventTitleService;
import com.event_title.model.EventTitleVO;



@WebServlet("/event_title/EventTitleServlet.do")
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

		String action = request.getParameter("action");

		
		
		
		
		
		
		

		
		// 請求來源 : backend -> listAllEventTitleRelatives.jsp
		if ("getOneEventTitle_For_Display".equals(action)) {
			
			String requestURL = request.getParameter("requestURL");

			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = request.getParameter("evetit_no");

				/****************************** 2.開始查詢資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				EventTitleVO eventTitleVO = eventTitleService.getOneEventTitle(evetit_no);
				if (eventTitleVO == null) {
					eventTitleErrorMsgs.put("evetit_no", "查無資料");
				}
				if (!eventTitleErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
					failureView.forward(request, response);
					return;
				}

				/****************************** 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/event_title/listOneEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 ******************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
			return;
		}

		
		
		
		
		
		
		

		
		// 請求來源 : backend -> listAllEventTitleRelatives.jsp / listOneEventTitle.jsp
		else if ("getOneEventTitle_For_Update".equals(action)) {
			
			String requestURL = request.getParameter("requestURL");

			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = request.getParameter("evetit_no");

				/****************************** 2.開始查詢資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				EventTitleVO eventTitleVO = eventTitleService.getOneEventTitle(evetit_no);
				if (eventTitleVO == null) {
					eventTitleErrorMsgs.put("evetit_no", "查無資料");
				}
				
				if (!eventTitleErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
					failureView.forward(request, response);
					return;
				}

				/******************************* 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/event_title/updateEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
			return;
		}

		
		
		
		
		
		
		

		
		// 請求來源 : backend -> updateEventTitle.jsp
		else if ("updateEventTitle".equals(action)) {

			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				String evetit_no = request.getParameter("evetit_no");
				
				String evetit_name = request.getParameter("evetit_name");
				if (evetit_name == null || evetit_name.trim().length() == 0) {
					eventTitleErrorMsgs.put("evetit_name", "請輸入活動主題名稱");
				}
				
				String ticrefpolicy_no = request.getParameter("ticrefpolicy_no");
		
				java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
				java.sql.Date evetit_startdate = null;
				try {
					evetit_startdate = java.sql.Date.valueOf(request.getParameter("evetit_startdate").trim());					
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("evetit_startdate", "請輸入開始日期");
				}
				java.sql.Date evetit_enddate = null;
				try {
					evetit_enddate = java.sql.Date.valueOf(request.getParameter("evetit_enddate").trim());
					if (today.compareTo(evetit_enddate) > 0) {
						eventTitleErrorMsgs.put("evetit_enddate_BiggerThanToday", "結束日期不得早於今天");
					} 				
					if (evetit_startdate.compareTo(evetit_enddate) > 0) {
						eventTitleErrorMsgs.put("evetit_enddate_BiggerThanEvetitStartdate", "結束日期不得早於開始日期");
					} 
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("evetit_enddate", "請輸入結束日期");
				} catch (NullPointerException e){
					
				}
							
				java.sql.Date launchdate = null;
				try {
					launchdate = java.sql.Date.valueOf(request.getParameter("launchdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("launchdate", "請輸入上架日期");
				}
				java.sql.Date offdate = null;
				try {
					offdate = java.sql.Date.valueOf(request.getParameter("offdate").trim());
					if (today.compareTo(offdate) > 0) {
						eventTitleErrorMsgs.put("offdate_BiggerThanToday", "下架日期不得早於今天");
					} 				
					if (launchdate.compareTo(offdate) > 0) {
						eventTitleErrorMsgs.put("offdate_BiggerThanLaunchdate", "下架日期不得早於上架日期");
					} 
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("offdate", "請輸入下架日期");
				} catch (NullPointerException e){
					
				}
				
				String eveclass_no = request.getParameter("eveclass_no");
				Integer promotionranking = new Integer(request.getParameter("promotionranking"));
				String evetit_status = request.getParameter("evetit_status");
				Integer evetit_sessions = new Integer(request.getParameter("evetit_sessions"));			
				
				byte[] evetit_poster = null;
				String evetit_poster_status = request.getParameter("evetit_poster_status");
				if("noUpload".equals(evetit_poster_status)) {
						
					request.setAttribute("evetit_poster_status", "noUpload");					
				} else if ("yesUpload".equals(evetit_poster_status)){
					String saveDirectory = "/tempEventTitle";
					String realPath = getServletContext().getRealPath(saveDirectory);
					File fileSaveDirectory = new File(realPath);		
					if(!fileSaveDirectory.exists()) {
						fileSaveDirectory.mkdirs();
					}
					Part part = request.getPart("evetit_poster");
					DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss_");  
					String strToday = dateFormat.format(today); 
					String submittedFileName = strToday + part.getSubmittedFileName();

					if(submittedFileName.length() != 0 && part.getContentType() != null) {
						File fileHere = new File(fileSaveDirectory, submittedFileName);
						part.write(fileHere.toString());								
					}			
					
					request.setAttribute("evetit_poster_status", "alreadyUpload");						
					request.getSession().setAttribute("evetit_poster_path", request.getContextPath() + saveDirectory + "/" + submittedFileName);									
				} else if ("alreadyUpload".equals(evetit_poster_status)){
					request.setAttribute("evetit_poster_status", "alreadyUpload");	
				}

				String info = request.getParameter("info");
				String notices = request.getParameter("notices");
				String eticpurchaserules = request.getParameter("eticpurchaserules");
				String eticrules = request.getParameter("eticrules");
				String refundrules = request.getParameter("refundrules");
				
				//====================================================================================================
			
				EventTitleVO eventTitleVO = new EventTitleVO();				
				eventTitleVO.setEvetit_no(evetit_no);
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
				eventTitleVO.setEvetit_sessions(evetit_sessions);
				eventTitleVO.setEvetit_status(evetit_status);				
				eventTitleVO.setLaunchdate(launchdate);
				eventTitleVO.setOffdate(offdate);
				eventTitleVO.setPromotionranking(promotionranking);				
				
				if (!eventTitleErrorMsgs.isEmpty()) {
					request.setAttribute("eventTitleVO", eventTitleVO);
					RequestDispatcher failureView = request.getRequestDispatcher("/backend/event_title/updateEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}
				
				//====================================================================================================
				
				if(!"noUpload".equals(evetit_poster_status)) {
					String evetit_poster_path = (String) request.getSession().getAttribute("evetit_poster_path");				
					String evetit_poster_path_forUse = evetit_poster_path.replace(request.getContextPath(), "").replace("/", "\\");
					String realPath = getServletContext().getRealPath("/") + evetit_poster_path_forUse.substring(1);
					InputStream in = new FileInputStream(realPath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i;
					while ((i = in.read()) != -1)
						baos.write(i);
					evetit_poster = baos.toByteArray();
					in.close();
					baos.close();
					
				}
				eventTitleVO.setEvetit_poster(evetit_poster);
				

				/****************************** 2.開始修改資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleVO = eventTitleService.updateEventTitle(evetit_no, eveclass_no, ticrefpolicy_no, evetit_name,
						evetit_startdate, evetit_enddate, evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions,
						evetit_status, launchdate, offdate, promotionranking);

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/event_title/listOneEventTitle.jsp");
				successView.forward(request, response);
				
				request.getSession().removeAttribute("evetit_poster_path");

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "修改資料失敗 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/backend/event_title/updateEventTitle.jsp");
				failureView.forward(request, response);
			}

		} 

		
		
		
		
		
		
		

		
		// 請求來源 : backend -> addEventTitle.jsp
		else if ("insertEventTitle".equals(action)) {

			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);


			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				
				String evetit_name = request.getParameter("evetit_name");
				if (evetit_name == null || evetit_name.trim().length() == 0) {
					eventTitleErrorMsgs.put("evetit_name", "請輸入活動主題名稱");
				}
				
				String ticrefpolicy_no = request.getParameter("ticrefpolicy_no");
		
				java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
				java.sql.Date evetit_startdate = null;
				try {
					evetit_startdate = java.sql.Date.valueOf(request.getParameter("evetit_startdate").trim());	
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("evetit_startdate", "請輸入開始日期");
				}
				java.sql.Date evetit_enddate = null;
				try {
					evetit_enddate = java.sql.Date.valueOf(request.getParameter("evetit_enddate").trim());
					if (today.compareTo(evetit_enddate) > 0) {
						eventTitleErrorMsgs.put("evetit_enddate_BiggerThanToday", "結束日期不得早於今天");
					} 				
					if (evetit_startdate.compareTo(evetit_enddate) > 0) {
						eventTitleErrorMsgs.put("evetit_enddate_BiggerThanEvetitStartdate", "結束日期不得早於開始日期");
					} 
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("evetit_enddate", "請輸入結束日期");
				} catch (NullPointerException e){
					
				}
							
				java.sql.Date launchdate = null;
				try {
					launchdate = java.sql.Date.valueOf(request.getParameter("launchdate").trim());	
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("launchdate", "請輸入上架日期");
				}
				java.sql.Date offdate = null;
				try {
					offdate = java.sql.Date.valueOf(request.getParameter("offdate").trim());
					if (today.compareTo(offdate) > 0) {
						eventTitleErrorMsgs.put("offdate_BiggerThanToday", "下架日期不得早於今天");
					} 				
					if (launchdate.compareTo(offdate) > 0) {
						eventTitleErrorMsgs.put("offdate_BiggerThanLaunchdate", "下架日期不得早於上架日期");
					} 
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.put("offdate", "請輸入下架日期");
				} catch (NullPointerException e){
					
				}
											
				String eveclass_no = request.getParameter("eveclass_no");
				Integer promotionranking = new Integer(request.getParameter("promotionranking"));
				String evetit_status = request.getParameter("evetit_status");
				Integer evetit_sessions = new Integer(request.getParameter("evetit_sessions"));
				
				byte[] evetit_poster = null;
				String evetit_poster_status = request.getParameter("evetit_poster_status");
				if("noUpload".equals(evetit_poster_status)) {
					eventTitleErrorMsgs.put("evetit_poster", "請上傳主海報");	
					request.setAttribute("evetit_poster_status", "noUpload");					
				} else if ("yesUpload".equals(evetit_poster_status)){
					String saveDirectory = "/tempEventTitle";
					String realPath = getServletContext().getRealPath(saveDirectory);
					File fileSaveDirectory = new File(realPath);		
					if(!fileSaveDirectory.exists()) {
						fileSaveDirectory.mkdirs();
					}
					Part part = request.getPart("evetit_poster");
					DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss_");  
					String strToday = dateFormat.format(today); 
					String submittedFileName = strToday + part.getSubmittedFileName();

					if(submittedFileName.length() != 0 && part.getContentType() != null) {
						File fileHere = new File(fileSaveDirectory, submittedFileName);
						part.write(fileHere.toString());								
					}			
					
					request.setAttribute("evetit_poster_status", "alreadyUpload");						
					request.getSession().setAttribute("evetit_poster_path", request.getContextPath() + saveDirectory + "/" + submittedFileName);									
				} else if ("alreadyUpload".equals(evetit_poster_status)){
					request.setAttribute("evetit_poster_status", "alreadyUpload");	
				}
				
				String info = request.getParameter("info");
				String notices = request.getParameter("notices");
				String eticpurchaserules = request.getParameter("eticpurchaserules");
				String eticrules = request.getParameter("eticrules");
				String refundrules = request.getParameter("refundrules");
				
				if (!eventTitleErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request.getRequestDispatcher("/backend/event_title/addEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}
				
				String evetit_poster_path = (String) request.getSession().getAttribute("evetit_poster_path");				
				String evetit_poster_path_forUse = evetit_poster_path.replace(request.getContextPath(), "").replace("/", "\\");
				String realPath = getServletContext().getRealPath("/") + evetit_poster_path_forUse.substring(1);
		
				InputStream in = new FileInputStream(realPath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int i;
				while ((i = in.read()) != -1)
					baos.write(i);
				evetit_poster = baos.toByteArray();
				in.close();
				baos.close();
	
				/****************************** 2.開始修改資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				EventTitleVO eventTitleVO = new EventTitleVO();
				eventTitleVO = eventTitleService.addEventTitle(eveclass_no, ticrefpolicy_no, evetit_name,
						evetit_startdate, evetit_enddate, evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions,
						evetit_status, launchdate, offdate, promotionranking);	
												
				request.getSession().removeAttribute("evetit_poster_path");
				
				/****************************** 3.修改完成,準備轉交 ***************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/event_title/listOneEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "新增資料失敗 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/backend/event_title/addEventTitle.jsp");
				failureView.forward(request, response);
			} 
				
		}

		
		
		
		
		
		
		

		
		// 請求來源 : backend -> listAllEventTitleRelatives.jsp
		else if ("deleteEventTitle".equals(action)) {
			
			String requestURL = request.getParameter("requestURL");

			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = request.getParameter("evetit_no");
				if (evetit_no == null || evetit_no.trim().length() == 0) {
					eventTitleErrorMsgs.put("evetit_no", "找不到活動主題編號");
				}

				/****************************** 2.開始刪除資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleService.deleteEventTitle(evetit_no);

				/****************************** 3.刪除完成,準備轉交 **************************************************/
				List<EventTitleVO> eventTitleList = eventTitleService.getAll();
				request.setAttribute("eventTitleList", eventTitleList);
				RequestDispatcher successView = request.getRequestDispatcher(requestURL);
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "無法刪除資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
			return;
		}

		
		
		
		
		
		
		

		
		// 請求來源 : frontend -> selectEventTitle.jsp
		if ("listEventTitle_ByCompositeQuery".equals(action)) {
		
			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);
			
			try {				
				/****************************** 1.將輸入資料轉為Map **************************************************/ 
				Map<String, String[]> map = request.getParameterMap();
				
				/****************************** 2.開始複合查詢 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				List<EventTitleVO> list  = eventTitleService.getAllLaunched(map);
				
				/****************************** 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("listEventTitle_ByCompositeQuery", list);
				
				RequestDispatcher successView = request.getRequestDispatcher("/frontend/event_title/selectEventTitle.jsp");
				successView.forward(request, response);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/frontend/event_title/selectEventTitle.jsp");
				failureView.forward(request, response);
			}
			
		}

		
		
		
		
		
		
		

		// 請求來源 : backend -> listAllEventTitleRelatives.jsp
		if ("listEvents_ByEventTitle".equals(action)) {
			
			String requestURL = request.getParameter("requestURL");
			
			Map<String, String> eventTitleErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = request.getParameter("evetit_no");

				/****************************** 2.開始查詢資料 **************************************************/
//				EventTitleService eventTitleService = new EventTitleService();
//				Set<EventVO> set = eventTitleService.getEventsByEventTitle(evetit_no);

				/****************************** 3.查詢完成,準備轉交 **************************************************/
//				request.setAttribute("listEvents_ByEventTitle", set);

				RequestDispatcher successView = request.getRequestDispatcher(requestURL);
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.put("Exception", "無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
		}
		
	}

	
	
	
	
	
	
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	
	
	
	
	
	
	
	
}
