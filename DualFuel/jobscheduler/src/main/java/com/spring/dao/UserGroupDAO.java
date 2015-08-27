package com.spring.dao;

import com.entity.UserGroup;
import com.spring.dao.base.AbstractDaoImpl;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserGroupDAO extends AbstractDaoImpl<UserGroup, String>{

	public UserGroupDAO() {
		super(UserGroup.class);
	}
	
	public UserGroup listAllUserByGroup(String userGroup) {
		return findUniqueRecord(Restrictions.eq("userGroup", userGroup));
	}
}
