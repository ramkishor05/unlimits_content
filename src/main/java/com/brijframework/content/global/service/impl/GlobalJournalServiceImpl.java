package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.content.global.entities.EOGlobalJournal;
import com.brijframework.content.global.mapper.GlobalJournalMapper;
import com.brijframework.content.global.model.UIGlobalJournal;
import com.brijframework.content.global.repository.GlobalJournalRepository;
import com.brijframework.content.global.service.GlobalJournalService;

@Service
public class GlobalJournalServiceImpl  extends CrudServiceImpl<UIGlobalJournal, EOGlobalJournal, Long> implements GlobalJournalService {
	
	@Autowired
	private GlobalJournalRepository globalJournalRepository;
	
	@Autowired
	private GlobalJournalMapper globalJournalMapper;
	

	@Override
	public JpaRepository<EOGlobalJournal, Long> getRepository() {
		return globalJournalRepository;
	}

	@Override
	public GenericMapper<EOGlobalJournal, UIGlobalJournal> getMapper() {
		return globalJournalMapper;
	}

}
