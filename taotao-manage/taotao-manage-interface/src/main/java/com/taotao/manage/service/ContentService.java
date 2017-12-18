package com.taotao.manage.service;

import com.taotao.common.vo.PageResult;
import com.taotao.manage.pojo.Content;

public interface ContentService extends BaseService<Content>{

	PageResult queryListByPage(Integer page, Integer rows, Long categoryId);

	String queryBigAdData() throws Exception;
	
}
