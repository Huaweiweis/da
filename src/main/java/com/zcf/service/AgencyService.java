package com.zcf.service;

import com.zcf.common.json.Body;
import com.zcf.pojo.Agency;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-14
 */
public interface AgencyService extends IService<Agency> {

	/**
	 * 添加分销
	 * @param choushuiBili
	 * @param topUp
	 * @param personNum
	 * @return
	 */
	Body addagency(Double choushuiBili,Double topUp,Integer personNum,Integer agencyClass);
	/**
	 * 用户修改分销
	 * @param agencyid
	 * @param choushuibili
	 * @param topup
	 * @param personnum
	 * @return
	 */
	Body upagency(Integer agencyid,Double choushuibili,Double topup,Integer personnum,Integer agencyClass);
	/**
	 * 查询所有分销
	 * @return
	 */
	Body getagency();
	/**
	 * 删除分销
	 * @param agencyid
	 * @return
	 */
	Body delagency(Integer agencyid);
}
