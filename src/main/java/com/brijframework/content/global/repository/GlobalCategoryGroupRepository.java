package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalCategoryGroup;

@Repository
@Transactional
public interface GlobalCategoryGroupRepository extends JpaRepository<EOGlobalCategoryGroup, Long>{
	
	EOGlobalCategoryGroup findOneByTypeId(String typeId);

	@Query(nativeQuery = true, value = "select * from EOGLOBAL_CATEGORY_GROUP where RECORD_STATUS in (?1)")
	List<EOGlobalCategoryGroup> getCategoryGroupListByStatus(List<String> statusIds);

	@Query(nativeQuery = true, value = "select * from EOGLOBAL_CATEGORY_GROUP where TYPE_ID = ?1")
	List<EOGlobalCategoryGroup> findAllByTypeId(String typeId);
	
	int countByTypeId(String typeId);

	@Query(nativeQuery = true, value = "select * from EOGLOBAL_CATEGORY_GROUP where TYPE_ID = ?1")
	Optional<EOGlobalCategoryGroup> findByTypeId(String typeId);
}
