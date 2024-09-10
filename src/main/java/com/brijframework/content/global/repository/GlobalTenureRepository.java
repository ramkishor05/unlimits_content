package com.brijframework.content.global.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalTenure;

@Repository
@Transactional
public interface GlobalTenureRepository extends CustomRepository<EOGlobalTenure, Long>{

	Optional<EOGlobalTenure> findByIdenNo(String idenNo);

	Optional<EOGlobalTenure> findOneByYear(Integer key);

}
