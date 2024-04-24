package edu.poly.dao;

import java.util.ArrayList;
import java.util.List;

import edu.poly.entity.Video;
import edu.poly.utils.JpaUtils;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class VideoDAO extends EntertaimentDAO<Video, String>{
	private EntityManager em = JpaUtils.getEntityManager();
	@Override
	protected void finalize() throws Throwable{
		em.close();
		super.finalize();
	}
	
	@Override
	public Video create(Video entity) {
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
	public Video update(Video entity) {
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
	public Video remove(String id) {
		try {
			em.getTransaction().begin();
			Video entity = this.findById(id);
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
	public Video findById(String id) {
		return em.find(Video.class, id);
	}
	
	public List<String> findCategory() {
		String jpql = "Select o.category from Video o where o.active = true group by o.category";
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		List<String> list = query.getResultList();
		for(int i=0;i<list.size();i++) {
			list.set(i, list.get(i).substring(0, 1).toUpperCase()+ list.get(i).substring(1));
		}
		return list;
	}
	
	public List<Video> findByCategory(String cate) {
		String jpql = "Select o from Video o where o.category =:cate and o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("cate", cate);
		List<Video> list = query.getResultList();
		return list;
	}
	
	public Video findByPoster(String pos) {
		String jpql = "Select o from Video o where o.poster =:pos and o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("pos", pos);
		return query.getSingleResult();
	}
	
	public List<Video> findByTitleGenres(String search) {
		String jpql = "Select o from Video o where o.category like :search or o.title like :search and o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("search", "%"+search+"%");
		List<Video> list = query.getResultList();
		return list;
	}
	
	public List<Video> findByCategoryShowIndex(String cate) {
		String jpql = "Select o from Video o where o.category =:cate and o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("cate", cate);
		List<Video> list = query.getResultList();
		List<Video> newList = new ArrayList<>();
		if(list.size() > 10) {
			for(int i=0;i<10;i++) {
				newList.add(list.get(i));
			}
			return newList;
		}
		return list;
	}
	
	public List<Video> findPage(int page, int size) {
		String jpql = "Select o from Video o where o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setFirstResult(page*size);	
		query.setMaxResults(size);
		List<Video> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<Video> findAll(){
		String jpql = "Select o from Video o where o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		List<Video> list = query.getResultList();
		return list;
	}
	
	public List<Video> findAllAdmin(){
		String jpql = "Select o from Video o";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		List<Video> list = query.getResultList();
		return list;
	}
}
