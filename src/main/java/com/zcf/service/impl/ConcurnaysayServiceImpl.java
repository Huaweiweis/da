package com.zcf.service.impl;

import com.zcf.pojo.Accountdetails;
import com.zcf.pojo.Applys;
import com.zcf.pojo.Concurnaysay;
import com.zcf.common.json.Body;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.AccountdetailsMapper;
import com.zcf.mapper.ApplysMapper;
import com.zcf.mapper.ConcurnaysayMapper;
import com.zcf.service.ConcurnaysayService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-06
 */
@Service
public class ConcurnaysayServiceImpl extends ServiceImpl<ConcurnaysayMapper, Concurnaysay> implements ConcurnaysayService {
	@Autowired
	private ConcurnaysayMapper concurnaysaymapper;
	@Autowired
	private ApplysMapper applysmapper;
	
	@Override
	public Body concurnaysays(Integer zhuangtai, Integer caozuorenid, Integer shenqingid) {
		
		Applys applys = applysmapper.selectById(shenqingid);
		
		Concurnaysay concurnaysay = new Concurnaysay();
		concurnaysay.setLeixing(applys.getType());//申请类型
		concurnaysay.setUserid(applys.getUserId());//申请id
		concurnaysay.setZhuangtai(zhuangtai);//1同意2拒绝
		concurnaysay.setShangxiatime(applys.getAddTime());//申请时间
		concurnaysay.setCaozuoren(caozuorenid);
		concurnaysay.setCaozuotime(Hutool.parseDateToString());
		concurnaysay.setMoney(applys.getMoney());
		
		concurnaysaymapper.insert(concurnaysay);
		
		Integer deleteById = applysmapper.deleteById(shenqingid);
		
		if(deleteById != null) {
			return Body.newInstance("操作成功");
					
		}
		
		
		return Body.newInstance(501,"操作失败");
	}

}
