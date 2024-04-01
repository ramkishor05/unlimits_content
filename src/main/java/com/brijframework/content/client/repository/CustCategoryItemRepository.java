package com.brijframework.content.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.client.entites.EOCustCategoryItem;

@Repository
@Transactional
public interface CustCategoryItemRepository extends JpaRepository<EOCustCategoryItem, Long>{
	
	EOCustCategoryItem findOneByTypeId(String typeId);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_ITEM where CUST_BUSINESS_APP_ID= ?1 and NAME=?2")
	Optional<EOCustCategoryItem> findByCustAppAndName(Long id, String name);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_ITEM where CUST_BUSINESS_APP_ID= ?1 and TYPE_ID=?2")
	List<EOCustCategoryItem> findAllByType(Long custAppId, String typeId);

	@Query(nativeQuery = true, value = "select * from EOCUST_CATEGORY_ITEM where CUST_BUSINESS_APP_ID= ?1")
	List<EOCustCategoryItem>  findAllByCustAppId(long custAppId);

}
