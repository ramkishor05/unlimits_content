package com.brijframework.content.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalPrompt;

@Repository
@Transactional
public interface GlobalPromptItemRepository extends JpaRepository<EOGlobalPrompt, Long>{
	
}
