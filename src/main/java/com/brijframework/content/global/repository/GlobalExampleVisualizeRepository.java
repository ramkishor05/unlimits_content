package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleVisualize;

@Repository
public interface GlobalExampleVisualizeRepository  extends CustomRepository<EOGlobalExampleVisualize, Long>{

	List<EOGlobalExampleVisualize> findAllByExampleLibararyId(Long id);

}
