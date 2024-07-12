package com.brijframework.content.filters;


import static com.brijframework.content.constants.Constants.AUTHORIZATION;
import static com.brijframework.content.constants.Constants.CLIENT_TOKEN;
import static com.brijframework.content.constants.Constants.CLIENT_USER_ID;
import static com.brijframework.content.constants.Constants.CLIENT_USER_NAME;
import static com.brijframework.content.constants.Constants.CLIENT_USER_ROLE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.unlimits.rest.context.ApiTokenContext;

import com.brijframework.content.exceptions.InvalidTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(0)
public class TransactionFilter extends OncePerRequestFilter {
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
    	System.out.println("TransactionFilter start");
        HttpServletRequest req = (HttpServletRequest) request;
        TransactionRequest requestWrapper = new TransactionRequest(req);
        requestWrapper.putHeader("Access-Control-Allow-Origin", "*");
		requestWrapper.putHeader("Access-Control-Allow-Headers", "Content-Type");
		requestWrapper.putHeader("Accept", "*");
        String authHeader = req.getHeader(AUTHORIZATION);
		if (StringUtils.isNotEmpty(authHeader)) {
			ApiTokenContext.getContext().setCurrentToken(authHeader);
			String token = authHeader.substring(7);
			if (!ApiTokenContext.validateToken(token)) {
				throw new InvalidTokenException("Invalid token !!");
			}
			String username = ApiTokenContext.getUsername(token);
			String userId = ApiTokenContext.getUserId(token);
			String userRole = ApiTokenContext.getUserRole(token);
			requestWrapper.setAttribute(CLIENT_USER_ID, userId);
			requestWrapper.putHeader(CLIENT_USER_ID, userId);
			requestWrapper.setAttribute(CLIENT_USER_ROLE, userRole);
			requestWrapper.putHeader(CLIENT_USER_ROLE, userRole);
			requestWrapper.putHeader(CLIENT_TOKEN, token);
			requestWrapper.putHeader(CLIENT_USER_NAME, username);
			requestWrapper.putHeader(AUTHORIZATION, authHeader);
			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
						getGrantedAuthority(userRole));
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
        filterChain.doFilter(requestWrapper, response);
        System.out.println("TransactionFilter end");
    }
    

    private List<GrantedAuthority> getGrantedAuthority(String authority) {
		return Arrays.asList(new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return authority;
			}
		});
	}
}