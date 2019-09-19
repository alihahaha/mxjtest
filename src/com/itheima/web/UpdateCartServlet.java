package com.itheima.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;
import com.itheima.service.ProdService;

public class UpdateCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡid,��Ҫ�޸�Ϊ������
		String id = request.getParameter("id");
		int newNum = Integer.parseInt(request.getParameter("newNum"));
		//2.��ѯ����Ʒ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = service.findProdById(id);
		if(prod==null){
			throw new RuntimeException("�Ҳ�������Ʒ!");
		}
		//3.��ȡ���ﳵ
		Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
		//4.�޸�����
		cartmap.put(prod, newNum);
		//5.�ص����ﳵҳ��
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
