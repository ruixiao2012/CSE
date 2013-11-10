package com.ncepu.service;

import com.ncepu.dao.UserDao;
import com.ncepu.model.User;

public class AccountService {

	/**
	 * 登陆
	 * 
	 * @return
	 */
	public boolean login(String userName, String password) {
		UserDao userDao = new UserDao();
		User user = userDao.queryOne("from User u where u.userName=?",
				new String[] { userName });
		if (user == null)
			return false;
		else {
			if (user.getPassword().equals(password))
				return true;
		}
		return false;
	}
}
