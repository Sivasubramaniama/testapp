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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class Parent.
 * @see com.test.db.Parent
 * @author Hibernate Tools
 */
public class ParentHome {

	private static final Log log = LogFactory.getLog(ParentHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}

	private static ParentHome paDao = null;
	
	public static ParentHome getInstance(){
		if(paDao == null){
			paDao = new ParentHome();
		}
		return paDao;
	}
	
	private ParentHome(){}
	
	public void persist(Parent transientInstance) {
		log.debug("persisting Parent instance");
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

	public void attachDirty(Parent instance) {
		log.debug("attaching dirty Parent instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Parent instance) {
		log.debug("attaching clean Parent instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Parent persistentInstance) {
		log.debug("deleting Parent instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Parent merge(Parent detachedInstance) {
		log.debug("merging Parent instance");
		try {
			Parent result = (Parent) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Parent findById(java.lang.Integer id) {
		log.debug("getting Parent instance with id: " + id);
		try {
			Parent instance = (Parent) sessionFactory.getCurrentSession().get("com.test.db.Parent", id);
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

	public List findByExample(Parent instance) {
		log.debug("finding Parent instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.test.db.Parent")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public Parent getDefaultParent(){
		Parent pa = new Parent();
		pa.setBoss(UNKNOWN);
		pa.setCreatedDate(new Date());
		pa.setIsActive(false);
		pa.setParentName(UNKNOWN);
	
		return pa;
	}

	public Parent findByName(String name) {
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Criteria cr = s.createCriteria(Parent.class);
		cr.add(Restrictions.eq("parentName", name));
		List list = cr.list();
		Parent p = null;
		if(list != null && list.size()>0){
			p = (Parent) list.get(0);
		}else{
			return null;
		}
		tx.commit();
		return p;
	}
	
}
