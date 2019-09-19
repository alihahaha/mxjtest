package com.itheima.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter {
	private boolean hasNotEncode = true;
	private String encode;
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//1.响应乱码解决
		response.setContentType("text/html;charset="+encode);
		//2.用装饰设计模式修改和获取请求参数相关的方法从而解决请求参数乱码
		chain.doFilter(new MyHttpServletReqeust((HttpServletRequest) request), response);
	}
	
	class MyHttpServletReqeust extends HttpServletRequestWrapper{
		private HttpServletRequest request;
		public MyHttpServletReqeust(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		
		@Override
		public Map<String,String[]> getParameterMap() {
			try {
				if("POST".equals(request.getMethod())){
					request.setCharacterEncoding(encode);
					return request.getParameterMap();
				}else if("GET".equals(request.getMethod())){
					Map<String,String []>map = request.getParameterMap();
					if(hasNotEncode){
						for(Map.Entry<String, String[]> entry : map.entrySet()){
							String [] vs = entry.getValue();
							for(int i = 0;i<vs.length ;i++){
								vs[i] = new String(vs[i].getBytes("iso8859-1"),encode);
							}
						}
						hasNotEncode = false;
					}
					return map;
				}else{
					return request.getParameterMap();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public String[] getParameterValues(String name) {
			return getParameterMap().get(name);
		}
		
		@Override
		public String getParameter(String name) {
			String [] vs = getParameterValues(name);
			return vs == null ? null : vs[0];
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String encode = filterConfig.getServletContext().getInitParameter("encode");
		this.encode = encode == null ? "utf-8" : encode;
	}

}
