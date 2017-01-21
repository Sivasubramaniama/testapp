package com.test.db;
// Generated Jan 21, 2017 2:12:33 PM by Hibernate Tools 4.3.5.Final

import static com.test.db.util.SessionFactoryHelper.UNKNOWN;
import static com.test.db.util.SessionFactoryHelper.getSessionFactory;

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
 * Home object for domain model class Barcode.
 * @see com.test.db.Barcode
 * @author Hibernate Tools
 */
public class BarcodeHome {

	private static final Log log = LogFactory.getLog(BarcodeHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}
	
	private static BarcodeHome bDao = null;
	
	public static BarcodeHome getInstance(){
		if(bDao == null){
			bDao = new BarcodeHome();
		}
		return bDao;
	}
	
	private BarcodeHome(){}

	public void persist(Barcode transientInstance) {
		log.debug("persisting Barcode instance");
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

	public void attachDirty(Barcode instance) {
		log.debug("attaching dirty Barcode instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Barcode instance) {
		log.debug("attaching clean Barcode instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Barcode persistentInstance) {
		log.debug("deleting Barcode instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Barcode merge(Barcode detachedInstance) {
		log.debug("merging Barcode instance");
		try {
			Barcode result = (Barcode) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Barcode findById(java.lang.Integer id) {
		log.debug("getting Barcode instance with id: " + id);
		try {
			Barcode instance = (Barcode) sessionFactory.getCurrentSession().get("com.test.db.Barcode", id);
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

	public List findByExample(Barcode instance) {
		log.debug("finding Barcode instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.test.db.Barcode")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public Barcode getDefaultsBarcode(){
		Barcode b = new Barcode();
		b.setIdentifier(UNKNOWN);
		b.setIsActive(false);
		b.setTypeName(UNKNOWN);
		return b;
	}

	public Barcode findByName(String name) {
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Criteria cr = s.createCriteria(Barcode.class);
		cr.add(Restrictions.eq("identifier", name));
		List list = cr.list();
		Barcode p = null;
		if(list != null && list.size()>0){
			p = (Barcode) list.get(0);
		}else{
			return null;
		}
		tx.commit();
		return p;
	}
	
}
