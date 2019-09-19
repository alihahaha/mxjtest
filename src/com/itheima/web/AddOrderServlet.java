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
		//1.��װ������Ϣ��bean
		Order order = new Order();
		//����id uuid��ֵ
		order.setId(UUID.randomUUID().toString());
		//�ջ���ַ ���������ȡ
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		//֧��״̬ Ĭ����δ֧��
		order.setPaystate(0);
		//�����û�id ��ȡ��ǰ�û���id
		order.setUser_id(user.getId());
		//��ȡ���ﳵ
		Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
		//׼��list�洢��ǰ������صĶ�������Ϣ
		List<OrderItem> list = new ArrayList<OrderItem>();
		//׼��money ���㶩�����,ע��˴��Ľ������ſͻ����ύ������,��ֹ�������޸�
		double money = 0;
		//ѭ���������ﳵ,������.
		//ѭ���������ﳵ,���ɶ����������ɼ���
		for(Map.Entry<Product, Integer>entry : cartmap.entrySet()){
			money += entry.getKey().getPrice() * entry.getValue();
			OrderItem item = new OrderItem();
			item.setOrder_id(order.getId());
			item.setProduct_id(entry.getKey().getId());
			item.setBuynum(entry.getValue());
			list.add(item);
		}
		//��ֵ���
		order.setMoney(money);
		//��ֵ������list
		order.setList(list);
		//2.����Service���Ӷ�����Ϣ
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		service.addOrder(order);
		//3.��չ��ﳵ
		cartmap.clear();
		//4.��ʾ�ɹ�,�ص���ҳ
		response.getWriter().write("���ɶ����ɹ�!");
		response.setHeader("Refresh", "1;url="+request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
