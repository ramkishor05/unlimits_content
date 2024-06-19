package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalMainCategory;

@Repository
@Transactional
public interface GlobalMainCategoryRepository extends JpaRepository<EOGlobalMainCategory, Long>{
	
	@Query(nativeQuery = true, value = "select * from EOGLOBAL_MAIN_CATEGORY where RECORD_STATUS in (?1)")
	List<EOGlobalMainCategory> getCategoryGroupListByStatus(List<String> statusIds);

	Optional<EOGlobalMainCategory> findByName(String name);

	Optional<EOGlobalMainCategory> findByIdenNo(String idenNo);
}
