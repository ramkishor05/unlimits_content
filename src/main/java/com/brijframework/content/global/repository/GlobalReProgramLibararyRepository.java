/**
 * 
 */
package com.brijframework.content.global.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalReProgramLibarary;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface GlobalReProgramLibararyRepository extends CustomRepository<EOGlobalReProgramLibarary, Long>{
	
}
