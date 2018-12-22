package com.EVENT_TITLE.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.EVENT_TITLE.model.EventTitleService;
import com.UTILITY.ImageUtil;

@WebServlet("/EVENT_TITLE/EventTitleGifReader")
public class EventTitleGifReader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;

	public void init() throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "CA105G2", "123456");
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Couldn't load JdbcOdbcDriver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	public void destroy() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		
		request.setCharacterEncoding("utf-8");
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("image/gif");

		try {
			if (!request.getParameter("evetit_no").isEmpty()) {
				String evetit_no = request.getParameter("evetit_no");
				int scaleSize = Integer.parseInt((request.getParameter("scaleSize")));

				EventTitleService eventTitleService = new EventTitleService();
				byte[] srcImageData = eventTitleService.getOneEventTitle(evetit_no).getEvetit_poster();

				byte[] scaledImageDate = ImageUtil.shrink(srcImageData, scaleSize);
				response.setContentLength(scaledImageDate.length);
				out.write(scaledImageDate);
			} else {
				InputStream in = getServletContext().getResourceAsStream("/NoData/imageComingSoon.jpg");
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				out.write(bytes);
				in.close();
			}
			
		} catch (Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/NoData/imageComingSoon.jpg");
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			out.write(bytes);
			in.close();					
		}

	}
}
