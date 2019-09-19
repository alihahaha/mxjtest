package com.itheima.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.domain.Product;
import com.itheima.utils.JDBCUtils;
import com.itheima.utils.TransactionManager;

public class ProdDaoImpl implements ProdDao {

	public void addProd(Product prod) {
		String sql = "insert into products values (?,?,?,?,?,?,?)";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			runner.update(sql,prod.getId(),prod.getName(),prod.getPrice(),prod.getCategory(),prod.getPnum(),prod.getImgurl(),prod.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Product> findAllProd() {
		String sql = "select * from products";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Product findProdById(String id) {
		String sql = "select * from products where id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanHandler<Product>(Product.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public Product findProdById2(String product_id) {
		String sql = "select * from products where id = ? for update";
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(TransactionManager.getConnection(),sql, new BeanHandler<Product>(Product.class),product_id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void updatePnum(String id, int newNum) {
		String sql = "update products set pnum = ? where id = ?";
		try {
			QueryRunner runner = new QueryRunner();
			runner.update(TransactionManager.getConnection(),sql,newNum,id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}

}
