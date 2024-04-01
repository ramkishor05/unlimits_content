package com.brijframework.content.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.brijframework.content.modal.UIUserAccount;

public interface UserAccountService extends UserDetailsService {

	@Override
	UIUserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

}
