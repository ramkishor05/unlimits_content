package com.brijframework.content.global.repository;

import static com.brijframework.content.constants.Constants.EOGLOBAL_JOURNAL_LIBARARY;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalJournalLibarary;

@Repository
@Transactional
public interface GlobalJournalLibararyRepository extends CustomRepository<EOGlobalJournalLibarary, Long>{

	/**
	 * @param idenNo
	 * @return
	 */
	Optional<EOGlobalJournalLibarary> findByIdenNo(String idenNo);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOGLOBAL_JOURNAL_LIBARARY+" where JOURNAL_DATE = current_date() and RECORD_STATUS in (?) ")
	List<EOGlobalJournalLibarary> findTodayJournalLibarary(List<String> statusList);

	
	@Query(nativeQuery = true , value= "SELECT * FROM "+EOGLOBAL_JOURNAL_LIBARARY+" where JOURNAL_DATE = DATE_SUB(CURRENT_DATE(),INTERVAL 1 DAY) and RECORD_STATUS in (?)")
	List<EOGlobalJournalLibarary> findYesterdayJournalLibarary(List<String> statusList);

	@Query(nativeQuery = true , value= "SELECT * FROM "+EOGLOBAL_JOURNAL_LIBARARY+" where JOURNAL_DATE = (select max(JOURNAL_DATE) FROM "+EOGLOBAL_JOURNAL_LIBARARY+" and RECORD_STATUS in (?))")
	List<EOGlobalJournalLibarary> findLastJournalLibarary(List<String> statusList);
	
}
