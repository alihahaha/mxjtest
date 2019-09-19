package com.itheima.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.UserService;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserService service = BasicFactory.getFactory().getInstance(UserService.class);
			
			//0.������֤��,�����֤�붼����ȷ,ʣ�µ�����Ͳ�������
			String valistr1 = request.getParameter("valistr");
			String valistr2 = (String) request.getSession().getAttribute("valistr");
			if(valistr1 == null || valistr2 == null || !valistr1.equals(valistr2)){
				request.setAttribute("valistrMsg", "��֤�벻��ȷ!");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
			}
			//1.��װ���� *У������
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			//2.����Service�еķ���ע���û�
			service.registUser(user);
			//3.�ص���ҳ
			response.getWriter().write("��ϲ��ע��ɹ�,�뵽�����м���!");
			response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
