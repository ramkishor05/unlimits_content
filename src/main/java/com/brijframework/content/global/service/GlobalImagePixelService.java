package com.brijframework.content.global.service;

import java.util.List;

import org.unlimits.rest.crud.beans.PageDetail;

import com.brijframework.content.global.model.UIGlobalImageLibarary;

public interface GlobalImagePixelService {

	PageDetail<UIGlobalImageLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, int pageNumber, int count);

	PageDetail<UIGlobalImageLibarary> findByPexels(Long subCategoryId, Long tagLibararyId, String name, int pageNumber, int count);

	List<UIGlobalImageLibarary> findByPexels(String name);

}
