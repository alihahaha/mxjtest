package com.itheima.service;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderInfo;
import com.itheima.domain.SaleInfo;

public interface OrderService {

	/**
	 * ���Ӷ���
	 * @param order ��װ�˶�����Ϣ��javabean
	 */
	void addOrder(Order order);

	/**
	 * ��ѯ�û������ж���
	 * @param user_id �û�Id
	 * @return
	 */
	List<OrderInfo> findOrdersByUserId(int user_id);

	/**
	 * ����idɾ������
	 * @param id ����id
	 */
	void delOrderById(String id);

	/**
	 * ����id��ѯ����
	 * @param id
	 * @return
	 */
	Order findOrderById(String id);

	/**
	 * �޸�֧��״̬
	 * @param id ����id
	 * @param state ����״̬
	 */
	void updatePayState(String id, int state);

	/**
	 * ��ѯ���۰�
	 * @return
	 */
	List<SaleInfo> saleInfo();

}
