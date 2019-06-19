package com.zcf.service.impl;

import com.zcf.pojo.Agreement;
import com.zcf.common.json.Body;
import com.zcf.common.result.ResultVo;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.AgreementMapper;
import com.zcf.service.AgreementService;
import com.zcf.utils.AutoPage;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 协议 服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-16
 */
@Service
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements AgreementService {

	@Autowired
	private AgreementMapper agreementmapper;
	@Override
	public Body addagreement(String agreementname) {
		Agreement agreement = new Agreement();
		agreement.setAgreementName(agreementname);
		agreement.setAgreementTime(Hutool.parseDateToString());
		Integer insert = agreementmapper.insert(agreement);
		if(insert!=null) {
			return Body.newInstance("协议增加成功");
		}
		return Body.newInstance(501,"协议增加是失败");
	}
	@Override
	public Body updagreement(String agreementname,Integer agreementid) {
		Agreement agreement = agreementmapper.selectById(agreementid);
		agreement.setAgreementName(agreementname);
		agreement.setAgreementUpdate(Hutool.parseDateToString());
		Integer updateById = agreementmapper.updateById(agreement);
		if(updateById!=null) {
			return Body.newInstance("修改成功");
		}
		return Body.newInstance(501,"修改失败");
	}
	@Override
	public Body getagreement() {
		EntityWrapper<Agreement> wrapper = new EntityWrapper<>();
		List<Agreement> selectList = agreementmapper.selectList(wrapper);
		ResultVo resultVo = AutoPage.work(selectList);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body delagreement(Integer agreementid) {
		Integer deleteById = agreementmapper.deleteById(agreementid);
		if(deleteById != null ) {
			return Body.newInstance("删除成功");
		}
		return Body.newInstance(501,"删除失败");
	}
	@Override
	public Body xieyi() {
		EntityWrapper<Agreement> wrapper = new EntityWrapper<>();
		List<Agreement> selectList = agreementmapper.selectList(wrapper);
		return Body.newInstance(selectList);
	}

}
