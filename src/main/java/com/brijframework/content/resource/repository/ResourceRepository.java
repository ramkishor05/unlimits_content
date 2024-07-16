package com.brijframework.content.resource.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.resource.entities.EOResource;

@Repository
@Transactional
public interface ResourceRepository extends CustomRepository<EOResource, Long>{
	
}
