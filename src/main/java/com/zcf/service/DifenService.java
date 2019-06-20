package com.zcf.service;

import com.baomidou.mybatisplus.service.IService;
import com.zcf.common.json.Body;
import com.zcf.pojo.Difen;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-01
 */
public interface DifenService extends IService<Difen> {

	Body bijiao(Integer diifen);
}
