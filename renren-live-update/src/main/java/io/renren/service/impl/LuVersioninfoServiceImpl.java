package io.renren.service.impl;

import io.renren.dao.LuVersioninfoDao;
import io.renren.entity.LuVersioninfoEntity;
import io.renren.service.LuVersioninfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("luVersioninfoService")
public class LuVersioninfoServiceImpl implements LuVersioninfoService {
	@Autowired
	private LuVersioninfoDao luVersioninfoDao;
	
	@Override
	public LuVersioninfoEntity queryObject(Long id){
		return luVersioninfoDao.queryObject(id);
	}
	
	@Override
	public List<LuVersioninfoEntity> queryList(Map<String, Object> map){
		return luVersioninfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return luVersioninfoDao.queryTotal(map);
	}
	
	@Override
	public void save(LuVersioninfoEntity luVersioninfo){
		luVersioninfoDao.save(luVersioninfo);
	}
	
	@Override
	public void update(LuVersioninfoEntity luVersioninfo){
		luVersioninfoDao.update(luVersioninfo);
	}
	
	@Override
	public void delete(Long id){
		luVersioninfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		luVersioninfoDao.deleteBatch(ids);
	}
	
}
