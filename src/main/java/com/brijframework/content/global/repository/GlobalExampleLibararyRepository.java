package com.brijframework.content.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.unlimits.rest.repository.CustomRepository;

import com.brijframework.content.global.entities.EOGlobalExampleLibarary;

@Repository
@Transactional
public interface GlobalExampleLibararyRepository  extends CustomRepository<EOGlobalExampleLibarary, Long>{

	Optional<EOGlobalExampleLibarary> findByIdenNo(String idenNo);

	Optional<EOGlobalExampleLibarary> findBySubCategoryIdAndProfileName(Long subCategoryId, String profileName);

	long countByRecordStateIn(List<String> statusIds);

}
