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

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��Ʒid
		String id = request.getParameter("id");
		//2.��ѯ��Ʒ��Ϣ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = service.findProdById(id);
		if(prod==null){
			throw new RuntimeException("�Ҳ�������Ʒ!");
		}
		//3.��ȡ�����ﳵ
		Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
		//4.������Ʒ�����ﳵ��,���֮ǰû�����һ����¼����Ϊ1,������򲻼Ӽ�¼����+1
		cartmap.put(prod, (cartmap.containsKey(prod) ? cartmap.get(prod)+1 : 1));
		//5.�ص����ﳵҳ��
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
