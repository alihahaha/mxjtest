package com.itheima.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.factory.BasicFactory;
import com.itheima.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service = BasicFactory.getFactory().getInstance(UserService.class);
		//1.��ȡ������
		String activcecode = request.getParameter("activecode");
		//2.����Service�еķ��������û�
		service.activeUser(activcecode);
		//3.��ʾ�ɹ�,3���ص���ҳ
		response.getWriter().write("��ϲ������ɹ�!3���ص���ҳ...");
		response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
