package com.brijframework.content.device.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceJournalModel;
import com.brijframework.content.device.service.DeviceJournalLibararyService;
import com.brijframework.content.global.entities.EOGlobalJournalLibarary;

@RestController
@RequestMapping("/api/device/journal/libarary")
public class DeviceJournalLibararyController implements QueryController<UIDeviceJournalModel, EOGlobalJournalLibarary, Long>{

	@Autowired
	private DeviceJournalLibararyService deviceJournalLibararyService;
	
	@Override
	public QueryService<UIDeviceJournalModel, EOGlobalJournalLibarary, Long> getService() {
		return deviceJournalLibararyService;
	}
	
	@GetMapping("/today")
	public Response<List<UIDeviceJournalModel>> findTodayJournalLibarary(){
		Response<List<UIDeviceJournalModel>> response=new Response<List<UIDeviceJournalModel>>();
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
