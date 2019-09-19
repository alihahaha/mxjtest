package com.itheima.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.utils.JDBCUtils;

public class InitServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	public void init() throws ServletException {
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				try {
					String sql = "delete from users where state = 0 and TIMESTAMPDIFF(HOUR,updatetime,now())>24";
					QueryRunner runner = new QueryRunner(JDBCUtils.getSource());
					runner.update(sql);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}, 0, 1000*3600*24);
	}

}
