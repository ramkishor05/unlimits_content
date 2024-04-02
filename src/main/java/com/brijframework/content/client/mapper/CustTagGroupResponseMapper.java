package com.brijframework.content.client.mapper;
import static com.brijframework.content.constants.Constants.*;

import org.mapstruct.Mapper;

import com.brijframework.content.client.entites.EOCustTagGroup;
import com.brijframework.content.client.rqrs.CustTagGroupResponse;
import com.brijframework.content.mapper.GenericMapper;

@Mapper(componentModel = SPRING, implementationPackage = APP_CLIENT_PACKAGE_IMPL)
public interface CustTagGroupResponseMapper  extends GenericMapper<EOCustTagGroup, CustTagGroupResponse>{

	@Override
	EOCustTagGroup mapToDAO(CustTagGroupResponse uiCustTag);
	
	@Override
	CustTagGroupResponse mapToDTO(EOCustTagGroup eoCustTag);
}
