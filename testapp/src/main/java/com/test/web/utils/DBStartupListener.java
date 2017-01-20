package com.test.web.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.test.db.util.SessionFactoryHelper;

/**
 * Application Lifecycle Listener implementation class DBStartupListener
 *
 */
public class DBStartupListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DBStartupListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	try{
			SessionFactoryHelper.getSessionFactory();
    	}catch(Exception e){
			e.printStackTrace();
		}
    }
	
}
