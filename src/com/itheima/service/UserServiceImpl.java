package com.itheima.service;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.utils.MD5Utils;

public class UserServiceImpl implements UserService {
	UserDao dao = BasicFactory.getFactory().getInstance(UserDao.class);
	public void registUser(User user) {
		//1.����û����Ƿ��Ѿ�����,���������ʾ
		if(dao.findUserByUName(user.getUsername())!=null){
			throw new RuntimeException("�û����Ѿ�����!!");
		}
		//2.��������������Dao�еķ������û���Ϣ�������ݿ�
		user.setPassword(MD5Utils.md5(user.getPassword()));
		user.setRole("user");
		user.setState(0);
		user.setActivecode(UUID.randomUUID().toString());
		dao.addUser(user);
		//3.���ͼ����ʼ�
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host", "localhost");
			//prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.debug", "true");
			Session session  = Session.getInstance(prop);
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("estore@itheima.com"));
			msg.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
			msg.setSubject(user.getUsername()+",��ӭ����Estore");
			msg.setText("��ϲ��ע��Estore�ɹ�,����������ӽ��м���,<a href='http://www.estore.com/ActiveServlet?activecode="+user.getActivecode()+"'>�������</a>");
			Transport transport = session.getTransport();
			transport.connect("estore", "123");
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void activeUser(String activcecode) {
		//1.���:�������Ƿ����
		User user = dao.findUserByActiveCode(activcecode);
		if(user == null){
			throw new RuntimeException("�����벻����!");
		}
		//2.���:�û��Ƿ��Ѿ������
		if(user.getState() != 0){
			throw new RuntimeException("��Ҫ�ظ�����!");
		}
		//3.���:�������Ƿ�ʱ
		if(System.currentTimeMillis() - user.getUpdatetime().getTime() > 1000 * 3600 *24){
			dao.delUser(user.getId());
			throw new RuntimeException("�����볬ʱ,Ҫ��24Сʱ�ڼ���,������ע��!");
		}
		//4.�����û�����򼤻��û�
		dao.updateState(user.getId(),1);
	}

	public User findUserByUnameAndPsw(String username, String password) {
		return dao.findUserByUNameAndPsw(username,password);
	}
}
