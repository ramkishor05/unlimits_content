package com.brijframework.content.filters;

import static com.brijframework.content.constants.ClientConstants.APP_ID_KEY;
import static com.brijframework.content.constants.ClientConstants.BUSINESS_ID_KEY;
import static com.brijframework.content.constants.ClientConstants.CUST_APP_ID;
import static com.brijframework.content.constants.ClientConstants.USER;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.token.TokenUtil;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.repository.CustBusinessAppRepository;
import com.brijframework.content.constants.ClientConstants;
import com.brijframework.content.util.CommanUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(0)
public class TransactionFilter implements Filter {
	
	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	System.out.println("TransactionFilter start");
    	 HttpServletRequest req = (HttpServletRequest) request;
    	TransactionRequest requestWrapper = new TransactionRequest(req);
    	try {
       
        String token =req.getHeader(ClientConstants.AUTHORIZATION);
        String ownerId = TokenUtil.getUserId(token);
        String userRole = TokenUtil.getUserRole(token); 
        System.out.println("ownerId="+ownerId);
        String appId = req.getHeader(APP_ID_KEY);
        System.out.println("appId="+appId);
        String businessId = req.getHeader(BUSINESS_ID_KEY);
        System.out.println("businessId="+businessId);
        
        requestWrapper.setAttribute("USER_ROLE", userRole);
        requestWrapper.putHeader("USER_ROLE", userRole);
        if(USER.equalsIgnoreCase(userRole)) {
	        if(Objects.nonNull(ownerId)&& CommanUtil.isNumeric(ownerId) && Objects.nonNull(businessId) && CommanUtil.isNumeric(businessId) && Objects.nonNull(appId) && CommanUtil.isNumeric(appId)) {
	        	EOCustBusinessApp custBusinessApp =custBusinessAppRepository.findByCustIdAndAppIdAndBusinessId(Long.valueOf(ownerId), Long.valueOf(appId),Long.valueOf(businessId)).orElse(new EOCustBusinessApp(Long.valueOf(appId), Long.valueOf(ownerId), Long.valueOf(businessId)));
	        	EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	        	requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	    		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	        } else  if(Objects.nonNull(ownerId) && CommanUtil.isNumeric(ownerId) && Objects.nonNull(businessId)&& CommanUtil.isNumeric(businessId)) {
	         	EOCustBusinessApp custBusinessApp = custBusinessAppRepository.findByCustIdAndAppIdAndBusinessId(Long.valueOf(ownerId), Long.valueOf(1l),Long.valueOf(businessId)).orElse(new EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId), Long.valueOf(businessId)));
	         	EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	     		requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	     		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         } else  if(Objects.nonNull(ownerId)&& CommanUtil.isNumeric(ownerId)) {
	        	List<EOCustBusinessApp> custBusinessAppList = custBusinessAppRepository.findByCustIdAndAppId(Long.valueOf(ownerId), Long.valueOf(1l)).orElse(Arrays.asList(new EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId), Long.valueOf(1l))));
	        	for(EOCustBusinessApp custBusinessApp : custBusinessAppList) {
	      			EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	         		requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	      		}
	        	if(CollectionUtils.isEmpty(custBusinessAppList)) {
	        		EOCustBusinessApp custBusinessApp = new EOCustBusinessApp(Long.valueOf(1l), Long.valueOf(ownerId), Long.valueOf(1l));
	        		EOCustBusinessApp eoCustBusinessApp=custBusinessAppRepository.save(custBusinessApp);
	         		requestWrapper.putHeader(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	         		req.setAttribute(CUST_APP_ID, ""+eoCustBusinessApp.getId());
	        	}
	         }
        }
    	}catch (Exception e) {
			e.printStackTrace();
		}
        chain.doFilter(requestWrapper, response);
        System.out.println("TransactionFilter end");
    }
}