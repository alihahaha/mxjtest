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
		//1.ֻ��δ��¼���û������Զ���¼
		if(req.getSession(false) == null || req.getSession().getAttribute("user")==null){
			//2.ֻ�д����Զ���¼cookie�û������Զ���¼
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
				//3.ֻ���Զ���¼cookie�б�����û������붼��ȷ�����Զ���¼
				String username = URLDecoder.decode(findC.getValue().split(":")[0], "utf-8");
				String password = findC.getValue().split(":")[1];
				UserService service = BasicFactory.getFactory().getInstance(UserService.class);
				User user = service.findUserByUnameAndPsw(username, password);
				if(user!=null){
					//4.�������������������,�����Զ���¼
					req.getSession().setAttribute("user", user);
				}
			}
			
			
		}
		
		//5.�����Ƿ��Զ���¼��Ҫ������Դ
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
