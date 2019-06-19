package com.zcf.service;

import com.zcf.common.json.Body;
import com.zcf.pojo.Agreement;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 协议 服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-16
 */
public interface AgreementService extends IService<Agreement> {

	/**
	 * 管理员添加协议
	 * @param agreementname
	 * @return
	 */
	Body addagreement(String agreementname);
	/**
	 * 管理员修改协议
	 * @param agreementname
	 * @param agreementid
	 * @return
	 */
	Body updagreement(String agreementname,Integer agreementid);
	/**
	 * 管理员查看所有协议
	 * @return
	 */
	Body getagreement();
	/**
	 * 管理员删除协议
	 * @param agreementid
	 * @return
	 */
	Body delagreement(Integer agreementid);
	
	Body xieyi();
}
