package com.spring.service;

import com.entity.User;
import com.spring.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("loginService")
@Transactional(readOnly = true)
public class LoginService {
	
	@Autowired
    private UserDAO userDAO;
	
	
	@Transactional(readOnly = true)
	public User getCurrentUser(String userId){
		return userDAO.findUserByEmailAddress(userId);
	}
	
	@Transactional(readOnly = false)
	public void saveOrUpdateUser(User user){
		userDAO.saveOrUpdate(user);
	}

}
