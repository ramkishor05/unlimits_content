package com.brijframework.content.global.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalTenure;

@Repository
@Transactional
public interface GlobalTenureRepository extends JpaRepository<EOGlobalTenure, Long>{

	Optional<EOGlobalTenure> findByIdenNo(String idenNo);

}
