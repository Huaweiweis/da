package com.zcf.mapper;

import com.zcf.pojo.Redenvelopes;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 红包 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-09
 */
public interface RedenvelopesMapper extends BaseMapper<Redenvelopes> {

	Integer updatestate(int redenvelopesid);

}
