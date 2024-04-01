package com.brijframework.content.service;

import java.util.Optional;

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
	public UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UIUserAccount> findUserLogin = userLoginRepository.findByUsername(username);
		if (!findUserLogin.isPresent()) {
			throw new UserNotFoundException();
		}
		UIUserAccount userDetails = findUserLogin.get();
		return userDetails;
	}

}
