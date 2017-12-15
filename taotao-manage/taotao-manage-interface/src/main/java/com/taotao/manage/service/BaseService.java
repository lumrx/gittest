package com.taotao.manage.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
	
	//根据ID查询
	public T queryById(Serializable id);
	//查询所有
	public List<T> queryAll();
	//根据查询条件查询总记录数
	public Integer queryCountByWhere(T t);
	//根据条件查询
	public List<T> queryListByWhere(T t);
	//分页查询列表
	public List<T> queryListByPage(Integer page, Integer rows);
	//选择性新增
	public void saveSelective(T t);
	//选择性更新
	public void updateSelective(T t);
	//根据id删除
	public void deleteById(Serializable id);
	//根据id集合删除
	public void deleteByIds(Serializable[] ids);

}
