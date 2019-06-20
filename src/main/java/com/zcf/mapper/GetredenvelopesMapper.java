package com.zcf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zcf.pojo.Getredenvelopes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 领取红包记录表 Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-09
 */
public interface GetredenvelopesMapper extends BaseMapper<Getredenvelopes> {

	List<Getredenvelopes> particulars(@Param("redenvelopesid")Integer redenvelopesid);

	List<Getredenvelopes> getsuccess(@Param("redenvelopesid")Integer redenvelopesid,@Param("userid")Integer userid);

	List<Getredenvelopes> selectliushui(@Param("yonghuming")String yonghuming,@Param("leixing") Integer leixing,@Param("shuxings") Integer shuxings);

	List<Getredenvelopes> fangzhu(@Param("userid")Integer userid,@Param("redenvelopesid") Integer redenvelopesid);

	List<Getredenvelopes> shuyinguser(@Param("redenvelopesid")Integer redenvelopesid);

	List<Getredenvelopes> shuyingusers(@Param("redenvelopesid")Integer redenvelopesid);
	
	List<Getredenvelopes> yonghu(@Param("yonghu")Integer yonghu,@Param("redenvelopesid") Integer redenvelopesid);

}
