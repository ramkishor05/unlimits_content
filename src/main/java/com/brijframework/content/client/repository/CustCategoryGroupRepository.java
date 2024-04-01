package com.brijframework.content.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.client.entites.EOCustCategoryGroup;

@Repository
@Transactional
public interface CustCategoryGroupRepository extends JpaRepository<EOCustCategoryGroup, Long>{
	
	EOCustCategoryGroup findOneByTypeId(String typeId);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_GROUP where CUST_BUSINESS_APP_ID= ?1 and NAME=?2")
	Optional<EOCustCategoryGroup> findByCustAppAndName(Long id, String name);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_GROUP where CUST_BUSINESS_APP_ID= ?1")
	List<EOCustCategoryGroup> findAllByCustAppId(long custAppId);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_GROUP where CUST_BUSINESS_APP_ID= ?1 and ID=?2")
	EOCustCategoryGroup findOneByIdAndCustAppId(long custAppId, Long id);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_GROUP where CUST_BUSINESS_APP_ID= ?1 and RECORD_STATUS in(?2)")
	List<EOCustCategoryGroup> findAllByCustAppIdAndStatusIn(long custAppId, List<String> statusIds);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_GROUP where CUST_BUSINESS_APP_ID= ?1 and TYPE_ID = ?2")
	List<EOCustCategoryGroup>  findOneByCustAppIdAndTypeId(long custAppId, String typeId);

}
