package com.brijframework.content.global.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brijframework.content.constants.RecordStatus;
import com.brijframework.content.global.model.UIGlobalDashboard;
import com.brijframework.content.global.repository.GlobalAffirmationLibararyRepository;
import com.brijframework.content.global.repository.GlobalExampleLibararyRepository;
import com.brijframework.content.global.repository.GlobalImageLibararyRepository;
import com.brijframework.content.global.repository.GlobalJournalLibararyRepository;
import com.brijframework.content.global.repository.GlobalMainCategoryRepository;
import com.brijframework.content.global.repository.GlobalMindSetLibararyRepository;
import com.brijframework.content.global.repository.GlobalPromptLibararyRepository;
import com.brijframework.content.global.repository.GlobalReProgramLibararyRepository;
import com.brijframework.content.global.repository.GlobalSubCategoryRepository;
import com.brijframework.content.global.repository.GlobalTagLibararyRepository;
import com.brijframework.content.global.service.GlobalDashboardService;

@Service
public class GlobalDashboardServiceImpl implements GlobalDashboardService {

	private static final Logger LOGGER= LoggerFactory.getLogger(GlobalDashboardServiceImpl.class);

	@Autowired
	private GlobalMainCategoryRepository globalMainCategoryRepository;
	
	@Autowired
	private GlobalSubCategoryRepository globalSubCategoryRepository;
	
	@Autowired
	private GlobalTagLibararyRepository globalTagLibararyRepository;
	
	@Autowired
	private GlobalImageLibararyRepository globalImageLibararyRepository;
	
	@Autowired
	private GlobalExampleLibararyRepository globalExampleLibararyRepository;
	
	@Autowired
	private GlobalPromptLibararyRepository globalPromptLibararyRepository;
	
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
		LOGGER.warn("Start geting the data for dashboard");
		UIGlobalDashboard clientDashboard=new UIGlobalDashboard();
		
		clientDashboard.setTotalMainCategories(globalMainCategoryRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalSubCategories(globalSubCategoryRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalTags(globalTagLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalImages(globalImageLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalExamples(globalExampleLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalPrompts(globalPromptLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalAffirmations(globalAffirmationLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalMindSets(globalMindSetLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalReprograms(globalReProgramLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		
		clientDashboard.setTotalJournals(globalJournalLibararyRepository.countByRecordStateIn(RecordStatus.ACTIVETED.getStatusIds()));
		LOGGER.warn("End geting the data for dashboard");
		return clientDashboard;
	}

}
