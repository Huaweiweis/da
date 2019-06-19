package com.zcf.service.impl;

import com.zcf.pojo.Bonus;
import com.zcf.mapper.BonusMapper;
import com.zcf.service.BonusService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发红包，红包金额存放表。 服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
@Service
public class BonusServiceImpl extends ServiceImpl<BonusMapper, Bonus> implements BonusService {

}
