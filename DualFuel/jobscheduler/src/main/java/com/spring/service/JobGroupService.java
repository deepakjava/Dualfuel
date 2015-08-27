package com.spring.service;

import com.entity.UserGroup;
import com.spring.dao.UserGroupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jobGroupService")
@Transactional(readOnly = true)
public class JobGroupService {

	@Autowired
    private UserGroupDAO userGroupDAO;
	
	
	@Transactional(readOnly = false)
	public UserGroup getCurrentUser(String userGroup){
		return userGroupDAO.listAllUserByGroup(userGroup);
	}
}
