package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.*;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustCategoryItem;
import com.brijframework.content.client.rqrs.CustCategoryItemResponse;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustCategoryItemResponseMapper  extends GenericMapper<EOCustCategoryItem, CustCategoryItemResponse>{

	@Override
	EOCustCategoryItem mapToDAO(CustCategoryItemResponse uiCustCategory);
	
	@Override
	CustCategoryItemResponse mapToDTO(EOCustCategoryItem eoCustCategory);
}
