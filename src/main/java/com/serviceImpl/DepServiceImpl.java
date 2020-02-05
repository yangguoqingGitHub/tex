package com.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Dep;
import com.service.IDepService;
import com.serviceutil.DaoService;
@Service("DepBiz")
@Transactional
public class DepServiceImpl implements IDepService {
    @Resource(name="DaoService")
	private DaoService daoService;
    
	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public List<Dep> findAll() {
		// TODO Auto-generated method stub
		return daoService.getDepMapper().findAll();
	}

}
