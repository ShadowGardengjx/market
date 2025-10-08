package com.hbh.service.imp;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbh.dao.CustomMapper;
import com.hbh.dao.KcxxMapper;
import com.hbh.dao.SaleMapper;
import com.hbh.entity.Custom;
import com.hbh.entity.Kcxx;
import com.hbh.entity.Sale;
import com.hbh.service.ISaleService;

import javax.servlet.http.HttpServletResponse;

@Service
public class SaleServiceImp implements ISaleService {
	@Autowired
	SaleMapper saleMapper;
	@Autowired
	KcxxMapper kcxxMapper;
	@Autowired
	CustomMapper customMapper;

	@Override
	public int delete(String saleid) {
		return saleMapper.deleteByPrimaryKey(saleid);
	}

	@Override
	public int insert(Sale record) {
		String id = record.getProid();
		String cusid = record.getCusid();
		String name = record.getCusname();

		// 检查库存
		Kcxx kcxx = kcxxMapper.selectByPrimaryKey(id);
		if (kcxx == null) {
			throw new RuntimeException("商品库存信息不存在，商品ID: " + id);
		}

		int kcnum = kcxx.getNum();
		int salenum = record.getNum();

		// 添加库存验证
		if (salenum > kcnum) {
			throw new RuntimeException("库存不足，当前库存:" + kcnum + "，请重新输入数量！");
		}

		// 计算新的库存数量（修正之前的错误计算）
		int nownum = kcnum - salenum;

		Custom custom = new Custom();
		if (customMapper.getbyparams(cusid, name).size() == 0) {
			custom.setCusid(cusid);
			custom.setCusname(name);
			if (record.getTotal() > 500) {
				int a = customMapper.insert(custom);
			}
		} else {
			customMapper.updateByPrimaryKey(custom);
		}

		// 更新库存信息
		kcxx.setNum(nownum);
		kcxx.setPname(record.getPname());
		kcxx.setProid(record.getProid());
		kcxx.setMarks(record.getMarks());
		kcxxMapper.updateByPrimaryKey(kcxx);

		return saleMapper.insert(record);
	}

	public List<Sale> getall() {
		// TODO Auto-generated method stub
		return saleMapper.selectByExample(null);
	}

	public Sale getbyid(String saleid) {
		// TODO Auto-generated method stub
		return saleMapper.selectByPrimaryKey(saleid);
	}

	public boolean update(Sale record) {
		// TODO Auto-generated method stub
		return saleMapper.updateByPrimaryKey(record);
	}

	public List<Sale> getbyparams(String proid, String cusid, String pname, String cusname) {
		// TODO Auto-generated method stub
		return saleMapper.getbyparams(proid, cusid, pname, cusname);
	}

	public List<Map<String, Object>> pieData() {
		List<Map<String,Object>> data =new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listdata=saleMapper.count();
		if(listdata.size()>0){
			for(int i=0;i<listdata.size();i++){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("name", listdata.get(i).get("pname"));
				map.put("value", listdata.get(i).get("num"));
				data.add(map);
			}
		}
		return data;
	}

	public Map<String, Object> trendData() {
		Map<String, Object> result = new HashMap<>();

		// 获取最近12个月的销售数据
		List<Map<String, Object>> monthlyData = saleMapper.getMonthlySales(12);

		// 提取月份数据
		List<String> months = new ArrayList<>();
		// 提取产品名称（去重）
		Set<String> productNames = new HashSet<>();
		// 按产品组织数据
		Map<String, List<Object>> productSales = new HashMap<>();

		for (Map<String, Object> data : monthlyData) {
			String month = (String) data.get("month");
			String productName = (String) data.get("pname");
			Object sales = data.get("sales");

			if (!months.contains(month)) {
				months.add(month);
			}

			productNames.add(productName);

			if (!productSales.containsKey(productName)) {
				productSales.put(productName, new ArrayList<>());
			}
			productSales.get(productName).add(sales);
		}

		// 构建ECharts需要的series数据
		List<Map<String, Object>> seriesData = new ArrayList<>();
		for (String productName : productNames) {
			Map<String, Object> series = new HashMap<>();
			series.put("name", productName);
			series.put("type", "line");
			series.put("data", productSales.get(productName));
			series.put("smooth", true);
			seriesData.add(series);
		}

		result.put("months", months);
		result.put("productNames", new ArrayList<>(productNames));
		result.put("seriesData", seriesData);

		return result;
	}

	public Map<String, Object> trendDataByDate(String startDate, String endDate) {
		// 实现按日期范围查询的趋势分析
		// 逻辑与上面类似，但需要添加日期过滤
		return trendData(); // 简化实现，实际应该按日期过滤
	}

	public Map<String, Object> amountData() {
		Map<String, Object> result = new HashMap<>();

		try {
			// 获取各产品的销售金额数据
			List<Map<String, Object>> amountData = saleMapper.getSalesAmountByProduct();
			System.out.println("销售金额原始数据: " + amountData);

			if (amountData == null || amountData.isEmpty()) {
				result.put("productNames", new ArrayList<>());
				result.put("amounts", new ArrayList<>());
				return result;
			}

			List<String> productNames = new ArrayList<>();
			List<BigDecimal> amounts = new ArrayList<>();

			// 按销售金额降序排序
			amountData.sort((a, b) -> {
				BigDecimal amountA = new BigDecimal(((Number) a.get("total_amount")).doubleValue());
				BigDecimal amountB = new BigDecimal(((Number) b.get("total_amount")).doubleValue());
				return amountB.compareTo(amountA); // 降序排序
			});

			for (Map<String, Object> data : amountData) {
				String productName = String.valueOf(data.get("pname"));
				BigDecimal amount = BigDecimal.valueOf(((Number) data.get("total_amount")).doubleValue());

				productNames.add(productName);
				amounts.add(amount);
			}

			result.put("productNames", productNames);
			result.put("amounts", amounts);

			System.out.println("处理后的销售金额数据: " + result);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("productNames", new ArrayList<>());
			result.put("amounts", new ArrayList<>());
		}

		return result;
	}

	public Map<String, Object> amountDataByDate(String startDate, String endDate) {
		// 实现逻辑与上面类似，但需要添加日期过滤
		Map<String, Object> result = new HashMap<>();
		try {
			List<Map<String, Object>> amountData = saleMapper.getSalesAmountByProductAndDate(startDate, endDate);
			// ... 处理逻辑与amountData相同
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, Object> hotProductData() {
		Map<String, Object> result = new HashMap<>();
		try {
			List<Map<String, Object>> hotList = saleMapper.getHotProductList();
			System.out.println("热销产品原始数据: " + hotList);

			if (hotList == null || hotList.isEmpty()) {
				result.put("productNames", new ArrayList<>());
				result.put("amounts", new ArrayList<>());
				return result;
			}

			List<String> productNames = new ArrayList<>();
			List<BigDecimal> amounts = new ArrayList<>();

			for (Map<String, Object> data : hotList) {
				String pname = String.valueOf(data.get("pname"));
				BigDecimal amount = BigDecimal.valueOf(((Number) data.get("total_amount")).doubleValue());
				productNames.add(pname);
				amounts.add(amount);
			}

			result.put("productNames", productNames);
			result.put("amounts", amounts);
			System.out.println("处理后的热销产品榜单数据: " + result);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("productNames", new ArrayList<>());
			result.put("amounts", new ArrayList<>());
		}
		return result;
	}
}
