package com.brijframework.content.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.resource.entities.EOResource;

@Repository
@Transactional
public interface ResourceRepository extends JpaRepository<EOResource, Long>{
	
}
