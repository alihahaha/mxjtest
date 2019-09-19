package com.itheima.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.UserService;
import com.itheima.utils.MD5Utils;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service = BasicFactory.getFactory().getInstance(UserService.class);
		//1.��ȡ�û�������
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		//2.����Service�����û�����������û�
		User user = service.findUserByUnameAndPsw(username,password);
		
		if(user == null){
			//3.���û���ҵ���ʾ�û������벻��ȷ
			throw new RuntimeException("�û������벻��ȷ!");
		}
		//4.����ҵ���,����û��Ƿ񼤻��,���û������ʾ��ȥ����
		if(user.getState()!=1){
			throw new RuntimeException("����ȥ�����ٵ�¼!");
		}
		//5.��������,���¼�û��ص���ҳ
		//--��������ѡ����ס�û���,����cookie�����û���
		if("true".equals(request.getParameter("remname"))){
			Cookie remnameC = new Cookie("remname",URLEncoder.encode(user.getUsername(), "utf-8"));
			remnameC.setMaxAge(3600*24*30);
			remnameC.setPath("/"+request.getContextPath());
			response.addCookie(remnameC);
		}else{
			Cookie remnameC = new Cookie("remname","");
			remnameC.setMaxAge(0);
			remnameC.setPath("/"+request.getContextPath());
			response.addCookie(remnameC);
		}
		
		//--����Ƿ�ѡ��30�����Զ���¼,�����ѡ������cookie�����û�������
		if("true".equals(request.getParameter("autologin"))){
			Cookie autologinC = new Cookie("autologin",URLEncoder.encode(user.getUsername(), "utf-8")+":"+user.getPassword());
			autologinC.setMaxAge(3600*24*30);
			autologinC.setPath("/"+request.getContextPath());
			response.addCookie(autologinC);
		}
		
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
