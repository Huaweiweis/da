package com.zcf.service;

import com.baomidou.mybatisplus.service.IService;
import com.zcf.common.json.Body;
import com.zcf.pojo.Concurnaysay;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-06
 */
public interface ConcurnaysayService extends IService<Concurnaysay> {

	Body concurnaysays(Integer zhuangtai,Integer caozuorenid,Integer shenqingid);
}
