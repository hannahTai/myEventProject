package com.member.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.member.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("find_By_Account".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			HttpSession session = req.getSession();
			try {
				//***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String member_account = req.getParameter("member_account");
				String member_password = req.getParameter("member_password");
				if (member_account == null || (member_account.trim()).length() == 0) {
					errorMsgs.add("請輸入會員帳號");
				}
				if (member_password == null || (member_password.trim()).length() == 0) {
					errorMsgs.add("請輸入會員密碼");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/login_front-end.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料,並準備轉交(Send the Success view)*****************************************/
				MemberService memberService = new MemberService();
				MemberVO member = memberService.findByAccount(member_account);
				if (member == null) {
					errorMsgs.add("無此帳號");
				}
				// Send the use back to the form, if there were errors
				if(member_password.equals(member.getMemberPassword())){
					session.setAttribute("member", member);
				}else {
					errorMsgs.add("輸入的密碼有誤");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/login_front-end.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String url = "/frontend/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/login_front-end.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if("member_Logout".equals(action)) {
			try {
				//***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				req.getSession().invalidate();
				
				/***************************2.開始查詢資料,並準備轉交(Send the Success view)*****************************************/
				String url = "/CA105G2/frontend/index.jsp";
				res.sendRedirect(url);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("memberno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String memberNo = null;
				try {
					memberNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				MemberService memberService = new MemberService();
				MemberVO member = memberService.getOneMember(memberNo);
				if (member == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("member", member); // 資料庫取出的empVO物件,存入req
				String url = "/backend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String memberNo = new String(req.getParameter("memberno"));
				
				/***************************2.開始查詢資料****************************************/
				MemberService memberService = new MemberService();
				MemberVO member = memberService.getOneMember(memberNo);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("member", member);         // 資料庫取出的empVO物件,存入req
				String url = "/backend/member/update_member_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/member/listAllMember.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String memberNo = new String(req.getParameter("memberno").trim());
				
				String memberFullname = req.getParameter("name");
				if (memberFullname == null || memberFullname.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				}
//				String memberFullnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{3,10}$";
//				if (memberFullname == null || memberFullname.trim().length() == 0) {
//					errorMsgs.add("姓名: 請勿空白");
//				} else if(!memberFullname.trim().matches(memberFullnameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("姓名: 只能是中、英文字母、數字和- , 且長度必需在3到10之間");
//	            }
				
				String email = req.getParameter("email").trim();
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("電子郵件請勿空白");
				}	
				
				String phone = req.getParameter("phone").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("電話號碼請勿空白");
				}	
				
//				String idcard = req.getParameter("idcard").trim();
//				if (idcard == null || idcard.trim().length() == 0) {
//					errorMsgs.add("身份證字號請勿空白");
//				}	
				
				String account = req.getParameter("account").trim();
				if (account == null || account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}	
				
				String password = req.getParameter("password").trim();
				if (password == null || password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}	
				
//				Integer ewalletBalance = req.getParameter("ewalletBalance");
//				if (ewalletBalance == null || ewalletBalance.trim().length() == 0) {
//					errorMsgs.add("餘額請勿空白");
//				}
				
//				java.sql.Timestamp creationDate = (Timestamp)System.currentTimeMillis();
//				java.sql.Timestamp creationDate = null;
//				try {
//				Timestamp creationDate = req.getParameter("creationDate");
//				} catch (IllegalArgumentException e) {
//					creationDate=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
				
				byte[] profilePicture = null;
				Part part = req.getPart("picture");
				try {
					String uploadFileName = part.getSubmittedFileName();
					if (uploadFileName != null && part.getContentType() != null) {
						InputStream in = part.getInputStream();
						profilePicture = new byte[in.available()];
						in.read(profilePicture);
						in.close();
					}
				} catch (FileNotFoundException e) {
					errorMsgs.add("找不到檔案");
				}
				if (part.getSize() == 0) {
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
				}
				
				String states = req.getParameter("states").trim();
				if (states == null || states.trim().length() == 0) {
					errorMsgs.add("狀態請勿空白");
				}
				
//				String thirduid = req.getParameter("thirduid").trim();
//				if (thirduid == null || thirduid.trim().length() == 0) {
//					errorMsgs.add("請勿空白");
//				}
				
//				Double sal = null;
//				try {
//					sal = new Double(req.getParameter("sal").trim());
//				} catch (NumberFormatException e) {
//					sal = 0.0;
//					errorMsgs.add("薪水請填數字.");
//				}
				
//				Double comm = null;
//				try {
//					comm = new Double(req.getParameter("comm").trim());
//				} catch (NumberFormatException e) {
//					comm = 0.0;
//					errorMsgs.add("獎金請填數字.");
//				}
				
				MemberVO member = new MemberVO();
				member.setMemberNo(memberNo);
				member.setMemberFullname(memberFullname);
				member.setEmail(email);
				member.setPhone(phone);
//				member.setIdcard(idcard);
				member.setMemberAccount(account);
				member.setMemberPassword(password);
//				member.setEwalletBalance(ewalletBalance);
//				member.setCreationDate(creationDate);
				member.setProfilePicture(profilePicture);
				member.setMemberStatus(states);
//				member.setThirduid(thirduid);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("member", member); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/member/update_member_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				MemberService memberService = new MemberService();
//				member = memberService.updateMember(memberNo, memberFullname, email, phone, idcard, account, password, ewalletBalance, creationDate, req.getParameter("profilePicture"), req.getParameter("memberStatus"));
				memberService.updateMember(memberNo, memberFullname, email, phone, account, password, profilePicture, states);
//				member = memberService.updateMember(memberNo, memberFullnameReg, email, phone, idcard, memberAccount, memberPassword, ewalletBalance, creationDate, profilePicture, memberStatus);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("member", member); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/backend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/member/update_member_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String memberFullname = req.getParameter("name");
//				String memberFullnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9-)]{3,10}$";
				if (memberFullname == null || memberFullname.trim().length() == 0) {
					errorMsgs.add("姓名請勿空白");
				}
//				else if(!memberFullname.trim().matches(memberFullnameReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("姓名: 只能是中、英文字母、數字和- , 且長度必需在3到10之間");
//	            }
				
				String email = req.getParameter("email").trim();
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("電子郵件請勿空白");
				}	
				
				String phone = req.getParameter("phone1").trim() + req.getParameter("phone2").trim();
				if (phone == null || phone.trim().length() == 0) {
					errorMsgs.add("手機號碼請勿空白");
				}	
				
				String idcard = req.getParameter("idcard").trim();
				if (idcard == null || idcard.trim().length() == 0) {
					errorMsgs.add("身份證字號請勿空白");
				}	
				
				String account = req.getParameter("account").trim();
				if (account == null || account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}	
				
				String password = req.getParameter("password").trim();
				if (password == null || password.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}	
				
				Integer ewalletBalance = 0;
//				try {
//					ewalletBalance = new Integer(req.getParameter("ewalletBalance").trim());
//				} catch (NumberFormatException e) {
//					ewalletBalance = 0;
//					errorMsgs.add("餘額請填數字.");
//				}
				
//				java.sql.Timestamp creationDate = (Timestamp)System.currentTimeMillis();
//				java.sql.Timestamp creationDate = null;
//				try {
//					creationDate = java.sql.Timestamp.valueOf(req.getParameter("creationDate").trim());
//				} catch (IllegalArgumentException e) {
//					creationDate=new java.sql.Timestamp(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
				
				byte[] profilePicture = null;
				Part part = req.getPart("picture");
				try {
					String uploadFileName = part.getSubmittedFileName();
					if (uploadFileName != null && part.getContentType() != null) {
						InputStream in = part.getInputStream();
						profilePicture = new byte[in.available()];
						in.read(profilePicture);
						in.close();
					}
				} catch (FileNotFoundException e) {
					errorMsgs.add("找不到檔案");
				}
				if (part.getSize() == 0) {
					errorMsgs.add("請上傳大頭貼");
				}
				
//				String realPath = getServletContext().getRealPath(saveDirectory);
//				System.out.println("realPath="+realPath); // 測試用
//				File fsaveDirectory = new File(realPath);
//				if (!fsaveDirectory.exists())
//					 fsaveDirectory.mkdirs(); // 於 ContextPath 之下,自動建立目地目錄
//
//				Collection<Part> parts = req.getParts(); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理
				
				String states = "normal";
//				String states = req.getParameter("states").trim();
//				if (states == null || states.trim().length() == 0) {
//					errorMsgs.add("狀態請勿空白");
//				}
				
				String thirduid = req.getParameter("thirduid").trim();
				if (thirduid == null || thirduid.trim().length() == 0) {
					errorMsgs.add("請勿空白");
				}
				
//				Double sal = null;
//				try {
//					sal = new Double(req.getParameter("sal").trim());
//				} catch (NumberFormatException e) {
//					sal = 0.0;
//					errorMsgs.add("薪水請填數字.");
//				}
				
//				Double comm = null;
//				try {
//					comm = new Double(req.getParameter("comm").trim());
//				} catch (NumberFormatException e) {
//					comm = 0.0;
//					errorMsgs.add("獎金請填數字.");
//				}
				
//				Integer deptno = new Integer(req.getParameter("deptno").trim());

				MemberVO member = new MemberVO();
				member.setMemberFullname(memberFullname);
				member.setEmail(email);
				member.setPhone(phone);
				member.setIdcard(idcard);
				member.setMemberAccount(account);
				member.setMemberPassword(password);
				member.setEwalletBalance(ewalletBalance);
//				member.setCreationDate(creationDate);
				member.setProfilePicture(profilePicture);
				member.setMemberStatus(states);
				member.setThirduid(thirduid);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("member", member); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/member/addMember.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				MemberService memberService = new MemberService();
				member = memberService.addMember(memberFullname, email, phone, idcard, account, password, ewalletBalance, profilePicture, states, thirduid);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/member/addMember.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String memberNo = new String(req.getParameter("memberno"));
				
				/***************************2.開始刪除資料***************************************/
				MemberService memberService = new MemberService();
				memberService.deleteMember(memberNo);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/backend/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/member/listAllMember.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
