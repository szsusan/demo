package com.quick.demo.repository;

import com.quick.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserHibernateRepository implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public User getByUserNo(String userNo) {
		Session session = currentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> from = criteriaQuery.from(User.class);
		criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("userId"), userNo));
		Query<User> query = session.createQuery(criteriaQuery);
		return query.uniqueResult();
	}

	@Override
	@Transactional
	public User saveOrUpdate(User user) {
		currentSession().saveOrUpdate(user);
		return user;
	}

	@Override
	@Transactional
	public void deleteByUserNo(String userNo) {
		String hql = "delete from User where userNo=:userId";
		currentSession().createQuery(hql).setParameter("userId", userNo).executeUpdate();
	}

	@Override
	public User get(Long id) {
		return currentSession().get(User.class, id);
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
}
