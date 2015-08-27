package com.spring.dao.base;

import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<E, I extends Serializable> {

    E findById(I id);
    void save(E e);
    void saveOrUpdate(E e);
    void delete(E e);
    List<E> listAllByPage(int firstResult, int maxResult);
    List<E> listAll();
    List<E> findByCriteria(Criterion criterion);
	E findUniqueRecord(Criterion criterion);
}
