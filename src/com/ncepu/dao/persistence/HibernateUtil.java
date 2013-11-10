package com.ncepu.dao.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory;// 数据存储源
	private static ServiceRegistry serviceRegistry;
	static {
		try {
			Configuration configuration = new Configuration().configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.err.println("===================error===================");
			e.printStackTrace();
			System.err.println("===================error===================");
		}
	}

	/**
	 * @return 获取会话对象
	 */
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();  
	public static Session getSession() throws HibernateException {   
	Session s = (Session) session.get();  
		if(s==null){  
			s = sessionFactory.openSession();  
			session.set(s);  
		}   
		return s;  
	}   
	public static void closeSession() throws HibernateException {   
			Session s = (Session) session.get();  
			session.set(null);  
			if (s != null) s.close();    
		}
}
