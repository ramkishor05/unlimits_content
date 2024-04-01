package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.*;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustMediaItem;
import com.brijframework.content.client.rqrs.CustMediaItemResponse;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustMediaItemResponseMapper  extends GenericMapper<EOCustMediaItem, CustMediaItemResponse>{

	@Override
	EOCustMediaItem mapToDAO(CustMediaItemResponse uiCustMedia);
	
	@Override
	CustMediaItemResponse mapToDTO(EOCustMediaItem eoCustMedia);
}
