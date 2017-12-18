package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.service.redis.RedisService;
import com.taotao.common.vo.PageResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

import tk.mybatis.mapper.entity.Example;

@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {
	
	private static final String REDIS_BIG_AD_KEY = "REDIS_BIG_AD_DATA";

	private static final int REDIS_BIG_AD_EXPIRE_TIME = 60*60*24;

	@Value("${category.id}")
	private Long CONTENT_CATEGORY_BIG_AD_ID;
	
	@Value("${bigAd.number}")
	private Integer TAOTAO_PORTAL_INDEX_BIG_AD_NUMBER;
	
	private ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ContentMapper contentMapper;

	@Override
	public PageResult queryListByPage(Integer page, Integer rows, Long categoryId) {
		
		Example example = new Example(Content.class);
		example.createCriteria().andEqualTo("categoryId", categoryId);
		//时间排序
		example.orderBy("updated").desc();
		//分页
		PageHelper.startPage(page, rows);
		
		List<Content> list = contentMapper.selectByExample(example);
		PageInfo<Content> info = new PageInfo<>(list);
		
		return new PageResult(info.getTotal(),info.getList());
	}

	@Override
	public String queryBigAdData() throws Exception {
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		//访问redis是否存在数据
		try {
			String str = redisService.get(REDIS_BIG_AD_KEY);
			
			if (StringUtils.isNotBlank(str)) {
				return str;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		PageResult page = queryListByPage(1, TAOTAO_PORTAL_INDEX_BIG_AD_NUMBER, CONTENT_CATEGORY_BIG_AD_ID);
		
		if (page != null) {
			
			List<Content> contents = (List<Content>) page.getRows();
			
			if (contents != null && contents.size()>0) {
				for (Content content : contents) {
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("alt", content.getTitle());
					map.put("height", 240);
					map.put("height", 240);
					map.put("href", content.getUrl());
					map.put("src", content.getPic());
					map.put("srcB", content.getPic2());
					map.put("width", 670);
					map.put("widthB", 550);
					list.add(map);
				}
			}
		}
		String adData = MAPPER.writeValueAsString(list);
		
		//保存到redis
		try {
			redisService.setex(REDIS_BIG_AD_KEY, REDIS_BIG_AD_EXPIRE_TIME, adData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adData;
	}

}
