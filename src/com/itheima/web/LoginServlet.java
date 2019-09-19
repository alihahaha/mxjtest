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
		//1.获取用户名密码
		String username = request.getParameter("username");
		String password = MD5Utils.md5(request.getParameter("password"));
		//2.调用Service根据用户名密码查找用户
		User user = service.findUserByUnameAndPsw(username,password);
		
		if(user == null){
			//3.如果没有找到提示用户名密码不正确
			throw new RuntimeException("用户名密码不正确!");
		}
		//4.如果找到了,检查用户是否激活过,如果没激活提示先去激活
		if(user.getState()!=1){
			throw new RuntimeException("请先去激活再登录!");
		}
		//5.如果激活过,则登录用户回到主页
		//--检查如果勾选过记住用户名,则发送cookie保存用户名
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
		
		//--检查是否勾选过30天内自动登录,如果勾选过则发送cookie保存用户名密码
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
