package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {
	
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	@Override
	public ContentCategory contentCategoryAdd(ContentCategory contentCategory) {
		
		//更改父节点
		ContentCategory record = new ContentCategory();
		record.setId(contentCategory.getParentId());
		record.setIsParent(true);
		contentCategoryMapper.updateByPrimaryKeySelective(record);
		
		//增加子节点
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(contentCategory.getCreated());
		contentCategory.setSortOrder(100);
		contentCategoryMapper.insertSelective(contentCategory);
		
		return contentCategory;
	}

	@Override
	public void contentCategoryUpdate(Long id,String name) {
		ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
	}

	@Override
	public void contentCategoryDelete(ContentCategory contentCategory) {
		
		//获取要删除的id集合
		List<Long> ids = new ArrayList<>();
		ids.add(contentCategory.getId());
		getCategoryIds(ids, contentCategory.getId());
		//执行批量删除
		for (Long id : ids) {
			ContentCategory record = contentCategoryMapper.selectByPrimaryKey(id);
			record.setStatus(2);
			contentCategoryMapper.updateByPrimaryKeySelective(record);
		}
		
		//更新状态
		ContentCategory category = new ContentCategory();
		category.setParentId(contentCategory.getParentId());
		List<ContentCategory> list = queryListByWhere(category);
		int count = 0;
		if (list!= null && list.size()>0) {
			for (ContentCategory contentCategory2 : list) {
				if (contentCategory2.getStatus() == 2) {
					count++;
				}
			}
		}
		if (list.size() == count) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			updateSelective(parent);
		}
	}
	
	private void getCategoryIds(List<Long> ids, Long id) {
		ContentCategory category = new ContentCategory();
		category.setParentId(id);
		List<ContentCategory> list = queryListByWhere(category);
		if(list != null && list.size() > 0){
			for (ContentCategory cc : list) {
				ids.add(cc.getId());
				getCategoryIds(ids, cc.getId());
			}
		}
	}

	@Override
	public List<ContentCategory> queryList(ContentCategory contentCategory) {
		
		List<ContentCategory> list = contentCategoryMapper.select(contentCategory);
		List<ContentCategory> contentCategories = new ArrayList<>();
		for (ContentCategory contentCategory2 : list) {
			if (contentCategory2.getStatus() != 2) {
				contentCategories.add(contentCategory2);
			}
		}
		return contentCategories;
	}
}
