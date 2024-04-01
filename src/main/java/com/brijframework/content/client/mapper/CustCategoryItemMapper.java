package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.*;
import static com.brijframework.content.constants.Constants.CUST_GROUP_ID_UI;
import static com.brijframework.content.constants.Constants.CUST_PROD_APP_ID_ENTITY;
import static com.brijframework.content.constants.Constants.CUST_PROD_APP_ID_UI;
import static com.brijframework.content.constants.Constants.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.brijframework.content.client.entites.EOCustCategoryItem;
import com.brijframework.content.client.model.UICustCategoryItem;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustCategoryItemMapper  extends GenericMapper<EOCustCategoryItem, UICustCategoryItem>{
	

	@Mapping(target=CUST_PROD_APP_ID_ENTITY, source=CUST_PROD_APP_ID_UI)
	@Mapping(target=CUST_GROUP_ID_ENTITY, source=CUST_GROUP_ID_UI)
	@Override
	EOCustCategoryItem mapToDAO(UICustCategoryItem uiCategory);
	
	@Mapping(source=CUST_PROD_APP_ID_ENTITY, target=CUST_PROD_APP_ID_UI)
	@Mapping(source=CUST_GROUP_ID_ENTITY, target=CUST_GROUP_ID_UI)
	@Override
	UICustCategoryItem mapToDTO(EOCustCategoryItem eoCustCategory);
}
