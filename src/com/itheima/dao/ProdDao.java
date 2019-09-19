package com.itheima.dao;

import java.sql.Connection;
import java.util.List;

import com.itheima.domain.Product;

public interface ProdDao {

	/**
	 * 添加商品到数据库
	 * @param prod
	 */
	void addProd(Product prod);

	/**
	 * 查询所有商品信息
	 * @return
	 */
	List<Product> findAllProd();

	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	Product findProdById(String id);

	/**
	 * 根据id查询商品 事务控制
	 * @param product_id
	 * @return
	 */
	Product findProdById2(String product_id);

	/**
	 * 修改库存数量 事务控制
	 * @param id 商品id
	 * @param nueNum 新的库存
	 */
	void updatePnum(String id, int newNum);


}
