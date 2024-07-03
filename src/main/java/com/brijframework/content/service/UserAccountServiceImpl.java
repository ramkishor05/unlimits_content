package com.brijframework.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brijframework.content.exceptions.UserNotFoundException;
import com.brijframework.content.forgin.UserAccountRepository;
import com.brijframework.content.modal.UIUserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	private UserAccountRepository userLoginRepository;

	@Override
	public UIUserAccount loadUserByUsername(String token) throws UsernameNotFoundException {
		UIUserAccount findUserLogin = userLoginRepository.findByToken(token);
		if (findUserLogin==null) {
			throw new UserNotFoundException();
		}
		return findUserLogin;
	}

}
