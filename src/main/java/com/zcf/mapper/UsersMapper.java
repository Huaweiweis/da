package com.zcf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zcf.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
