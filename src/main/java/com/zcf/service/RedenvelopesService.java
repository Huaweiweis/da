package com.zcf.service;

import com.baomidou.mybatisplus.service.IService;
import com.zcf.common.json.Body;
import com.zcf.pojo.Redenvelopes;

/**
 * <p>
 * 红包 服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
public interface RedenvelopesService extends IService<Redenvelopes> {
	/**
	 * 发红包，分包
	 * @param userid//房主id
	 * @param money
	 * @param num
	 * @param roomid
	 * @return
	 */
	Redenvelopes addredenvelopes(Integer userid,Double money,Integer num,Integer roomid);
	/**
	 * 抢红包接口
	 * @param usersid
	 * @param redenvelopesid
	 * @return
	 */
	Body adduserredenvelopes(Integer usersid,Integer redenvelopesid);
	/**
	 * 自动拆包
	 * @param redenvelopesid
	 * @return
	 */
	Body getunclaimed(int redenvelopesid);
	/**
	 * 赢家
	 * @param redenvelopesid
	 * @return
	 */
	Body getsuccess(Integer redenvelopesid,Integer roomid);
	/**
	 * 查看所有用户领取详细
	 * @param redenvelopesid
	 * @return
	 */
	Body getlingquuser(Integer redenvelopesid);
	/**
	 * 流水管理
	 * @return
	 */
	Body getliushui(String yonghuming,Integer leixing,Integer shuxings);
	
	Body usersuccess(Integer redenvelopesid);
	
	Body tuichu(Integer userid,Integer money);
}
