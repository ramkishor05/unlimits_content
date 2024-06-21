package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalSubCategory;

@Repository
@Transactional
public interface GlobalSubCategoryRepository extends JpaRepository<EOGlobalSubCategory, Long>{
	
	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_SUB_CATEGORY GCI INNER JOIN EOGLOBAL_MAIN_CATEGORY GCG ON GCG.ID=  GCI.MAIN_CATEGORY_ID WHERE GCI.RECORD_STATUS IN (?) ORDER BY GCG.NAME, GCI.NAME ")
	List<EOGlobalSubCategory> findAllByStatus(List<String> status);

	Optional<EOGlobalSubCategory> findByName(String name);

	Optional<EOGlobalSubCategory> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_SUB_CATEGORY GCI INNER JOIN EOGLOBAL_MAIN_CATEGORY GCG ON GCG.ID=  GCI.MAIN_CATEGORY_ID WHERE GCI.MAIN_CATEGORY_ID IN (?) ORDER BY GCG.NAME, GCI.NAME ")
	List<EOGlobalSubCategory>  findAllByMainCategoryId(Long mainCategoryId);
}
