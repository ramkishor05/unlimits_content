package com.brijframework.content.global.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;

@Repository
public interface GlobalExampleLibararyRepository  extends CustomRepository<EOGlobalExampleLibarary, Long>{

	Optional<EOGlobalExampleLibarary> findByIdenNo(String idenNo);

}
