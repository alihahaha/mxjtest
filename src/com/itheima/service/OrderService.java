package com.itheima.service;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderInfo;
import com.itheima.domain.SaleInfo;

public interface OrderService {

	/**
	 * 增加订单
	 * @param order 封装了订单信息的javabean
	 */
	void addOrder(Order order);

	/**
	 * 查询用户的所有订单
	 * @param user_id 用户Id
	 * @return
	 */
	List<OrderInfo> findOrdersByUserId(int user_id);

	/**
	 * 根据id删除订单
	 * @param id 订单id
	 */
	void delOrderById(String id);

	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	Order findOrderById(String id);

	/**
	 * 修改支付状态
	 * @param id 订单id
	 * @param state 订单状态
	 */
	void updatePayState(String id, int state);

	/**
	 * 查询销售榜单
	 * @return
	 */
	List<SaleInfo> saleInfo();

}
