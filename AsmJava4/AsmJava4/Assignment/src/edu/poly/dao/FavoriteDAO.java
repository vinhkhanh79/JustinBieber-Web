package edu.poly.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.poly.entity.Favorite;
import edu.poly.entity.Video;
import edu.poly.utils.JpaUtils;

public class FavoriteDAO extends EntertaimentDAO<Favorite, String>{
	private EntityManager em = JpaUtils.getEntityManager();
	@Override
	protected void finalize() throws Throwable{
		em.close();
		super.finalize();
	}

	@Override
	public Favorite create(Favorite entity) {
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
	public Favorite update(Favorite entity) {
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
	public Favorite remove(String key) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			Favorite entity = this.findById(key);
			em.remove(entity);
			em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

	public void removeByVideo(String vdId, String usId) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			em.remove(this.findByVideoUser(vdId, usId));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
	}

	@Override
	public Favorite findById(String key) {
		// TODO Auto-generated method stub
		return em.find(Favorite.class, key);
	}

	@Override
	public List<Favorite> findAll() {
		// TODO Auto-generated method stub
		String jpql = "Select o from Favorite o";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		List<Favorite> list = query.getResultList();
		return list;
	}
	
	public List<Favorite> findByUser(String id) {
		String jpql = "Select o from Favorite o where o.user.id =:id";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("id", id);
		List<Favorite> list = query.getResultList();
		return list;
	}
	
	public List<Favorite> findByDate(Date date) {
		String jpql = "Select o from Favorite o where o.likeDate =: date";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("date", date);
		return query.getResultList();
	}
	
	public List<Long> findHighestByDate(Date date) {
		String jpql = "Select count(o) from Favorite o where o.likeDate =: date group by o.video.id order by count(o) desc";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("date", date);
		query.setMaxResults(1);
		return query.getResultList();
	}
	
	public Favorite findByVideoUser(String idVd, String idUs) {
		String jpql = "Select o from Favorite o where o.user.id =:id and o.video.id =:idvd";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("id", idUs);
		query.setParameter("idvd", idVd);
		return query.getSingleResult();
	}

}
