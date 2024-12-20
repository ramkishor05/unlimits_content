package com.brijframework.content.device.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.beans.PageDetail;
import org.unlimits.rest.crud.beans.QueryRequest;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CQRSController;
import org.unlimits.rest.crud.controller.QueryController;
import org.unlimits.rest.crud.service.QueryService;

import com.brijframework.content.device.model.UIDeviceImageLibarary;
import com.brijframework.content.device.service.DeviceImageLibararyService;
import com.brijframework.content.device.service.DeviceImagePixelService;
import com.brijframework.content.global.entities.EOGlobalImageLibarary;

@RestController
@RequestMapping("/api/device/image/libarary")
public class DeviceImageLibararyController
		implements QueryController<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> {

	private static final String NAME = "name";

	private static final String TYPE = "type";

	private static final String SUB_CATEGORY_ID = "subCategoryId";

	@Autowired
	private DeviceImageLibararyService deviceImageLibararyService;

	@Autowired
	private DeviceImagePixelService deviceImagePixelService;

	@Override
	public QueryService<UIDeviceImageLibarary, EOGlobalImageLibarary, Long> getService() {
		return deviceImageLibararyService;
	}

	@GetMapping("/types")
	public Response<List<String>> getTypes(@RequestParam(SUB_CATEGORY_ID) Long subCategoryId,
			@RequestHeader(required = false) MultiValueMap<String, String> headers, WebRequest webRequest) {
		Response<List<String>> response = new Response<List<String>>();
		try {
			response.setData(deviceImageLibararyService.getTypes(subCategoryId));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		} catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@GetMapping("/groupby/folder")
	public Response<Map<String, List<UIDeviceImageLibarary>>> getImagesGroupbyFolder(
			@RequestHeader(required = false) MultiValueMap<String, String> headers, WebRequest webRequest) {
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> actions = CQRSController.getActions(webRequest);
		Response<Map<String, List<UIDeviceImageLibarary>>> response = new Response<Map<String, List<UIDeviceImageLibarary>>>();
		try {
			response.setData(deviceImageLibararyService.findAll(headers, filters, actions).stream()
					.collect(Collectors.groupingBy(UIDeviceImageLibarary::getType)));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@Override
	public Object customizedResponse(List<UIDeviceImageLibarary> values, QueryRequest queryRequest) {
		if (!CollectionUtils.isEmpty(values)) {
			return values;
		}
		if (PAGE_LIST.equalsIgnoreCase(queryRequest.getType())) {
			Integer pageNumber = (Integer) queryRequest.getParams().get(PARAM_PAGE_NUMBER);
			Integer pageCount = (Integer) queryRequest.getParams().get(PARAM_PAGE_COUNT);
			Object subCategoryIdData = queryRequest.getFilters().get(SUB_CATEGORY_ID);
			Object type = queryRequest.getFilters().get(TYPE);
			Object name = queryRequest.getFilters().get(NAME);
			if (subCategoryIdData != null
					&& org.apache.commons.lang.math.NumberUtils.isNumber(subCategoryIdData.toString())
					&& name != null) {
				Long subCategoryId = Long.valueOf(subCategoryIdData.toString());
				if (type != null)
					return deviceImagePixelService.fetchPageListFromPexels(subCategoryId, type.toString(), name.toString(), pageNumber, pageCount);
				else
					return deviceImagePixelService.fetchPageListFromPexels(subCategoryId, name.toString(), pageNumber, pageCount);
			}
		} else if (FIND_ALL.equalsIgnoreCase(queryRequest.getType())) {
			Object subCategoryIdData = queryRequest.getFilters().get(SUB_CATEGORY_ID);
			Object type = queryRequest.getFilters().get(TYPE);
			Object name = queryRequest.getFilters().get(NAME);
			if (subCategoryIdData != null
					&& org.apache.commons.lang.math.NumberUtils.isNumber(subCategoryIdData.toString())
					&& name != null) {
				Long subCategoryId = Long.valueOf(subCategoryIdData.toString());
				if (type != null)
					return deviceImagePixelService.fetchListFromPexels(subCategoryId, type.toString(), name.toString());
				else
					return deviceImagePixelService.fetchListFromPexels(subCategoryId, name.toString());
			}
		}
		return values;
	}

	@Override
	public Object customizedResponse(PageDetail<UIDeviceImageLibarary> fetchPageObject, QueryRequest queryRequest) {
		if (!CollectionUtils.isEmpty(fetchPageObject.getElements())) {
			return fetchPageObject;
		}
		if (PAGE_DATA.equalsIgnoreCase(queryRequest.getType())) {
			Integer pageNumber = (Integer) queryRequest.getParams().get(PARAM_PAGE_NUMBER);
			Integer pageCount = (Integer) queryRequest.getParams().get(PARAM_PAGE_COUNT);
			Object subCategoryIdData = queryRequest.getFilters().get(SUB_CATEGORY_ID);
			Object type = queryRequest.getFilters().get(TYPE);
			Object name = queryRequest.getFilters().get(NAME);
			if (subCategoryIdData != null
					&& org.apache.commons.lang.math.NumberUtils.isNumber(subCategoryIdData.toString())
					&& name != null) {
				Long subCategoryId = Long.valueOf(subCategoryIdData.toString());
				if (type != null)
					return deviceImagePixelService.fetchPageObjectForPexels(subCategoryId, type.toString(), name.toString(),
							pageNumber, pageCount);
				else
					return deviceImagePixelService.fetchPageObjectFromPexels(subCategoryId, name.toString(), pageNumber, pageCount);
			}
		}
		return fetchPageObject;
	}
}
