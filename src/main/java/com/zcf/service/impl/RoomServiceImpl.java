package com.zcf.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zcf.common.json.Body;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.RoomMapper;
import com.zcf.mapper.UserroomMapper;
import com.zcf.mapper.UsersMapper;
import com.zcf.pojo.Room;
import com.zcf.pojo.Userroom;
import com.zcf.pojo.Users;
import com.zcf.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 房间 服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-30
 */
@Service("roomService")
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

	@Autowired
	private RoomMapper roommapper;
	@Autowired
	private UsersMapper usersmapper;
	@Autowired
	private UserroomMapper userroommapper;
	@Override
	public Body entranceroom(String roomname, String roompassword, Integer roommulriple,Integer userid,Integer whetherpassword) {
		Users users = usersmapper.selectById(userid);//查询用户详细信息
		Double jifen =(double) roommulriple*3;//创建房间积分
		if(users.getUserRechargeIntegral()>=jifen) {
			Room room = new Room();
			if(whetherpassword==0) {//是否有密码   0有
				room.setRoomIntegral(jifen);//房间积分
				room.setRoomName(roomname);//房间名称
				room.setRoomPassword(roompassword);//房间密码
				room.setRoomMulriple(roommulriple);//房间倍数
				room.setUserId(userid);//创建房间用户
				room.setWhetherToEnd(0);//未结束
				room.setAddTime(Hutool.parseDateToString());//创建房间时间
				room.setWhetherPassword(0);//有密码
				room.setRoomNum(0);//当前房间人数
				room.setResidue(roommulriple);
				Integer insert = roommapper.insert(room);
				if(insert>0) {
					return Body.newInstance(room);
				} 
			}else if(whetherpassword==1) {//无密码
				room.setRoomIntegral(jifen);//房间积分
				room.setRoomName(roomname);//房间名称
				room.setRoomMulriple(roommulriple);//房间倍数
				room.setUserId(userid);//创建房间用户
				room.setWhetherToEnd(0);//未结束
				room.setAddTime(Hutool.parseDateToString());//创建房间时间
				room.setWhetherPassword(1);//无密码
				room.setRoomNum(0);//当前房间人数
				room.setResidue(roommulriple);
				Integer insert = roommapper.insert(room);
				
				if(insert>0) {
					return Body.newInstance(room);
				} 
			}
		}
		return Body.newInstance(504,"积分不足，请充值");
	}
	@Override
	public Body sfintegral(Integer userid, Double roomintegral,Double money) {
		Users selectById = usersmapper.selectById(userid);
		//用户积分
		if(selectById.getUserRechargeIntegral()>=roomintegral && selectById.getUserRechargeIntegral()>=money){
			return Body.newInstance("可以继续担任房主");
		}else {
			return Body.newInstance(501,"积分不足请提醒充值,或者转让房主");
		}
	}
	@Override
	public Body becomehouseowner(Integer userid, Integer roomid, Double roomintegral) {
		EntityWrapper<Userroom> wrapper =  new EntityWrapper<>();
		wrapper.eq("room_id", roomid);
		List<Userroom> selectList = userroommapper.selectList(wrapper);
		List<Users> list = new ArrayList<>();
		for (Userroom userroom : selectList) {
			userroom.getUserId();
			Users selectById = usersmapper.selectById(userroom.getUserId());
			if(selectById.getUserRechargeIntegral()>roomintegral) {
				list.add(selectById);
			}
		}
		return Body.newInstance(list);
	}
	@Override
	public Body getroom() {
		EntityWrapper<Room> wrapper = new EntityWrapper<>();
		wrapper.eq("whether_to_end", 0);
		List<Room> selectList = roommapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			return Body.newInstance(selectList);
		}
		return Body.newInstance(501,"没有房间");
	}
	@Override
	public Body entrancerooms(Integer roomid, Integer userid, String password) {
		Users selectById = usersmapper.selectById(userid);
		if(selectById.getUserRobot()==1) {
			selectById.setUserRobot(2);
			usersmapper.updateById(selectById);
		}
		Room room = roommapper.selectById(roomid);//房间详细信息
		if(room.getWhetherToEnd()==1) {
			return Body.newInstance(501,"房间已经结束...");
		}
		if(room.getYouxizhong()==2) {
			return Body.newInstance(509,"游戏已开始...");
		}
		EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
		wrapper.eq("room_id", roomid);
		wrapper.eq("user_id", userid);
		List<Userroom> selectList = userroommapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			return Body.newInstance(room);
		}
		Users users = usersmapper.selectById(userid);//用户详细信息
		if(room.getWhetherPassword().intValue()==0) {//房间是够有密码
			if(room.getRoomPassword().equals(password)) {//密码是否相同
				if(room.getRoomIntegral()<=users.getUserRechargeIntegral()&& users.getUserRechargeIntegral() !=null) {//用户积分是否大于当前游戏房间的积分
					if(room.getRoomNum()<=20) {//房间人数是够大于20
						Userroom userroom = new Userroom();
						userroom.setRoomId(roomid);//房间id
						userroom.setUserId(userid);//用户id
						userroom.setAddTime(Hutool.parseDateToString());//添加时间
						Integer insert = userroommapper.insert(userroom);
						Integer ss = room.getRoomNum()+1;//房间人数加一
						room.setRoomNum(ss);
						Integer update = roommapper.updateById(room);
						if(insert!=null && update!=null) {
							return Body.newInstance(room);
						}
					}else {
						return Body.newInstance(504,"人数已满");
					}
				}else {
					return Body.newInstance(502,"积分不足");
				}
			}else {
				return Body.newInstance(503,"密码错误");
			}
		}else {
			if(room.getRoomIntegral()<=users.getUserRechargeIntegral() && users.getUserRechargeIntegral() != null) {//用户积分是否大于当前游戏房间的积分
				if(room.getRoomNum()<=20) {//房间人数是够大于20
					Userroom userroom = new Userroom();
					userroom.setRoomId(roomid);//房间id
					userroom.setUserId(userid);//用户id
					userroom.setAddTime(Hutool.parseDateToString());//添加时间
					Integer insert = userroommapper.insert(userroom);
					Integer ss = room.getRoomNum()+1;//房间人数加一
					room.setRoomNum(ss);
					Integer update = roommapper.updateById(room);
					if(insert!=null && update!=null) {
						return Body.newInstance(room);
					}
				}else {
					return Body.newInstance(504,"人数已满");
				}
			}else {
				return Body.newInstance(502,"积分不足");
			}
		}
		return Body.newInstance(506,"进入失败");
	}
	@Override
	public Body getwhetherpassword(Integer roomid) {
		Room selectById = roommapper.selectById(roomid);
		if(selectById.getWhetherPassword()==0) {
			return Body.newInstance(508,"有密码");
		}else {
			return Body.newInstance(509,"无密码");
		}
	}
	@Override
	public Room quituser(Integer userid,Integer roomid) {
		EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
		wrapper.eq("user_id", userid);
		wrapper.eq("room_id", roomid);
		List<Userroom> list = userroommapper.selectList(wrapper);
		if(!list.isEmpty()) {
			
			Users selectById = usersmapper.selectById(list.get(0).getUserId());
			if(selectById.getUserRobot()==2) {
				selectById.setUserRobot(1);
				usersmapper.updateById(selectById);
			}
			userroommapper.deleteById(list.get(0).getUserRoomId());
			Room room = roommapper.selectById(roomid);
			Integer renshu =  room.getRoomNum()-1;
			if(renshu.intValue()==0) {
				room.setRoomNum(renshu);
				room.setWhetherToEnd(1);
				roommapper.updateById(room);
				return room;
			}else {
				room.setRoomNum(renshu);
				roommapper.updateById(room);
				return room;
			}
		}
		return null;
	}
	@Override
	public Body jiesan(Integer userid, Integer roomid) {
		EntityWrapper<Userroom> wrapperq1 = new EntityWrapper<>();
		wrapperq1.eq("room_id", roomid);
		List<Userroom> selectList2 = userroommapper.selectList(wrapperq1);
		int ss= 0;
		for (int i = 0; i < selectList2.size(); i++) {
			Users users = usersmapper.selectById(selectList2.get(i).getUserId());
			if(users.getUserRobot()==2) {
				users.setUserRobot(1);
				usersmapper.updateById(users);
				
			}
			ss++;
		}
		for (int i = 0; i < selectList2.size(); i++) {
			usersmapper.deleteById(selectList2.get(i).getUserRoomId());
		}
		EntityWrapper<Room> wrapper = new EntityWrapper<>();
		wrapper.eq("user_id", userid);
		wrapper.eq("room_id", roomid);
		List<Room> selectList = roommapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			selectList.get(0).setWhetherToEnd(1);
			int sss = selectList.get(0).getRoomNum()-ss;
			selectList.get(0).setRoomNum(sss);
			Integer updateById = roommapper.updateById(selectList.get(0));
			if(updateById!=null) {
				return Body.newInstance("房主已退出，房间已解散");
			}
			
//			EntityWrapper<Userroom> wrappers = new EntityWrapper<>();
//			wrappers.eq("room_id", roomid);
//			List<Userroom> selectList2 = userroommapper.selectList(wrappers);
//			if(!selectList2.isEmpty()) {
//				for (int i = 0; i < selectList2.size(); i++) {
//					userroommapper.deleteById(selectList2.get(i).getUserRoomId());
//				}
//				
//			}
		}
		return Body.newInstance(501,"解散失败");
	}
	@Override
	public Body zhuanrang(Integer userid, Integer roomid, Integer yonghuid) {
		EntityWrapper<Room> wrapper = new EntityWrapper<>();
		wrapper.eq("user_id", userid);
		wrapper.eq("room_id", roomid);
		List<Room> selectList = roommapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			
			Users users = usersmapper.selectById(yonghuid);
			if(users.getUserRechargeIntegral()>selectList.get(0).getRoomIntegral()) {
				selectList.get(0).setRoomNum(selectList.get(0).getRoomNum());
				selectList.get(0).setUserId(yonghuid);
				roommapper.updateById(selectList.get(0));
				
				EntityWrapper<Userroom> wrapper1 =  new EntityWrapper<>();
				wrapper1.eq("user_id", userid);
				wrapper1.eq("room_id", roomid);
				Integer delete = userroommapper.delete(wrapper1);
				if(delete!= null) {
					
					Users user = usersmapper.selectById(selectList.get(0).getUserId());
					selectList.get(0).setTouxiang(user.getUserImg());
					selectList.get(0).setNicheng(user.getUserNickname());
					selectList.get(0).setMoney(user.getUserRechargeIntegral());
					return Body.newInstance(selectList.get(0));
				}
			}else {
				return Body.newInstance(505,"成员积分不足");
			}
		}
		return Body.newInstance(501,"转让失败");
	}
	@Override
	public Body chengyuan(Integer roomid) {
		EntityWrapper<Userroom>wrapper = new EntityWrapper<>();
		wrapper.eq("room_id", roomid);
		List<Userroom> selectList = userroommapper.selectList(wrapper);
		List<Users> list = new ArrayList<>();
		if(!selectList.isEmpty()) {
			
			for (int i = 0; i < selectList.size(); i++) {
				Users selectById = usersmapper.selectById(selectList.get(i).getUserId());
//				EntityWrapper<Users> wrapperuser = new EntityWrapper<>();
//				wrapperuser.eq("user_id", selectList.get(i).getUserId());
//				List<Users> selectList2 = usersmapper.selectList(wrapperuser);
				list.add(selectById);
			}
			return Body.newInstance(list);
		}
		return Body.newInstance(501,"房间不存在");
	}
	@Override
	public Body youxizhongquit(Integer roomid) {
		Room selectById = roommapper.selectById(roomid);
		if(selectById.getYouxizhong().intValue()==1||selectById.getYouxizhong().intValue()==0) {
			return Body.newInstance("游戏结束");
		}else {
			return Body.newInstance(501,"游戏进行中...");
		}
	}
}
