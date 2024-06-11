package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalCategoryImage;

@Repository
@Transactional
public interface GlobalCategoryImageRepository extends JpaRepository<EOGlobalCategoryImage, Long>{
	
	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_CATEGORY_IMAGE GCI INNER JOIN EOGLOBAL_CATEGORY_ITEM GCS ON GCS.ID=  GCI.SUB_CATEGORY_ID WHERE GCI.RECORD_STATUS IN (?) ORDER BY GCS.NAME, GCI.NAME ")
	List<EOGlobalCategoryImage> findAllByStatus(List<String> status);

	Optional<EOGlobalCategoryImage> findByName(String name);

	Optional<EOGlobalCategoryImage> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_CATEGORY_IMAGE GCI INNER JOIN EOGLOBAL_CATEGORY_ITEM GCS ON GCS.ID=  GCI.SUB_CATEGORY_ID WHERE GCI.SUB_CATEGORY_ID IN (?) ORDER BY GCS.NAME, GCI.NAME ")
	List<EOGlobalCategoryImage>  findAllByGroupId(Long categoryId);
}
