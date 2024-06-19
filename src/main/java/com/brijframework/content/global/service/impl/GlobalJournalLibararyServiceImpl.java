package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalJournalLibarary;
import com.brijframework.content.global.mapper.GlobalJournalLibararyMapper;
import com.brijframework.content.global.model.UIGlobalJournalLibarary;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;
import com.brijframework.content.global.service.GlobalJournalLibararyService;

@Service
public class GlobalJournalLibararyServiceImpl  extends CrudServiceImpl<UIGlobalJournalLibarary, EOGlobalJournalLibarary, Long> implements GlobalJournalLibararyService {
	
	@Autowired
	private GlobalJournalLibararyRepository globalJournalLibararyRepository;
	
	@Autowired
	private GlobalJournalLibararyMapper globalJournalLibararyMapper;
	

	@Override
	public JpaRepository<EOGlobalJournalLibarary, Long> getRepository() {
		return globalJournalLibararyRepository;
	}

	@Override
	public GenericMapper<EOGlobalJournalLibarary, UIGlobalJournalLibarary> getMapper() {
		return globalJournalLibararyMapper;
	}

}
