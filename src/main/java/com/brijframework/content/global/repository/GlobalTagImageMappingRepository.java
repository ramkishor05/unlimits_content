package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalTagImageMapping;

@Repository
@Transactional
public interface GlobalTagImageMappingRepository extends CustomRepository<EOGlobalTagImageMapping, Long>{

	@Query(nativeQuery = true, value = "SELECT * FROM EOGLOBAL_TAGE_IMAGE_MAPPING WHERE TAG_LIBARARY_ID=?1")
	List<EOGlobalTagImageMapping> findAllByTagLibararyId(Long tagLibararyId, List<String> status);

	void deleteAllByTagLibararyId(Long id);
	
	void deleteAllByImageLibararyId(Long id);
	
	@Query(nativeQuery = true, value = "SELECT * FROM EOGLOBAL_TAGE_IMAGE_MAPPING WHERE IMAGE_LIBARARY_ID=?1")
	List<EOGlobalTagImageMapping> findAllByImageLibararyId(Long id , List<String> status);

	@Query(nativeQuery = true, value = "SELECT * FROM EOGLOBAL_TAGE_IMAGE_MAPPING WHERE IMAGE_LIBARARY_ID=?1 and TAG_LIBARARY_ID=?2")
	Optional<EOGlobalTagImageMapping> findOneByImageLibararyIdAndTagLibararyId(Long imageLibararyId, Long tagLibararyId);

}
