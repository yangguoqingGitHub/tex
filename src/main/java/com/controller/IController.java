package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.po.Emp;

public interface IController {
  public String  save(HttpServletRequest request, HttpServletResponse response, Emp emp);
  public String  update(HttpServletRequest request, HttpServletResponse response, Emp emp);
  public String  delById(HttpServletRequest request, HttpServletResponse response, Integer eid);
  public String  findById(HttpServletRequest request, HttpServletResponse response, Integer eid);
  public String  findDetail(HttpServletRequest request, HttpServletResponse response, Integer eid);
  public String  findPageAll(HttpServletRequest request, HttpServletResponse response, Integer page, Integer rows);
  public String  doinit(HttpServletRequest request, HttpServletResponse response);
}
