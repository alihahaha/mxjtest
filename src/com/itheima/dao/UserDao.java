package com.itheima.dao;

import com.itheima.domain.User;

public interface UserDao {

	/**
	 * �����û��������û�
	 * @param username Ҫ���ҵ��û���
	 * @return ���ҵ����û�,���û�ҵ�����null
	 */
	User findUserByUName(String username);

	/**
	 * ����û������ݿ�
	 * @param user ��װ���û���Ϣ��bean
	 */
	void addUser(User user);

	/**
	 * ���ݼ���������û�
	 * @param activcecode ������
	 * @return �ҵ����û�,���û�ҵ�����null
	 */
	User findUserByActiveCode(String activcecode);

	/**
	 * ����idɾ���û�
	 * @param id Ҫɾ���Ŀͻ���id
	 */
	void delUser(int id);

	/**
	 * �޸��û�״̬
	 * @param id Ҫ�޸ĵĿͻ�id
	 * @param newStat Ҫ�޸�Ϊ����״̬
	 */
	void updateState(int id, int newStat);

	/**
	 * �����û�����������û�
	 * @param username �û���
	 * @param password ����
	 * @return �ҵ����û�,�Ҳ�������null
	 */
	User findUserByUNameAndPsw(String username, String password);

	/**
	 * ����id��ѯ�û�
	 * @param user_id �û�id
	 * @return
	 */
	User findUserById(int user_id);

}
