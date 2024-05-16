package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalMediaItem;

@Repository
@Transactional
public interface GlobalMediaItemRepository extends JpaRepository<EOGlobalMediaItem, Long>{
	
	List<EOGlobalMediaItem> findOneByTypeId(String typeId);

	int countByTypeId(String typeId);

	@Query(nativeQuery = true, value="Select GMI.* from EOGLOBAL_MEDIA_ITEM GMI WHERE GMI.NAME LIKE (%?1%) ORDER BY GMI.NAME")
	List<EOGlobalMediaItem> filter(String name);
}
