package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brijframework.content.global.entities.EOGlobalMindSetItem;

@Repository
public interface GlobalMindSetItemRepository extends JpaRepository<EOGlobalMindSetItem, Long>{

	List<EOGlobalMindSetItem> findOneByTypeId(String typeId);

}
