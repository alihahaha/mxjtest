package com.itheima.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.UserService;

public class AutoLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//1.只有未登录的用户才做自动登录
		if(req.getSession(false) == null || req.getSession().getAttribute("user")==null){
			//2.只有带了自动登录cookie用户才做自动登录
			Cookie [] cs = req.getCookies();
			Cookie findC = null;
			if(cs!=null){
				for(Cookie c : cs){
					if("autologin".equals(c.getName())){
						findC = c;
						break;
					}
				}
			}
			if(findC!=null){
				//3.只有自动登录cookie中保存的用户名密码都正确才做自动登录
				String username = URLDecoder.decode(findC.getValue().split(":")[0], "utf-8");
				String password = findC.getValue().split(":")[1];
				UserService service = BasicFactory.getFactory().getInstance(UserService.class);
				User user = service.findUserByUnameAndPsw(username, password);
				if(user!=null){
					//4.如果符合上面三个条件,可以自动登录
					req.getSession().setAttribute("user", user);
				}
			}
			
			
		}
		
		//5.无论是否自动登录都要放行资源
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
