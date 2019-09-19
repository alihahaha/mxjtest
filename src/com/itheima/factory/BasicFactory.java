package com.itheima.factory;

import java.io.FileInputStream;
import java.util.Properties;

public class BasicFactory {

	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = null;
	private BasicFactory() {
	}
	static{
		try {
			prop = new Properties();
			prop.load(new FileInputStream(BasicFactory.class.getClassLoader().getResource("config.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static BasicFactory getFactory(){
		return factory;
	}
	
	public <T> T getInstance(Class<T> intfClz){
		try {
			String implClzStr = prop.getProperty(intfClz.getSimpleName());
			Class impClz = Class.forName(implClzStr);
			return  (T) impClz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
