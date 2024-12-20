package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalAffirmationLibarary;

@Repository
@Transactional
public interface GlobalAffirmationLibararyRepository extends CustomRepository<EOGlobalAffirmationLibarary, Long>{

	long countByRecordStateIn(List<String> statusIds);


}
