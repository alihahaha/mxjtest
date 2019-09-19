package com.itheima.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

public class DelOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡҪɾ���Ķ���id
		String id = request.getParameter("id");
		//2.����Serviceɾ������
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		service.delOrderById(id);
		//3.��ʾ�ɹ��ص������б�ҳ��
		response.getWriter().write("ɾ�������ɹ�!");
		response.setHeader("refresh", "1;url="+request.getContextPath()+"/OrderListServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
