package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	private static ThreadLocal<Connection> conn_local = new ThreadLocal<Connection>(){
		@Override
		protected Connection initialValue() {
			return JDBCUtils.getConn();
		}
	};
	
	private TransactionManager() {
	}

	public static void startTran(){
		try {
			conn_local.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static Connection getConnection(){
		return conn_local.get();
		
	}
	public static void commit(){
		try {
			conn_local.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void rollback(){
		try {
			conn_local.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static void release(){
		try {
			conn_local.get().close();
			conn_local.remove();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
