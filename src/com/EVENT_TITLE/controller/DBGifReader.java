package com.EVENT_TITLE.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/EVENT_TITLE/DBGifReader")
public class DBGifReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection con;
       
	public void init() throws ServletException{
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
			if(con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 基本款
		request.setCharacterEncoding("utf-8");
		response.setContentType("image/gif");
		ServletOutputStream out = response.getOutputStream();
		
		Statement stmt = null;
		ResultSet rs = null;
		InputStream in = null;
		
		try {
			
			String evetit_no = request.getParameter("evetit_no").trim();
			
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT evetit_poster from event_title where evetit_no='" + evetit_no + "'");
			
			if(rs.next()) {
				in = new BufferedInputStream(rs.getBinaryStream("evetit_poster"));
				byte[] buf = new byte[4 * 1024];
				int len;
				while((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
			} else {
				in = getServletContext().getResourceAsStream("/NoData/CantFind.jpg");
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				out.write(bytes);
			}
			
		} catch (Exception e) {
			in = getServletContext().getResourceAsStream("/NoData/SomethingWrong.png");
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			out.write(bytes);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
