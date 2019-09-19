package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
	/**
	 * ע���û�
	 * @param user ��װ���û���Ϣ��Bean
	 */
	void registUser(User user);

	/**
	 * ���ݼ����뼤���û�
	 * @param activcecode ������
	 */
	void activeUser(String activcecode);

	/**
	 * �����û��� ��������û�
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByUnameAndPsw(String username, String password);

}
