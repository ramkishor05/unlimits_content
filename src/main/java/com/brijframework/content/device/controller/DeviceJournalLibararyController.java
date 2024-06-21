package com.brijframework.content.device.controller;
import static com.brijframework.content.constants.ClientConstants.FAILED;
import static com.brijframework.content.constants.ClientConstants.SUCCESS;
import static com.brijframework.content.constants.ClientConstants.SUCCESSFULLY_PROCCEED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.content.device.service.DeviceJournalLibararyService;

@RestController
@RequestMapping("/api/device/journal/libarary")
public class DeviceJournalLibararyController {

	@Autowired
	private DeviceJournalLibararyService deviceJournalLibararyService;
	
	@GetMapping("/today")
	public Response findTodayJournalLibarary(){
		Response response=new Response();
		try {
			response.setData(deviceJournalLibararyService.findTodayJournalLibarary());
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
