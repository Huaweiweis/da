package com.zcf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zcf.pojo.Applys;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 申请表 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-17
 */
public interface ApplysMapper extends BaseMapper<Applys> {

	List<Applys> selectByIdy(@Param("yaoqingma")String yaoqingma);

}
