package com.zcf.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zcf.common.json.Body;
import com.zcf.common.result.ResultVo;
import com.zcf.mapper.CustomerserviceMapper;
import com.zcf.pojo.Customerservice;
import com.zcf.service.CustomerserviceService;
import com.zcf.utils.AutoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-06
 */
@Service
public class CustomerserviceServiceImpl extends ServiceImpl<CustomerserviceMapper, Customerservice> implements CustomerserviceService {
	@Autowired
	private CustomerserviceMapper customerservicemapper;
	@Override
	public Body getkefu(String niname) {
		EntityWrapper<Customerservice> wrapper= new EntityWrapper<>();
		wrapper.like("customerservice_name", niname);
		List<Customerservice> selectList = customerservicemapper.selectList(wrapper);
		ResultVo resultVo = AutoPage.work(selectList);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body addcustomer(Integer customerserviceid, String customerservicephone, String customerservicename) {
		Customerservice selectById = customerservicemapper.selectById(customerserviceid);
		selectById.setCustomerserviceName(customerservicename);
		selectById.setCustomerservicePhone(customerservicephone);
		Integer updateById = customerservicemapper.updateById(selectById);
		if(updateById != null) {
			return Body.newInstance("修改成功");
		}
		return Body.newInstance(501,"修改失败");
	}

}
