package com.itheima.dao;

import com.itheima.domain.User;

public interface UserDao {

	/**
	 * 根据用户名查找用户
	 * @param username 要查找的用户名
	 * @return 查找到的用户,如果没找到返回null
	 */
	User findUserByUName(String username);

	/**
	 * 添加用户到数据库
	 * @param user 封装了用户信息的bean
	 */
	void addUser(User user);

	/**
	 * 根据激活码查找用户
	 * @param activcecode 激活码
	 * @return 找到的用户,如果没找到返回null
	 */
	User findUserByActiveCode(String activcecode);

	/**
	 * 根据id删除用户
	 * @param id 要删除的客户的id
	 */
	void delUser(int id);

	/**
	 * 修改用户状态
	 * @param id 要修改的客户id
	 * @param newStat 要修改为的新状态
	 */
	void updateState(int id, int newStat);

	/**
	 * 根据用户名密码查找用户
	 * @param username 用户名
	 * @param password 密码
	 * @return 找到的用户,找不到返回null
	 */
	User findUserByUNameAndPsw(String username, String password);

	/**
	 * 根据id查询用户
	 * @param user_id 用户id
	 * @return
	 */
	User findUserById(int user_id);

}
