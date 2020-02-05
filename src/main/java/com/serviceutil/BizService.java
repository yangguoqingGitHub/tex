package com.serviceutil;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.service.IDepService;
import com.service.IEmpService;
import com.service.IWelfareService;

@Service("BizService")
public class BizService {
	@Resource(name="DepBiz")
	private IDepService depBiz;
	@Resource(name="EmpBiz")
	private IEmpService empBiz;
	@Resource(name="WelfareBiz")
	private IWelfareService welfareBiz;
	public IDepService getDepBiz() {
		return depBiz;
	}
	public void setDepBiz(IDepService depBiz) {
		this.depBiz = depBiz;
	}
	public IEmpService getEmpBiz() {
		return empBiz;
	}
	public void setEmpBiz(IEmpService empBiz) {
		this.empBiz = empBiz;
	}
	public IWelfareService getWelfareBiz() {
		return welfareBiz;
	}
	public void setWelfareBiz(IWelfareService welfareBiz) {
		this.welfareBiz = welfareBiz;
	}
	

}
