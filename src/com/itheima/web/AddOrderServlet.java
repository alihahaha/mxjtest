package com.itheima.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

public class AddOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		//1.封装订单信息到bean
		Order order = new Order();
		//订单id uuid赋值
		order.setId(UUID.randomUUID().toString());
		//收货地址 请求参数获取
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		//支付状态 默认是未支付
		order.setPaystate(0);
		//所属用户id 获取当前用户的id
		order.setUser_id(user.getId());
		//获取购物车
		Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
		//准备list存储当前订单相关的订单项信息
		List<OrderItem> list = new ArrayList<OrderItem>();
		//准备money 计算订单金额,注意此处的金额不能相信客户端提交的数据,防止被恶意修改
		double money = 0;
		//循环遍历购物车,计算金额.
		//循环遍历购物车,生成订单项对象组成集合
		for(Map.Entry<Product, Integer>entry : cartmap.entrySet()){
			money += entry.getKey().getPrice() * entry.getValue();
			OrderItem item = new OrderItem();
			item.setOrder_id(order.getId());
			item.setProduct_id(entry.getKey().getId());
			item.setBuynum(entry.getValue());
			list.add(item);
		}
		//赋值金额
		order.setMoney(money);
		//赋值订单项list
		order.setList(list);
		//2.调用Service增加订单信息
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		service.addOrder(order);
		//3.清空购物车
		cartmap.clear();
		//4.提示成功,回到主页
		response.getWriter().write("生成订单成功!");
		response.setHeader("Refresh", "1;url="+request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
