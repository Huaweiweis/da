package com.zcf.mapper;

import com.zcf.pojo.Bonus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 发红包，红包金额存放表。 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
public interface BonusMapper extends BaseMapper<Bonus> {

	List<Bonus> select_shuying(@Param("userid")Integer userid, @Param("redenvelopesid")Integer redenvelopesid);

	List<Bonus> selectListsss(@Param("redenvelopesid")Integer redenvelopesid);
	
	 List<Bonus> redenvelopesid(String redenvelopesid);

}
