package com.zcf.mapper;

import com.zcf.pojo.Concurnaysay;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-06
 */
public interface ConcurnaysayMapper extends BaseMapper<Concurnaysay> {

	List<Concurnaysay> jilu(String nicheng);

}
