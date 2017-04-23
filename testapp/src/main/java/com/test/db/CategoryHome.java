package com.test.db;
// Generated Jan 21, 2017 2:12:33 PM by Hibernate Tools 4.3.5.Final

import static com.test.db.util.SessionFactoryHelper.UNKNOWN;
import static com.test.db.util.SessionFactoryHelper.getSessionFactory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;



/**
 * Home object for domain model class Category.
 * @see com.test.db.Category
 * @author Hibernate Tools
 */
public class CategoryHome {

	private static final Log log = LogFactory.getLog(CategoryHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}

	private static CategoryHome cDao = null;
	
	public static CategoryHome getInstance(){
		if(cDao == null){
			cDao = new CategoryHome();
		}
		return cDao;
	}
	
	private CategoryHome(){}
	
	public void persist(Category transientInstance) {
		log.debug("persisting Category instance");
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

	public void attachDirty(Category instance) {
		log.debug("attaching dirty Category instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Category instance) {
		log.debug("attaching clean Category instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Category persistentInstance) {
		log.debug("deleting Category instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Category merge(Category detachedInstance) {
		log.debug("merging Category instance");
		try {
			Category result = (Category) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Category findById(java.lang.Integer id) {
		log.debug("getting Category instance with id: " + id);
		try {
			Category instance = (Category) sessionFactory.getCurrentSession().get("com.test.db.Category", id);
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

	public List findByExample(Category instance) {
		log.debug("finding Category instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.test.db.Category")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public Category getDefaultCateogry(){
		Category c = new Category();
		c.setCategoryName(UNKNOWN);
		c.setCreatedDate(new Date());
		return c;
		
	}

	public Category findByName(String name) {
	
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Criteria cr = s.createCriteria(Category.class);
		cr.add(Restrictions.eq("categoryName", name));
		List list = cr.list();
		Category p = null;
		if(list != null && list.size()>0){
			p = (Category) list.get(0);
		}else{
			return null;
		}
		tx.commit();
		return p;	
		
	} 
	
	public List<Category> findAll() {

		String hql ="from Category";
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query query = s.createQuery(hql);
		List results = query.list();
		tx.commit();
		if(results != null && results.size()>0){
			return results;
		}else{
			return null;
		}
		
	}

	
	public static void main(String[] args){
		CategoryHome cDao = CategoryHome.getInstance();
		Category c = new Category();
		c.setCategoryName("Beverages");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Toothpaste");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Toothbrush");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Tea Coffee");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Blade");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Shaving Cream");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Shampoo");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Talcum Powder");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		

		c = new Category();
		c.setCategoryName("Milk");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Mobile Connection");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Clothes");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Mobile");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Bike");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Footwear");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Watches");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Food");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Salt");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Icecream");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Biscuits");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Ketchup Jam");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Snacks");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Water");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Tonic");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Oil");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Washing");
		c.setCreatedDate(new Date());
		cDao.persist(c);

		c = new Category();
		c.setCategoryName("Cosmetics");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Pen");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Electronics");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Tablets");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Online Shopping");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		c = new Category();
		c.setCategoryName("Car");
		c.setCreatedDate(new Date());
		cDao.persist(c);
		
		
		
	}
	
}
