package com.test.db.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


public class SessionFactoryHelper {
	public static final String UNKNOWN = "Unknown";
	private static SessionFactory sessionFactory = null; 	
	
	public static SessionFactory getSessionFactory(){
		if(sessionFactory == null){
				sessionFactory = new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();
			return sessionFactory;
		}
		return sessionFactory;
	}
}
