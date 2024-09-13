package com.brijframework.content.global.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.global.model.UIGlobalDashboard;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;
import com.brijframework.content.global.service.GlobalDashboardService;

@Service
public class GlobalDashboardServiceImpl implements GlobalDashboardService {

	@Autowired
	private GlobalMindSetLibararyRepository globalMindSetLibararyRepository;
	
	@Autowired
	private GlobalReProgramLibararyRepository globalReProgramLibararyRepository;
	
	@Autowired
	private GlobalAffirmationLibararyRepository globalAffirmationLibararyRepository;
	
	@Autowired
	private GlobalJournalLibararyRepository globalJournalLibararyRepository;
	
	@Override
	public UIGlobalDashboard getDashboard() {
		
		UIGlobalDashboard clientDashboard=new UIGlobalDashboard();
		
		clientDashboard.setTotalAffirmations(globalAffirmationLibararyRepository.count());
		
		clientDashboard.setTotalMindSets(globalMindSetLibararyRepository.count());
		
		clientDashboard.setTotalReprograms(globalReProgramLibararyRepository.count());
		
		clientDashboard.setTotalJournals(globalJournalLibararyRepository.count());
		
		return clientDashboard;
	}

}
