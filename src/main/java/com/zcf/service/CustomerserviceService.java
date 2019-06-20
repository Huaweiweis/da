package com.zcf.service;

import com.baomidou.mybatisplus.service.IService;
import com.zcf.common.json.Body;
import com.zcf.pojo.Customerservice;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-06
 */
public interface CustomerserviceService extends IService<Customerservice> {

	Body getkefu(String niname);
	
	Body addcustomer(Integer customerserviceid,String customerservicephone,String customerservicename);
}
