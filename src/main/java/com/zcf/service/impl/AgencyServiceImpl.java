package com.zcf.service.impl;

import com.zcf.pojo.Agency;
import com.zcf.common.json.Body;
import com.zcf.common.result.ResultVo;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.AgencyMapper;
import com.zcf.service.AgencyService;
import com.zcf.utils.AutoPage;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-14
 */
@Service
public class AgencyServiceImpl extends ServiceImpl<AgencyMapper, Agency> implements AgencyService {
	@Autowired
	private AgencyMapper agencymapper;
	@Override
	public Body addagency(Double choushuiBili, Double topUp, Integer personNum,Integer agencyClass) {
		Agency agency = new Agency();
		agency.setChoushuiBili(choushuiBili);//抽水比例
		agency.setTopUp(topUp);//充值金额
		agency.setPersonNum(personNum);//线下金额
		agency.setAddTime(Hutool.parseDateToString());//添加时间
		agency.setAgenyClass(agencyClass);//用户代理等级
		Integer insert = agencymapper.insert(agency);
		if(insert != null) {
			return Body.newInstance("分销项目增加");
		}
		return Body.newInstance(501,"添加失败");
	}
	@Override
	public Body upagency(Integer agencyid, Double choushuibili, Double topup, Integer personnum,Integer agencyClass) {
		Agency agency = agencymapper.selectById(agencyid);
		agency.setChoushuiBili(choushuibili);//抽水比例
		agency.setTopUp(topup);//充值金额
		agency.setPersonNum(personnum);//线下人数
		agency.setAgenyClass(agencyClass);//用户代理等级
		agency.setUpdTime(Hutool.parseDateToString());//修改时间
		Integer updateById = agencymapper.updateById(agency);
		if(updateById != null) {
			return Body.newInstance("修改分销程序成功");
		}
		return Body.newInstance(501,"刷新重试");
	}
	@Override
	public Body getagency() {
		EntityWrapper<Agency> wrapper = new EntityWrapper<>();
		List<Agency> selectList = agencymapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			ResultVo resultVo = AutoPage.work(selectList);
			return Body.newInstance(resultVo);
		}
		return Body.newInstance(501,"没有分销比例");
	}
	@Override
	public Body delagency(Integer agencyid) {
		Integer deleteById = agencymapper.deleteById(agencyid);
		if(deleteById != null) {
			return Body.newInstance("删除成功");
		}
		return Body.newInstance(501,"删除失败");
	}
}
