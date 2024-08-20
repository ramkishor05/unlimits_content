package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalTagImageMapping;

@Repository
@Transactional
public interface GlobalTagImageMappingRepository extends CustomRepository<EOGlobalTagImageMapping, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM EOGLOBAL_TAGE_IMAGE_MAPPING WHERE TAG_LIBARARY_ID=?1")
	List<EOGlobalTagImageMapping> findAllByTagLibararyId(Long id);

	void deleteAllByTagLibararyId(Long id);
	
	void deleteAllByImageLibararyId(Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM EOGLOBAL_TAGE_IMAGE_MAPPING WHERE IMAGE_LIBARARY_ID=?1")
	List<EOGlobalTagImageMapping> findAllByImageLibararyId(Long id);

}
