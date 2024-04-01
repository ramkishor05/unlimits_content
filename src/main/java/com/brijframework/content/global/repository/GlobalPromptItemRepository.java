package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalPromptItem;

@Repository
@Transactional
public interface GlobalPromptItemRepository extends JpaRepository<EOGlobalPromptItem, Long>{
	
	List<EOGlobalPromptItem> findOneByTypeId(String typeId);

	int countByTypeId(String typeId);
}
