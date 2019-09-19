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
			
			//0.检验验证码,如果验证码都不正确,剩下的事情就不用做了
			String valistr1 = request.getParameter("valistr");
			String valistr2 = (String) request.getSession().getAttribute("valistr");
			if(valistr1 == null || valistr2 == null || !valistr1.equals(valistr2)){
				request.setAttribute("valistrMsg", "验证码不正确!");
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
			}
			//1.封装数据 *校验数据
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			//2.调用Service中的方法注册用户
			service.registUser(user);
			//3.回到主页
			response.getWriter().write("恭喜您注册成功,请到邮箱中激活!");
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
