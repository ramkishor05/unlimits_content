package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleItem;

@Repository
public interface GlobalExampleItemRepository  extends CustomRepository<EOGlobalExampleItem, Long>{

	List<EOGlobalExampleItem> findAllByExampleLibararyId(Long id);

}
