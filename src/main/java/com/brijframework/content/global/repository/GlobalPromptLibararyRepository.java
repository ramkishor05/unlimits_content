package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalPromptLibarary;

@Repository
@Transactional
public interface GlobalPromptLibararyRepository extends CustomRepository<EOGlobalPromptLibarary, Long>{

	/**
	 * @param type
	 * @return
	 */
	List<EOGlobalPromptLibarary> findAllByType(String type);

	/**
	 * @param idenNo
	 * @return
	 */
	Optional<EOGlobalPromptLibarary> findByIdenNo(String idenNo);
	
}
