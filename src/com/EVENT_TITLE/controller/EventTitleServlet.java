package com.EVENT_TITLE.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
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



@WebServlet("/EVENT_TITLE/EventTitleServlet.do")
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

		
		
		
		if (action.indexOf("getOneEventTitle_For_Display") != -1) {

			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = action.substring(29, 34);

				/****************************** 2.開始查詢資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				EventTitleVO eventTitleVO = eventTitleService.getOneEventTitle(evetit_no);
				if (eventTitleVO == null) {
					eventTitleErrorMsgs.add("查無資料");
				}
				if (!eventTitleErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/EVENT_TITLE/listAllEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}

				/****************************** 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request
						.getRequestDispatcher("/back-end/EVENT_TITLE/listOneEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 ******************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.add("無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/EVENT_TITLE/listAllEventTitle.jsp");
				failureView.forward(request, response);
			}
			return;
		}

		
		
		
		
		
		
		
		
		
		else if (action.indexOf("getOneEventTitle_For_Update") != -1) {

			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = action.substring(28, 33);

				/****************************** 2.開始查詢資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				EventTitleVO eventTitleVO = eventTitleService.getOneEventTitle(evetit_no);
				if (eventTitleVO == null) {
					eventTitleErrorMsgs.add("查無資料");
				}
				if (!eventTitleErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("/back-end/EVENT_TITLE/listAllEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}

				/******************************* 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/updateEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.add(e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/listAllEventTitle.jsp");
				failureView.forward(request, response);
			}
			return;
		}

		
		
		
		
		
		
		
		
		
		else if (action.indexOf("deleteEventTitle") != -1) {

			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String evetit_no = action.substring(17, 22);
				if (evetit_no == null || evetit_no.trim().length() == 0) {
					eventTitleErrorMsgs.add("找不到活動主題編號");
				}

				/****************************** 2.開始刪除資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleService.deleteEventTitle(evetit_no);

				/****************************** 3.刪除完成,準備轉交 **************************************************/
				List<EventTitleVO> eventTitleList = eventTitleService.getAll();
				request.setAttribute("eventTitleList", eventTitleList);
				System.out.println("hey");
				RequestDispatcher successView = request
						.getRequestDispatcher("/back-end/EVENT_TITLE/listAllEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.add(e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/back-end/EVENT_TITLE/listAllEventTitle.jsp");
				failureView.forward(request, response);
			}
			return;
		}

		
		
		
		
		
		
		
		
		
		else if ("updateEventTitle".equals(action)) {

			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			ByteArrayOutputStream baos = null;

			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				String evetit_no = request.getParameter("evetit_no");
				
				String evetit_name = request.getParameter("evetit_name");
				if (evetit_name == null || evetit_name.trim().length() == 0) {
					eventTitleErrorMsgs.add("請輸入活動主題名稱");
				}
				
				String ticrefpolicy_no = request.getParameter("ticrefpolicy_no");
		
				java.sql.Date evetit_startdate = null;
				try {
					evetit_startdate = java.sql.Date.valueOf(request.getParameter("evetit_startdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入開始日期");
				}
				java.sql.Date evetit_enddate = null;
				try {
					evetit_enddate = java.sql.Date.valueOf(request.getParameter("evetit_enddate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入結束日期");
				}
				
				java.sql.Date today = new java.sql.Date(System.currentTimeMillis());				
				if (today.compareTo(evetit_startdate) > 0) {
					eventTitleErrorMsgs.add("開始日期不得早於今天");
				}				
				if (today.compareTo(evetit_enddate) > 0) {
					eventTitleErrorMsgs.add("結束日期不得早於今天");
				} 				
				if (evetit_startdate.compareTo(evetit_enddate) > 0) {
					eventTitleErrorMsgs.add("結束日期不得早於開始日期");
				} 
				
				java.sql.Date launchdate = null;
				try {
					launchdate = java.sql.Date.valueOf(request.getParameter("launchdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入上架日期");
				}
				java.sql.Date offdate = null;
				try {
					offdate = java.sql.Date.valueOf(request.getParameter("offdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入下架日期");
				}
				
				if (today.compareTo(launchdate) > 0) {
					eventTitleErrorMsgs.add("上架日期不得早於今天");
				}				
				if (today.compareTo(offdate) > 0) {
					eventTitleErrorMsgs.add("下架日期不得早於今天");
				} 				
				if (launchdate.compareTo(offdate) > 0) {
					eventTitleErrorMsgs.add("下架日期不得早於上架日期");
				} 
				
				String eveclass_no = request.getParameter("eveclass_no");
				Integer promotionranking = new Integer(request.getParameter("promotionranking"));
				String evetit_status = request.getParameter("evetit_status");
				Integer evetit_sessions = new Integer(request.getParameter("evetit_sessions"));
				
				byte[] evetit_poster = null;
				if( request.getPart("evetit_poster") == null ) {
					
				} else {
					Part part = request.getPart("evetit_poster");
					InputStream in = part.getInputStream();
					baos = new ByteArrayOutputStream();
					int i;
					while ((i = in.read()) != -1)
						baos.write(i);
					evetit_poster = baos.toByteArray();
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
					RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/updateEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}

				/****************************** 2.開始修改資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleVO = eventTitleService.updateEventTitle(evetit_no, eveclass_no, ticrefpolicy_no, evetit_name,
						evetit_startdate, evetit_enddate, evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions,
						evetit_status, launchdate, offdate, promotionranking);

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/listOneEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.add("修改資料失敗 : "+e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/updateEventTitle.jsp");
				failureView.forward(request, response);
			}

		}

		
		
		
		
		
		
		
		
		
		else if ("insertEventTitle".equals(action)) {

			List<String> eventTitleErrorMsgs = new LinkedList<String>();
			request.setAttribute("eventTitleErrorMsgs", eventTitleErrorMsgs);

			ByteArrayOutputStream baos = null;

			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				
				String evetit_name = request.getParameter("evetit_name");
				if (evetit_name == null || evetit_name.trim().length() == 0) {
					eventTitleErrorMsgs.add("請輸入活動主題名稱");
				}
				
				String ticrefpolicy_no = request.getParameter("ticrefpolicy_no");
		
				java.sql.Date evetit_startdate = null;
				try {
					evetit_startdate = java.sql.Date.valueOf(request.getParameter("evetit_startdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入開始日期");
				}
				java.sql.Date evetit_enddate = null;
				try {
					evetit_enddate = java.sql.Date.valueOf(request.getParameter("evetit_enddate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入結束日期");
				}
				
//				java.sql.Date today = new java.sql.Date(System.currentTimeMillis());				
//				if (today.compareTo(evetit_startdate) > 0) {
//					eventTitleErrorMsgs.add("開始日期不得早於今天");
//				}				
//				if (today.compareTo(evetit_enddate) > 0) {
//					eventTitleErrorMsgs.add("結束日期不得早於今天");
//				} 				
//				if (evetit_startdate.compareTo(evetit_enddate) > 0) {
//					eventTitleErrorMsgs.add("結束日期不得早於開始日期");
//				} 
				
				java.sql.Date launchdate = null;
				try {
					launchdate = java.sql.Date.valueOf(request.getParameter("launchdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入上架日期");
				}
				java.sql.Date offdate = null;
				try {
					offdate = java.sql.Date.valueOf(request.getParameter("offdate").trim());
				} catch (IllegalArgumentException e) {
					eventTitleErrorMsgs.add("請輸入下架日期");
				}
							
//				if (today.compareTo(launchdate) > 0) {
//					eventTitleErrorMsgs.add("上架日期不得早於今天");
//				}				
//				if (today.compareTo(offdate) > 0) {
//					eventTitleErrorMsgs.add("下架日期不得早於今天");
//				} 				
//				if (launchdate.compareTo(offdate) > 0) {
//					eventTitleErrorMsgs.add("下架日期不得早於上架日期");
//				} 
				
				String eveclass_no = request.getParameter("eveclass_no");
				Integer promotionranking = new Integer(request.getParameter("promotionranking"));
				String evetit_status = request.getParameter("evetit_status");
				Integer evetit_sessions = new Integer(request.getParameter("evetit_sessions"));
				
				byte[] evetit_poster = null;
				String evetit_poster_status = request.getParameter("evetit_poster_status");
				System.out.println(evetit_poster_status);
				
				if("noUpload".equals(evetit_poster_status)) {
					eventTitleErrorMsgs.add("請上傳主海報");	
					request.setAttribute("evetit_poster_status", "noUpload");
					
				} else if ("yesUpload".equals(evetit_poster_status)){
									
					String saveDirectory = "/tempEventTitle";
					String realPath = getServletContext().getRealPath(saveDirectory);
					File fileSaveDirectory = new File(realPath);		
					if(!fileSaveDirectory.exists()) {
						fileSaveDirectory.mkdirs();
					}
									
					Part part = request.getPart("evetit_poster");
					String submittedFileName = part.getSubmittedFileName();

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
				eventTitleVO.setEveclass_no(eveclass_no);
				eventTitleVO.setTicrefpolicy_no(ticrefpolicy_no);
				eventTitleVO.setEvetit_name(evetit_name);
				eventTitleVO.setEvetit_startdate(evetit_startdate);
				eventTitleVO.setEvetit_enddate(evetit_enddate);
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
					RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/addEventTitle.jsp");
					failureView.forward(request, response);
					return;
				}
				
				//====================================================================================================
				
				String evetit_poster_path = (String) request.getSession().getAttribute("evetit_poster_path");
				String evetit_poster_path_forUse = evetit_poster_path.replace(request.getContextPath(), "").replace("/", "\\");
				String realPath = getServletContext().getRealPath("/") + evetit_poster_path_forUse;

				InputStream in = new FileInputStream(realPath);
				baos = new ByteArrayOutputStream();
				int i;
				while ((i = in.read()) != -1)
					baos.write(i);
				evetit_poster = baos.toByteArray();
				
				eventTitleVO.setEvetit_poster(evetit_poster);
	
				/****************************** 2.開始修改資料 **************************************************/
				EventTitleService eventTitleService = new EventTitleService();
				eventTitleVO = eventTitleService.addEventTitle(eveclass_no, ticrefpolicy_no, evetit_name,
						evetit_startdate, evetit_enddate, evetit_poster, info, notices, eticpurchaserules, eticrules, refundrules, evetit_sessions,
						evetit_status, launchdate, offdate, promotionranking);			
				
				// I can't remove the file that is already uploaded........................!!!!
//				System.out.println(realPath);
//				File realPathFile = new File(realPath);				
//				System.out.println("realPathFile" + realPathFile.getPath());
//				File fileRemove = new File("C:\\CA105G2_ETIckeTs\\CA105G2_workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\myEventProject\\tempEventTitle\\E0001_EVETIT_POSTER.jpg");								
//				String deletePath = realPath.replace("\\", "\\\\");	
//				System.out.println(deletePath);
//				File fileRemove = new File(deletePath);				
//				System.out.println("fileRemove" + fileRemove.getPath());			
//				File fileRemove = new File("/tempEventTitle");	
//				fileRemove.delete();			
//				C:\CA105G2_ETIckeTs\CA105G2_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\myEventProject\tempEventTitle\E0001_EVETIT_POSTER.jpg
				
				request.getSession().removeAttribute("evetit_poster_path");
				
				/****************************** 3.修改完成,準備轉交 ***************************************************/
				request.setAttribute("eventTitleVO", eventTitleVO);
				RequestDispatcher successView = request.getRequestDispatcher("/back-end/EVENT_TITLE/listOneEventTitle.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				eventTitleErrorMsgs.add("修改資料失敗 : "+e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher("/back-end/EVENT_TITLE/updateEventTitle.jsp");
				failureView.forward(request, response);
			}

		}

		
		
	}

	
	
	
	
	
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	
	
	
	
	
	
	
	
}
