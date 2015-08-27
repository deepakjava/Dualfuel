package com.spring.dao;

import com.entity.User;
import com.spring.dao.base.AbstractDaoImpl;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAO extends AbstractDaoImpl<User, String> {
	
	public UserDAO() {
		super(User.class);
	}

	public User findUserByEmailAddress(String userId) {
		return findUniqueRecord(Restrictions.eq("userId", userId));
	}
}
