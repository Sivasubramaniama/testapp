package com.test.db;
// Generated Jan 21, 2017 2:12:33 PM by Hibernate Tools 4.3.5.Final


import static com.test.db.util.SessionFactoryHelper.UNKNOWN;
import static com.test.db.util.SessionFactoryHelper.getSessionFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.test.db.util.SessionFactoryHelper;

/**
 * Home object for domain model class Product.
 * @see com.test.db.Product
 * @author Hibernate Tools
 */
public class ProductHome {

	private static final Log log = LogFactory.getLog(ProductHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}

	private static ProductHome pDao = null;
	public static ProductHome getInstance(){
		if(pDao == null){
			pDao = new ProductHome();
		}
		return pDao;
	}
	
	private ProductHome(){}

	public void persist(Product transientInstance) {
		log.debug("persisting Product instance");
		try {
			Session s = sessionFactory.getCurrentSession();
			Transaction tx = s.beginTransaction();
			s.persist(transientInstance);
			log.debug("persist successful");
			tx.commit();
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Product instance) {
		log.debug("attaching dirty Product instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Product instance) {
		log.debug("attaching clean Product instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Product persistentInstance) {
		log.debug("deleting Product instance");
		try {
			Session s = sessionFactory.getCurrentSession();
			Transaction tx = s.beginTransaction();
			s.delete(persistentInstance);
			log.debug("delete successful");
			tx.commit();
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Product merge(Product detachedInstance) {
		log.debug("merging Product instance");
		try {
			Product result = (Product) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Product findById(java.lang.Integer id) {
		log.debug("getting Product instance with id: " + id);
		try {
			Product instance = (Product) sessionFactory.getCurrentSession().get("com.test.db.Product", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Product instance) {
		log.debug("finding Product instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.test.db.Product")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Product findByName(String name) {

		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Criteria cr = s.createCriteria(Product.class);
		cr.add(Restrictions.eq("productName", name));
		List list = cr.list();
		Product p = null;
		if(list != null && list.size()>0){
			p = (Product) list.get(0);
		}else{
			return null;
		}
		tx.commit();
		return p;
	}
	
	public Product getDefaultProduct(){
		Product p = new Product();
		p.setCreatedDate(new Date());
		p.setIsActive(false);
		p.setProductName(UNKNOWN);
		return p;
	}
	
	public List findAlternateProduct(Product where){
		
		String sql ="select p.p_id ,p.product_name,c.category_name from product p inner join category c on p.category_id=c.c_id inner join (select p.p_id ,c.category_name, c.c_id  from product p inner join category c on p.category_id=c.c_id where Product_name =:pName) a on a.c_id =c.c_id and a.p_id <> p.p_id";
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		SQLQuery query = s.createSQLQuery(sql);
		query.setParameter("pName", where.getProductName());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		tx.commit();
		if(results != null && results.size()>0){
			return results;
		}else{
			return null;
		}
		
	}
	
public List findAlternateCountryProduct(Product where, Address address){
		
String sql ="select p.p_id ,p.product_name,c.category_name from Product p inner join Category c on p.category_id=c.c_id inner join parent Pa on pa.pa_id=p.parent_id inner join address ad on ad.a_id=pa.address_id" +
" inner join (select p.p_id ,c.category_name, c.c_id  from Product p" +
" inner join Category c on p.category_id=c.c_id inner join parent Pa on pa.pa_id=p.parent_id"+ 
" inner join address ad on ad.a_id=pa.address_id where Product_name =:pName) a on a.c_id =c.c_id and a.p_id <> p.p_id"+
" where ad.country=:country";
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		SQLQuery query = s.createSQLQuery(sql);
		query.setParameter("pName", where.getProductName());
		query.setParameter("country", address.getCountry());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List results = query.list();
		tx.commit();
		if(results != null && results.size()>0){
			return results;
		}else{
			return null;
		}
		
	}

	
	public static void main(String... strings){
		ProductHome pdao = ProductHome.getInstance();
		Product pepsi = pdao.findByName("PepsiCo");
		List l = pdao.findAlternateProduct(pepsi);
		Iterator itr = l.iterator();
		System.out.println("p_id" + "\t"+"product Name" + "\t"+ "category Name");
        
		while (itr.hasNext()) {
			Object object = (Object) itr.next();
			//System.out.println(object.toString());
			Map row = (Map)object;
			System.out.print(row.get("p_id")); 
            System.out.print("\t" + row.get("product_name")); 
            System.out.println("\t" + row.get("category_name")); 
			
		}
	}
	
}
