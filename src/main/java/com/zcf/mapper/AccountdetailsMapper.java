package com.zcf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zcf.pojo.Accountdetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-06
 */
public interface AccountdetailsMapper extends BaseMapper<Accountdetails> {
	List<Accountdetails> selectByIds(@Param("nicheng")String nicheng);

	List<Accountdetails> choushuijifen(@Param("userid")Integer userid);

	List<Accountdetails> selectListsss(@Param("userid")Integer userid);

}
