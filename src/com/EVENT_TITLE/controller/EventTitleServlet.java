package com.EVENT_TITLE.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EventTitleServlet")
public class EventTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventTitleServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		out.println("hey");
		
		/***************************2.開始新增資料***************************************/
//		EmpService empSvc = new EmpService();
//		empSvc.addEmp(ename, job, hiredate, sal, comm, deptno);
		
		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		String url = "/back-end/eventManagement_V2/addEventTitle.html";
		RequestDispatcher successView = request.getRequestDispatcher(url);
		successView.forward(request, response);				


	}

}
