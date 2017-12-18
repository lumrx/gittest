package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.pojo.ItemCat;

public interface ContentCategoryService extends BaseService<ContentCategory>{

	ContentCategory contentCategoryAdd(ContentCategory contentCategory);

	void contentCategoryUpdate(Long id,String name);

	void contentCategoryDelete(ContentCategory contentCategory);

	List<ContentCategory> queryList(ContentCategory contentCategory);
	

}
