package com.zcf.service.impl;

import com.zcf.pojo.Difen;
import com.zcf.common.json.Body;
import com.zcf.mapper.DifenMapper;
import com.zcf.service.DifenService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-01
 */
@Service
public class DifenServiceImpl extends ServiceImpl<DifenMapper, Difen> implements DifenService {

	@Autowired
	private DifenMapper difenMapper;
	@Override
	public Body bijiao(Integer diifen) {
		EntityWrapper<Difen> wrapper = new EntityWrapper<>();
		List<Difen> selectList = difenMapper.selectList(wrapper);
		if(selectList!=null) {
			if(selectList.get(0).getZuidi()<=diifen && selectList.get(0).getZuida()>=diifen) {
				return Body.newInstance("底分在一千之内");
			}
		}
		return Body.newInstance(501,"请输入1到1000底分");
	}

}
