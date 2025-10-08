package com.hbh.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hbh.entity.Supply;
import com.hbh.entity.SupplyExample;


public interface ISupplyMapperService {

    int delete(String supid);

    int insert(Supply record);

    List<Supply> getall();

    Supply getbyid(String supid);

    boolean update(Supply record);
    List<Supply> getbyparams(String supid, String suppname);
}
