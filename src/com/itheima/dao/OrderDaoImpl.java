package com.itheima.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.SaleInfo;
import com.itheima.utils.JDBCUtils;
import com.itheima.utils.TransactionManager;

public class OrderDaoImpl implements OrderDao {

	public void addOrder(Order order) {
		String sql = "insert into orders values (?,?,?,?,null,?)";
		try {
			QueryRunner runner = new QueryRunner();
			runner.update(TransactionManager.getConnection(),sql,order.getId(),order.getMoney(),order.getReceiverinfo(),order.getPaystate(),order.getUser_id());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void addOrderItem(OrderItem item) {
		String sql = "insert into orderitem values (?,?,?)";
		try {
			QueryRunner runner = new QueryRunner();
			runner.update(TransactionManager.getConnection(),sql,item.getOrder_id(),item.getProduct_id(),item.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<OrderItem> findOrderItemsByOrderId(String id) {
		String sql = "select * from orderitem where order_id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Order> findOrdersByUserId(int user_id) {
		String sql = "select * from orders where user_id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanListHandler<Order>(Order.class),user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void delOrderById(String id) {
		String sql = "delete from Orders where id = ?";
		try {
			QueryRunner runner = new QueryRunner();
			runner.update(TransactionManager.getConnection(),sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public void delOrderItemByOrderId(String id) {
		String sql = "delete from orderitem where order_id = ?";
		try {
			QueryRunner runner = new QueryRunner();
			runner.update(TransactionManager.getConnection(),sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public List<OrderItem> findOrderItemsByOrderId2(String id) {
		String sql = "select * from orderitem where order_id = ?";
		try {
			QueryRunner runner = new QueryRunner();
			return runner.query(TransactionManager.getConnection(),sql, new BeanListHandler<OrderItem>(OrderItem.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Order findOrderById(String id) {
		String sql = "select * from orders where id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql,new BeanHandler<Order>(Order.class),id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void updatePayState(String id, int payState) {
		String sql = "update orders set paystate = ? where id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			runner.update(sql,payState,id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<SaleInfo> saleInfo() {
		String sql = 
				"select products.id prod_id,products.name prod_name,sum(orderitem.buynum) sale_num from orders,products,orderitem " +
				" where orders.id = orderitem.order_id and products.id=orderitem.product_id " +
				" and orders.paystate = 1" +
				" group by products.id " +
				" order by sale_num desc";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
