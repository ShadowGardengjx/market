package com.hbh.dao;

import com.hbh.entity.Ckretire;
import com.hbh.entity.Sale;
import com.hbh.entity.SaleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SaleMapper {
    int deleteByPrimaryKey(String saleid);

    int insert(Sale record);


    List<Sale> selectByExample(SaleExample example);

    Sale selectByPrimaryKey(String saleid);

    boolean updateByPrimaryKey(Sale record);
    
    List<Sale> getbyparams(@Param("proid") String proid,@Param("cusid")String cusid,@Param("pname")String pname,@Param("cusname")String cusname );
    
    @Select("select SUM(num) as num,pname from sale GROUP BY pname")
    List<Map<String,Object>> count();

    // 获取月度销售数据
    List<Map<String, Object>> getMonthlySales(@Param("months") int months);

    // 按日期范围获取销售数据
    List<Map<String, Object>> getSalesByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    // 获取各产品的销售金额
    List<Map<String, Object>> getSalesAmountByProduct();

    // 按日期范围获取销售金额
    List<Map<String, Object>> getSalesAmountByProductAndDate(@Param("startDate") String startDate,
                                                             @Param("endDate") String endDate);

    // 从视图中查询热销产品榜单
    @Select("SELECT pname, total_amount FROM v_hot_products ORDER BY total_amount DESC LIMIT 5")
    List<Map<String, Object>> getHotProductList();
}