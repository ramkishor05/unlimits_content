package com.brijframework.content.forgin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.brijframework.content.modal.UIUserAccount;

@Component
public class UserAccountRepository {

	 public Optional<UIUserAccount> findByUsername(String username){
		UIUserAccount uiUserAccount=new UIUserAccount("Admin","$2a$12$MIbZKMMedyp1qD53ISvGV.XQZkEn/0z4p.HboxPYdDrah.dQCpWZ2", getAuthority("ADMIN"));
		return Optional.ofNullable(uiUserAccount);
	}
	
	private Set<SimpleGrantedAuthority> getAuthority(String roleId) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(roleId));
		return authorities;
	}

	Optional<UIUserAccount> findById(Long id) {
		return null;
	}

}
