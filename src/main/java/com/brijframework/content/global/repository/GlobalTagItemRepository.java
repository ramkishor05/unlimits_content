package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalTagItem;

@Repository
@Transactional
public interface GlobalTagItemRepository extends JpaRepository<EOGlobalTagItem, Long>{

	Optional<EOGlobalTagItem> findByIdenNo(String idenNo);

	@Query(nativeQuery = true, value="Select GTI.* from EOGLOBAL_TAG_ITEM GTI INNER JOIN EOGLOBAL_TAG_GROUP GTG ON GTG.ID=  GTI.GROUP_ID WHERE GTI.GROUP_ID IN (?) ORDER BY GTG.NAME, GTI.NAME ")
	List<EOGlobalTagItem> findAllByGroupId(Long tagId);
}
