package com.zcf.service.impl;

import com.zcf.pojo.Userroom;
import com.zcf.mapper.UserroomMapper;
import com.zcf.service.UserroomService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-05
 */
@Service("userroomService")
public class UserroomServiceImpl extends ServiceImpl<UserroomMapper, Userroom> implements UserroomService {

}
