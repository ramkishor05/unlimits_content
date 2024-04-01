package com.brijframework.content.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.client.entites.EOCustPromptItem;

@Repository
@Transactional
public interface CustPromptItemRepository extends JpaRepository<EOCustPromptItem, Long>{
	
	EOCustPromptItem findOneByTypeId(String typeId);

	@Query(nativeQuery = true, value = "select * from EOCUST_PROMPT_ITEM where CUST_BUSINESS_APP_ID= ?1 and NAME=?2")
	Optional<EOCustPromptItem> findByCustAppAndName(Long id, String name);

	@Query(nativeQuery = true, value = "select * from EOCUST_PROMPT_ITEM where CUST_BUSINESS_APP_ID= ?1 and TYPE_ID=?2")
	List<EOCustPromptItem> findAllByType(Long custAppId, String typeId);

	@Query(nativeQuery = true, value = "select * from EOCUST_PROMPT_ITEM where CUST_BUSINESS_APP_ID= ?1")
	List<EOCustPromptItem>  findAllByCustAppId(long custAppId);

}
