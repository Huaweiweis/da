package com.zcf.mapper;

import com.zcf.pojo.Users;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-28
 */
public interface UsersMapper extends BaseMapper<Users> {

	List<Users> getusers(@Param("roomid")Integer roomid);

}
