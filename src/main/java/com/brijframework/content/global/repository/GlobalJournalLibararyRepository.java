package com.brijframework.content.global.repository;

import static com.brijframework.content.constants.Constants.EOGLOBAL_JOURNAL_LIBARARY;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.brijframework.content.global.entities.EOGlobalJournalLibarary;

@Repository
@Transactional
public interface GlobalJournalLibararyRepository extends JpaRepository<EOGlobalJournalLibarary, Long>{

	/**
	 * @param idenNo
	 * @return
	 */
	Optional<EOGlobalJournalLibarary> findByIdenNo(String idenNo);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOGLOBAL_JOURNAL_LIBARARY+" where JOURNAL_DATE = current_date() ")
	List<EOGlobalJournalLibarary> findTodayJournalLibarary();

	
	@Query(nativeQuery = true , value= "SELECT * FROM "+EOGLOBAL_JOURNAL_LIBARARY+" where JOURNAL_DATE = DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY) ")
	List<EOGlobalJournalLibarary> findYesterdayJournalLibarary();
	
}
