package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalTagItem;

@Repository
@Transactional
public interface GlobalTagItemRepository extends JpaRepository<EOGlobalTagItem, Long>{
	
	List<EOGlobalTagItem> findOneByTypeId(String typeId);

	int countByTypeId(String typeId);
}
