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
		//1.检查用户名是否已经存在,如果存在提示
		if(dao.findUserByUName(user.getUsername())!=null){
			throw new RuntimeException("用户名已经存在!!");
		}
		//2.如果不存在则调用Dao中的方法将用户信息存入数据库
		user.setPassword(MD5Utils.md5(user.getPassword()));
		user.setRole("user");
		user.setState(0);
		user.setActivecode(UUID.randomUUID().toString());
		dao.addUser(user);
		//3.发送激活邮件
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
			msg.setSubject(user.getUsername()+",欢迎激活Estore");
			msg.setText("恭喜您注册Estore成功,点击如下链接进行激活,<a href='http://www.estore.com/ActiveServlet?activecode="+user.getActivecode()+"'>点击激活</a>");
			Transport transport = session.getTransport();
			transport.connect("estore", "123");
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void activeUser(String activcecode) {
		//1.检查:激活码是否存在
		User user = dao.findUserByActiveCode(activcecode);
		if(user == null){
			throw new RuntimeException("激活码不存在!");
		}
		//2.检查:用户是否已经激活过
		if(user.getState() != 0){
			throw new RuntimeException("不要重复激活!");
		}
		//3.检查:激活码是否超时
		if(System.currentTimeMillis() - user.getUpdatetime().getTime() > 1000 * 3600 *24){
			dao.delUser(user.getId());
			throw new RuntimeException("激活码超时,要在24小时内激活,请重新注册!");
		}
		//4.如果都没问题则激活用户
		dao.updateState(user.getId(),1);
	}

	public User findUserByUnameAndPsw(String username, String password) {
		return dao.findUserByUNameAndPsw(username,password);
	}
}
