package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalImageLibarary;

@Repository
@Transactional
public interface GlobalImageLibararyRepository extends JpaRepository<EOGlobalImageLibarary, Long>{
	
	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_IMAGE_LIBARARY GCI INNER JOIN EOGLOBAL_CATEGORY_ITEM GCS ON GCS.ID=  GCI.SUB_CATEGORY_ID WHERE GCI.RECORD_STATUS IN (?) ORDER BY GCS.NAME, GCI.NAME ")
	List<EOGlobalImageLibarary> findAllByStatus(List<String> status);

	Optional<EOGlobalImageLibarary> findByName(String name);

	Optional<EOGlobalImageLibarary> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_IMAGE_LIBARARY GCI INNER JOIN EOGLOBAL_CATEGORY_ITEM GCS ON GCS.ID=  GCI.SUB_CATEGORY_ID WHERE GCI.SUB_CATEGORY_ID IN (?) ORDER BY GCS.NAME, GCI.NAME ")
	List<EOGlobalImageLibarary>  findAllByGroupId(Long categoryId);

	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_IMAGE_LIBARARY GCI WHERE GCI.SUB_CATEGORY_ID=?1 AND GCI.TAG_LIBARARY_ID=?2 ORDER BY GCI.NAME")
	List<EOGlobalImageLibarary> filter(Long subCategoryId, Long tagLibararyId);
	
	@Query(nativeQuery = true, value="Select GCI.* from EOGLOBAL_IMAGE_LIBARARY GCI WHERE GCI.SUB_CATEGORY_ID=?1 AND GCI.TAG_LIBARARY_ID=?2 and GCI.NAME LIKE (%?3%) ORDER BY GCI.NAME")
	List<EOGlobalImageLibarary> filter(Long subCategoryId, Long tagLibararyId, String name);
}
