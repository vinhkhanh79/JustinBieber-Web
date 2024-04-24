package edu.poly.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.poly.entity.Comment;
import edu.poly.entity.Share;
import edu.poly.utils.JpaUtils;

public class ShareDAO extends EntertaimentDAO<Share, String>{
	
	private EntityManager em = JpaUtils.getEntityManager();
	@Override
	protected void finalize() throws Throwable{
		em.close();
		super.finalize();
	}

	@Override
	public Share create(Share entity) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

	@Override
	public Share update(Share entity) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

	@Override
	public Share remove(String key) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			Share entity = this.findById(key);
			em.remove(entity);
			em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

	@Override
	public Share findById(String key) {
		// TODO Auto-generated method stub
		return em.find(Share.class, key);
	}

	@Override
	public List<Share> findAll() {
		// TODO Auto-generated method stub
		String jpql = "Select o from Share o";
		TypedQuery<Share> query = em.createQuery(jpql, Share.class);
		List<Share> list = query.getResultList();
		return list;
	}
	
	public List<Share> findByDate(Date date) {
		String jpql = "Select o from Share o where o.shareDate =: date";
		TypedQuery<Share> query = em.createQuery(jpql, Share.class);
		query.setParameter("date", date);
		return query.getResultList();
	}
	
	public List<Long> findHighestByDate(Date date) {
		String jpql = "Select count(o) from Share o where o.shareDate =: date group by o.video.id order by count(o) desc";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("date", date);
		query.setMaxResults(1);
		return query.getResultList();
	}
}
