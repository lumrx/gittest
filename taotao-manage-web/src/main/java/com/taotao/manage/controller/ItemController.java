package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.vo.PageResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@RequestMapping("/item")
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam(value="desc",required=false) String desc){
		
		try {
			Long id = itemService.saveItem(item,desc);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam(value="desc",required=false) String desc){
		
		try {
			itemService.updateItem(item,desc);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PageResult> listItem(@RequestParam(value="page",defaultValue="1") Integer page,@RequestParam(value="rows",defaultValue="30") Integer rows, @RequestParam(value="title",required=false)String title ){
		
		try {
			//分页
			PageResult pageResult = itemService.queryListByPage(page,rows,title);
			return ResponseEntity.ok(pageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	public ResponseEntity<Void> deleteItem(@RequestParam("ids")String[] ids){
		
		try {
			itemService.deleteItemByIds(ids);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(value="/instock",method = RequestMethod.POST)
	public ResponseEntity<Void> instockItem(@RequestParam("ids")String[] ids){
		
		try {
			itemService.instockByIds(ids);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(value="/reshelf",method = RequestMethod.POST)
	public ResponseEntity<Void> reshelfItem(@RequestParam("ids")String[] ids){
		
		try {
			itemService.reshelfByIds(ids);
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}
