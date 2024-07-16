package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalSubCategory;
import com.brijframework.content.global.entities.EOGlobalTagLibarary;

@Repository
@Transactional
public interface GlobalTagLibararyRepository extends CustomRepository<EOGlobalTagLibarary, Long>{

	Optional<EOGlobalTagLibarary> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GTL.* from EOGLOBAL_TAG_LIBARARY GTL WHERE GTL.SUB_CATEGORY_ID IN (?1) ORDER BY GTL.NAME ")
	List<EOGlobalTagLibarary> findAllBSubCategoryId(Long subCategoryId);

	@Query(nativeQuery = true, value="Select GTL.* from EOGLOBAL_TAG_LIBARARY GTL WHERE GTL.SUB_CATEGORY_ID IN (?1) and GTL.NAME LIKE (%?2%) ORDER BY GTL.NAME ")
	List<EOGlobalTagLibarary> search(Long subCategoryId, String name);

	EOGlobalTagLibarary findBySubCategoryAndName(EOGlobalSubCategory globalCategoryItem, String name);
}
