package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalSubCategory;

@Repository
@Transactional
public interface GlobalSubCategoryRepository extends CustomRepository<EOGlobalSubCategory, Long>{
	
	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_SUB_CATEGORY GCI INNER JOIN EOGLOBAL_MAIN_CATEGORY GCG ON GCG.ID=  GCI.MAIN_CATEGORY_ID WHERE GCI.RECORD_STATUS IN (?1) ORDER BY GCG.NAME, GCI.NAME ")
	List<EOGlobalSubCategory> findAllByStatus(List<String> statusIds);

	Optional<EOGlobalSubCategory> findByName(String name);

	Optional<EOGlobalSubCategory> findByIdenNo(String idenNo);
	
	Optional<EOGlobalSubCategory> findByMainCategoryIdAndName(Long mainCategoryId, String name);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_SUB_CATEGORY GCI INNER JOIN EOGLOBAL_MAIN_CATEGORY GCG ON GCG.ID=  GCI.MAIN_CATEGORY_ID WHERE GCI.MAIN_CATEGORY_ID IN (?1) and GCI.RECORD_STATUS IN (?2) ORDER BY GCG.NAME, GCI.NAME ")
	List<EOGlobalSubCategory>  findAllByMainCategoryId(Long mainCategoryId, List<String> status);
	
	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_SUB_CATEGORY GCI WHERE UPPER(GCI.NAME) IN (?1) and GCI.RECORD_STATUS IN (?2) ORDER BY GCI.NAME ")
	List<EOGlobalSubCategory> findAllBySubCategoryNameIgnoreCaseIn(List<String> subCategoryNameList, List<String> statusIds);

	long countByRecordStateIn(List<String> statusIds);
}
