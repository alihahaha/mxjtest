package com.itheima.service;

import java.util.List;

import com.itheima.domain.Product;

public interface ProdService {

	/**
	 * �����Ʒ
	 * @param prod ��װ����Ʒ��Ϣ��javabean
	 */
	void addProd(Product prod);

	/**
	 * ��ѯ������Ʒ
	 * @return ������Ʒ������ɵļ���
	 */
	List<Product> findAllProd();

	/**
	 * ����id��ѯ��Ʒ
	 * @param id
	 * @return
	 */
	Product findProdById(String id);

}
