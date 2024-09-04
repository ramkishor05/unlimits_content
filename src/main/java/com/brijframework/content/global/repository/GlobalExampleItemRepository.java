package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleItem;

@Repository
@Transactional
public interface GlobalExampleItemRepository  extends CustomRepository<EOGlobalExampleItem, Long>{

	List<EOGlobalExampleItem> findAllByExampleLibararyId(Long id);

	void deleteByExampleLibararyId(Long id);

}
