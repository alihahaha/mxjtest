package com.itheima.dao;

import java.sql.Connection;
import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.SaleInfo;

public interface OrderDao {

	/**
	 * 增加订单 事务控制
	 * @param order 封装了订单信息的javabean
	 */
	void addOrder(Order order);

	/**
	 * 增加订单项 事务控制
	 * @param item
	 */
	void addOrderItem(OrderItem item);

	/**
	 * 查询指定用户的所有订单
	 * @param user_id 用户id
	 * @return
	 */
	List<Order> findOrdersByUserId(int user_id);

	/**
	 * 根据订单id查询订单的所有订单项
	 * @param id 订单id
	 * @return
	 */
	List<OrderItem> findOrderItemsByOrderId(String id);

	/**
	 * 删除指定订单的所有订单项 事务控制
	 * @param id 订单id
	 */
	void delOrderItemByOrderId(String id);

	/**
	 * 根据id删除订单 事务控制
	 * @param id 订单id
	 */
	void delOrderById(String id);

	/**
	 * 根据id查询订单 事务控制
	 * @param id
	 * @return
	 */
	List<OrderItem> findOrderItemsByOrderId2(String id);

	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	Order findOrderById(String id);

	/**
	 * 修改订单的支付状态
	 * @param id 订单编号
	 * @param payState 支付状态
	 */
	void updatePayState(String id, int payState);

	/**
	 * 查询销售榜单
	 * @return
	 */
	List<SaleInfo> saleInfo();

}
