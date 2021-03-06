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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;


/**
 * Home object for domain model class Item.
 * @see com.test.db.Item
 * @author Hibernate Tools
 */
public class ItemHome {

	private static final Log log = LogFactory.getLog(ItemHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

//	protected SessionFactory getSessionFactory() {
//		try {
//			return (SessionFactory) new InitialContext().lookup("SessionFactory");
//		} catch (Exception e) {
//			log.error("Could not locate SessionFactory in JNDI", e);
//			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
//		}
//	}

	private static ItemHome iDao = null;
	
	public static ItemHome getInstance(){
		if(iDao == null){
			iDao = new ItemHome();
		}
		return iDao;
	}
	
	private ItemHome(){}
	
	public void persist(Item transientInstance) {
		log.debug("persisting Item instance");
		Session s = null;
		try {
			s = sessionFactory.getCurrentSession();
			Transaction tx = s.beginTransaction();
			s.persist(transientInstance);
			log.debug("persist successful");
			tx.commit();
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		} finally{
			if(s.isOpen()){
				s.close();	
			}
		}
	}

	public void attachDirty(Item instance) {
		log.debug("attaching dirty Item instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Item instance) {
		log.debug("attaching clean Item instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Item persistentInstance) {
		log.debug("deleting Item instance");
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

	public Item merge(Item detachedInstance) {
		log.debug("merging Item instance");
		try {
			Session s = sessionFactory.getCurrentSession();
			Transaction tx = s.beginTransaction();
			Item result = (Item) s.merge(detachedInstance);
			log.debug("merge successful");
			tx.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Item findById(java.lang.Integer id) {
		log.debug("getting Item instance with id: " + id);
		try {
			Item instance = (Item) sessionFactory.getCurrentSession().get("com.test.db.Item", id);
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

	public List findByExample(Item instance) {
		log.debug("finding Item instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.test.db.Item")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public Item getDefaultItem(){
		Item i = new Item();
		i.setCreatedDate(new Date());
		i.setIsActive(false);
		i.setItemName(UNKNOWN);
		return i;
	}

	public Item findItemByName(String item) {
		
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Criteria cr = s.createCriteria(Item.class);
		cr.add(Restrictions.eq("itemName", item));
		List list = cr.list();
		Item p = null;
		if(list != null && list.size()>0){
			p = (Item) list.get(0);
		}else{
			return null;
		}
		tx.commit();
		return p;
	}
	
	public List<Item> getItemWithoutProduct(Product unknown){
		//sql : select item_id from Item a inner join product b on a.product_id = b.p_id and b.product_name='Unknown'
		//String sql = "select item_id from Item a inner join product b on a.product_id = b.p_id and b.product_name='Unknown'";
		//String hql = "from Item a inner join product b on a.product_id = b.p_id and b.product_name=:unknown";
		
		String hql ="from Item i where i.product.PId=:pid";
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		Query query = s.createQuery(hql);
		query.setParameter("pid", unknown.getPId());
		query.setFirstResult(0);
		query.setMaxResults(10);
		List results = query.list();
		tx.commit();
		if(results != null && results.size()>0){
			return results;
		}else{
			return null;
		}
		
	}

	public List<Item> findAll() {

		String hql ="from Item";
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

	//select * from Item i inner join product p on i.product_id = p.p_id inner join parent pa on pa.pa_id = p.parent_id and pa.parent_name = 'Unknown';

	public List getItemByParentName(String unknown){
		
		String sql ="select c.category_name, a.country, i.item_name, pa.parent_name, p.product_name, pa.boss from item i inner join product p on i.product_id = p.p_id inner join parent pa on pa.pa_id = p.parent_id and pa.parent_name = :parentName inner join address a on a.a_id = pa.address_id inner join category c on c.c_id = p.category_id";
		Session s = sessionFactory.getCurrentSession();
		Transaction tx = s.beginTransaction();
		SQLQuery query = s.createSQLQuery(sql);
		//Query query = s.createQuery(sql);
		
		query.setParameter("parentName", unknown);
		
		query.setFirstResult(0);
		query.setMaxResults(10);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List results = query.list();
		tx.commit();
		if(results != null && results.size()>0){
			return results;
		}else{
			return null;
		}
		
	}
}