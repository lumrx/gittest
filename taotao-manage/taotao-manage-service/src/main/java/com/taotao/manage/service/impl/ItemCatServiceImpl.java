package com.taotao.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {
	
	@Autowired
	private ItemCatMapper mapper;
	
	@Deprecated
	@Override
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {
		
		//开启分页
		PageHelper.startPage(page,rows);
		
		List<ItemCat> list = mapper.selectAll();
		
		
		return list;
	}

}
