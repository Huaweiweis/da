package com.zcf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zcf.mapper.CodeMapper;
import com.zcf.pojo.Code;
import com.zcf.service.CodeService;
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
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {

}
