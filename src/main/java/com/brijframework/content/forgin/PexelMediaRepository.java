package com.brijframework.content.forgin;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.brijframework.content.forgin.model.FileContent;

@FeignClient(name= "UNLIMITS-INTEGRATION" , url = "http://localhost:4444")
public interface PexelMediaRepository {

	@GetMapping(value = "/api/pexel/media/files/{fileId}")
	public List<FileContent> getAllFiles(@PathVariable String fileId);
}
