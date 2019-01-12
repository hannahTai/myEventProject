package com.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberService;

public class MemberImageServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String memberno = req.getParameter("memberno");
		MemberService memberService = new MemberService();
		byte[] memberPic = memberService.getOneMember(memberno).getProfilePicture();
		
		ServletOutputStream output = res.getOutputStream();
		res.setContentLengthLong(memberPic.length);
		res.setContentType("image/*");
		output.write(memberPic);
		output.close();
//		doPost(req,res);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, res);
	}
	

}
