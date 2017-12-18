package com.taotao.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.vo.PageResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentService;

//content?categoryId=32&page=1&rows=

@RequestMapping("/content")
@Controller
public class ContentController {
	
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<PageResult> contentList(@RequestParam(value="page",defaultValue="1") Integer page,@RequestParam(value="rows",defaultValue="20") Integer rows, @RequestParam(value="categoryId")Long categoryId){
		try {
			PageResult pageResult = contentService.queryListByPage(page,rows,categoryId);
			
			return ResponseEntity.ok(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//增加
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> contentAdd(Content content){
		try {
			contentService.saveSelective(content);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//修改
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public ResponseEntity<Void> contentEdit(Content content){
		try {
			contentService.updateSelective(content);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//修改
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> contenDelete(@RequestParam("ids")Long[] ids){
		Map<String, Object> map = new HashMap<>();
		map.put("status", 500);
		try {
			contentService.deleteByIds(ids);
			map.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(map);
	}

}
