package com.taotao.manage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.PageResult;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	@Autowired
	private ItemDescMapper itemDescMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public Long saveItem(Item item, String desc) {
		// 保存商品基本信息
		this.saveSelective(item);

		// 保存商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.insertSelective(itemDesc);
		return item.getId();
	}

	@Override
	public void updateItem(Item item, String desc) {
		// 更新商品基本信息
		this.updateSelective(item);

		// 保存商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public PageResult queryListByPage(Integer page, Integer rows, String title) {
		
		Example example = new  Example(Item.class);
		try {
			if (StringUtils.isNotBlank(title)) {
				title = URLDecoder.decode(title,"utf-8");
				Criteria createCriteria = example.createCriteria();
				createCriteria.andLike("title", "%"+title+"%");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		example.orderBy("updated").desc();
		//分页
		PageHelper.startPage(page, rows);
		List<Item> items = itemMapper.selectByExample(example);
		PageInfo<Item> pageInfo = new PageInfo<>(items);
		return new PageResult(pageInfo.getTotal(),pageInfo.getList());
	}

	@Override
	public void reshelfByIds(String[] ids) {
		for (String id : ids) {
			Item item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
			item.setStatus(1);
			itemMapper.updateByPrimaryKeySelective(item);
		}
	}

	@Override
	public void instockByIds(String[] ids) {
		for (String id : ids) {
			Item item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
			item.setStatus(2);
			itemMapper.updateByPrimaryKeySelective(item);
		}
	}

	@Override
	public void deleteItemByIds(String[] ids) {
		for (String id : ids) {
			itemMapper.deleteByPrimaryKey(Long.parseLong(id));
			itemDescMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
	}
}
