package com.brijframework.content.forgin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.brijframework.content.modal.UIUserAccount;

@FeignClient(name= "UNLIMITS-AUTH" , url = "http://host.docker.internal:2222")
public interface UserAccountRepository {

	@GetMapping(value = "/api/auth/userdetail")
	public UIUserAccount findByToken(@RequestHeader String token) ;;
}
