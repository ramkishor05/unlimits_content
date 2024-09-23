/**
 * 
 */
package com.brijframework.content.global.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalReProgramItem;

/**
 *  @author omnie
 */
@Repository
@Transactional
public interface GlobalReProgramItemRepository extends CustomRepository<EOGlobalReProgramItem, Long>{

	void deleteByReProgramLibararyId(Long id);

	void deleteByReProgramLibararyIdAndIdNotIn(Long id, List<Long> ids);
	
}
