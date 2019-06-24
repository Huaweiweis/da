package com.zcf.service;

import com.baomidou.mybatisplus.service.IService;
import com.zcf.common.json.Body;
import com.zcf.pojo.Room;

/**
 * <p>
 * 房间 服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-30
 */
public interface RoomService extends IService<Room> {

	/**
	 * 房主创建房间
	 * @param roomname
	 * @param roompassword
	 * @param roommulriple
	 * @param userid
	 * @return
	 */
	Body entranceroom(String roomname,String roompassword,Integer roommulriple,Integer userid,Integer whetherpassword);
	/**
	 * 判断用户是够有足够积分继续担任房主
	 * @param userid
	 * @param roomintegral
	 * @return
	 */
	Body sfintegral(Integer userid,Double roomintegral,Double money);
	/**
	 * 查询积分充足用户
	 * @param userid
	 * @param roomid
	 * @param roomintegral
	 * @return
	 */
	Body becomehouseowner(Integer userid,Integer roomid,Double roomintegral);
	/**
	 * 查看所有房间
	 * @return
	 */
	Body getroom();
	/**
	 * 用户点击进入房间
	 * @param roomid
	 * @param userid
	 * @return
	 */
	Body entrancerooms(Integer roomid,Integer userid,String password);
	/**
	 * 点击房间判断房间是否有密码接口
	 * @param roomid
	 * @return
	 */
	Body getwhetherpassword(Integer roomid);
	/**
	 * 用户退出房间
	 * @param userid
	 * @return
	 */
	Room quituser(Integer userid,Integer roomid);
	/**
	 * 房主解散房间
	 * @param userid
	 * @param roomid
	 * @return
	 */
	Body jiesan(Integer userid,Integer roomid);
	/**
	 * 房主转让房主
	 * @param userid
	 * @param roomid
	 * @param zhuangrangid
	 * @return
	 */
	Body zhuanrang(Integer userid,Integer roomid,Integer yonghuid);
	
	Body chengyuan(Integer roomid);
	
	Body youxizhongquit(Integer roomid);
	/**
	 * 用户搜索房间底分
	 * @param difen
	 * @return
	 */
	Body chaxunroom(Integer difen);
	
	Body xuniroom(String roomname,Double roomintegral,Integer num);
	
	Body sfjqrroom(Integer roomid);
	/**
	 * 创建虚拟房间，加入机器人
	 * @param roomid
	 * @return
	 */
	Body jiqirenfanzhu(Integer roomid);
}
