package com.brijframework.content.forgin.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.brijframework.content.constants.Constants;
import com.brijframework.content.modal.UIUserAccount;

@FeignClient(name= "UNLIMITS-AUTH" , url = "http://${server.eureka.host}:${server.eureka.port}/auth")
public interface UserAccountRepository {

	@GetMapping(value = "/api/device/authentication")
	public UIUserAccount findByToken(@RequestHeader(Constants.AUTHORIZATION) String token) ;;
}
