package com.itheima.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;
import com.itheima.service.ProdService;

public class ImgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.��ȡ��Ʒid
		String id = request.getParameter("id");
		//2.��ѯ��Ʒ��Ϣ
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = service.findProdById(id);
		//3.ת������Ʒ��ͼƬ
		if(prod == null){
			throw new RuntimeException("�Ҳ�����Ӧ��Ʒ");
		}else{
			String path = prod.getImgurl();
			if("s".equals(request.getParameter("size"))){
				path = path.substring(0, path.lastIndexOf("."))+"_s"+path.substring(path.lastIndexOf("."));
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
