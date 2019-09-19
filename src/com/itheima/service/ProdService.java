package com.itheima.service;

import java.util.List;

import com.itheima.domain.Product;

public interface ProdService {

	/**
	 * 添加商品
	 * @param prod 封装了商品信息的javabean
	 */
	void addProd(Product prod);

	/**
	 * 查询所有商品
	 * @return 所有商品对象组成的集合
	 */
	List<Product> findAllProd();

	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	Product findProdById(String id);

}
