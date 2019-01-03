package com.venue.controller;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.event_title.model.EventTitleService;
import com.event_title.model.EventTitleVO;
import com.google.gson.Gson;
import com.venue.model.*;


@WebServlet("/venue/VenueServlet.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class VenueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public VenueServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		
		
		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		
		
		
		
		// 請求來源 : backend -> listAllVenue.jsp
		if ("getOneVenue_For_Display".equals(action)) {

			String requestURL = request.getParameter("requestURL");

			Map<String, String> venueErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("venueErrorMsgs", venueErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String venue_no = request.getParameter("venue_no");

				/****************************** 2.開始查詢資料 **************************************************/
				VenueService venueService = new VenueService();
				VenueVO venueVO = venueService.getOneVenue(venue_no);
				if (venueVO == null) {
					venueErrorMsgs.put("venue_no", "查無資料");
				}
				if (!venueErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
					failureView.forward(request, response);
					return;
				}

				/****************************** 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("venueVO", venueVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/venue/listOneVenue.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 ******************************/
			} catch (Exception e) {
				venueErrorMsgs.put("Exception", "無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
		}
		
		
		
		
		
		// 請求來源 : backend -> listAllVenue.jsp / listOneVenue.jsp
		else if ("getOneVenue_For_Update".equals(action)) {

			String requestURL = request.getParameter("requestURL");

			Map<String, String> venueErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("venueErrorMsgs", venueErrorMsgs);

			try {
				/****************************** 1.接收請求參數 **************************************************/
				String venue_no = request.getParameter("venue_no");

				/****************************** 2.開始查詢資料 **************************************************/
				VenueService venueService = new VenueService();
				VenueVO venueVO = venueService.getOneVenue(venue_no);
				if (venueVO == null) {
					venueErrorMsgs.put("venue_no", "查無資料");
				}
				if (!venueErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
					failureView.forward(request, response);
					return;
				}

				/****************************** 3.查詢完成,準備轉交 **************************************************/
				request.setAttribute("venueVO", venueVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/venue/updateVenue.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 ******************************/
			} catch (Exception e) {
				venueErrorMsgs.put("Exception", "無法取得資料 : " + e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
		}

		
		
		
		
		// 請求來源 : backend -> updateVenue.jsp
		else if ("updateVenue".equals(action)) {

			String requestURL = request.getParameter("requestURL");

			Map<String, String> venueErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("venueErrorMsgs", venueErrorMsgs);

			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				String venue_no = request.getParameter("venue_no");
				
				String venue_name = request.getParameter("venue_name");
				if (venue_name == null || venue_name.trim().length() == 0) {
					venueErrorMsgs.put("venue_name", "請輸入場地名稱");
				}
				
				String address = request.getParameter("address");
				if (address == null || address.trim().length() == 0) {
					venueErrorMsgs.put("address", "請輸入地址");
				}
				
				Double latitude = null;
				try {
					latitude = new Double(request.getParameter("latitude"));
				} catch (NumberFormatException e) {
					venueErrorMsgs.put("latitude", "請利用搜尋取得緯度");
				}
				
				Double longitude = null;
				try {
					longitude = new Double(request.getParameter("longitude"));
				} catch (NumberFormatException e) {
					venueErrorMsgs.put("longitude", "請利用搜尋取得經度");
				}
				
				String venue_info = request.getParameter("venue_info");
				
				java.sql.Timestamp today = new java.sql.Timestamp(System.currentTimeMillis());

				byte[] venue_locationPic = null;
				String venue_locationPic_status = request.getParameter("venue_locationPic_status");
				if("noUpload".equals(venue_locationPic_status)) {
					request.setAttribute("venue_locationPic_status", "noUpload");					
				} else if ("yesUpload".equals(venue_locationPic_status)){
					String saveDirectory = "/tempVenue";
					String realPath = getServletContext().getRealPath(saveDirectory);
					File fileSaveDirectory = new File(realPath);		
					if(!fileSaveDirectory.exists()) {
						fileSaveDirectory.mkdirs();
					}
					Part part = request.getPart("venue_locationPic");
					DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss_");  
					String strToday = dateFormat.format(today); 
					String submittedFileName = strToday + part.getSubmittedFileName();

					if(submittedFileName.length() != 0 && part.getContentType() != null) {
						File fileHere = new File(fileSaveDirectory, submittedFileName);
						part.write(fileHere.toString());								
					}			
					request.setAttribute("venue_locationPic_status", "alreadyUpload");						
					request.getSession().setAttribute("venue_locationPic_path", request.getContextPath() + saveDirectory + "/" + submittedFileName);									
				} else if ("alreadyUpload".equals(venue_locationPic_status)){
					request.setAttribute("venue_locationPic_status", "alreadyUpload");	
				}
				
				//====================================================================================================
				
				VenueVO venueVO = new VenueVO();				
				venueVO.setVenue_no(venue_no);
				venueVO.setVenue_name(venue_name);
				venueVO.setAddress(address);
				venueVO.setLatitude(latitude);
				venueVO.setLongitude(longitude);
				venueVO.setVenue_info(venue_info);
			
				if (!venueErrorMsgs.isEmpty()) {
					request.setAttribute("venueVO", venueVO);
					RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
					failureView.forward(request, response);
					return;
				}

				//====================================================================================================
				
				if(!"noUpload".equals(venue_locationPic_status)) {
					String venue_locationPic_path = (String) request.getSession().getAttribute("venue_locationPic_path");				
					String venue_locationPic_path_forUse = venue_locationPic_path.replace(request.getContextPath(), "").replace("/", "\\");
					String realPath = getServletContext().getRealPath("/") + venue_locationPic_path_forUse.substring(1);
					InputStream in = new FileInputStream(realPath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i;
					while ((i = in.read()) != -1)
						baos.write(i);
					venue_locationPic = baos.toByteArray();
					in.close();
					baos.close();
					
				}
				venueVO.setVenue_locationPic(venue_locationPic);
				
				/****************************** 2.開始修改資料 **************************************************/
				VenueService venueService = new VenueService();
				venueVO = venueService.updateVenue(venue_no, venue_name, address, latitude, longitude, venue_info, venue_locationPic);
				
				request.getSession().removeAttribute("venue_locationPic_path");

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				request.setAttribute("venueVO", venueVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/venue/listOneVenue.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				venueErrorMsgs.put("Exception", "修改資料失敗 : "+e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}
		}
		
		
		
		
		
		// 請求來源 : backend -> listAllVenue.jsp
		else if ("addVenue".equals(action)) {

			String requestURL = request.getParameter("requestURL");

			Map<String, String> venueErrorMsgs = new LinkedHashMap<String, String>();
			request.setAttribute("venueErrorMsgs", venueErrorMsgs);

			try {
				/****************************** 1.接收請求參數 - 輸入格式的錯誤處理 **************************************************/
				String venue_no = request.getParameter("venue_no");
				
				String venue_name = request.getParameter("venue_name");
				if (venue_name == null || venue_name.trim().length() == 0) {
					venueErrorMsgs.put("venue_name", "請輸入場地名稱");
				}
				
				String address = request.getParameter("address");
				if (address == null || address.trim().length() == 0) {
					venueErrorMsgs.put("address", "請輸入地址");
				}
				
				Double latitude = null;
				try {
					latitude = new Double(request.getParameter("latitude"));
				} catch (NumberFormatException e) {
					venueErrorMsgs.put("latitude", "請利用搜尋取得緯度");
				}
				
				Double longitude = null;
				try {
					longitude = new Double(request.getParameter("longitude"));
				} catch (NumberFormatException e) {
					venueErrorMsgs.put("longitude", "請利用搜尋取得經度");
				}
				
				String venue_info = request.getParameter("venue_info");
				
				java.sql.Timestamp today = new java.sql.Timestamp(System.currentTimeMillis());

				byte[] venue_locationPic = null;
				String venue_locationPic_status = request.getParameter("venue_locationPic_status");
				if("noUpload".equals(venue_locationPic_status)) {
					venueErrorMsgs.put("venue_locationPic", "請上傳位置圖");
					request.setAttribute("venue_locationPic_status", "noUpload");					
				} else if ("yesUpload".equals(venue_locationPic_status)){
					String saveDirectory = "/tempVenue";
					String realPath = getServletContext().getRealPath(saveDirectory);
					File fileSaveDirectory = new File(realPath);		
					if(!fileSaveDirectory.exists()) {
						fileSaveDirectory.mkdirs();
					}
					Part part = request.getPart("venue_locationPic");
					DateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss_");  
					String strToday = dateFormat.format(today); 
					String submittedFileName = strToday + part.getSubmittedFileName();

					if(submittedFileName.length() != 0 && part.getContentType() != null) {
						File fileHere = new File(fileSaveDirectory, submittedFileName);
						part.write(fileHere.toString());								
					}			
					request.setAttribute("venue_locationPic_status", "alreadyUpload");						
					request.getSession().setAttribute("venue_locationPic_path", request.getContextPath() + saveDirectory + "/" + submittedFileName);									
				} else if ("alreadyUpload".equals(venue_locationPic_status)){
					request.setAttribute("venue_locationPic_status", "alreadyUpload");	
				}
				
				if (!venueErrorMsgs.isEmpty()) {
					RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
					failureView.forward(request, response);
					return;
				}

				//====================================================================================================
				
				if(!"noUpload".equals(venue_locationPic_status)) {
					String venue_locationPic_path = (String) request.getSession().getAttribute("venue_locationPic_path");				
					String venue_locationPic_path_forUse = venue_locationPic_path.replace(request.getContextPath(), "").replace("/", "\\");
					String realPath = getServletContext().getRealPath("/") + venue_locationPic_path_forUse.substring(1);
					InputStream in = new FileInputStream(realPath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i;
					while ((i = in.read()) != -1)
						baos.write(i);
					venue_locationPic = baos.toByteArray();
					in.close();
					baos.close();
					
				}
				
				/****************************** 2.開始修改資料 **************************************************/
				VenueService venueService = new VenueService();
				VenueVO venueVO = venueService.addVenue(venue_name, address, latitude, longitude, venue_info, venue_locationPic);
				
				request.getSession().removeAttribute("venue_locationPic_path");

				/****************************** 3.修改完成,準備轉交 ***************************************************/
				request.setAttribute("venueVO", venueVO);
				RequestDispatcher successView = request.getRequestDispatcher("/backend/venue/listOneVenue.jsp");
				successView.forward(request, response);

				/****************************** 其他可能的錯誤處理 **************************************************/
			} catch (Exception e) {
				venueErrorMsgs.put("Exception", "修改資料失敗 : "+e.getMessage());
				RequestDispatcher failureView = request.getRequestDispatcher(requestURL);
				failureView.forward(request, response);
			}

		}
		
		
				
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}

}
