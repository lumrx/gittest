package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@RequestMapping("/content/category")
@Controller
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentcategoryService;
	
	//显示树
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> contentCategoryList(@RequestParam(value="id",defaultValue="0")Long parentId){
		
		try {
			ContentCategory contentCategory = new ContentCategory();
			contentCategory.setParentId(parentId);
			List<ContentCategory> list = contentcategoryService.queryList(contentCategory);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//增加
	@RequestMapping("/add")
	public ResponseEntity<ContentCategory> contentCategoryAdd(ContentCategory contentCategory){
		try {
			ContentCategory contentCategory1 = contentcategoryService.contentCategoryAdd(contentCategory);
			return ResponseEntity.ok(contentCategory1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//重命名
	@RequestMapping("/update")
	public ResponseEntity<Void> contentCategoryUpdate(@RequestParam(value="id")Long id,@RequestParam(value="name")String name){
		try {
			contentcategoryService.contentCategoryUpdate(id,name);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//删除
	@RequestMapping("/delete")
	public ResponseEntity<Void> contentCategoryDelete(ContentCategory contentCategory){
		try {
			contentcategoryService.contentCategoryDelete(contentCategory);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
}
