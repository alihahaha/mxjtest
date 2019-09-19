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
			//1.增加订单
			order_dao.addOrder(order);
			
			for(OrderItem item : order.getList()){
				//2.检查商品库存是否充足
				Product prod = prod_dao.findProdById2(item.getProduct_id());
				if(prod.getPnum()<item.getBuynum()){
					throw new RuntimeException("商品"+prod.getName()+"库存不足!");
				}
				//3.扣除商品库存
				prod_dao.updatePnum(prod.getId(),prod.getPnum()-item.getBuynum());
				//4.增加订单项
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
		//1.查询该用户所有的订单
		List<Order> list  = order_dao.findOrdersByUserId(user_id);
		//2.遍历所有的订单将每一个订单信息封装到OrderInfo中
		for(Order order : list){
			OrderInfo oi = new OrderInfo();
			oi.setUser(user_dao.findUserById(user_id));
			oi.setOrder(order);
			//--查询该订单的所有订单项信息
			List<OrderItem> itemList = order_dao.findOrderItemsByOrderId(order.getId());
			Map<Product,Integer> map = new HashMap<Product,Integer>();
			for(OrderItem item : itemList){
				Product prod = prod_dao.findProdById(item.getProduct_id());
				map.put(prod, item.getBuynum());
			}
			oi.setMap(map);
			retList.add(oi);
		}
		//3.最终组成一个OrderInfo的集合返回
		return retList;
	}
	public void delOrderById(String id) {
		try{
			TransactionManager.startTran();
			//1.将商品库存加回去
			List<OrderItem> list = order_dao.findOrderItemsByOrderId2(id);
			for(OrderItem item : list){
				Product prod = prod_dao.findProdById2(item.getProduct_id());
				prod_dao.updatePnum(item.getProduct_id(), prod.getPnum()+item.getBuynum());
			}
			//2.删除订单项
			order_dao.delOrderItemByOrderId(id);
			//3.删除订单
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
