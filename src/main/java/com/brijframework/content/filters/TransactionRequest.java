package com.brijframework.content.filters;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
 
final class TransactionRequest extends HttpServletRequestWrapper {
	
    private final Map<String, String> customHeaders;
 
    public TransactionRequest(HttpServletRequest request){
        super(request);
        this.customHeaders = new HashMap<String, String>();
    }
    
    public void putHeader(String name, String value){
        this.customHeaders.put(name, value);
    }
 
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        
        if (headerValue != null){
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }
 
    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet<String>(this.customHeaders.keySet());
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }
        return Collections.enumeration(set);
    }
    
    public Map<String, String> getCustomHeaders() {
		return customHeaders;
	}
    
    @Override
    public Enumeration<String> getHeaders(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null){
            return Collections.enumeration(Arrays.asList(headerValue));
        }
    	return super.getHeaders(name);
    }
}