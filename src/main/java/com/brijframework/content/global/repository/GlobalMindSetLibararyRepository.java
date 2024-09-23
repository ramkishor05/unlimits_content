package com.brijframework.content.global.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalMindSetLibarary;

@Repository
@Transactional
public interface GlobalMindSetLibararyRepository extends CustomRepository<EOGlobalMindSetLibarary, Long>{

}
