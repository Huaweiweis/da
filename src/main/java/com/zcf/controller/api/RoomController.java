package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.StringUtils;
import com.zcf.common.json.Body;
import com.zcf.pojo.Room;
import com.zcf.pojo.Userroom;
import com.zcf.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 房间 前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-30
 */
@Controller
@RequestMapping("/room/")
@ResponseBody
@CrossOrigin
public class RoomController {

	@Autowired
	private RoomService roomservice;
	/**
	 * 创建房间
	 * @param roomname
	 * @param roompassword
	 * @param roommulriple
	 * @param userid
	 * @param whetherpassword
	 * @return
	 */
	@PostMapping("entranceroom")
	public Body entranceroom(String roomname, String roompassword, Integer roommulriple,Integer userid,Integer whetherpassword) {
		if(StringUtils.isEmpty(roomname)) {
			return Body.newInstance(501,"房间名称为空");
		}
		if(roommulriple==null) {
			return Body.newInstance(502,"请选择房间倍数");
		}
		if(userid==null) {
			return Body.newInstance(503,"没有用户信息");
		}
		
		return roomservice.entranceroom(roomname, roompassword, roommulriple, userid,whetherpassword);
	}
	/**
	 * TODO 房间风控概率问题；结算
	 *  机器人、用户总积分，总人数
	 *  机器人账户的金额
	 *  时间区间内的流水
	 *  机器人名称修改
	 *  分销（直接改）
	 *  机器人风控
	 *  前端搜索底分
	 *  真实房间不进入机器人
	 * 用户是够能够继续担任房主
	 * @param userid
	 * @param roomintegral
	 * @return
	 */
	@PostMapping("sfintegral")
	public Body sfintegral(Integer userid, Double roomintegral,Double money) {
		if(userid ==null) {
			return Body.newInstance(502,"用户编号为空");
		}
		if(roomintegral <= 0.0) {
			return Body.newInstance(503,"房间编号积分为空");
		}
		if(money==null) {
			return Body.newInstance(505,"发送金额为空");
		}
		return roomservice.sfintegral(userid, roomintegral,money);
	}
	@PostMapping("becomehouseowner")
	public Body becomehouseowner(Integer userid, Integer roomid, Double roomintegral) {
		if(userid==null) {
			return Body.newInstance(502,"用户编号为空");
		}
		if(roomid==null) {
			return Body.newInstance(503,"用户房间");
		}
		if(roomintegral==null) {
			return Body.newInstance(503,"用户编号为空");
		}
		return roomservice.becomehouseowner(userid, roomid, roomintegral);
	}
	/**
	 * 用户查看所有房间
	 * @return
	 */
	@PostMapping("getroom")
	public Body getroom() {
		return roomservice.getroom();
	}
	/**
	 * 点击进入房间
	 * @param roomid
	 * @param userid
	 * @param password
	 * @return
	 */
	@PostMapping("entrancerooms")
	public Body entrancerooms(Integer roomid, Integer userid, String password) {
		
		return roomservice.entrancerooms(roomid, userid, password);
	}

	/**
	 * 点击房间判断房间是否有密码
	 * @param roomid
	 * @return
	 */
	@PostMapping("getwhetherpassword")
	public Body getwhetherpassword(Integer roomid) {
		if(roomid ==null) {
			return Body.newInstance(506,"用户编号为空");
		}
		return roomservice.getwhetherpassword(roomid);
	}

	@PostMapping("quituser")
	public Room quituser(Integer userid,Integer roomid) {
		
		return roomservice.quituser(userid,roomid);
	}

	@PostMapping("jiesan")
	public Body jiesan(Integer userid, Integer roomid) {
		return roomservice.jiesan(userid, roomid);
	}

	@PostMapping("zhuanrang")
	public Body zhuanrang(Integer userid, Integer roomid, Integer yonghuid) {
		return roomservice.zhuanrang(userid, roomid, yonghuid);
	}
	@PostMapping("chengyuan")
	public Body chengyuan(Integer roomid) {
		
		return roomservice.chengyuan(roomid);
	}
	@PostMapping("youxizhongquit")
	public Body youxizhongquit(Integer roomid) {
		return roomservice.youxizhongquit(roomid);
	}
}

