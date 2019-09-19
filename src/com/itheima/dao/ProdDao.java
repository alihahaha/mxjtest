package com.itheima.dao;

import java.sql.Connection;
import java.util.List;

import com.itheima.domain.Product;

public interface ProdDao {

	/**
	 * �����Ʒ�����ݿ�
	 * @param prod
	 */
	void addProd(Product prod);

	/**
	 * ��ѯ������Ʒ��Ϣ
	 * @return
	 */
	List<Product> findAllProd();

	/**
	 * ����id��ѯ��Ʒ
	 * @param id
	 * @return
	 */
	Product findProdById(String id);

	/**
	 * ����id��ѯ��Ʒ �������
	 * @param product_id
	 * @return
	 */
	Product findProdById2(String product_id);

	/**
	 * �޸Ŀ������ �������
	 * @param id ��Ʒid
	 * @param nueNum �µĿ��
	 */
	void updatePnum(String id, int newNum);


}
