package edu.poly.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.poly.entity.Comment;
import edu.poly.entity.Favorite;
import edu.poly.utils.JpaUtils;

public class CommentDAO extends EntertaimentDAO<Comment, String>{

	private EntityManager em = JpaUtils.getEntityManager();
	@Override
	protected void finalize() throws Throwable{
		em.close();
		super.finalize();
	}

	@Override
	public Comment create(Comment entity) {
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
	public Comment update(Comment entity) {
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
	public Comment remove(String key) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			Comment entity = this.findById(key);
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
	public Comment findById(String key) {
		// TODO Auto-generated method stub
		return em.find(Comment.class, key);
	}

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		String jpql = "Select o from Comment o";
		TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
		List<Comment> list = query.getResultList();
		return list;
	}
	
	public List<Comment> findByVideo(String id) {
		String jpql = "Select o from Comment o where o.video.id =: id";
		TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Comment> findByDate(Date date) {
		String jpql = "Select o from Comment o where o.cmtDate =: date";
		TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
		query.setParameter("date", date);
		return query.getResultList();
	}
	
	public List<Long> findHighestByDate(Date date) {
		String jpql = "Select count(o) from Comment o where o.cmtDate =: date group by o.video.id order by count(o) desc";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("date", date);
		query.setMaxResults(1);
		return query.getResultList();
	}

}
