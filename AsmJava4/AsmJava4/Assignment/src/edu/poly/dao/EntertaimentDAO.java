package edu.poly.dao;

import java.util.List;

public abstract class EntertaimentDAO <E, K>{
	abstract public E create(E entity);
	abstract public E update(E entity);
	abstract public E remove(K key);
	abstract public E findById(K key);
	abstract public List<E> findAll();
}
