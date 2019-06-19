package com.zcf.mapper;

import com.zcf.pojo.Room;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 房间 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-30
 */
public interface RoomMapper extends BaseMapper<Room> {

	List<Room> selectByIds(@Param("fangjianname")String fangjianname);

}
