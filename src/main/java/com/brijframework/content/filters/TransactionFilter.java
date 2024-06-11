package com.brijframework.content.filters;

import static com.brijframework.content.constants.ClientConstants.APP_ID_KEY;
import static com.brijframework.content.constants.ClientConstants.BUSINESS_ID_KEY;
import static com.brijframework.content.constants.ClientConstants.CUST_APP_ID;
import static com.brijframework.content.constants.ClientConstants.USER;

import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.unlimits.rest.token.TokenUtil;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.util.CommanUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class TransactionFilter extends OncePerRequestFilter  {
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;
    
    private List<GrantedAuthority> getGrantedAuthority(String authority) {
		return Arrays.asList(new GrantedAuthority() {

			@Serial
            private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return authority;
			}
		});
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("TransactionFilter start");
        HttpServletRequest req = (HttpServletRequest) request;
        
        String token = "Bearer eyJyb2xlIjoiREVWRUxPUEVSIiwidXNlcklkIjoyLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEZXYiLCJpYXQiOjE3MTY5NzcyNTUsImV4cCI6MTcxNjk3OTA1NX0.uNOytX0euEinOkcbredcCFIjM3yGTbxQ3GOG0_h_WcY"; //req.getHeader(ClientConstants.AUTHORIZATION);
		/*
		 * if(!TokenUtil.isTokenExpired(token)) { String ownerId =
		 * TokenUtil.getUserId(token); //req.getHeader(OWNER_ID_KEY); String userRole =
		 * TokenUtil.getUserRole(token); String appId = req.getHeader(APP_ID_KEY);
		 * String businessId = req.getHeader(BUSINESS_ID_KEY);
		 * requestWrapper.setAttribute("USER_ROLE", userRole);
		 * requestWrapper.putHeader("USER_ROLE", userRole);
		 * if(USER.equalsIgnoreCase(userRole)) { if(Objects.nonNull(ownerId)&&
		 * CommanUtil.isNumeric(ownerId) && Objects.nonNull(businessId) &&
		 * CommanUtil.isNumeric(businessId) && Objects.nonNull(appId) &&
		 * CommanUtil.isNumeric(appId)) { EOCustBusinessApp custBusinessApp
		 * =custBusinessAppRepository.findByCustIdAndAppIdAndBusinessId(Long.valueOf(
		 * ownerId), Long.valueOf(appId),Long.valueOf(businessId)).orElse(new
		 * EOCustBusinessApp(Long.valueOf(appId), Long.valueOf(ownerId),
		 * Long.valueOf(businessId))); EOCustBusinessApp
		 * eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
		 * requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
		 * req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId()); } else
		 * if(Objects.nonNull(ownerId) && CommanUtil.isNumeric(ownerId) &&
		 * Objects.nonNull(businessId)&& CommanUtil.isNumeric(businessId)) {
		 * EOCustBusinessApp custBusinessApp =
		 * custBusinessAppRepository.findByCustIdAndAppIdAndBusinessId(Long.valueOf(
		 * ownerId), Long.valueOf(1l),Long.valueOf(businessId)).orElse(new
		 * EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId),
		 * Long.valueOf(businessId))); EOCustBusinessApp
		 * eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
		 * requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
		 * req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId()); } else
		 * if(Objects.nonNull(ownerId)&& CommanUtil.isNumeric(ownerId)) {
		 * List<EOCustBusinessApp> custBusinessAppList =
		 * custBusinessAppRepository.findByCustIdAndAppId(Long.valueOf(ownerId),
		 * Long.valueOf(1l)).orElse(Arrays.asList(new
		 * EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId),
		 * Long.valueOf(1l)))); for(EOCustBusinessApp custBusinessApp :
		 * custBusinessAppList) { EOCustBusinessApp
		 * eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
		 * requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
		 * req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId()); }
		 * if(CollectionUtils.isEmpty(custBusinessAppList)) { EOCustBusinessApp
		 * custBusinessApp = new EOCustBusinessApp(Long.valueOf(1l),
		 * Long.valueOf(ownerId), Long.valueOf(1l)); EOCustBusinessApp
		 * eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
		 * requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
		 * req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId()); } } }
		 * UsernamePasswordAuthenticationToken authToken = new
		 * UsernamePasswordAuthenticationToken(token, null,
		 * getGrantedAuthority(userRole)); authToken.setDetails(new
		 * WebAuthenticationDetailsSource().buildDetails(requestWrapper));
		 * SecurityContextHolder.getContext().setAuthentication(authToken); }
		 */
        filterChain.doFilter(requestWrapper, response);
        System.out.println("TransactionFilter end");
		
	}
}