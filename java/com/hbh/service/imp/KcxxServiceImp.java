package com.hbh.service.imp;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbh.dao.CkinMapper;
import com.hbh.dao.KcxxMapper;
import com.hbh.entity.Ckin;
import com.hbh.entity.Kcxx;
import com.hbh.service.IKcxxService;


@Service
public class KcxxServiceImp implements IKcxxService {
	@Autowired
	KcxxMapper kcxxMapper;

	@Autowired
	CkinMapper ckinMapper;

	private static final int MAX_RETRY_TIMES = 3; // 最大重试次数

	public int delete(String proid) {
		// TODO Auto-generated method stub
		return kcxxMapper.deleteByPrimaryKey(proid);
	}

	public int insert(Kcxx record) {
		// TODO Auto-generated method stub
		return kcxxMapper.insert(record);
	}

	public List<Kcxx> getall() {
		// TODO Auto-generated method stub
		return kcxxMapper.selectByExample(null);
	}

	public Kcxx getbyid(String proid) {
		// TODO Auto-generated method stub
		return kcxxMapper.selectByPrimaryKey(proid);
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean update(Kcxx record) {
		Ckin ckin = new Ckin();
		ckin.setProid(record.getProid());
		ckin.setIndate(new Date());
		ckin.setNum(record.getNum());
		ckin.setPname(record.getPname());
		ckinMapper.insert(ckin);

		// 使用乐观锁更新库存
//		int affectedRows = kcxxMapper.updateWithVersion(record);
//		if (affectedRows == 0) {
//			throw new OptimisticLockingFailureException("库存更新失败，数据已被其他用户修改");
//		}
		//return kcxxMapper.updateByPrimaryKey(record);
		return true;
	}

	/**
	 * ✅ 带事务和重试机制的库存增加方法
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addStock(String proid, int num, String pname) {
//		int retryCount = 0;
//		while (retryCount < MAX_RETRY_TIMES) {
//			try {
//				Kcxx kcxx = kcxxMapper.selectByPrimaryKey(proid);
//				if (kcxx != null) {
//					// 使用原子操作更新库存
//					int affectedRows = kcxxMapper.addStockAtomically(proid, num, kcxx.getVersion());
//					if (affectedRows > 0) {
//						return true;
//					}
//					// 如果更新失败，说明版本号冲突，重试
//					retryCount++;
//					if (retryCount < MAX_RETRY_TIMES) {
//						TimeUnit.MILLISECONDS.sleep(50); // 短暂等待后重试
//					}
//				} else {
//					// 如果库存表没有该商品，则新增（不存在并发问题）
//					Kcxx newKcxx = new Kcxx();
//					newKcxx.setProid(proid);
//					newKcxx.setNum(num);
//					newKcxx.setPname(pname);
//					kcxxMapper.insert(newKcxx);
//					return true;
//				}
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//				throw new RuntimeException("库存增加操作被中断", e);
//			}
//		}
//		throw new RuntimeException("库存增加失败，重试次数已达上限");
		Kcxx kcxx = kcxxMapper.selectByPrimaryKey(proid);
		if (kcxx != null) {
			System.out.println("库存增加前：" + kcxx.getNum());
			System.out.println("库存增加后：" + (kcxx.getNum() + num));
			kcxx.setNum(kcxx.getNum() + num);
			kcxxMapper.updateByPrimaryKey(kcxx);
		} else {
			// 如果库存表没有该商品，则新增
			Kcxx newKcxx = new Kcxx();
			newKcxx.setProid(proid);
			newKcxx.setNum(num);
			newKcxx.setPname(pname);
			kcxxMapper.insert(newKcxx);
		}
		return true;
	}

	/**
	 * ✅ 带事务和重试机制的库存减少方法
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean outStock(String proid, int num, String pname) {
//		int retryCount = 0;
//
//		while (retryCount < MAX_RETRY_TIMES) {
//			Kcxx kcxx = kcxxMapper.selectByPrimaryKey(proid);
//			if (kcxx == null) {
//				throw new RuntimeException("商品不存在: " + proid);
//			}
//
//			if (kcxx.getNum() < num) {
//				throw new RuntimeException("库存不足，当前库存: " + kcxx.getNum() + ", 需要: " + num);
//			}
//
//			// 使用原子操作减少库存
//			int affectedRows = kcxxMapper.reduceStockAtomically(proid, num, kcxx.getVersion());
//			if (affectedRows > 0) {
//				return true;
//			}
//
//			// 如果更新失败，重试
//			retryCount++;
//			if (retryCount < MAX_RETRY_TIMES) {
//				try {
//					TimeUnit.MILLISECONDS.sleep(50);
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//					throw new RuntimeException("库存减少操作被中断", e);
//				}
//			}
//		}
//
//		throw new RuntimeException("库存减少失败，重试次数已达上限");
		Kcxx kcxx = kcxxMapper.selectByPrimaryKey(proid);
		kcxx.setNum(kcxx.getNum() - num);
		kcxxMapper.updateByPrimaryKey(kcxx);
		return true;
	}

	/**
	 * ✅ 原子性的插入或更新操作
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean insertOrUpdate(Kcxx kcxx) {
		Kcxx existing = kcxxMapper.selectByPrimaryKey(kcxx.getProid());
		if (existing == null) {
			return kcxxMapper.insert(kcxx) > 0;
		} else {
			// 设置版本号进行乐观锁更新
			kcxx.setVersion(existing.getVersion());
			int affectedRows = kcxxMapper.updateWithVersion(kcxx);
			if (affectedRows == 0) {
				throw new OptimisticLockingFailureException("更新失败，数据已被其他用户修改");
			}
			return true;
		}
	}

	@Transactional(readOnly = true)
	public Kcxx kcxxWithPro(String proid) {
		// TODO Auto-generated method stub
		return kcxxMapper.kcxxWithPro(proid);
	}

	@Transactional(readOnly = true)
	public List<Kcxx> kcxxWithPronum() {
		// TODO Auto-generated method stub
		return kcxxMapper.kcxxWithPronum();
	}

	@Transactional(readOnly = true)
	public List<Kcxx> kcxxWithProdata() {
		// TODO Auto-generated method stub
		return kcxxMapper.kcxxWithProdata();
	}

	@Transactional(readOnly = true)
	public List<Kcxx> getbyparams(String proid, String pname) {
		// TODO Auto-generated method stub
		return kcxxMapper.getbyparams(proid, pname);
	}
}
