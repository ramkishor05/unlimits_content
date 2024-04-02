package com.brijframework.content.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.client.entites.EOCustTagGroup;

@Repository
@Transactional
public interface CustTagGroupRepository extends JpaRepository<EOCustTagGroup, Long>{
	
	EOCustTagGroup findOneByTypeId(String typeId);
	
	@Query(nativeQuery = true, value = "select * from EOCUST_TAG_GROUP where CUST_BUSINESS_APP_ID= ?1 and NAME=?2")
	Optional<EOCustTagGroup> findByCustAppAndName(Long id, String name);

	@Query(nativeQuery = true, value = "select * from EOCUST_TAG_GROUP where CUST_BUSINESS_APP_ID= ?1 and TYPE_ID=?2")
	List<EOCustTagGroup> findAllByType(Long custAppId, String typeId);

	@Query(nativeQuery = true, value = "select * from EOCUST_TAG_GROUP where CUST_BUSINESS_APP_ID= ?1")
	List<EOCustTagGroup>  findAllByCustAppId(long custAppId);

}
