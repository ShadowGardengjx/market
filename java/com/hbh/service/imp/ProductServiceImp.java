package com.hbh.service.imp;

import com.hbh.dao.ProductMapper;
import com.hbh.entity.Product;
import com.hbh.entity.ProductExample;
import com.hbh.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImp  implements IProductService{

	@Autowired
	ProductMapper productMapper;
//	根据产品id删除对应产品信息
	public int deleteByPrimaryKey(String proid) {
		return productMapper.deleteByPrimaryKey(proid);
	}
//有选择的插入
	public int   insert(Product record) {


		return productMapper.insert(record);
	} 
//根据条件查询
	public List<Product> selectByExample(ProductExample example) {
		return productMapper.selectByExample(example);

	}
//根据主键查询
	public Product selectByPrimaryKey(String proid) {
		return productMapper.selectByPrimaryKey(proid);
	}
//根据主键更新
	public boolean updateByPrimaryKey(Product record) {



		return productMapper.updateByPrimaryKey(record);
	}






	public List<Product> getlist() {
		// TODO Auto-generated method stub
		// 查询产品列表

		List<Product> products = productMapper.selectAll();

		// 构造CSV文件路径，这里使用桌面路径
		String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "products.csv";
		File csvFile = new File(desktopPath);

		// 使用try-with-resources自动关闭资源
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
			// 写入CSV头部（可选）
			writer.write('\uFEFF');
			writer.write("商品id,名称,售价,进价,生产日期,过期时间,供应商名称,商品类型\n"); // 假设Product有这些属性

			// 遍历产品列表并写入CSV
			for (Product product : products) {
				writer.write(product.getProid() + "," +product.getPname() +","+product.getPrice() +  "," +product.getInprice() + "," + product.getProdate() + "," + product.getReledate() +","+product.getSupname()+","+product.getProtype()+ "\n");

			}

			System.out.println("Products successfully saved to " + csvFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to save products to CSV file.");
		}
		return productMapper.selectByExample(null);
	}

	//	按输入的条件查询
	public List<Product> getbyparams(String proid, String supname, String pname, String protype) {
		// TODO Auto-generated method stub
		return productMapper.getbyparams(proid, supname, pname, protype);
	}


}
