package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalTagGroup;

@Repository
@Transactional
public interface GlobalTagGroupRepository extends JpaRepository<EOGlobalTagGroup, Long>{
	
	List<EOGlobalTagGroup> findOneByTypeId(String typeId);

	int countByTypeId(String typeId);

	Optional<EOGlobalTagGroup> findByTypeId(String typeId);
}
