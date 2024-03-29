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
		//1.获取激活码
		String activcecode = request.getParameter("activecode");
		//2.调用Service中的方法激活用户
		service.activeUser(activcecode);
		//3.提示成功,3秒后回到主页
		response.getWriter().write("恭喜您激活成功!3秒后回到主页...");
		response.setHeader("refresh", "3;url="+request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
