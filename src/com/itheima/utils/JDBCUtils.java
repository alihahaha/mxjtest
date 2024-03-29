package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static DataSource source = new ComboPooledDataSource();
	private JDBCUtils() {
	}
	
	public static DataSource getSource(){
		return source;
	}
	
	public static Connection getConn() {
		try {
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
