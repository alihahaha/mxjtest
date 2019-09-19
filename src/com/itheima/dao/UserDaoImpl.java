package com.itheima.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.domain.User;
import com.itheima.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	public void addUser(User user) {
		String sql = "insert into users values (null,?,?,?,?,?,?,?,null)";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			runner.update(sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User findUserByUName(String username) {
		String sql = "select * from users where username = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void delUser(int id) {
		String sql = "delete from users where id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			runner.update(sql,id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User findUserByActiveCode(String activcecode) {
		String sql = "select * from users where activecode = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),activcecode);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void updateState(int id, int newStat) {
		String sql = "update users set state = ? where id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			runner.update(sql,newStat,id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User findUserByUNameAndPsw(String username, String password) {
		String sql = "select * from users where username = ? and password = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),username,password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User findUserById(int user_id) {
		String sql = "select * from users where id = ?";
		try {
			QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
