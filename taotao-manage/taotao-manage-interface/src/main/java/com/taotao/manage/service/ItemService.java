package com.taotao.manage.service;

import com.taotao.common.vo.PageResult;
import com.taotao.manage.pojo.Item;

public interface ItemService extends BaseService<Item>{

	Long saveItem(Item item, String desc);

	void updateItem(Item item, String desc);

	PageResult queryListByPage(Integer page, Integer rows, String title);

	void reshelfByIds(String[] ids);

	void instockByIds(String[] ids);

	void deleteItemByIds(String[] ids);

}
