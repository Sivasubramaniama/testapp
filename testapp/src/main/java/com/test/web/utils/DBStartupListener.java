package com.test.web.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.test.db.Address;
import com.test.db.AddressHome;
import com.test.db.Barcode;
import com.test.db.BarcodeHome;
import com.test.db.Category;
import com.test.db.CategoryHome;
import com.test.db.ItemHome;
import com.test.db.Parent;
import com.test.db.ParentHome;
import com.test.db.Product;
import com.test.db.ProductHome;
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

	ProductHome pdao = ProductHome.getInstance();
	ItemHome iDao = ItemHome.getInstance();
	BarcodeHome bDao = BarcodeHome.getInstance();
	ParentHome paDao = ParentHome.getInstance();
	CategoryHome cDao = CategoryHome.getInstance();
    AddressHome aDao = AddressHome.getInstance();
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	try{
			SessionFactoryHelper.getSessionFactory();
    	
			String unknown = "Unknown";
			
			Barcode bdefa = bDao.findByName(unknown);
			if( bdefa== null){
				bDao.persist(bDao.getDefaultsBarcode());
			}
			
			Category cdefa = cDao.findByName(unknown);
			if(cdefa == null){
				cDao.persist(cDao.getDefaultCateogry());
			}
			
			Address adefa = aDao.findByCountry(unknown);
			if(adefa == null){
				aDao.persist(aDao.getDefaultAddress());
			}
			
			
			Parent padefa = paDao.findByName(unknown);
			if(padefa == null){
				Parent pa = paDao.getDefaultParent();
				pa.setAddress(aDao.findByCountry(unknown));
				paDao.persist(pa);
			}
			
			Product pdefa = pdao.findByName(unknown);
			if(pdefa == null){
				Product p = pdao.getDefaultProduct();
				p.setCategory(cDao.findByName(unknown));
				p.setParent(paDao.findByName(unknown));
				pdao.persist(p);
			}
    	
    	}catch(Exception e){
			e.printStackTrace();
		}
    }
	
}
