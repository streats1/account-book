package com.hipo.account_book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hipo.account_book.repository.UserDao;
import com.hipo.account_book.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public boolean join( UserVo userVo ) {
		boolean result = userDao.insert( userVo );
		return result;
	}
}