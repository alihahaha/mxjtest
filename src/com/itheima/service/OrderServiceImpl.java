package com.itheima.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.dao.OrderDao;
import com.itheima.dao.ProdDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderInfo;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.domain.SaleInfo;
import com.itheima.factory.BasicFactory;
import com.itheima.utils.TransactionManager;

public class OrderServiceImpl implements OrderService {
	private UserDao user_dao = BasicFactory.getFactory().getInstance(UserDao.class);
	private OrderDao order_dao = BasicFactory.getFactory().getInstance(OrderDao.class);
	private ProdDao prod_dao = BasicFactory.getFactory().getInstance(ProdDao.class);
	public void addOrder(Order order) {
		try{
			TransactionManager.startTran();
			//1.���Ӷ���
			order_dao.addOrder(order);
			
			for(OrderItem item : order.getList()){
				//2.�����Ʒ����Ƿ����
				Product prod = prod_dao.findProdById2(item.getProduct_id());
				if(prod.getPnum()<item.getBuynum()){
					throw new RuntimeException("��Ʒ"+prod.getName()+"��治��!");
				}
				//3.�۳���Ʒ���
				prod_dao.updatePnum(prod.getId(),prod.getPnum()-item.getBuynum());
				//4.���Ӷ�����
				order_dao.addOrderItem(item);
			}
			TransactionManager.commit();
		}catch (Exception e) {
			TransactionManager.rollback();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			TransactionManager.release();
		}
	}
	public List<OrderInfo> findOrdersByUserId(int user_id) {
		List<OrderInfo> retList = new ArrayList<OrderInfo>();
		//1.��ѯ���û����еĶ���
		List<Order> list  = order_dao.findOrdersByUserId(user_id);
		//2.�������еĶ�����ÿһ��������Ϣ��װ��OrderInfo��
		for(Order order : list){
			OrderInfo oi = new OrderInfo();
			oi.setUser(user_dao.findUserById(user_id));
			oi.setOrder(order);
			//--��ѯ�ö��������ж�������Ϣ
			List<OrderItem> itemList = order_dao.findOrderItemsByOrderId(order.getId());
			Map<Product,Integer> map = new HashMap<Product,Integer>();
			for(OrderItem item : itemList){
				Product prod = prod_dao.findProdById(item.getProduct_id());
				map.put(prod, item.getBuynum());
			}
			oi.setMap(map);
			retList.add(oi);
		}
		//3.�������һ��OrderInfo�ļ��Ϸ���
		return retList;
	}
	public void delOrderById(String id) {
		try{
			TransactionManager.startTran();
			//1.����Ʒ���ӻ�ȥ
			List<OrderItem> list = order_dao.findOrderItemsByOrderId2(id);
			for(OrderItem item : list){
				Product prod = prod_dao.findProdById2(item.getProduct_id());
				prod_dao.updatePnum(item.getProduct_id(), prod.getPnum()+item.getBuynum());
			}
			//2.ɾ��������
			order_dao.delOrderItemByOrderId(id);
			//3.ɾ������
			order_dao.delOrderById(id);
			TransactionManager.commit();
		}catch (Exception e) {
			TransactionManager.rollback();
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			TransactionManager.release();
		}
	}
	public Order findOrderById(String id) {
		return order_dao.findOrderById(id);
	}
	public void updatePayState(String id, int payState) {
		order_dao.updatePayState(id,payState);
	}
	public List<SaleInfo> saleInfo() {
		return order_dao.saleInfo();
	}

}
