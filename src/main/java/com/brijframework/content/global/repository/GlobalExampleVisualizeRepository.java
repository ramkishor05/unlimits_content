package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleVisualize;

@Repository
@Transactional
public interface GlobalExampleVisualizeRepository  extends CustomRepository<EOGlobalExampleVisualize, Long>{

	List<EOGlobalExampleVisualize> findAllByExampleLibararyId(Long id);

	void deleteByExampleLibararyId(Long id);

}
