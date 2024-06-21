package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalTagLibarary;

@Repository
@Transactional
public interface GlobalTagLibararyRepository extends JpaRepository<EOGlobalTagLibarary, Long>{

	Optional<EOGlobalTagLibarary> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GTL.* from EOGLOBAL_TAG_LIBARARY GTL WHERE GTL.SUB_CATEGORY_ID IN (?) ORDER BY GTL.NAME ")
	List<EOGlobalTagLibarary> findAllBSubCategoryId(Long subCategoryId);
}
