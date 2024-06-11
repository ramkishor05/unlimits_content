package com.brijframework.content.global.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalPrompt;

@Repository
@Transactional
public interface GlobalPromptRepository extends JpaRepository<EOGlobalPrompt, Long>{

	Optional<EOGlobalPrompt> findByIdenNo(String idenNo);
	
}
