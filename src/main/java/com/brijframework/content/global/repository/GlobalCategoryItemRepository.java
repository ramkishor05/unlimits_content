package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalCategoryItem;

@Repository
@Transactional
public interface GlobalCategoryItemRepository extends JpaRepository<EOGlobalCategoryItem, Long>{
	
	List<EOGlobalCategoryItem> findOneByTypeId(String typeId);

	int countByTypeId(String typeId);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_CATEGORY_ITEM GCI INNER JOIN EOGLOBAL_CATEGORY_GROUP GCG ON GCG.ID=  GCI.GROUP_ID WHERE GCI.RECORD_STATUS IN (?) ORDER BY GCG.NAME, GCI.NAME ")
	List<EOGlobalCategoryItem> findAllByStatus(List<String> status);

	Optional<EOGlobalCategoryItem> findByName(String name);

	Optional<EOGlobalCategoryItem> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_CATEGORY_ITEM GCI INNER JOIN EOGLOBAL_CATEGORY_GROUP GCG ON GCG.ID=  GCI.GROUP_ID WHERE GCI.GROUP_ID IN (?) ORDER BY GCG.NAME, GCI.NAME ")
	List<EOGlobalCategoryItem>  findAllByGroupId(Long categoryId);
}
