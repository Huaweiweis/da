package com.zcf.service.impl;

import com.zcf.pojo.Codes;
import com.zcf.mapper.CodesMapper;
import com.zcf.service.CodesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 验证码 服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-28
 */
@Service
public class CodesServiceImpl extends ServiceImpl<CodesMapper, Codes> implements CodesService {

}
