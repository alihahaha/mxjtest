package com.itheima.dao;

import java.sql.Connection;
import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.SaleInfo;

public interface OrderDao {

	/**
	 * ���Ӷ��� �������
	 * @param order ��װ�˶�����Ϣ��javabean
	 */
	void addOrder(Order order);

	/**
	 * ���Ӷ����� �������
	 * @param item
	 */
	void addOrderItem(OrderItem item);

	/**
	 * ��ѯָ���û������ж���
	 * @param user_id �û�id
	 * @return
	 */
	List<Order> findOrdersByUserId(int user_id);

	/**
	 * ���ݶ���id��ѯ���������ж�����
	 * @param id ����id
	 * @return
	 */
	List<OrderItem> findOrderItemsByOrderId(String id);

	/**
	 * ɾ��ָ�����������ж����� �������
	 * @param id ����id
	 */
	void delOrderItemByOrderId(String id);

	/**
	 * ����idɾ������ �������
	 * @param id ����id
	 */
	void delOrderById(String id);

	/**
	 * ����id��ѯ���� �������
	 * @param id
	 * @return
	 */
	List<OrderItem> findOrderItemsByOrderId2(String id);

	/**
	 * ����id��ѯ����
	 * @param id
	 * @return
	 */
	Order findOrderById(String id);

	/**
	 * �޸Ķ�����֧��״̬
	 * @param id �������
	 * @param payState ֧��״̬
	 */
	void updatePayState(String id, int payState);

	/**
	 * ��ѯ���۰�
	 * @return
	 */
	List<SaleInfo> saleInfo();

}
