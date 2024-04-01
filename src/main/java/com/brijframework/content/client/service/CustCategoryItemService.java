package com.brijframework.content.client.service;

import java.util.List;

import com.brijframework.content.client.entites.EOCustBusinessApp;
import com.brijframework.content.client.rqrs.CustCategoryItemRequest;
import com.brijframework.content.client.rqrs.CustCategoryItemResponse;

public interface CustCategoryItemService {

	CustCategoryItemResponse saveCategory(long custAppId, CustCategoryItemRequest custCategoryRequest);

	CustCategoryItemResponse saveCategory(EOCustBusinessApp eoCustBusinessApp, CustCategoryItemRequest uiCustCategory);

	CustCategoryItemResponse getCategory(long custAppId, long id);

	List<CustCategoryItemResponse> getCategoryList(long custAppId);

	boolean deleteCategory(long custAppId, Long id);

	List<CustCategoryItemResponse> findAllByType(long custAppId, String typeId);

}
