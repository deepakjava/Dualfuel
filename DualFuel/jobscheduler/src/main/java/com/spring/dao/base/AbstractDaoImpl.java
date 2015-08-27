package com.spring.dao.base;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements
		AbstractDao<E, I> {

	private Class<E> entityClass;

	protected AbstractDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Autowired
	protected SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public E findById(I id) {
		return (E) getCurrentSession().get(entityClass, id);
	}

	public void save(E e) {
		getCurrentSession().save(e);
		getCurrentSession().flush();
	}

	public void saveOrUpdate(E e) {
		getCurrentSession().saveOrUpdate(e);
		getCurrentSession().flush();
	}

	public void delete(E e) {
		getCurrentSession().delete(e);
	}

	public List<E> findByCriteria(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.add(criterion);
		return criteria.list();
	}

	public E findUniqueRecord(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.add(criterion);
		return (E) criteria.uniqueResult();
	}

	public List<E> listAll() {

		String strQry = "from " + entityClass.getName()
				+ " c order by c.id desc";
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				strQry);
		return query.list();
	}

	public List<E> listAllByPage(int firstResult, int maxResult) {

		String strQry = "from " + entityClass.getName()
				+ " c order by c.id desc";
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				strQry);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.list();
	}

}
