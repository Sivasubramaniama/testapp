package com.test.db;
// Generated Dec 1, 2016 3:21:18 PM by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.test.db.util.SessionFactoryHelper;

/**
 * Home object for domain model class Person.
 * @see com.metlife.db.Person
 * @author Hibernate Tools
 */
public class PersonHome {

	private static final Log log = LogFactory.getLog(PersonHome.class);

	private final SessionFactory sessionFactory = SessionFactoryHelper.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Person transientInstance) {
		log.debug("persisting Person instance");
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
	
	public void update(Person transientInstance) {
		log.debug("persisting Person instance");
		try {
			Session s = sessionFactory.getCurrentSession();
			Transaction tx = s.beginTransaction();
			s.saveOrUpdate(transientInstance);
			log.debug("persist successful");
			tx.commit();
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	

	public void attachDirty(Person instance) {
		log.debug("attaching dirty Person instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Person instance) {
		log.debug("attaching clean Person instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Person persistentInstance) {
		log.debug("deleting Person instance");
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

	public Person merge(Person detachedInstance) {
		log.debug("merging Person instance");
		try {
			Person result = (Person) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Person findById(int id) {
		log.debug("getting Person instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			Transaction tx = s.beginTransaction();
			Person instance = (Person) s.get("com.metlife.db.Person", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Person instance) {
		log.debug("finding Person instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.metlife.db.Person")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Person findByName(String name) {
		Person p = null;
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Criteria cr = s.createCriteria(Person.class);
		Criterion catname = Restrictions.eq("name", name);
		cr.add(catname);
		List list = cr.list();
		if(list != null && list.size() >0){
			p = (Person) list.get(0);
		}else{
			throw new IllegalStateException("No record found for name = "+ name);
		}
		return p;
	}

	public List<Person> findAll() {
		String hql = "from Person p";
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query query = s.createQuery(hql);
		List results = query.list();
		tx.commit();
		return results;
	}
	

	
}
