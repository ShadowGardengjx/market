package com.hbh.service;

import org.apache.ibatis.annotations.Param;

import com.hbh.entity.Manager;


public interface IManagerService {
	   Manager getbyid( String managerid,String pwd);
	   
	   boolean update(Manager manager);
	   
	   Manager getmanager(String managerid);
}
