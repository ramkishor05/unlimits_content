package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;

@Repository
@Transactional
public interface GlobalMindSetItemRepository extends CustomRepository<EOGlobalMindSetItem, Long>{

	void deleteByMindSetLibararyId(Long id);

	void deleteByMindSetLibararyIdAndIdNotIn(Long id, List<Long> ids);

}
