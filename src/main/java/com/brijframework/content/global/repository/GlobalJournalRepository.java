package com.brijframework.content.global.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalJournal;

@Repository
@Transactional
public interface GlobalJournalRepository extends JpaRepository<EOGlobalJournal, Long>{

	/**
	 * @param idenNo
	 * @return
	 */
	Optional<EOGlobalJournal> findByIdenNo(String idenNo);
	
}
