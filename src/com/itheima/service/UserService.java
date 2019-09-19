package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
	/**
	 * 注册用户
	 * @param user 封装了用户信息的Bean
	 */
	void registUser(User user);

	/**
	 * 根据激活码激活用户
	 * @param activcecode 激活码
	 */
	void activeUser(String activcecode);

	/**
	 * 根据用户名 密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByUnameAndPsw(String username, String password);

}
