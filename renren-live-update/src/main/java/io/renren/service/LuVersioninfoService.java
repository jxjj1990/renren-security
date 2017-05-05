package io.renren.service;

import io.renren.entity.LuVersioninfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 版本管理表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-03 16:19:54
 */
public interface LuVersioninfoService {
	
	LuVersioninfoEntity queryObject(Long id);
	
	List<LuVersioninfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(LuVersioninfoEntity luVersioninfo);
	
	void update(LuVersioninfoEntity luVersioninfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
