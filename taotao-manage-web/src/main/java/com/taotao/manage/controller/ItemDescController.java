package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

@RequestMapping("/item/desc")
@Controller
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;
	
	//回显商品描述
	@RequestMapping("/{itemId}")
	@ResponseBody
	public ItemDesc queryItemDesc(@PathVariable("itemId")Long itemId){
		
		try {
			ItemDesc itemDesc = itemDescService.queryById(itemId);
			return itemDesc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
