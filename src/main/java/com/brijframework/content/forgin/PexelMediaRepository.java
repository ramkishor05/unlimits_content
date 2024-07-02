package com.brijframework.content.forgin;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.brijframework.content.forgin.model.FileContent;

@Component
public class PexelMediaRepository {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<FileContent> getAllFiles(@PathVariable String fileId){
		ResponseEntity<List<FileContent>> rateResponse =
		        restTemplate.exchange("http://51.79.159.7:8080/integration/api/pexel/media/files/"+fileId,
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<FileContent>>() {
		            });
		return rateResponse.getBody();
	}
}
