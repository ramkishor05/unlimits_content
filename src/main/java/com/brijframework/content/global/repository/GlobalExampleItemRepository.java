package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleItem;

@Repository
@Transactional
public interface GlobalExampleItemRepository  extends CustomRepository<EOGlobalExampleItem, Long>{

	List<EOGlobalExampleItem> findAllByExampleLibararyId(Long id);

	@Modifying
	@Query(nativeQuery = true, value = "delete from EOGLOBAL_EXAMPLE_ITEM where EXAMPLE_LIBARARY_ID=?1")
	void deleteByExampleLibararyId(Long id);

	Optional<EOGlobalExampleItem> findByExampleLibararyIdAndImageLibararyId(Long exampleLibararyId, Long imageLibararyId);
	
	Optional<EOGlobalExampleItem> findByExampleLibararyIdAndTagLibararyId(Long exampleLibararyId, Long tagLibararyId);


}
