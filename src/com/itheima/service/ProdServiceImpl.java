package com.itheima.service;

import java.util.List;

import com.itheima.dao.ProdDao;
import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;

public class ProdServiceImpl implements ProdService {
	private ProdDao dao = BasicFactory.getFactory().getInstance(ProdDao.class);
	public void addProd(Product prod) {
		dao.addProd(prod);
	}
	public List<Product> findAllProd() {
		return dao.findAllProd();
	}
	public Product findProdById(String id) {
		return dao.findProdById(id);
	}

}
