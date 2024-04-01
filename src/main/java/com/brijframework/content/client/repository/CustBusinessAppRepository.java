package com.brijframework.content.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.client.entites.EOCustBusinessApp;

@Repository
@Transactional
public interface CustBusinessAppRepository  extends JpaRepository<EOCustBusinessApp, Long>{

	Optional<List<EOCustBusinessApp>> findByCustIdAndAppId(long custId, long appId);

	Optional<EOCustBusinessApp> findByCustIdAndAppIdAndBusinessId(long custId, long appId, long businessId);

}
