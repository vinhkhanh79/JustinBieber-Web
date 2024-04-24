package edu.poly.dao;

import java.util.List;

import edu.poly.entity.Users;
import edu.poly.utils.JpaUtils;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserDAO extends EntertaimentDAO<Users, String>{
	private EntityManager em = JpaUtils.getEntityManager();
	@Override
	protected void finalize() throws Throwable{
		em.close();
		super.finalize();
	}
	
	@Override
	public Users create(Users entity) {
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
	public Users update(Users entity) {
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
	public Users remove(String id) {
		try {
			em.getTransaction().begin();
			Users entity = this.findById(id);
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
	public Users findById(String id) {
		return em.find(Users.class, id);
	}
	
	public List<Users> findByRole(boolean role) {
		String jpql = "Select o from Users o where o.admin=:role";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		query.setParameter("role", role);
		List<Users> list = query.getResultList();
		return list;
	}
	
	public List<Users> findByKeyWord(String keyword) {
		String jpql = "Select o from Users o where o.fullname like ?0";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		query.setParameter(0, keyword);
		List<Users> list = query.getResultList();
		return list;
	}
	
	public Users findOne(String username, String password) {
		String jpql = "Select o from Users o where o.id=:id and o.password=:pass";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		query.setParameter("id", username);
		query.setParameter("pass", password);
		return query.getSingleResult();
	}
	
	public Users findByEmail(String email) {
		String jpql = "Select o from Users o where o.email=:email";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
	
	public List<Users> findPage(int page, int size) {
		String jpql = "Select o from Users o";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		query.setFirstResult(page*size);	
		query.setMaxResults(size);
		List<Users> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<Users> findAll(){
		String jpql = "Select o from Users o";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		List<Users> list = query.getResultList();
		return list;
	}
}
