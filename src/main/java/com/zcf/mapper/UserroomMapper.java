package com.zcf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zcf.pojo.Userroom;
import com.zcf.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-05
 */
public interface UserroomMapper extends BaseMapper<Userroom> {

	List<Userroom> selectfangzhu(@Param("roomid")Integer roomid);

	List<Users> selectchengyuan(@Param("roomid")Integer roomid);

}
