package com.itheima.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Order;
import com.itheima.domain.OrderInfo;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

public class OrderListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��ǰ�û�
		User user = (User) request.getSession().getAttribute("user");
		//2.�����û�id��ѯ���û����ж���
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		List<OrderInfo> list = service.findOrdersByUserId(user.getId());
		//3.��������Ϣ����request�����orderList.jspҳ����ʾ
		request.setAttribute("list", list);
		request.getRequestDispatcher("/orderList.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
