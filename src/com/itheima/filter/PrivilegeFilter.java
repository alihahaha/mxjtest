package com.itheima.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.User;

public class PrivilegeFilter implements Filter {
	private List<String> user_list = new ArrayList<String>();
	private List<String> admin_list = new ArrayList<String>();
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		
		if(admin_list.contains(uri) || user_list.contains(uri)){
			//��ҪȨ��
			if(req.getSession(false)==null || req.getSession().getAttribute("user")==null){
				//��ҪȨ��,����δ��¼,��ʾ��ȥ��¼
				throw new RuntimeException("��ǰ��Դ��ҪȨ��,���ȵ�¼���ٷ���!");
			}else{
				//��ҪȨ��,�ҵ�¼��
				User user = (User) req.getSession().getAttribute("user");
				if(admin_list.contains(uri) && "admin".equals(user.getRole())){
					chain.doFilter(request, response);
				}else if(user_list.contains(uri) && "user".equals(user.getRole())){
					chain.doFilter(request, response);
				}else{
					throw new RuntimeException("��û��Ȩ�޷��ʸ���Դ!����ϵ����Ա!");
				}
			}
		}else{
			//����ҪȨ��
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filterConfig.getServletContext().getRealPath("/WEB-INF/user.txt")));
			String line = null;
			while((line=reader.readLine())!=null){
				user_list.add(line);
			}
			reader.close();
			
			reader = new BufferedReader(new FileReader(filterConfig.getServletContext().getRealPath("/WEB-INF/admin.txt")));
			line = null;
			while((line=reader.readLine())!=null){
				admin_list.add(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
