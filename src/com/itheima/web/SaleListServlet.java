package com.itheima.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.SaleInfo;
import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

public class SaleListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.调用Service中的方法查询出销售榜单
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		List<SaleInfo> list = service.saleInfo();
		//2.组织成excel格式的数据
		StringBuffer buffer = new StringBuffer();
		buffer.append("商品id,商品名称,销售总量\r\n");
		for(SaleInfo si : list){
			buffer.append(si.getProd_id()+","+si.getProd_name()+","+si.getSale_num()+"\r\n");
		}
		String text = buffer.toString();
		//3.提供下载
		String fname = "Estore销售榜单"+new Date().toLocaleString()+".csv";
		response.setContentType(this.getServletContext().getMimeType(fname));
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fname, "utf-8"));
		response.getWriter().write(text);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
