package com.brijframework.content.forgin.repository;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.forgin.model.FileContent;

@FeignClient(name= "UNLIMITS-INTEGRATION" , url = "http://${server.gateway.host}:${server.gateway.port}/integration")
public interface PexelMediaRepository {

	@GetMapping(value = "/api/pexel/media/files/{fileId}")
	public List<FileContent> getAllFiles(@PathVariable String fileId);
	
	@GetMapping(value = "/api/pexel/media/files/page/data/{pageNumber}/count/{count}/search")
	public PageDetail<FileContent> getPageDetail(@RequestParam String name, @PathVariable int pageNumber,
			@PathVariable int count);
}
