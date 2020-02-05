package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.serviceutil.BizService;
import com.util.AjAxUtils;
@Controller
public class EmpController implements IController {
    @Resource(name="BizService")
	private BizService bizService;
    
	public BizService getBizService() {
		return bizService;
	}

	public void setBizService(BizService bizService) {
		this.bizService = bizService;
	}

	@RequestMapping(value="save_Emp.do")
	public String save(HttpServletRequest request, HttpServletResponse response, Emp emp) {
		System.out.println("11111111");
		String realpath = request.getRealPath("/");
		/***************** 上传文件 ************************************/
		// 获取上传照片的对象
		MultipartFile multipartFile = emp.getPic();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// 获取上传的文件名称
			String fname = multipartFile.getOriginalFilename();
			// 更名
			if (fname.lastIndexOf(".") != -1) {// 存在后缀
				// 获取后缀名
				String ext = fname.substring(fname.lastIndexOf("."));

				// 判断后缀是否为jpg格式
				if (ext.equalsIgnoreCase(".jpg")) {
					// 改名
					String newfname = new Date().getTime() + ext;
					// 创建文件对象，指定上传文件的路径
					File destFile = new File(realpath + "/uppic/" + newfname);
					// 上传
					try {
						FileUtils.copyInputStreamToFile(
								multipartFile.getInputStream(), destFile);
						emp.setPhoto(newfname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/****************************************************************************/
		boolean flag=bizService.getEmpBiz().save(emp);
		System.out.println(flag);
		if(flag){
			AjAxUtils.printString(response, 1+"");
		}else{
			AjAxUtils.printString(response, 0+"");
		}
		return null;
	}

	@RequestMapping(value="update_Emp.do")
	public String update(HttpServletRequest request, HttpServletResponse response, Emp emp) {
		//获取原来上传的员工张片
		String oldphoto=bizService.getEmpBiz().findById(emp.getEid()).getPhoto();
		String realpath = request.getRealPath("/");
		/***************** 上传文件 ************************************/
		// 获取上传照片的对象
		MultipartFile multipartFile = emp.getPic();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// 获取上传的文件名称
			String fname = multipartFile.getOriginalFilename();
			// 更名
			if (fname.lastIndexOf(".") != -1) {// 存在后缀
				// 获取后缀名
				String ext = fname.substring(fname.lastIndexOf("."));

				// 判断后缀是否为jpg格式
				if (ext.equalsIgnoreCase(".jpg")) {
					// 改名
					String newfname = new Date().getTime() + ext;
					// 创建文件对象，指定上传文件的路径
					File destFile = new File(realpath + "/uppic/" + newfname);
					// 上传
					try {
						FileUtils.copyInputStreamToFile(
								multipartFile.getInputStream(), destFile);
						emp.setPhoto(newfname);
					  //删除原来的照片
						File oldFile=new File(realpath+"/uppic/"+oldphoto);
						if(oldFile.exists()&&!oldphoto.equalsIgnoreCase("default.jpg")){
							oldFile.delete();//删除
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			emp.setPhoto(oldphoto);
		}
		/****************************************************************************/
		boolean flag=bizService.getEmpBiz().update(emp);
		if(flag){
			AjAxUtils.printString(response, 1+"");
		}else{
			AjAxUtils.printString(response, 0+"");
		}
		return null;
	}

	@RequestMapping(value="delById_Emp.do")
	public String delById(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		boolean flag=bizService.getEmpBiz().delById(eid);
		if(flag){
			AjAxUtils.printString(response, 1+"");
		}else{
			AjAxUtils.printString(response, 0+"");
		}
		return null;
	}

	@RequestMapping(value="findById_Emp.do")
	public String findById(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		System.out.println(eid);
		Emp oldemp=bizService.getEmpBiz().findById(eid);
		System.out.println(oldemp.toString());
		PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		System.out.println("转换后json:"+jsonstr);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

	@RequestMapping(value="findDetail_Emp.do")
	public String findDetail(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		Emp oldemp=bizService.getEmpBiz().findById(eid);
		PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		System.out.println("转换后json:"+jsonstr);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

	@RequestMapping(value="findPageAll_Emp.do")
	public String findPageAll(HttpServletRequest request, HttpServletResponse response, Integer page, Integer rows) {
		//页面使用的是easyUI,需要返回数据格式必须是map的key-value形式
		Map<String,Object> map=new HashMap<>();
		PageBean pb=new PageBean();
		page=page==null||page<1?pb.getPage():page;
		rows=rows==null||rows<1?pb.getRows():rows;
		if(rows>10) rows=10;
		pb.setPage(page);
		pb.setRows(rows);
		//获取当前页记录集合
		List<Emp> lsemp=bizService.getEmpBiz().findPageAll(pb);
		//总记录数
		int maxrows=bizService.getEmpBiz().findMaxRows();
		//封装数据到easyui
		map.put("page", page);
		map.put("rows", lsemp);
		map.put("total",maxrows);
		PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

	@RequestMapping(value="doinit_Emp.do")
	public String doinit(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map=new HashMap<>();
		List<Dep> lsdep=bizService.getDepBiz().findAll();
		List<Welfare> lswf=bizService.getWelfareBiz().findAll();
		map.put("lsdep", lsdep);
		map.put("lswf", lswf);
		PropertyFilter propertyFilter=AjAxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjAxUtils.printString(response, jsonstr);
		return null;
	}

}
