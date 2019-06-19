package com.zcf.mapper;

import com.zcf.pojo.Redenvelopes;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 红包 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
public interface RedenvelopesMapper extends BaseMapper<Redenvelopes> {

	Redenvelopes insertid(@Param("money")Double money,@Param("userid")Integer userid,@Param("roomid")Integer roomid,@Param("num")Integer num);
}
