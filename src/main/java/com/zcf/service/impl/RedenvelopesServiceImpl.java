package com.zcf.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zcf.common.json.Body;
import com.zcf.common.result.ResultVo;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.*;
import com.zcf.pojo.*;
import com.zcf.service.RedenvelopesService;
import com.zcf.utils.Agencys;
import com.zcf.utils.AutoPage;
import com.zcf.utils.Pickup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 红包 服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
@Service("redenvelopesservice")
public class RedenvelopesServiceImpl extends ServiceImpl<RedenvelopesMapper, Redenvelopes>
		implements RedenvelopesService {
	@Autowired
	private RedenvelopesMapper redenvelopesmapper;
	@Autowired
	private BonusMapper bonusmapper;
	@Autowired
	private UsersMapper usersmapper;
	@Autowired
	private GetredenvelopesMapper getredenvelopesmapper;
	@Autowired
	private UserroomMapper userroommapper;
	@Autowired
	private AccountdetailsMapper accountdetailsmapper;
	@Autowired
	private AgencyMapper agencymapper;
	@Autowired
	private RoomMapper roommapper;

	@Override
	public Redenvelopes addredenvelopes(Integer userid, Double money, Integer num, Integer roomid) {

		Room room = roommapper.selectById(roomid);
		room.setYouxizhong(2);
		roommapper.updateById(room);

		Accountdetails accountdetails1 = new Accountdetails();
		accountdetails1.setType(3);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
		accountdetails1.setSymbol(2);//一加  二减
		accountdetails1.setMoney(money+"");
		accountdetails1.setUseridId(userid);
		accountdetails1.setBeishangfenid(roomid);
		accountdetails1.setAddTime(Hutool.parseDateToString());
		accountdetailsmapper.insert(accountdetails1);

//		Users selectById = usersmapper.selectById(userid);
//		Double ss= selectById.getUserRechargeIntegral()-money;
//		selectById.setUserRechargeIntegral(ss);
//		usersmapper.updateById(selectById);
//
//		List<BigDecimal> list = Pickup.math(new BigDecimal(money), num);
//		System.out.println(list);
//		Collections.sort(list, new Comparator<BigDecimal>() {
//			public int compare(BigDecimal o1, BigDecimal o2) {
//				if (Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
//					// 比较数和被比较数都为豹子时处理
//					return Pickup.getlast(o1) - Pickup.getlast(o2);
//				}
//				if (Pickup.is_baozi(o1) && !Pickup.is_baozi(o2)) {
//					// 比较数是豹子和被比较数不为豹子
//					return 1;
//				}
//				if (!Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
//					// 比较数不为豹子 和 被比较数为豹子
//					return -1;
//				}
//				Integer result = Pickup.getsumlast(o1) - Pickup.getsumlast(o2);
//				// 末两位求和结果相等 结果处理 比较首位
//				return result == 0 ? Pickup.getfirst(o1) - Pickup.getfirst(o2) : result;
//			}
//		});
//		Redenvelopes redenvelopes = new Redenvelopes();
//		redenvelopes.setValueOfPack(money);
//		redenvelopes.setUserId(userid);
//		redenvelopes.setRoomId(roomid);
//		redenvelopes.setRedEnvelopesNum(num);
//		redenvelopes.setAddTime(Hutool.parseDateToString());
//		redenvelopesmapper.insert(redenvelopes);
//
//		List<Userroom> userroomlist = null;
//		List<Users> list2 = new ArrayList<>();
//		Users users = new Users();
//		EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
//		wrapper.eq("room_id", roomid);
//		userroomlist = userroommapper.selectList(wrapper);
//		for (Userroom userroom : userroomlist) {
//			users = usersmapper.selectById(userroom.getUserId());
//			list2.add(users);
//		}
//		int size = list2.size();
//		for (int i = 0; i < size - 1; i++) {// ---》for循环选取第一个数
//			for (int j = 0; j < size - 1 - i; j++) {// 原数组中，取出第一个数之后，相邻的另外一个数
//				// 比较两个整数的大小
//				if (list2.get(j).getChangci() < list2.get(j + 1).getChangci()) {
//					Users user = list2.get(j);
//					// 使用list集合的set方法，找到位置并将数字赋值到该位置
//					list2.set(j, list2.get(j + 1));
//					list2.set(j + 1, user);
//				}
//			}
//		}
//		System.out.println(list2 + "////////////////////////////");
//		System.out.println(list2 + "-----------------------------------");
//		Collections.reverse(list);
//		System.out.println(list + "===============");
//		int j = 0;
//		for (int i = 0; i < list.size(); i++) {
//			if (list.iterator().hasNext()) {// 判断当前人员是否已经 都领完红包
//				list.get(i);// 當前红包金额
//				list2.get(i).getUserId();// 与红包匹配的用户id
//				/**
//				 * 操作数据库 关联红包金额和用户id
//				 */
//				Bonus bonus = new Bonus();
//				bonus.setBonusMony(list.get(i).doubleValue());// 每一份的红包
//				bonus.setAddTime(Hutool.parseDateToString());// 添加时间
//				bonus.setUserId(list2.get(i).getUserId());
//				bonus.setRoomId(roomid);
//				bonus.setBonusState(0);// 未领取
//				bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());// 领取用户
//				bonus.setDianshu(list2.get(i).getUserId());
//				bonusmapper.insert(bonus);
//				if (j <= 0) {// 判斷此時用戶的場次是否需要減1
//					/**
//					 * 操作数据库 修改用户的场次
//					 */
//					Users userss = usersmapper.selectById(list2.get(i).getUserId());
//					userss.setChangci(userss.getChangci() - 10);
//					usersmapper.updateById(userss);
//				}
//				j++;
//			} else {
//				break;// 结束所有循环
//			}
//		}
//		return redenvelopes;
		Users selectById = usersmapper.selectById(userid);
		if(selectById.getUserRechargeIntegral()<money) {
			return null;
		}
		Double ss= selectById.getUserRechargeIntegral()-money;
		selectById.setUserRechargeIntegral(ss);
		usersmapper.updateById(selectById);

		List<BigDecimal> list = Pickup.math(new BigDecimal(money), num);

		EntityWrapper<Userroom> wrapper12 = new EntityWrapper<>();
		wrapper12.eq("room_id", roomid);
		List<Userroom> userroomlists = userroommapper.selectList(wrapper12);
		List<Users> userlist = new ArrayList<>();
		for (int i = 0; i < userroomlists.size(); i++) {
			Users user = usersmapper.selectById(userroomlists.get(i).getUserId());
			userlist.add(user);
		}
		int counts=0;
		for (Users users : userlist) {
			if(users.getChangci()>0) {
				counts++;
			}
			continue;
		}
		if(counts>0) {
			System.out.println(list);
			Collections.sort(list, new Comparator<BigDecimal>() {
				public int compare(BigDecimal o1, BigDecimal o2) {
					if (Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
						// 比较数和被比较数都为豹子时处理
						return Pickup.getlast(o1) - Pickup.getlast(o2);
					}
					if (Pickup.is_baozi(o1) && !Pickup.is_baozi(o2)) {
						// 比较数是豹子和被比较数不为豹子
						return 1;
					}
					if (!Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
						// 比较数不为豹子 和 被比较数为豹子
						return -1;
					}
					Integer result = Pickup.getsumlast(o1) - Pickup.getsumlast(o2);
					// 末两位求和结果相等 结果处理 比较首位
					return result == 0 ? Pickup.getfirst(o1) - Pickup.getfirst(o2) : result;
				}
			});
			Redenvelopes redenvelopes = new Redenvelopes();
			redenvelopes.setValueOfPack(money);
			redenvelopes.setUserId(userid);
			redenvelopes.setRoomId(roomid);
			redenvelopes.setRedEnvelopesNum(num);
			redenvelopes.setAddTime(Hutool.parseDateToString());
			redenvelopesmapper.insert(redenvelopes);

			List<Userroom> userroomlist = null;
			List<Users> list2 = new ArrayList<>();
			Users users = new Users();
			EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
			wrapper.eq("room_id", roomid);
			userroomlist = userroommapper.selectList(wrapper);
			for (Userroom userroom : userroomlist) {
				users = usersmapper.selectById(userroom.getUserId());
				list2.add(users);
			}
			int size = list2.size();
			for (int i = 0; i < size - 1; i++) {// ---》for循环选取第一个数
				for (int j = 0; j < size - 1 - i; j++) {// 原数组中，取出第一个数之后，相邻的另外一个数
					// 比较两个整数的大小
					if (list2.get(j).getChangci() < list2.get(j + 1).getChangci()) {
						Users user = list2.get(j);
						// 使用list集合的set方法，找到位置并将数字赋值到该位置
						list2.set(j, list2.get(j + 1));
						list2.set(j + 1, user);
					}
				}
			}
			System.out.println(list2 + "////////////////////////////");
			System.out.println(list2 + "-----------------------------------");
			Collections.reverse(list);
			System.out.println(list + "===============");
			int j = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.iterator().hasNext()) {// 判断当前人员是否已经 都领完红包
					list.get(i);// 當前红包金额
					list2.get(i).getUserId();// 与红包匹配的用户id
					/**
					 * 操作数据库 关联红包金额和用户id
					 */
					Bonus bonus = new Bonus();
					bonus.setBonusMony(list.get(i).doubleValue());// 每一份的红包
					bonus.setAddTime(Hutool.parseDateToString());// 添加时间
					bonus.setUserId(list2.get(i).getUserId());
					bonus.setRoomId(roomid);
					bonus.setBonusState(0);// 未领取
					bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());// 领取用户
					bonus.setDianshu(list2.get(i).getUserId());
					bonusmapper.insert(bonus);
					if (j <= 0) {// 判斷此時用戶的場次是否需要減1
						/**
						 * 操作数据库 修改用户的场次
						 */
						Users userss = usersmapper.selectById(list2.get(i).getUserId());
						userss.setChangci(userss.getChangci() - 10);
						usersmapper.updateById(userss);
					}
					j++;
				} else {
					break;// 结束所有循环
				}
			}
			return redenvelopes;
		}else {
			Redenvelopes redenvelopes = new Redenvelopes();
			redenvelopes.setValueOfPack(money);
			redenvelopes.setUserId(userid);
			redenvelopes.setRoomId(roomid);
			redenvelopes.setRedEnvelopesNum(num);
			redenvelopes.setAddTime(Hutool.parseDateToString());
			redenvelopesmapper.insert(redenvelopes);

			for (int i = 0; i < list.size(); i++) {
				Bonus bonus = new Bonus();
				bonus.setBonusMony(list.get(i).doubleValue());// 每一份的红包
				bonus.setAddTime(Hutool.parseDateToString());// 添加时间
				bonus.setUserId(userlist.get(i).getUserId());
				bonus.setRoomId(roomid);
				bonus.setBonusState(0);// 未领取
				bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());// 领取用户
				bonus.setDianshu(userlist.get(i).getUserId());
				bonusmapper.insert(bonus);
			}
			return redenvelopes;
		}

//		System.out.println(list);
//		Collections.sort(list, new Comparator<BigDecimal>() {
//			public int compare(BigDecimal o1, BigDecimal o2) {
//				if (Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
//					// 比较数和被比较数都为豹子时处理
//					return Pickup.getlast(o1) - Pickup.getlast(o2);
//				}
//				if (Pickup.is_baozi(o1) && !Pickup.is_baozi(o2)) {
//					// 比较数是豹子和被比较数不为豹子
//					return 1;
//				}
//				if (!Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) {
//					// 比较数不为豹子 和 被比较数为豹子
//					return -1;
//				}
//				Integer result = Pickup.getsumlast(o1) - Pickup.getsumlast(o2);
//				// 末两位求和结果相等 结果处理 比较首位
//				return result == 0 ? Pickup.getfirst(o1) - Pickup.getfirst(o2) : result;
//			}
//		});
//		Redenvelopes redenvelopes = new Redenvelopes();
//		redenvelopes.setValueOfPack(money);
//		redenvelopes.setUserId(userid);
//		redenvelopes.setRoomId(roomid);
//		redenvelopes.setRedEnvelopesNum(num);
//		redenvelopes.setAddTime(Hutool.parseDateToString());
//		redenvelopesmapper.insert(redenvelopes);
//
//		List<Userroom> userroomlist = null;
//		List<Users> list2 = new ArrayList<>();
//		Users users = new Users();
//		EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
//		wrapper.eq("room_id", roomid);
//		userroomlist = userroommapper.selectList(wrapper);
//		for (Userroom userroom : userroomlist) {
//			users = usersmapper.selectById(userroom.getUserId());
//			list2.add(users);
//		}
//		//判断该房间内玩家场次是否大于0
//		int counts=0;
//		for (Users users2 : list2) {
//			if(users2.getChangci()>0) {
//				counts++;
//			}
//			continue;
//		}
//		if(counts>0) {
//			int size = list2.size();
//			for (int i = 0; i < size - 1; i++) {// ---》for循环选取第一个数
//				for (int j = 0; j < size - 1 - i; j++) {// 原数组中，取出第一个数之后，相邻的另外一个数
//					// 比较两个整数的大小
//					if (list2.get(j).getChangci() < list2.get(j + 1).getChangci()) {
//						Users user = list2.get(j);
//						// 使用list集合的set方法，找到位置并将数字赋值到该位置
//						list2.set(j, list2.get(j + 1));
//						list2.set(j + 1, user);
//					}
//				}
//			}
//			System.out.println(list2 + "////////////////////////////");
//			System.out.println(list2 + "-----------------------------------");
//			Collections.reverse(list);
//			System.out.println(list + "===============");
//			int j = 0;
//			for (int i = 0; i < list.size(); i++) {
//				if (list.iterator().hasNext()) {// 判断当前人员是否已经 都领完红包
//					list.get(i);// 當前红包金额
//					list2.get(i).getUserId();// 与红包匹配的用户id
//					/**
//					 * 操作数据库 关联红包金额和用户id
//					 */
//					Bonus bonus = new Bonus();
//					bonus.setBonusMony(list.get(i).doubleValue());// 每一份的红包
//					bonus.setAddTime(Hutool.parseDateToString());// 添加时间
//					bonus.setUserId(list2.get(i).getUserId());
//					bonus.setRoomId(roomid);
//					bonus.setBonusState(0);// 未领取
//					bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());// 领取用户
//					bonus.setDianshu(list2.get(i).getUserId());
//					bonusmapper.insert(bonus);
//					Users userss = usersmapper.selectById(list2.get(i).getUserId());
//					if (j <= 0 && userss.getChangci().intValue()>0) {// 判斷此時用戶的場次是否需要減1
//						/**
//						 * 操作数据库 修改用户的场次
//						 */
//						userss.setChangci(userss.getChangci() - 10);
//						usersmapper.updateById(userss);
//					}
//					j++;
//				} else {
//					break;// 结束所有循环
//				}
//			}
//			return redenvelopes;
//		}else {
//			for (int i = 0; i < list.size(); i++) {
//					Bonus bonus = new Bonus();
//					bonus.setBonusMony(list.get(i).doubleValue());// 每一份的红包
//					bonus.setAddTime(Hutool.parseDateToString());// 添加时间
//					bonus.setUserId(list2.get(i).getUserId());
//					bonus.setRoomId(roomid);
//					bonus.setBonusState(0);// 未领取
//					bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());// 领取用户
//					bonus.setDianshu(list2.get(i).getUserId());
//					bonusmapper.insert(bonus);
//		}
//			return redenvelopes;
		}

	@Override
	public Body adduserredenvelopes(Integer usersid, Integer redenvelopesid) {
		EntityWrapper<Getredenvelopes> wtapper = new EntityWrapper<>();
		wtapper.eq("user_id", usersid);
		wtapper.eq("redenvelopes_id", redenvelopesid);
		List<Getredenvelopes> selectList2 = getredenvelopesmapper.selectList(wtapper);
		if (!selectList2.isEmpty()) {
			return Body.newInstance(501,"您已经领取过一次");//已经领取过一次
		}

		EntityWrapper<Bonus> wrapper = new EntityWrapper<>();
		wrapper.eq("hongbao_id", redenvelopesid);
		wrapper.eq("dianshu", usersid);
		List<Bonus> selectList = bonusmapper.selectList(wrapper);// 用户唯一被风控的红包
		Bonus bonus = selectList.get(0);

		// 添加被领取的红包
		if (bonus != null) {
			Getredenvelopes getredenvelopes = new Getredenvelopes();
			getredenvelopes.setUserId(bonus.getDianshu());// 领取红包用户id
			getredenvelopes.setRedenvelopesId(redenvelopesid);// 被领取红包
			getredenvelopes.setGetRedenvelopesMoney(bonus.getBonusMony());// 用户领取红包金额
			getredenvelopes.setAddTime(Hutool.parseDateToString());// 领取红包时间
			Integer insert = getredenvelopesmapper.insert(getredenvelopes);

			// 修改分配红包的状态
			bonus.setBonusState(1);// 已经领取
			bonusmapper.updateById(bonus);// 修改被风控红包id

			if (insert != null) {
				Users selectById = usersmapper.selectById(bonus.getUserId());
				bonus.setImg(selectById.getUserImg());
				bonus.setNicheng(selectById.getUserNickname());

				EntityWrapper<Bonus> wrapper1 = new EntityWrapper<>();
				wrapper1.eq("hongbao_id", redenvelopesid);
				wrapper1.eq("bonus_state", 0);
				List<Bonus> selectList3 = bonusmapper.selectList(wrapper1);
				bonus.setGeshu(selectList3.size());

				Users users = usersmapper.selectById(usersid);
				users.setUserRechargeIntegral(users.getUserRechargeIntegral()+getredenvelopes.getGetRedenvelopesMoney());
				usersmapper.updateById(users);

				Accountdetails accountdetails1 = new Accountdetails();
				accountdetails1.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
				accountdetails1.setSymbol(1);//一加  二减
				accountdetails1.setMoney(bonus.getBonusMony()+"");
				accountdetails1.setUseridId(usersid);
				accountdetails1.setBeishangfenid(bonus.getRoomId());
				accountdetails1.setAddTime(Hutool.parseDateToString());
				accountdetailsmapper.insert(accountdetails1);

				if(bonus.getGeshu().intValue()==0) {
					Redenvelopes selectByIds = redenvelopesmapper.selectById(String.valueOf(redenvelopesid));
					selectByIds.setRedEnvelopesState(1);// 自然拆开红包
					redenvelopesmapper.updateById(selectByIds);
				}

				return Body.newInstance(bonus);
			}
		}
		return Body.newInstance(502,"领取失败");
	}

	@Override
	public Body getunclaimed(int redenvelopesid) {
//		Users users = usersmapper.selectById(userid);
//		EntityWrapper<Bonus> wrapper = new EntityWrapper<>();
//		wrapper.eq("hongbao_id", redenvelopesid);
//		wrapper.eq("user_id", userid);
//		wrapper.eq("bonus_state", 0);
//		List<Bonus> selectList = bonusmapper.selectList(wrapper);
//		if(selectList.isEmpty()){
//			return Body.newInstance(501,"您没有需要强拆的红包");
//		}else {
//			Getredenvelopes getredenvelopes = new Getredenvelopes();
//			getredenvelopes.setGetRedenvelopesMoney(selectList.get(0).getBonusMony());// 红包金额
//			getredenvelopes.setUserId(selectList.get(0).getDianshu());// 领取用户id
//			getredenvelopes.setRedenvelopesId(selectList.get(0).getHongbaoId());// 红包id
//			getredenvelopes.setAddTime(Hutool.parseDateToString());// 领取时间
//			getredenvelopesmapper.insert(getredenvelopes);
//			selectList.get(0).setBonusState(1);
//			bonusmapper.updateById(selectList.get(0));
//
//			users.setUserRechargeIntegral(users.getUserRechargeIntegral()+selectList.get(0).getBonusMony());
//			usersmapper.updateById(users);
//		}
//
//		EntityWrapper<Bonus> wrapper12 = new EntityWrapper<>();
//		wrapper12.eq("hongbao_id", redenvelopesid);
//		wrapper12.eq("bonus_state", 0);
//		List<Bonus> selectList12 = bonusmapper.selectList(wrapper12);
//
//		Accountdetails accountdetails1 = new Accountdetails();
//
//		if(selectList12.isEmpty()) {
//			Redenvelopes redenvelopes = redenvelopesmapper.selectById(redenvelopesid);
//			redenvelopes.setRedEnvelopesState(2);// 强拆红包
//			Integer updateById = redenvelopesmapper.updateById(redenvelopes);
//			if(updateById!= null) {
//				selectList.get(0).setImg(users.getUserImg());
//				selectList.get(0).setNicheng(users.getUserNickname());
//
//				accountdetails1.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
//				accountdetails1.setSymbol(1);//一加  二减
//				accountdetails1.setMoney(selectList.get(0).getBonusMony()+"");
//				accountdetails1.setUseridId(userid);
//				accountdetails1.setBeishangfenid(selectList.get(0).getRoomId());
//				accountdetails1.setAddTime(Hutool.parseDateToString());
//				accountdetailsmapper.insert(accountdetails1);
//
//
//				return Body.newInstance(selectList.get(0));
//			}
//		}else {
//			selectList.get(0).setImg(users.getUserImg());
//			selectList.get(0).setNicheng(users.getUserNickname());
//
//			accountdetails1.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
//			accountdetails1.setSymbol(1);//一加  二减
//			accountdetails1.setMoney(selectList.get(0).getBonusMony()+"");
//			accountdetails1.setUseridId(userid);
//			accountdetails1.setBeishangfenid(selectList.get(0).getRoomId());
//			accountdetails1.setAddTime(Hutool.parseDateToString());
//			accountdetailsmapper.insert(accountdetails1);
//
//			return Body.newInstance(selectList.get(0));
//
//		}
//		return Body.newInstance(505,"拆包失败");

		EntityWrapper<Bonus> ew = new EntityWrapper<Bonus>();
		ew.eq("hongbao_id", redenvelopesid);
		 List<Bonus> selectList2 = bonusmapper.selectList(ew);
		 List<Bonus> bo = bonusmapper.redenvelopesid(String.valueOf(redenvelopesid));
		 int bonusState = bo.size();
		 System.out.println("xxxxxxxxxxxxx"+bonusState);

		 System.out.println("+++++++++++++++++++++++++++"+ selectList2.get(0).getBonusState());
		EntityWrapper<Bonus> wrapper = new EntityWrapper<>();
		wrapper.eq("hongbao_id", String.valueOf(redenvelopesid));
		wrapper.eq("bonus_state", 0);
		List<Bonus> selectList = bonusmapper.selectList(wrapper);

		Integer selectCount = bonusmapper.selectCount(wrapper);
		System.out.println("+++++++++++++++++++++++++++"+selectCount);
		int s = selectList.size();
		System.out.println("==5555555555555555555555555555555555"+s);

		Getredenvelopes getredenvelopes = new Getredenvelopes();
		if(bo.isEmpty()) {
			return Body.newInstance(502,"没有需要拆的包");
		}
		Accountdetails accountdetails1 = new Accountdetails();
		for (Bonus bonus : selectList) {
			getredenvelopes.setGetRedenvelopesMoney(bonus.getBonusMony());// 红包金额
			getredenvelopes.setUserId(bonus.getDianshu());// 领取用户id
			getredenvelopes.setRedenvelopesId(bonus.getHongbaoId());// 红包id
			getredenvelopes.setAddTime(Hutool.parseDateToString());// 领取时间
			getredenvelopesmapper.insert(getredenvelopes);
			bonus.setBonusState(1);
			bonusmapper.updateById(bonus);

			accountdetails1.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
			accountdetails1.setSymbol(1);//一加  二减
			accountdetails1.setMoney(bonus.getBonusMony()+"");
			accountdetails1.setUseridId(bonus.getUserId());
			accountdetails1.setBeishangfenid(bonus.getRoomId());
			accountdetails1.setAddTime(Hutool.parseDateToString());
			accountdetailsmapper.insert(accountdetails1);

			Users users = usersmapper.selectById(bonus.getDianshu());
			users.setUserRechargeIntegral(users.getUserRechargeIntegral()+bonus.getBonusMony());
			usersmapper.updateById(users);

		}
		Integer selectById2 = redenvelopesmapper.updatestate(redenvelopesid);
		if (selectById2 != null) {
			for (int i = 0; i < selectList.size(); i++) {
				Users user = usersmapper.selectById(selectList.get(i).getUserId());
				selectList.get(i).setImg(user.getUserImg());
				selectList.get(i).setNicheng(user.getUserNickname());
			}
			return Body.newInstance(selectList);
		}
		return Body.newInstance(505,"拆包失败");
	}

    /**
     * 结算 TODO
     * @param redenvelopesid
     * @param roomid
     * @return
     */
	@Override
	public Body getsuccess(Integer redenvelopesid,Integer roomid) {
		Redenvelopes selectById2 = redenvelopesmapper.selectById(redenvelopesid);

		List<Getredenvelopes> selectList = getredenvelopesmapper.particulars(redenvelopesid);
		List<Getredenvelopes> selectLists=new ArrayList<>();

		Redenvelopes selectById = redenvelopesmapper.selectById(redenvelopesid);
		for (int i = 0; i < selectList.size(); i++) {

			if(selectList.get(i).getUsersid().intValue()==selectById.getUserId().intValue()) {
				continue;
//				selectList.remove(selectList.get(i));
			}else {
				Getredenvelopes getredenvelopes=new Getredenvelopes();
				getredenvelopes.setUsersid(selectList.get(i).getUsersid());
				getredenvelopes.setMoney(selectList.get(i).getMoney());
				getredenvelopes.setRedenvelopesId(selectList.get(i).getRedenvelopesId());
				selectLists.add(getredenvelopes);
			}
		}


		List<Getredenvelopes>  fangzhu= getredenvelopesmapper.fangzhu(selectById.getUserId(),redenvelopesid);//抢红包房主金额

		Users fangzhuuser = usersmapper.selectById(fangzhu.get(0).getUsersid());
		Room room = roommapper.selectById(roomid);

		for (int i = 0; i < selectLists.size(); i++) {
			String[] numArr = selectLists.get(i).getMoney().toString().replace(".", "").split("");//用户
			String[] numArr1 = fangzhu.get(0).getMoney().toString().replace(".", "").split("");//房主
			if(numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2])) {//判断是否用户是豹子
				if(numArr1[0].equals(numArr1[1]) && numArr1[1].equals(numArr1[2])) {//如果用户是豹子，判断房主是不是豹子
					if(Integer.parseInt(numArr[0])<Integer.parseInt(numArr1[0])){//两家都是豹子，房主豹子大
						Double yingkou= (double) (room.getResidue()*3);

						Users users = usersmapper.selectById(selectLists.get(i).getUsersid());//用户输
						Double yonghu= users.getUserRechargeIntegral()-yingkou;
						users.setUserRechargeIntegral(yonghu);
						usersmapper.updateById(users);//修改用户余额

						Accountdetails accountdetails = new Accountdetails();
						accountdetails.setMoney(yingkou+"");
						accountdetails.setType(4);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setSymbol(2);//加减符号    1加号，2减号
						accountdetails.setUseridId(users.getUserId());//输钱人的id   用户输
						accountdetailsmapper.insert(accountdetails);

						EntityWrapper<Users> wrapper =  new EntityWrapper<>();
						wrapper.eq("user_invitation_code", fangzhuuser.getTheHigherTheAgent());
						List<Users> selectList2 = usersmapper.selectList(wrapper);
						if(!selectList2.isEmpty()) {//有上级代理
							Agencys agencys = new Agencys();
							Double pingtai= yingkou*0.01;//平台抽取剩下金额
							if(pingtai>10) {
								Double dayu= pingtai-10;//多出金额
								Double fangzhuyingde = yingkou-pingtai+dayu;
								//分销
								agencys.defautTest(selectList2.get(0).getUserId(), 10.0,fangzhuuser.getUserId(),fangzhuyingde);//上级抽水

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",users.getUserId());
								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
								selectList31.get(0).setShuying(1);//一输   2赢
								getredenvelopesmapper.updateById(selectList31.get(0));
							}else {
								Double chouqu = yingkou-pingtai;
								Double fangzhuyingde = yingkou-pingtai;
//								agencys.defautTest(selectList2.get(0).getUserId(), chouqu);//上级抽水
//
								Double fangzhus = fangzhuuser.getUserRechargeIntegral()+fangzhuyingde;
								fangzhuuser.setUserRechargeIntegral(fangzhus);
								usersmapper.updateById(fangzhuuser);//房主应得；

								Accountdetails fangzhuying = new Accountdetails();
								fangzhuying.setMoney(fangzhuyingde+"");
								fangzhuying.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
								fangzhuying.setAddTime(Hutool.parseDateToString());
								fangzhuying.setSymbol(1);//加减符号    1加号，2减号
								fangzhuying.setUseridId(fangzhuuser.getUserId());//房主赢 房主id
								accountdetailsmapper.insert(fangzhuying);

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",users.getUserId());
								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
								selectList31.get(0).setShuying(1);//一输   2赢
								getredenvelopesmapper.updateById(selectList31.get(0));

							}
						}else {//没有上级代理，判断自己是不是代理

						}

					}else {//用户豹子大

					}
			}else {//用户是豹子

			}
			}else if(numArr1[0].equals(numArr1[1]) && numArr1[1].equals(numArr1[2])) {//庄家是豹子，

			}else {//庄家和用户都不是豹子    比较后两位相加值，   如果等，比较左边一位，如果一样相等，房主赢
				//两位都不是豹子
				//赢家后两位
				String yingjjias = selectLists.get(i).getMoney();
				String[] str_Arr = yingjjias.substring(yingjjias.lastIndexOf('.') + 1, yingjjias.length()).split("");
				Integer index_1 = Integer.parseInt(str_Arr[0]);
				Integer index_2 = Integer.parseInt(str_Arr[1]);
				String yingjiamonye = (index_1+index_2)+"";
				//房主后两位
				String fangzhusw = fangzhu.get(0).getMoney();
				String[] str_Arrs = fangzhusw.substring(fangzhusw.lastIndexOf('.') + 1, fangzhusw.length()).split("");
				Integer index_11 = Integer.parseInt(str_Arrs[0]);
				Integer index_22 = Integer.parseInt(str_Arrs[1]);
				String fangzhumonye = (index_11+index_22)+"";

				if(yingjiamonye.length()>1) {
					yingjiamonye = yingjiamonye.substring(yingjiamonye.length() - 1, yingjiamonye.length());
				}
				if(fangzhumonye.length()>1) {
					fangzhumonye = fangzhumonye.substring(fangzhumonye.length() - 1, fangzhumonye.length());
				}
				//用户抢到的钱
				Integer index_33  = Integer.parseInt(yingjiamonye);
				//房主抢到的钱
				Integer index_44  = Integer.parseInt(fangzhumonye);

				String yonghuzuobian = selectLists.get(i).getMoney();
				String substring = yonghuzuobian.substring(0, 1);
				int yonghuzuo1 = Integer.parseInt(substring);

				String fangzhuzuobian = fangzhu.get(0).getMoney();
				String substring2 = fangzhuzuobian.substring(0, 1);
				int fangzhuzuo1 = Integer.parseInt(substring2);

				if(index_33.intValue()==index_44.intValue()) {//判断用户和庄家右边两位是否相等，如果相等左边一位
					if(yonghuzuo1 == fangzhuzuo1) {//房主赢
						if(index_44==8 || index_44==9) {
						}else {
						}
					}else if(yonghuzuo1<fangzhuzuo1){//房主赢
						if(index_44==8 || index_44==9) {
						}else {
						}
					}else {//用户赢
						if(index_33==8 || index_33==9) {
						}else {
						}
					}
				}else if(index_33.intValue()<index_44.intValue()) {//庄家赢
					//房主赢
					if(index_44==8 || index_44==9) {
					}else {
					}

				}else {//用户赢
					//用户赢
					if(index_33==8 || index_33==9) {
					}else {
					}
				}
			}
		}

		List<Getredenvelopes> list = getredenvelopesmapper.shuyinguser(redenvelopesid);
		for (int j = 0; j < list.size(); j++) {
			String  s= list.get(j).getAddTime();
			String sss = s.substring(11);
			list.get(j).setAddTime(sss);
			EntityWrapper<Users> wrapper = new EntityWrapper<>();
			wrapper.eq("user_id", list.get(j).getUserId());
			List<Users> selectList2 = usersmapper.selectList(wrapper);
			list.get(j).setUsers(selectList2.get(0).getUserRechargeIntegral());
		}

		return Body.newInstance(list);

//			for (int i = 0; i < selectLists.size(); i++) {
//				String[] numArr = selectLists.get(i).getMoney().toString().replace(".", "").split("");
//				String[] numArr1 = fangzhu.get(0).getMoney().toString().replace(".", "").split("");
//
//				if(numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2])) {//用户豹子
//					if(numArr1[0].equals(numArr1[1]) && numArr1[1].equals(numArr1[2])) {//房主豹子
//						if(Integer.parseInt(numArr[0]) > Integer.parseInt(numArr1[0])) {
//							//用户赢
//							Users user = usersmapper.selectById(selectLists.get(i).getUsersid());//赢用户id
//							Double yingjiamoney = Double.valueOf(selectLists.get(i).getMoney())*2;//用户应该翻倍得到金额
//
//							Users yingjiauser = usersmapper.selectById(userid);
//							Double yingde = yingjiauser.getUserRechargeIntegral()-yingjiamoney;
//							yingjiauser.setUserRechargeIntegral(yingde);
//							usersmapper.updateById(yingjiauser);//修改房主
//
//							//算应该反水金额
//							Double fanshui = Double.valueOf(selectLists.get(i).getMoney())*3;
//
//							//上级代理用户
//							EntityWrapper<Users> wrapper = new EntityWrapper<>();
//							wrapper.eq("the_higher_the_agent", user.getTheHigherTheAgent());
//							List<Users> fanhuiyonghu = usersmapper.selectList(wrapper);
//							if(!fanhuiyonghu.isEmpty()) {
//								if(fanhuiyonghu.get(0).getUserGrade()<user.getUserGrade()) {
//									//进行反水，查询分销表用户设置的抽水比例
//									EntityWrapper<Agency> daili = new EntityWrapper<>();
//									daili.eq("ageny_class", fanhuiyonghu.get(0).getUserGrade());
//									List<Agency> selectList2 = agencymapper.selectList(daili);
//									Agency agency = selectList2.get(0);
//									Double ss = (fanshui*0.01)*agency.getChoushuiBili();//应该反水上级的金额
//									//房主应得金额
//									Double asd= fanshui-ss;
//
//									//上级用户原有金额加上用户分销金额
//									fanhuiyonghu.get(0).setUserRechargeIntegral(fanhuiyonghu.get(0).getUserRechargeIntegral()+ss);
//									//修改上级用户金额
//									usersmapper.updateById(fanhuiyonghu.get(0));
//
//									//修改用户应得钱
//									Double yonghuyingdee = user.getUserRechargeIntegral()+asd;
//									user.setUserRechargeIntegral(yonghuyingdee);
//									usersmapper.updateById(user);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									Accountdetails accountdetails2 = new Accountdetails();//上级用户
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setMoney(asd+"");
//									accountdetails.setUseridId(user.getUserId());
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(yingjiamoney+"");
//									accountdetails1.setUseridId(userid);
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									accountdetails2.setAddTime(Hutool.parseDateToString());
//									accountdetails2.setType(6);//红包扣除
//									accountdetails2.setSymbol(1);//一加  二减
//									accountdetails2.setMoney(ss+"");
//									accountdetails2.setUseridId(fanhuiyonghu.get(0).getUserId());
//									accountdetails2.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails2);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}else {
//									//不反水
//									Double ss = fanshui*0.01;//应该反水上级的金额
//									Double asd= fanshui-ss;
//
//									//修改用户应得钱
//									Double yonghuyingdee = user.getUserRechargeIntegral()+asd;
//									user.setUserRechargeIntegral(yonghuyingdee);
//									usersmapper.updateById(user);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setMoney(asd+"");
//									accountdetails.setUseridId(user.getUserId());
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(yingjiamoney+"");
//									accountdetails1.setUseridId(userid);
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}else {
//								//没有上级用户
//								Double ss = fanshui*0.01;//应该反水上级的金额          0.01赢取金额直接消失，平台领取
//								Double asd= fanshui-ss;
//
//								//修改用户应得钱
//								Double yonghuyingdee = user.getUserRechargeIntegral()+asd;
//								user.setUserRechargeIntegral(yonghuyingdee);
//								usersmapper.updateById(user);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setMoney(asd+"");
//								accountdetails.setUseridId(user.getUserId());
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(yingjiamoney+"");
//								accountdetails1.setUseridId(userid);
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(1);
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//
//						}else if(Integer.parseInt(numArr[0])<Integer.parseInt(numArr1[0])) {//房主豹子比用户豹子大
//
//							Double ss = Double.parseDouble( fangzhu.get(0).getMoney())*3;//房主赢钱总额
//							Users shubaoziyonghu = usersmapper.selectById(selectLists.get(i).getUsersid());//同样是豹子的用户
//							Double yonghushu = shubaoziyonghu.getUserRechargeIntegral()-ss;//用户输给房主总额
//							shubaoziyonghu.setUserRechargeIntegral(yonghushu);
//							usersmapper.updateById(shubaoziyonghu);//修改用户输钱总额
//
//							//房主用户
//							Users yingjiauser = usersmapper.selectById(userid);
//							//查房主上级代理
//							EntityWrapper<Users> wrapper = new EntityWrapper<>();
//							wrapper.eq("the_higher_the_agent", yingjiauser.getTheHigherTheAgent());
//							List<Users> fanhuiyonghu = usersmapper.selectList(wrapper);
//							if(!fanhuiyonghu.isEmpty()) {
//								//判断上级代理登记时候有自己高
//								if(fanhuiyonghu.get(0).getUserGrade()<yingjiauser.getUserGrade()) {
//									EntityWrapper<Agency> daili = new EntityWrapper<>();
//									daili.eq("ageny_class", fanhuiyonghu.get(0).getUserGrade());
//									List<Agency> selectList2 = agencymapper.selectList(daili);
//									Agency agency = selectList2.get(0);//上级代理抽水比例
//									//计算上级抽水金额
//									Double shangji = (ss*0.01)*agency.getChoushuiBili();//计算抽水
//									Double asd= ss-shangji;
//
//									Double shangjiuser= fanhuiyonghu.get(0).getUserRechargeIntegral()+shangji;//上级用户应得钱
//									fanhuiyonghu.get(0).setUserRechargeIntegral(shangjiuser);
//									usersmapper.updateById(fanhuiyonghu.get(0));
//
//									Double fangzhuyingdes = yingjiauser.getUserRechargeIntegral()+asd;
//									yingjiauser.setUserRechargeIntegral(fangzhuyingdes);
//									usersmapper.updateById(yingjiauser);//房主最终应得
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									Accountdetails accountdetails2 = new Accountdetails();//上级用户
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(shubaoziyonghu.getUserId());
//									accountdetails.setMoney(asd+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(ss+"");
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetails1.setUseridId(shubaoziyonghu.getUserId());
//									accountdetailsmapper.insert(accountdetails1);
//
//									accountdetails2.setAddTime(Hutool.parseDateToString());
//									accountdetails2.setType(6);//红包分红
//									accountdetails2.setSymbol(1);//一加  二减
//									accountdetails2.setMoney(shangji+"");
//									accountdetails2.setBeishangfenid(roomid);
//									accountdetails2.setUseridId(fanhuiyonghu.get(0).getUserId());
//									accountdetailsmapper.insert(accountdetails2);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}else {
//									//上级代理没有自己等级高，不给上级分红吗 百分之一的抽取给平台
//									Double shangji = ss*0.01;//计算抽水
//									Double asd= ss-shangji;
//
//									Double fangzhuyingdes = yingjiauser.getUserRechargeIntegral()+asd;
//									yingjiauser.setUserRechargeIntegral(fangzhuyingdes);
//									usersmapper.updateById(yingjiauser);//房主最终应得
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(2);//一加  二减
//									accountdetails.setUseridId(shubaoziyonghu.getUserId());
//									accountdetails.setMoney(asd+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(1);//一加  二减
//									accountdetails1.setMoney(ss+"");
//									accountdetails1.setUseridId(yingjiauser.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}else {
//								//没有上级代理
//								Double shangji = ss*0.01;//计算抽水
//								Double asd= ss-shangji;
//
//								Double fangzhuyingdes = yingjiauser.getUserRechargeIntegral()+asd;
//								yingjiauser.setUserRechargeIntegral(fangzhuyingdes);
//								usersmapper.updateById(yingjiauser);//房主最终应得
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(2);//一加  二减
//								accountdetails.setUseridId(shubaoziyonghu.getUserId());
//								accountdetails.setMoney(asd+"");
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(1);//一加  二减
//								accountdetails1.setMoney(ss+"");
//								accountdetails1.setUseridId(yingjiauser.getUserId());
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(2);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//						}
//					}else {
//						//用户赢
//						Users user = usersmapper.selectById(selectLists.get(i).getUsersid());//赢用户id
//						Double yingjiamoney = Double.valueOf(selectLists.get(i).getMoney())*2;//用户应该翻倍得到金额
//
//						Users yingjiauser = usersmapper.selectById(userid);
//						Double yingde = yingjiauser.getUserRechargeIntegral()-yingjiamoney;
//						yingjiauser.setUserRechargeIntegral(yingde);
//						usersmapper.updateById(yingjiauser);//修改房主
//
//						//算应该反水金额
//						Double fanshui = Double.valueOf(selectLists.get(i).getMoney())*3;
//
//						//上级代理用户
//						EntityWrapper<Users> wrapper = new EntityWrapper<>();
//						wrapper.eq("the_higher_the_agent", user.getTheHigherTheAgent());
//						List<Users> fanhuiyonghu = usersmapper.selectList(wrapper);
//						if(!fanhuiyonghu.isEmpty()) {
//							if(fanhuiyonghu.get(0).getUserGrade()<user.getUserGrade()) {
//								//进行反水，查询分销表用户设置的抽水比例
//								EntityWrapper<Agency> daili = new EntityWrapper<>();
//								daili.eq("ageny_class", fanhuiyonghu.get(0).getUserGrade());
//								List<Agency> selectList2 = agencymapper.selectList(daili);
//								Agency agency = selectList2.get(0);
//								Double ss = (fanshui*0.01)*agency.getChoushuiBili();//应该反水上级的金额
//								//房主应得金额
//								Double asd= fanshui-ss;
//
//								//上级用户原有金额加上用户分销金额
//								fanhuiyonghu.get(0).setUserRechargeIntegral(fanhuiyonghu.get(0).getUserRechargeIntegral()+ss);
//								//修改上级用户金额
//								usersmapper.updateById(fanhuiyonghu.get(0));
//
//								//修改用户应得钱
//								Double yonghuyingdee = user.getUserRechargeIntegral()+asd;
//								user.setUserRechargeIntegral(yonghuyingdee);
//								usersmapper.updateById(user);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								Accountdetails accountdetails2 = new Accountdetails();//上级用户
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setMoney(asd+"");
//								accountdetails.setUseridId(user.getUserId());
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(yingjiamoney+"");
//								accountdetails1.setUseridId(userid);
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								accountdetails2.setAddTime(Hutool.parseDateToString());
//								accountdetails2.setType(6);//红包扣除
//								accountdetails2.setSymbol(1);//一加  二减
//								accountdetails2.setMoney(ss+"");
//								accountdetails2.setBeishangfenid(roomid);
//								accountdetails2.setUseridId(fanhuiyonghu.get(0).getUserId());
//								accountdetailsmapper.insert(accountdetails2);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(1);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}else {
//								//不反水
//								Double ss = fanshui*0.01;//应该反水上级的金额
//								Double asd= fanshui-ss;
//
//								//修改用户应得钱
//								Double yonghuyingdee = user.getUserRechargeIntegral()+asd;
//								user.setUserRechargeIntegral(yonghuyingdee);
//								usersmapper.updateById(user);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setMoney(asd+"");
//								accountdetails.setUseridId(user.getUserId());
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(yingjiamoney+"");
//								accountdetails1.setUseridId(userid);
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(1);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//						}else {
//							//没有上级用户
//							Double ss = fanshui*0.01;//应该反水上级的金额          0.01赢取金额直接消失，平台领取
//							Double asd= fanshui-ss;
//
//							//修改用户应得钱
//							Double yonghuyingdee = user.getUserRechargeIntegral()+asd;
//							user.setUserRechargeIntegral(yonghuyingdee);
//							usersmapper.updateById(user);
//
//							Accountdetails accountdetails = new Accountdetails();//用户
//							Accountdetails accountdetails1 = new Accountdetails();//房主
//							accountdetails.setAddTime(Hutool.parseDateToString());
//							accountdetails.setType(5);
//							accountdetails.setSymbol(1);//一加  二减
//							accountdetails.setMoney(asd+"");
//							accountdetails.setUseridId(user.getUserId());
//							accountdetails.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails);
//
//							accountdetails1.setAddTime(Hutool.parseDateToString());
//							accountdetails1.setType(4);//红包扣除
//							accountdetails1.setSymbol(2);//一加  二减
//							accountdetails1.setMoney(yingjiamoney+"");
//							accountdetails1.setUseridId(userid);
//							accountdetails1.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails1);
//
//							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//							wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//							wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//							List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//							selectList31.get(0).setShuying(1);//1赢  二输
//							getredenvelopesmapper.updateById(selectList31.get(0));
//						}
//					}
//				}else if(numArr1[0].equals(numArr1[1]) && numArr1[1].equals(numArr1[2])) {
//					//房主豹子比用户豹子大
//
//					Double ss = Double.parseDouble( fangzhu.get(0).getMoney())*3;//房主赢钱总额
//					Users shubaoziyonghu = usersmapper.selectById(selectLists.get(i).getUsersid());//同样是豹子的用户
//					Double yonghushu = shubaoziyonghu.getUserRechargeIntegral()-ss;//用户输给房主总额
//					shubaoziyonghu.setUserRechargeIntegral(yonghushu);
//					usersmapper.updateById(shubaoziyonghu);//修改用户输钱总额
//
//					//房主用户
//					Users yingjiauser = usersmapper.selectById(userid);
//					//查房主上级代理
//					EntityWrapper<Users> wrapper = new EntityWrapper<>();
//					wrapper.eq("the_higher_the_agent", yingjiauser.getTheHigherTheAgent());
//					List<Users> fanhuiyonghu = usersmapper.selectList(wrapper);
//					if(!fanhuiyonghu.isEmpty()) {
//						//判断上级代理登记时候有自己高
//						if(fanhuiyonghu.get(0).getUserGrade()<yingjiauser.getUserGrade()) {
//							EntityWrapper<Agency> daili = new EntityWrapper<>();
//							daili.eq("ageny_class", fanhuiyonghu.get(0).getUserGrade());
//							List<Agency> selectList2 = agencymapper.selectList(daili);
//							Agency agency = selectList2.get(0);//上级代理抽水比例
//							//计算上级抽水金额
//							Double shangji = (ss*0.01)*agency.getChoushuiBili();//计算抽水
//							Double asd= ss-shangji;
//
//							Double shangjiuser= fanhuiyonghu.get(0).getUserRechargeIntegral()+shangji;//上级用户应得钱
//							fanhuiyonghu.get(0).setUserRechargeIntegral(shangjiuser);
//							usersmapper.updateById(fanhuiyonghu.get(0));
//
//							Double fangzhuyingdes = yingjiauser.getUserRechargeIntegral()+asd;
//							yingjiauser.setUserRechargeIntegral(fangzhuyingdes);
//							usersmapper.updateById(yingjiauser);//房主最终应得
//
//							Accountdetails accountdetails = new Accountdetails();//用户
//							Accountdetails accountdetails1 = new Accountdetails();//房主
//							Accountdetails accountdetails2 = new Accountdetails();//上级用户
//							accountdetails.setAddTime(Hutool.parseDateToString());
//							accountdetails.setType(5);
//							accountdetails.setSymbol(1);//一加  二减
//							accountdetails.setUseridId(shubaoziyonghu.getUserId());
//							accountdetails.setMoney(asd+"");
//							accountdetails.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails);
//
//							accountdetails1.setAddTime(Hutool.parseDateToString());
//							accountdetails1.setType(4);//红包扣除
//							accountdetails1.setSymbol(2);//一加  二减
//							accountdetails1.setMoney(ss+"");
//							accountdetails1.setUseridId(shubaoziyonghu.getUserId());
//							accountdetails1.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails1);
//
//							accountdetails2.setAddTime(Hutool.parseDateToString());
//							accountdetails2.setType(6);//红包分红
//							accountdetails2.setSymbol(1);//一加  二减
//							accountdetails2.setMoney(shangji+"");
//							accountdetails2.setUseridId(fanhuiyonghu.get(0).getUserId());
//							accountdetails2.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails2);
//
//							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//							wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//							wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//							List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//							selectList31.get(0).setShuying(2);//1赢  二输
//							getredenvelopesmapper.updateById(selectList31.get(0));
//						}else {
//							//上级代理没有自己等级高，不给上级分红吗 百分之一的抽取给平台
//							Double shangji = ss*0.01;//计算抽水
//							Double asd= ss-shangji;
//
//							Double fangzhuyingdes = yingjiauser.getUserRechargeIntegral()+asd;
//							yingjiauser.setUserRechargeIntegral(fangzhuyingdes);
//							usersmapper.updateById(yingjiauser);//房主最终应得
//
//							Accountdetails accountdetails = new Accountdetails();//用户
//							Accountdetails accountdetails1 = new Accountdetails();//房主
//							accountdetails.setAddTime(Hutool.parseDateToString());
//							accountdetails.setType(5);
//							accountdetails.setSymbol(1);//一加  二减
//							accountdetails.setUseridId(shubaoziyonghu.getUserId());
//							accountdetails.setMoney(asd+"");
//							accountdetails.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails);
//
//							accountdetails1.setAddTime(Hutool.parseDateToString());
//							accountdetails1.setType(4);//红包扣除
//							accountdetails1.setSymbol(2);//一加  二减
//							accountdetails1.setMoney(ss+"");
//							accountdetails1.setUseridId(shubaoziyonghu.getUserId());
//							accountdetails1.setBeishangfenid(roomid);
//							accountdetailsmapper.insert(accountdetails1);
//
//							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//							wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//							wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//							List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//							selectList31.get(0).setShuying(2);//1赢  二输
//							getredenvelopesmapper.updateById(selectList31.get(0));
//						}
//
//					}else {
//						//没有上级代理
//						Double shangji = ss*0.01;//计算抽水
//						Double asd= ss-shangji;
//
//						Double fangzhuyingdes = yingjiauser.getUserRechargeIntegral()+asd;
//						yingjiauser.setUserRechargeIntegral(fangzhuyingdes);
//						usersmapper.updateById(yingjiauser);//房主最终应得
//
//						Accountdetails accountdetails = new Accountdetails();//用户
//						Accountdetails accountdetails1 = new Accountdetails();//房主
//						accountdetails.setAddTime(Hutool.parseDateToString());
//						accountdetails.setType(5);
//						accountdetails.setSymbol(1);//一加  二减
//						accountdetails.setUseridId(shubaoziyonghu.getUserId());
//						accountdetails.setMoney(asd+"");
//						accountdetails.setBeishangfenid(roomid);
//						accountdetailsmapper.insert(accountdetails);
//
//						accountdetails1.setAddTime(Hutool.parseDateToString());
//						accountdetails1.setType(4);//红包扣除
//						accountdetails1.setSymbol(2);//一加  二减
//						accountdetails1.setMoney(ss+"");
//						accountdetails1.setUseridId(shubaoziyonghu.getUserId());
//						accountdetails1.setBeishangfenid(roomid);
//						accountdetailsmapper.insert(accountdetails1);
//
//						EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//						wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//						wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//						List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//						selectList31.get(0).setShuying(2);//1赢  二输
//						getredenvelopesmapper.updateById(selectList31.get(0));
//					}
//				}else{
//					//两位都不是豹子
//					//赢家后两位
//					String yingjjias = selectLists.get(i).getMoney();
//					String[] str_Arr = yingjjias.substring(yingjjias.lastIndexOf('.') + 1, yingjjias.length()).split("");
//					Integer index_1 = Integer.parseInt(str_Arr[0]);
//					Integer index_2 = Integer.parseInt(str_Arr[1]);
//					String yingjiamonye = (index_1+index_2)+"";
//					//房主后两位
//					String fangzhusw = fangzhu.get(0).getMoney();
//					String[] str_Arrs = fangzhusw.substring(fangzhusw.lastIndexOf('.') + 1, fangzhusw.length()).split("");
//					Integer index_11 = Integer.parseInt(str_Arrs[0]);
//					Integer index_22 = Integer.parseInt(str_Arrs[1]);
//					String fangzhumonye = (index_11+index_22)+"";
//
//					if(yingjiamonye.length()>1) {
//						yingjiamonye = yingjiamonye.substring(yingjiamonye.length() - 1, yingjiamonye.length());
//					}
//					if(fangzhumonye.length()>1) {
//						fangzhumonye = fangzhumonye.substring(fangzhumonye.length() - 1, fangzhumonye.length());
//					}
//					//用户抢到的钱
//					Integer index_33  = Integer.parseInt(yingjiamonye);
//					//房主抢到的钱
//					Integer index_44  = Integer.parseInt(fangzhumonye);
//
//					String yonghuzuobian = selectLists.get(i).getMoney();
//					String substring = yonghuzuobian.substring(0, 1);
//					int yonghuzuo1 = Integer.parseInt(substring);
//
//					String fangzhuzuobian = fangzhu.get(0).getMoney();
//					String substring2 = fangzhuzuobian.substring(0, 1);
//					int fangzhuzuo1 = Integer.parseInt(substring2);
//
//					//用户小数点后面两位相加
//					if(index_33==index_44) {
//						if(yonghuzuo1>fangzhuzuo1){
//							//用户赢
//							if(index_33==8 || index_33==9) {
//								//1:2
//								Users fangzhus = usersmapper.selectById(userid);
//								//赢家应得金额,扣除房主账户金额
//								Double yingqianyingde= fangzhus.getUserRechargeIntegral()-Double.parseDouble(selectLists.get(i).getMoney())*2;//用户金额减去应该赔偿金额
//								fangzhus.setUserRechargeIntegral(yingqianyingde);
//								usersmapper.updateById(fangzhus);
//								//用户
//								Users yonghus = usersmapper.selectById(selectLists.get(i).getUsersid());
//								//用户的上级用户
//								EntityWrapper<Users> wrapper = new EntityWrapper<>();
//								wrapper.eq("user_invitation_code", yonghus.getTheHigherTheAgent());
//								List<Users> selectList2 = usersmapper.selectList(wrapper);
//
//								Double danshuizong = Double.parseDouble(selectLists.get(i).getMoney())*2;
//								if(!selectList2.isEmpty()) {
//									if(selectList2.get(0).getUserGrade()<yonghus.getUserGrade()) {
//										EntityWrapper<Agency> daili = new EntityWrapper<>();
//										daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//										List<Agency> agency= agencymapper.selectList(daili);
//										Agency agencys = agency.get(0);//上级代理抽水比例
//
//										Double fanshui= (danshuizong*0.01)*agencys.getChoushuiBili();//上级用户应得金额
//
//										//修改上级代理金额
//										Double shangjiyingde = selectList2.get(0).getUserRechargeIntegral()+fanshui;
//										selectList2.get(0).setUserRechargeIntegral(shangjiyingde);
//										usersmapper.updateById(selectList2.get(0));
//
//										//自己应得
//										Double shengyu =Double.parseDouble(selectLists.get(i).getMoney())-fanshui;
//										Double shengyus =Double.parseDouble(selectLists.get(i).getMoney())+shengyu;//用户真正抢到的钱
//
//										yonghus.setUserRechargeIntegral(yonghus.getUserRechargeIntegral()+shengyu);
//										usersmapper.updateById(yonghus);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										Accountdetails accountdetails2 = new Accountdetails();//上级用户
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(yonghus.getUserId());
//										accountdetails.setMoney(shengyus+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(selectLists.get(i).getMoney());
//										accountdetails1.setUseridId(userid);
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										accountdetails2.setAddTime(Hutool.parseDateToString());
//										accountdetails2.setType(6);//红包分红
//										accountdetails2.setMoney(fanshui+"");
//										accountdetails2.setSymbol(1);//一加  二减
//										accountdetails2.setBeishangfenid(roomid);
//										accountdetails2.setUseridId(selectList2.get(0).getUserId());
//										accountdetailsmapper.insert(accountdetails2);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(1);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}else {
//										Double fanshui= danshuizong*0.01;//上级用户应得金额
//										//自己应得
//										Double shengyu =Double.parseDouble(selectLists.get(i).getMoney())-fanshui;
//										Double shengyus =Double.parseDouble(selectLists.get(i).getMoney())+shengyu;//用户真正抢到的钱
//										//修改用户金额
//										yonghus.setUserRechargeIntegral(yonghus.getUserRechargeIntegral()+shengyu);
//										usersmapper.updateById(yonghus);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(yonghus.getUserId());
//										accountdetails.setMoney(shengyus+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(selectLists.get(i).getMoney());
//										accountdetails1.setUseridId(userid);
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(1);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}
//								}else {
//									//没有找到上级用户
//
//									Double fanshui= danshuizong*0.01;//上级用户应得金额
//									//自己应得
//									Double shengyu =Double.parseDouble(selectLists.get(i).getMoney())-fanshui;
//									Double shengyus =Double.parseDouble(selectLists.get(i).getMoney())+shengyu;//用户真正抢到的钱
//									//修改用户金额
//									yonghus.setUserRechargeIntegral(yonghus.getUserRechargeIntegral()+shengyu);
//									usersmapper.updateById(yonghus);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(yonghus.getUserId());
//									accountdetails.setMoney(shengyus+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(selectLists.get(i).getMoney());
//									accountdetails1.setUseridId(userid);
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//
//								}
//							}else {
//								//金额减去抽水比例
//
//								Double jine = Double.parseDouble(selectLists.get(i).getMoney());
//								Double fanshui = jine*0.01;
//								Double jines =jine-fanshui;
//								Users users = usersmapper.selectById(selectLists.get(i).getUsersid());
//								//用户的上级用户
//								EntityWrapper<Users> wrapper = new EntityWrapper<>();
//								wrapper.eq("user_invitation_code", users.getTheHigherTheAgent());
//								List<Users> selectList2 = usersmapper.selectList(wrapper);
//								if(!selectList2.isEmpty()) {
//									EntityWrapper<Agency> daili = new EntityWrapper<>();
//									daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//									List<Agency> agency= agencymapper.selectList(daili);
//									Agency agencys = agency.get(0);//上级代理抽水比例
//
//									Double shangji= fanshui*agencys.getChoushuiBili();
//
//									Double ss = selectList2.get(0).getUserRechargeIntegral()+shangji;
//									selectList2.get(0).setUserRechargeIntegral(ss);
//									usersmapper.updateById(selectList2.get(0));
//
//									Double yonghude = users.getUserRechargeIntegral()-fanshui;//用户应得
//									users.setUserRechargeIntegral(yonghude);
//									usersmapper.updateById(users);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//代理
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(users.getUserId());
//									accountdetails.setMoney(jines+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(shangji+"");
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetails1.setUseridId(selectList2.get(0).getUserId());
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}else {
//									//没有上级代理
//									Double yonghude = users.getUserRechargeIntegral()-fanshui;//用户应得
//									users.setUserRechargeIntegral(yonghude);
//									usersmapper.updateById(users);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(users.getUserId());
//									accountdetails.setMoney(jines+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//
//							}
//						}else if(yonghuzuo1<fangzhuzuo1) {
//							//房主赢
//							if(fangzhuzuo1==8 || fangzhuzuo1==9) {
//								//1:2
//								//selectLists.get(i)
//								Double fanhuan= Double.parseDouble(fangzhu.get(0).getMoney())*2;
//								Double yingdes = fanhuan*0.01;
//								Double fangzhu2 = fanhuan-yingdes;
//
//								Users user = usersmapper.selectById(selectLists.get(i).getUsersid());
//								Double kouchu= user.getUserRechargeIntegral()-fanhuan;
//								user.setUserRechargeIntegral(kouchu);
//								usersmapper.updateById(user);
//
//								Users fangzhu1 = usersmapper.selectById(userid);
//								EntityWrapper<Users> wrapper = new EntityWrapper<>();
//								wrapper.eq("user_invitation_code", fangzhu1.getTheHigherTheAgent());
//								List<Users> selectList2 = usersmapper.selectList(wrapper);
//								if(!selectList2.isEmpty()) {
//									if(selectList2.get(0).getUserGrade()<fangzhu1.getUserGrade()) {
//										EntityWrapper<Agency> daili = new EntityWrapper<>();
//										daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//										List<Agency> agency= agencymapper.selectList(daili);
//										Agency agencys = agency.get(0);//上级代理抽水比例
//
//										Double fanshui =  (fanhuan*0.01)*agencys.getChoushuiBili();
//
//										selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+fanshui);
//										usersmapper.updateById(selectList2.get(0));
//
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										Accountdetails accountdetails2 = new Accountdetails();//上级用户
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										accountdetails2.setAddTime(Hutool.parseDateToString());
//										accountdetails2.setType(6);//红包分红
//										accountdetails2.setSymbol(1);//一加  二减
//										accountdetails2.setMoney(fanshui+"");
//										accountdetails2.setUseridId(selectList2.get(0).getUserId());
//										accountdetails2.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails2);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//
//									}else {
//										//房主等级高
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}
//								}else {
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}else {
//								//金额减去抽水比例
//								//1:1
//								//selectLists.get(i)
//								Double fanhuan= Double.parseDouble(fangzhu.get(0).getMoney());
//								Double yingdes = fanhuan*0.01;
//								Double fangzhu2 = fanhuan-yingdes;
//
//								Users user = usersmapper.selectById(selectLists.get(i).getUsersid());
//								Double kouchu= user.getUserRechargeIntegral()-fanhuan;
//								user.setUserRechargeIntegral(kouchu);
//								usersmapper.updateById(user);
//
//								Users fangzhu1 = usersmapper.selectById(userid);
//								EntityWrapper<Users> wrapper = new EntityWrapper<>();
//								wrapper.eq("user_invitation_code", fangzhu1.getTheHigherTheAgent());
//								List<Users> selectList2 = usersmapper.selectList(wrapper);
//								if(!selectList2.isEmpty()) {
//									if(selectList2.get(0).getUserGrade()<fangzhu1.getUserGrade()) {
//										EntityWrapper<Agency> daili = new EntityWrapper<>();
//										daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//										List<Agency> agency= agencymapper.selectList(daili);
//										Agency agencys = agency.get(0);//上级代理抽水比例
//
//										Double fanshui =  (fanhuan*0.01)*agencys.getChoushuiBili();
//
//										selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+fanshui);
//										usersmapper.updateById(selectList2.get(0));
//
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										Accountdetails accountdetails2 = new Accountdetails();//上级用户
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										accountdetails2.setAddTime(Hutool.parseDateToString());
//										accountdetails2.setType(6);//红包分红
//										accountdetails2.setSymbol(1);//一加  二减
//										accountdetails2.setMoney(fanshui+"");
//										accountdetails2.setBeishangfenid(roomid);
//										accountdetails2.setUseridId(selectList2.get(0).getUserId());
//										accountdetailsmapper.insert(accountdetails2);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//
//									}else {
//										//房主等级高
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}
//								}else {
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}
//						}else{
//							//房主赢
//							if(fangzhuzuo1==8 || fangzhuzuo1==9) {
//								//1:2
//								//selectLists.get(i)
//								Double fanhuan= Double.parseDouble(fangzhu.get(0).getMoney())*2;
//								Double yingdes = fanhuan*0.01;
//								Double fangzhu2 = fanhuan-yingdes;
//
//								Users user = usersmapper.selectById(selectLists.get(i).getUsersid());
//								Double kouchu= user.getUserRechargeIntegral()-fanhuan;
//								user.setUserRechargeIntegral(kouchu);
//								usersmapper.updateById(user);
//
//								Users fangzhu1 = usersmapper.selectById(userid);
//								EntityWrapper<Users> wrapper = new EntityWrapper<>();
//								wrapper.eq("user_invitation_code", fangzhu1.getTheHigherTheAgent());
//								List<Users> selectList2 = usersmapper.selectList(wrapper);
//								if(!selectList2.isEmpty()) {
//									if(selectList2.get(0).getUserGrade()<fangzhu1.getUserGrade()) {
//										EntityWrapper<Agency> daili = new EntityWrapper<>();
//										daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//										List<Agency> agency= agencymapper.selectList(daili);
//										Agency agencys = agency.get(0);//上级代理抽水比例
//
//										Double fanshui =  (fanhuan*0.01)*agencys.getChoushuiBili();
//
//										selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+fanshui);
//										usersmapper.updateById(selectList2.get(0));
//
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										Accountdetails accountdetails2 = new Accountdetails();//上级用户
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										accountdetails2.setAddTime(Hutool.parseDateToString());
//										accountdetails2.setType(6);//红包分红
//										accountdetails2.setSymbol(1);//一加  二减
//										accountdetails2.setMoney(fanshui+"");
//										accountdetails2.setUseridId(selectList2.get(0).getUserId());
//										accountdetails2.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails2);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}else {
//										//房主等级高
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}
//								}else {
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//
//							}else {
//								//金额减去抽水比例
//								//1:1
//								//selectLists.get(i)
//								Double fanhuan= Double.parseDouble(fangzhu.get(0).getMoney());
//								Double yingdes = fanhuan*0.01;
//								Double fangzhu2 = fanhuan-yingdes;
//
//								Users user = usersmapper.selectById(selectLists.get(i).getUsersid());
//								Double kouchu= user.getUserRechargeIntegral()-fanhuan;
//								user.setUserRechargeIntegral(kouchu);
//								usersmapper.updateById(user);
//
//								Users fangzhu1 = usersmapper.selectById(userid);
//								EntityWrapper<Users> wrapper = new EntityWrapper<>();
//								wrapper.eq("user_invitation_code", fangzhu1.getTheHigherTheAgent());
//								List<Users> selectList2 = usersmapper.selectList(wrapper);
//								if(!selectList2.isEmpty()) {
//									if(selectList2.get(0).getUserGrade()<fangzhu1.getUserGrade()) {
//										EntityWrapper<Agency> daili = new EntityWrapper<>();
//										daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//										List<Agency> agency= agencymapper.selectList(daili);
//										Agency agencys = agency.get(0);//上级代理抽水比例
//
//										Double fanshui =  (fanhuan*0.01)*agencys.getChoushuiBili();
//
//										selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+fanshui);
//										usersmapper.updateById(selectList2.get(0));
//
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										Accountdetails accountdetails2 = new Accountdetails();//上级用户
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										accountdetails2.setAddTime(Hutool.parseDateToString());
//										accountdetails2.setType(6);//红包分红
//										accountdetails2.setSymbol(1);//一加  二减
//										accountdetails2.setMoney(fanshui+"");
//										accountdetails2.setUseridId(selectList2.get(0).getUserId());
//										accountdetails2.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails2);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}else {
//										//房主等级高
//
//										fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//										usersmapper.updateById(fangzhu1);
//
//										Accountdetails accountdetails = new Accountdetails();//用户
//										Accountdetails accountdetails1 = new Accountdetails();//房主
//										accountdetails.setAddTime(Hutool.parseDateToString());
//										accountdetails.setType(5);
//										accountdetails.setSymbol(1);//一加  二减
//										accountdetails.setUseridId(fangzhu1.getUserId());
//										accountdetails.setMoney(fangzhu2+"");
//										accountdetails.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails);
//
//										accountdetails1.setAddTime(Hutool.parseDateToString());
//										accountdetails1.setType(4);//红包扣除
//										accountdetails1.setSymbol(2);//一加  二减
//										accountdetails1.setMoney(fanhuan+"");
//										accountdetails1.setUseridId(user.getUserId());
//										accountdetails1.setBeishangfenid(roomid);
//										accountdetailsmapper.insert(accountdetails1);
//
//										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//										selectList31.get(0).setShuying(2);//1赢  二输
//										getredenvelopesmapper.updateById(selectList31.get(0));
//									}
//								}else {
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}
//						}
//
//					}else if(index_33>index_44){
//						//用户大
//						if(index_33==8 || index_33==9) {
//							//1:2
//							Users fangzhus = usersmapper.selectById(userid);
//							//赢家应得金额,扣除房主账户金额
//							Double yingqianyingde= fangzhus.getUserRechargeIntegral()-Double.parseDouble(selectLists.get(i).getMoney())*2;//用户金额减去应该赔偿金额
//							fangzhus.setUserRechargeIntegral(yingqianyingde);
//							usersmapper.updateById(fangzhus);
//							//用户
//							Users yonghus = usersmapper.selectById(selectLists.get(i).getUsersid());
//							//用户的上级用户
//							EntityWrapper<Users> wrapper = new EntityWrapper<>();
//							wrapper.eq("user_invitation_code", yonghus.getTheHigherTheAgent());
//							List<Users> selectList2 = usersmapper.selectList(wrapper);
//
//							Double danshuizong = Double.parseDouble(selectLists.get(i).getMoney())*2;
//							if(!selectList2.isEmpty()) {
//								if(selectList2.get(0).getUserGrade()<yonghus.getUserGrade()) {
//									EntityWrapper<Agency> daili = new EntityWrapper<>();
//									daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//									List<Agency> agency= agencymapper.selectList(daili);
//									Agency agencys = agency.get(0);//上级代理抽水比例
//
//									Double fanshui= (danshuizong*0.01)*agencys.getChoushuiBili();//上级用户应得金额
//
//									//修改上级代理金额
//									Double shangjiyingde = selectList2.get(0).getUserRechargeIntegral()+fanshui;
//									selectList2.get(0).setUserRechargeIntegral(shangjiyingde);
//									usersmapper.updateById(selectList2.get(0));
//
//									//自己应得
//									Double shengyu =Double.parseDouble(selectLists.get(i).getMoney())-fanshui;
//									Double shengyus =Double.parseDouble(selectLists.get(i).getMoney())+shengyu;//用户真正抢到的钱
//
//									yonghus.setUserRechargeIntegral(yonghus.getUserRechargeIntegral()+shengyu);
//									usersmapper.updateById(yonghus);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									Accountdetails accountdetails2 = new Accountdetails();//上级用户
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(yonghus.getUserId());
//									accountdetails.setMoney(shengyus+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(selectLists.get(i).getMoney());
//									accountdetails1.setUseridId(userid);
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									accountdetails2.setAddTime(Hutool.parseDateToString());
//									accountdetails2.setType(6);//红包分红
//									accountdetails2.setSymbol(1);//一加  二减
//									accountdetails2.setMoney(fanshui+"");
//									accountdetails2.setUseridId(selectList2.get(0).getUserId());
//									accountdetails2.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails2);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}else {
//									Double fanshui= danshuizong*0.01;//上级用户应得金额
//									//自己应得
//									Double shengyu =Double.parseDouble(selectLists.get(i).getMoney())-fanshui;
//									Double shengyus =Double.parseDouble(selectLists.get(i).getMoney())+shengyu;//用户真正抢到的钱
//									//修改用户金额
//									yonghus.setUserRechargeIntegral(yonghus.getUserRechargeIntegral()+shengyu);
//									usersmapper.updateById(yonghus);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(yonghus.getUserId());
//									accountdetails.setMoney(shengyus+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(selectLists.get(i).getMoney());
//									accountdetails1.setUseridId(userid);
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(1);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}else {
//								//没有找到上级用户
//
//								Double fanshui= danshuizong*0.01;//上级用户应得金额
//								//自己应得
//								Double shengyu =Double.parseDouble(selectLists.get(i).getMoney())-fanshui;
//								Double shengyus =Double.parseDouble(selectLists.get(i).getMoney())+shengyu;//用户真正抢到的钱
//								//修改用户金额
//								yonghus.setUserRechargeIntegral(yonghus.getUserRechargeIntegral()+shengyu);
//								usersmapper.updateById(yonghus);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setUseridId(yonghus.getUserId());
//								accountdetails.setMoney(shengyus+"");
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(selectLists.get(i).getMoney());
//								accountdetails1.setUseridId(userid);
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(1);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//						}else {
//							//金额减去抽水比例
//
//							Double jine = Double.parseDouble(selectLists.get(i).getMoney());
//							Double fanshui = jine*0.01;
//							Double jines =jine-fanshui;
//							Users users = usersmapper.selectById(selectLists.get(i).getUsersid());
//							//用户的上级用户
//							EntityWrapper<Users> wrapper = new EntityWrapper<>();
//							wrapper.eq("user_invitation_code", users.getTheHigherTheAgent());
//							List<Users> selectList2 = usersmapper.selectList(wrapper);
//							if(!selectList2.isEmpty()) {
//								EntityWrapper<Agency> daili = new EntityWrapper<>();
//								daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//								List<Agency> agency= agencymapper.selectList(daili);
//								Agency agencys = agency.get(0);//上级代理抽水比例
//
//								Double shangji= fanshui*agencys.getChoushuiBili();
//
//								Double ss = selectList2.get(0).getUserRechargeIntegral()+shangji;
//								selectList2.get(0).setUserRechargeIntegral(ss);
//								usersmapper.updateById(selectList2.get(0));
//
//								Double yonghude = users.getUserRechargeIntegral()-fanshui;//用户应得
//								users.setUserRechargeIntegral(yonghude);
//								usersmapper.updateById(users);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//代理
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setUseridId(users.getUserId());
//								accountdetails.setMoney(jines+"");
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(shangji+"");
//								accountdetails1.setUseridId(selectList2.get(0).getUserId());
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(1);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}else {
//								//没有上级代理
//								Double yonghude = users.getUserRechargeIntegral()-fanshui;//用户应得
//								users.setUserRechargeIntegral(yonghude);
//								usersmapper.updateById(users);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setUseridId(users.getUserId());
//								accountdetails.setMoney(jines+"");
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(1);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//						}
//					}else if(index_33<index_44){
//						//房主大
//						//房主赢
//						if(index_44==8 || index_44==9) {
//							//1:2
//							//selectLists.get(i)
//							Double fanhuan= Double.parseDouble(fangzhu.get(0).getMoney())*2;
//							Double yingdes = fanhuan*0.01;
//							Double fangzhu2 = fanhuan-yingdes;
//
//							Users user = usersmapper.selectById(selectLists.get(i).getUsersid());
//							Double kouchu= user.getUserRechargeIntegral()-fanhuan;
//							user.setUserRechargeIntegral(kouchu);
//							usersmapper.updateById(user);
//
//							Users fangzhu1 = usersmapper.selectById(userid);
//							EntityWrapper<Users> wrapper = new EntityWrapper<>();
//							wrapper.eq("user_invitation_code", fangzhu1.getTheHigherTheAgent());
//							List<Users> selectList2 = usersmapper.selectList(wrapper);
//							if(!selectList2.isEmpty()) {
//								if(selectList2.get(0).getUserGrade()<fangzhu1.getUserGrade()) {
//									EntityWrapper<Agency> daili = new EntityWrapper<>();
//									daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//									List<Agency> agency= agencymapper.selectList(daili);
//									Agency agencys = agency.get(0);//上级代理抽水比例
//
//									Double fanshui =  (fanhuan*0.01)*agencys.getChoushuiBili();
//
//									selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+fanshui);
//									usersmapper.updateById(selectList2.get(0));
//
//
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									Accountdetails accountdetails2 = new Accountdetails();//上级用户
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									accountdetails2.setAddTime(Hutool.parseDateToString());
//									accountdetails2.setType(6);//红包分红
//									accountdetails2.setSymbol(1);//一加  二减
//									accountdetails2.setMoney(fanshui+"");
//									accountdetails2.setUseridId(selectList2.get(0).getUserId());
//									accountdetails2.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails2);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}else {
//									//房主等级高
//
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}else {
//								fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//								usersmapper.updateById(fangzhu1);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setUseridId(fangzhu1.getUserId());
//								accountdetails.setMoney(fangzhu2+"");
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(fanhuan+"");
//								accountdetails1.setUseridId(user.getUserId());
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(2);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//						}else{
//							//金额减去抽水比例
//							//1:1
//							//selectLists.get(i)
//							Double fanhuan= Double.parseDouble(fangzhu.get(0).getMoney());
//							Double yingdes = fanhuan*0.01;
//							Double fangzhu2 = fanhuan-yingdes;
//
//							Users user = usersmapper.selectById(selectLists.get(i).getUsersid());
//							Double kouchu= user.getUserRechargeIntegral()-fanhuan;
//							user.setUserRechargeIntegral(kouchu);
//							usersmapper.updateById(user);
//
//							Users fangzhu1 = usersmapper.selectById(userid);
//							EntityWrapper<Users> wrapper = new EntityWrapper<>();
//							wrapper.eq("user_invitation_code", fangzhu1.getTheHigherTheAgent());
//							List<Users> selectList2 = usersmapper.selectList(wrapper);
//							if(!selectList2.isEmpty()) {
//								if(selectList2.get(0).getUserGrade()<fangzhu1.getUserGrade()) {
//									EntityWrapper<Agency> daili = new EntityWrapper<>();
//									daili.eq("ageny_class", selectList2.get(0).getUserGrade());
//									List<Agency> agency= agencymapper.selectList(daili);
//									Agency agencys = agency.get(0);//上级代理抽水比例
//
//									Double fanshui =  (fanhuan*0.01)*agencys.getChoushuiBili();
//
//									selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+fanshui);
//									usersmapper.updateById(selectList2.get(0));
//
//
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									Accountdetails accountdetails2 = new Accountdetails();//上级用户
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									accountdetails2.setAddTime(Hutool.parseDateToString());
//									accountdetails2.setType(6);//红包分红
//									accountdetails2.setSymbol(1);//一加  二减
//									accountdetails2.setMoney(fanshui+"");
//									accountdetails2.setUseridId(selectList2.get(0).getUserId());
//									accountdetails2.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails2);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}else {
//									//房主等级高
//
//									fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//									usersmapper.updateById(fangzhu1);
//
//									Accountdetails accountdetails = new Accountdetails();//用户
//									Accountdetails accountdetails1 = new Accountdetails();//房主
//									accountdetails.setAddTime(Hutool.parseDateToString());
//									accountdetails.setType(5);
//									accountdetails.setSymbol(1);//一加  二减
//									accountdetails.setUseridId(fangzhu1.getUserId());
//									accountdetails.setMoney(fangzhu2+"");
//									accountdetails.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails);
//
//									accountdetails1.setAddTime(Hutool.parseDateToString());
//									accountdetails1.setType(4);//红包扣除
//									accountdetails1.setSymbol(2);//一加  二减
//									accountdetails1.setMoney(fanhuan+"");
//									accountdetails1.setUseridId(user.getUserId());
//									accountdetails1.setBeishangfenid(roomid);
//									accountdetailsmapper.insert(accountdetails1);
//
//									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//									List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//									selectList31.get(0).setShuying(2);//1赢  二输
//									getredenvelopesmapper.updateById(selectList31.get(0));
//								}
//							}else {
//								fangzhu1.setUserRechargeIntegral(fangzhu1.getUserRechargeIntegral()+fangzhu2);
//								usersmapper.updateById(fangzhu1);
//
//								Accountdetails accountdetails = new Accountdetails();//用户
//								Accountdetails accountdetails1 = new Accountdetails();//房主
//								accountdetails.setAddTime(Hutool.parseDateToString());
//								accountdetails.setType(5);
//								accountdetails.setSymbol(1);//一加  二减
//								accountdetails.setUseridId(fangzhu1.getUserId());
//								accountdetails.setMoney(fangzhu2+"");
//								accountdetails.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails);
//
//								accountdetails1.setAddTime(Hutool.parseDateToString());
//								accountdetails1.setType(4);//红包扣除
//								accountdetails1.setSymbol(2);//一加  二减
//								accountdetails1.setMoney(fanhuan+"");
//								accountdetails1.setUseridId(user.getUserId());
//								accountdetails1.setBeishangfenid(roomid);
//								accountdetailsmapper.insert(accountdetails1);
//
//								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
//								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
//								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
//								List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
//								selectList31.get(0).setShuying(2);//1赢  二输
//								getredenvelopesmapper.updateById(selectList31.get(0));
//							}
//						}
//
//					}
//
//				}
//
//			}







				/*//赢家后两位
				String yingjjias = selectLists.get(i).getMoney();
				String[] str_Arr = yingjjias.substring(yingjjias.lastIndexOf('.') + 1, yingjjias.length()).split("");
				Integer index_1 = Integer.parseInt(str_Arr[0]);
				Integer index_2 = Integer.parseInt(str_Arr[1]);
				String yingjiamonye = (index_1+index_2)+"";
				//房主后两位
				String fangzhusw = fangzhu.get(0).getMoney();
				String[] str_Arrs = fangzhusw.substring(fangzhusw.lastIndexOf('.') + 1, fangzhusw.length()).split("");
				Integer index_11 = Integer.parseInt(str_Arrs[0]);
				Integer index_22 = Integer.parseInt(str_Arrs[1]);
				String fangzhumonye = (index_11+index_22)+"";

				if(yingjiamonye.length()>1) {
					yingjiamonye = yingjiamonye.substring(yingjiamonye.length() - 1, yingjiamonye.length());
				}
				if(fangzhumonye.length()>1) {
					fangzhumonye = fangzhumonye.substring(fangzhumonye.length() - 1, fangzhumonye.length());
				}
				//用户抢到的钱
				Integer index_33  = Integer.parseInt(yingjiamonye);
				//房主抢到的钱
				Integer index_44  = Integer.parseInt(fangzhumonye);
				//判断小数点后两位计算出的一位是否相等
				if(index_33.intValue()== index_44.intValue()) {
					//用户左边一位
					String yonghuzuo = lists.get(i).getMoney();
					//房主左边一位
					String fangzhuzuo = fangzhu.get(0).getMoney();
					Integer yonghuzuo1  = Integer.parseInt(yonghuzuo);
					Integer fangzhuzuo1  = Integer.parseInt(fangzhuzuo);

					String yonghuzuobian = selectLists.get(i).getMoney();
					String substring = yonghuzuobian.substring(0, 1);
					int yonghuzuo1 = Integer.parseInt(substring);

					String fangzhuzuobian = fangzhu.get(0).getMoney();
					String substring2 = fangzhuzuobian.substring(0, 1);
					int fangzhuzuo1 = Integer.parseInt(substring2);
					//如果左边相等房主赢
					if(yonghuzuo1==fangzhuzuo1) {
						if(index_44.intValue()<=7) {//扣除用户账户抢红包的钱*2
							Double fanqian = Double.parseDouble(selectLists.get(i).getMoney())*2;
							Users selectById = usersmapper.selectById(selectLists.get(i).getUsersid());
							Double fanqian1 = selectById.getUserRechargeIntegral()-fanqian;
							selectById.setUserRechargeIntegral(fanqian1);
							usersmapper.updateById(selectById);

							Users fangzhus = usersmapper.selectById(fangzhu.get(0).getUsersid());
							if(!StringUtils.isEmpty(fangzhus.getTheHigherTheAgent())) {
								EntityWrapper<Users> wrapper= new EntityWrapper<>();
								wrapper.eq("user_invitation_code", fangzhus.getTheHigherTheAgent());
								List<Users> selectList2 = usersmapper.selectList(wrapper);
								if(!selectList2.isEmpty()) {
									if(selectList2.get(0).getUserGrade().intValue()<fangzhus.getUserGrade()) {
										EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
										wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
										List<Agency> selectList3 = agencymapper.selectList(wrapper1);
										if(!selectList3.isEmpty()) {
											Double shoushui=(fanqian*0.1)*selectList3.get(0).getChoushuiBili();
											Double choushuiyingde = selectList2.get(0).getUserRechargeIntegral()+shoushui;
											selectList2.get(0).setUserRechargeIntegral(choushuiyingde);
											usersmapper.updateById(selectList2.get(0));
											Accountdetails accountdetails = new Accountdetails();
											accountdetails.setUseridId(selectList2.get(0).getUserId());
											accountdetails.setSymbol(1);
											accountdetails.setAddTime(Hutool.parseDateToString());
											accountdetails.setMoney(shoushui);
											accountdetails.setType(6);
											accountdetailsmapper.insert(accountdetails);

											Double yingjiass = fanqian-shoushui;
											Double s= fangzhus.getUserRechargeIntegral()+yingjiass;
											fangzhus.setUserRechargeIntegral(s);
											usersmapper.updateById(fangzhus);

											EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
											wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
											wrapper12.eq("user_id",selectLists.get(i).getUsersid());
											List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
											selectList31.get(0).setShuying(2);
											getredenvelopesmapper.updateById(selectList31.get(0));
										}
									}
								}
							}else {
								Double s= fangzhus.getUserRechargeIntegral()+fanqian;
								fangzhus.setUserRechargeIntegral(s);
								usersmapper.updateById(fangzhus);
								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(2);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}

						}else if(index_44.intValue()<=8 || index_44.intValue()<=9 ) {
							Double fanqian = Double.parseDouble(selectLists.get(i).getMoney())*3;
							Users selectById = usersmapper.selectById(selectLists.get(i).getUsersid());
							Double fanqian1 = selectById.getUserRechargeIntegral()-fanqian;
							selectById.setUserRechargeIntegral(fanqian1);
							usersmapper.updateById(selectById);

							Users fangzhus = usersmapper.selectById(fangzhu.get(0).getUsersid());
							if(!StringUtils.isEmpty(fangzhus.getTheHigherTheAgent())) {
								EntityWrapper<Users> wrapper= new EntityWrapper<>();
								wrapper.eq("user_invitation_code", fangzhus.getTheHigherTheAgent());
								List<Users> selectList2 = usersmapper.selectList(wrapper);
								if(!selectList2.isEmpty()) {
									if(selectList2.get(0).getUserGrade().intValue()<fangzhus.getUserGrade()) {
										EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
										wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
										List<Agency> selectList3 = agencymapper.selectList(wrapper1);
										if(!selectList3.isEmpty()) {
											Double shoushui=(fanqian*0.1)*selectList3.get(0).getChoushuiBili();
											Double choushuiyingde = selectList2.get(0).getUserRechargeIntegral()+shoushui;
											selectList2.get(0).setUserRechargeIntegral(choushuiyingde);
											usersmapper.updateById(selectList2.get(0));
											Accountdetails accountdetails = new Accountdetails();
											accountdetails.setUseridId(selectList2.get(0).getUserId());
											accountdetails.setSymbol(1);
											accountdetails.setAddTime(Hutool.parseDateToString());
											accountdetails.setMoney(shoushui);
											accountdetails.setType(6);
											accountdetailsmapper.insert(accountdetails);

											Double yingjiass = fanqian-shoushui;
											Double s= fangzhus.getUserRechargeIntegral()+yingjiass;
											fangzhus.setUserRechargeIntegral(s);
											usersmapper.updateById(fangzhus);

											EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
											wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
											wrapper12.eq("user_id",selectLists.get(i).getUsersid());
											List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
											selectList31.get(0).setShuying(2);
											getredenvelopesmapper.updateById(selectList31.get(0));
										}
									}
								}
							}else {
								Double s= fangzhus.getUserRechargeIntegral()+fanqian;
								fangzhus.setUserRechargeIntegral(s);
								usersmapper.updateById(fangzhus);

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(2);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}
						}


					}else if(yonghuzuo1<fangzhuzuo1) {//房主赢
						if(index_44.intValue()<=7) {//扣除用户账户抢红包的钱*2
							Double fanqian = Double.parseDouble(selectLists.get(i).getMoney())*2;
							Users selectById = usersmapper.selectById(selectLists.get(i).getUsersid());
							Double fanqian1 = selectById.getUserRechargeIntegral()-fanqian;
							selectById.setUserRechargeIntegral(fanqian1);
							usersmapper.updateById(selectById);

							Users fangzhus = usersmapper.selectById(fangzhu.get(0).getUsersid());
							if(!StringUtils.isEmpty(fangzhus.getTheHigherTheAgent())) {
								EntityWrapper<Users> wrapper= new EntityWrapper<>();
								wrapper.eq("user_invitation_code", fangzhus.getTheHigherTheAgent());
								List<Users> selectList2 = usersmapper.selectList(wrapper);
								if(!selectList2.isEmpty()) {
									if(selectList2.get(0).getUserGrade().intValue()<fangzhus.getUserGrade()) {
										EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
										wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
										List<Agency> selectList3 = agencymapper.selectList(wrapper1);
										if(!selectList3.isEmpty()) {
											Double shoushui=(fanqian*0.1)*selectList3.get(0).getChoushuiBili();
											Double choushuiyingde = selectList2.get(0).getUserRechargeIntegral()+shoushui;
											selectList2.get(0).setUserRechargeIntegral(choushuiyingde);
											usersmapper.updateById(selectList2.get(0));
											Accountdetails accountdetails = new Accountdetails();
											accountdetails.setUseridId(selectList2.get(0).getUserId());
											accountdetails.setSymbol(1);
											accountdetails.setAddTime(Hutool.parseDateToString());
											accountdetails.setMoney(shoushui);
											accountdetails.setType(6);
											accountdetailsmapper.insert(accountdetails);

											Double yingjiass = fanqian-shoushui;
											Double s= fangzhus.getUserRechargeIntegral()+yingjiass;
											fangzhus.setUserRechargeIntegral(s);
											usersmapper.updateById(fangzhus);

											EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
											wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
											wrapper12.eq("user_id",selectLists.get(i).getUsersid());
											List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
											selectList31.get(0).setShuying(2);
											getredenvelopesmapper.updateById(selectList31.get(0));
										}
									}
								}
							}else {
								Double s= fangzhus.getUserRechargeIntegral()+fanqian;
								fangzhus.setUserRechargeIntegral(s);
								usersmapper.updateById(fangzhus);

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(2);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}

						}else if(index_44.intValue()<=8 || index_44.intValue()<=9 ) {
							Double fanqian = Double.parseDouble(selectLists.get(i).getMoney())*3;
							Users selectById = usersmapper.selectById(selectLists.get(i).getUsersid());
							Double fanqian1 = selectById.getUserRechargeIntegral()-fanqian;
							selectById.setUserRechargeIntegral(fanqian1);
							usersmapper.updateById(selectById);

							Users fangzhus = usersmapper.selectById(fangzhu.get(0).getUsersid());
							if(!StringUtils.isEmpty(fangzhus.getTheHigherTheAgent())) {
								EntityWrapper<Users> wrapper= new EntityWrapper<>();
								wrapper.eq("user_invitation_code", fangzhus.getTheHigherTheAgent());
								List<Users> selectList2 = usersmapper.selectList(wrapper);
								if(!selectList2.isEmpty()) {
									if(selectList2.get(0).getUserGrade().intValue()<fangzhus.getUserGrade()) {
										EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
										wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
										List<Agency> selectList3 = agencymapper.selectList(wrapper1);
										if(!selectList3.isEmpty()) {
											Double shoushui=(fanqian*0.1)*selectList3.get(0).getChoushuiBili();
											Double choushuiyingde = selectList2.get(0).getUserRechargeIntegral()+shoushui;
											selectList2.get(0).setUserRechargeIntegral(choushuiyingde);
											usersmapper.updateById(selectList2.get(0));
											Accountdetails accountdetails = new Accountdetails();
											accountdetails.setUseridId(selectList2.get(0).getUserId());
											accountdetails.setSymbol(1);
											accountdetails.setAddTime(Hutool.parseDateToString());
											accountdetails.setMoney(shoushui);
											accountdetails.setType(6);
											accountdetailsmapper.insert(accountdetails);

											Double yingjiass = fanqian-shoushui;
											Double s= fangzhus.getUserRechargeIntegral()+yingjiass;
											fangzhus.setUserRechargeIntegral(s);
											usersmapper.updateById(fangzhus);

											EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
											wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
											wrapper12.eq("user_id",selectLists.get(i).getUsersid());
											List<Getredenvelopes> selectList3q = getredenvelopesmapper.selectList(wrapper12);
											selectList3q.get(0).setShuying(2);
											getredenvelopesmapper.updateById(selectList3q.get(0));
										}
									}
								}
							}else {
								Double s= fangzhus.getUserRechargeIntegral()+fanqian;
								fangzhus.setUserRechargeIntegral(s);
								usersmapper.updateById(fangzhus);

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(2);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}
						}

					}else if(yonghuzuo1>fangzhuzuo1) {//用户赢
						if(index_33.intValue()<=8 || index_33.intValue()<=9 ) {


							Users yonghuying = usersmapper.selectById(selectLists.get(i).getUsersid());//赢得用户id
							Users fangzhushu = usersmapper.selectById(fangzhu.get(0).getUsersid());//房主输

							Double sss= (Double.parseDouble(selectLists.get(i).getMoney())*2)*0.01;

							Double yonghu= yonghuying.getUserRechargeIntegral()+Double.parseDouble(selectLists.get(i).getMoney());
							Double shu = fangzhushu.getUserRechargeIntegral()-Double.parseDouble(selectLists.get(i).getMoney());

							List<Users> userlist = usersmapper.selectList(new EntityWrapper<Users>().eq("user_invitation_code", yonghuying.getTheHigherTheAgent()));
							if(!userlist.isEmpty()) {
								yonghuying.setUserRechargeIntegral(yonghu-sss);
								fangzhushu.setUserRechargeIntegral(shu);

								usersmapper.updateById(yonghuying);
								usersmapper.updateById(fangzhushu);

								if(userlist.get(0).getUserGrade()<yonghuying.getUserGrade()) {
									EntityWrapper<Agency> wrapp = new EntityWrapper<>();
									wrapp.eq("ageny_class", userlist.get(0).getUserGrade());
									List<Agency> selectList2 = agencymapper.selectList(wrapp);
									selectList2.get(0).getChoushuiBili();
									Double s=sss*selectList2.get(0).getChoushuiBili();
									Double ss = userlist.get(0).getUserRechargeIntegral()+s;
									userlist.get(0).setUserRechargeIntegral(ss);
									usersmapper.updateById(userlist.get(0));

									EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
									wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
									wrapper12.eq("user_id",selectLists.get(i).getUsersid());
									List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
									selectList3.get(0).setShuying(1);
									getredenvelopesmapper.updateById(selectList3.get(0));
								}
							}else {
								yonghuying.setUserRechargeIntegral(yonghu);
								fangzhushu.setUserRechargeIntegral(shu);
								usersmapper.updateById(yonghuying);
								usersmapper.updateById(fangzhushu);

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(1);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}


							Users yonghuying = usersmapper.selectById(selectList.get(i).getUsersid());//赢得用户id
							Users fangzhushu = usersmapper.selectById(fangzhu.get(0).getUsersid());//房主输

							Double yonghu= yonghuying.getUserRechargeIntegral()+Double.parseDouble(selectList.get(i).getMoney());
							Double shu = fangzhushu.getUserRechargeIntegral()-Double.parseDouble(selectList.get(i).getMoney());

							List<Users> userlist = usersmapper.selectList(new EntityWrapper<Users>().eq("user_invitation_code", yonghuying.getTheHigherTheAgent()));
							if(!userlist.isEmpty()) {
								Double fanshui= (yonghu*2)*0.01;
								yonghuying.setUserRechargeIntegral(yonghu-fanshui);
								fangzhushu.setUserRechargeIntegral(shu);

								usersmapper.updateById(yonghuying);
								usersmapper.updateById(fangzhushu);

								EntityWrapper<Agency> wrapp = new EntityWrapper<>();
								wrapp.eq("ageny_class", userlist.get(0).getUserGrade());
								List<Agency> selectList2 = agencymapper.selectList(wrapp);
								selectList2.get(0).getChoushuiBili();
								Double s=fanshui*fanshui;
								Double ss = userlist.get(0).getUserRechargeIntegral()+s;
								userlist.get(0).setUserRechargeIntegral(ss);
								usersmapper.updateById(userlist.get(0));

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectList.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectList.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(1);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}else {
								yonghuying.setUserRechargeIntegral(yonghu);
								fangzhushu.setUserRechargeIntegral(shu);
								usersmapper.updateById(yonghuying);
								usersmapper.updateById(fangzhushu);

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectList.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectList.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(1);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}

						}
					}
				}else if(index_33.intValue()<index_44.intValue()){//房主赢
					if(index_44.intValue()<=7) {//扣除用户账户抢红包的钱*2
						Double fanqian = Double.parseDouble(selectLists.get(i).getMoney())*2;
						Users selectById = usersmapper.selectById(selectLists.get(i).getUsersid());
						Double fanqian1 = selectById.getUserRechargeIntegral()-fanqian;
						selectById.setUserRechargeIntegral(fanqian1);
						usersmapper.updateById(selectById);

						Users fangzhus = usersmapper.selectById(fangzhu.get(0).getUsersid());
						if(!StringUtils.isEmpty(fangzhus.getTheHigherTheAgent())) {
							EntityWrapper<Users> wrapper= new EntityWrapper<>();
							wrapper.eq("user_invitation_code", fangzhus.getTheHigherTheAgent());
							List<Users> selectList2 = usersmapper.selectList(wrapper);
							if(!selectList2.isEmpty()) {
								if(selectList2.get(0).getUserGrade().intValue()<fangzhus.getUserGrade()) {
									EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
									wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
									List<Agency> selectList3 = agencymapper.selectList(wrapper1);
									if(!selectList3.isEmpty()) {
										Double shoushui=(fanqian*0.1)*selectList3.get(0).getChoushuiBili();
										Double choushuiyingde = selectList2.get(0).getUserRechargeIntegral()+shoushui;
										selectList2.get(0).setUserRechargeIntegral(choushuiyingde);
										usersmapper.updateById(selectList2.get(0));
										Accountdetails accountdetails = new Accountdetails();
										accountdetails.setUseridId(selectList2.get(0).getUserId());
										accountdetails.setSymbol(1);
										accountdetails.setAddTime(Hutool.parseDateToString());
										accountdetails.setMoney(shoushui);
										accountdetails.setType(6);
										accountdetailsmapper.insert(accountdetails);

										Double yingjiass = fanqian-shoushui;
										Double s= fangzhus.getUserRechargeIntegral()+yingjiass;
										fangzhus.setUserRechargeIntegral(s);
										usersmapper.updateById(fangzhus);

										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
										selectList31.get(0).setShuying(2);
										getredenvelopesmapper.updateById(selectList31.get(0));
									}
								}
							}
						}else {
							Double s= fangzhus.getUserRechargeIntegral()+fanqian;
							fangzhus.setUserRechargeIntegral(s);
							usersmapper.updateById(fangzhus);

							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
							wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
							wrapper12.eq("user_id",selectLists.get(i).getUsersid());
							List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
							selectList3.get(0).setShuying(2);
							getredenvelopesmapper.updateById(selectList3.get(0));
						}
					}else if(index_44.intValue()<=8 || index_44.intValue()<=9 ) {
						Double fanqian = Double.parseDouble(selectLists.get(i).getMoney())*3;
						Users selectById = usersmapper.selectById(selectLists.get(i).getUsersid());
						Double fanqian1 = selectById.getUserRechargeIntegral()-fanqian;
						selectById.setUserRechargeIntegral(fanqian1);
						usersmapper.updateById(selectById);

						Users fangzhus = usersmapper.selectById(fangzhu.get(0).getUsersid());
						if(!StringUtils.isEmpty(fangzhus.getTheHigherTheAgent())) {
							EntityWrapper<Users> wrapper= new EntityWrapper<>();
							wrapper.eq("user_invitation_code", fangzhus.getTheHigherTheAgent());
							List<Users> selectList2 = usersmapper.selectList(wrapper);
							if(!selectList2.isEmpty()) {
								if(selectList2.get(0).getUserGrade().intValue()<fangzhus.getUserGrade()) {
									EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
									wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
									List<Agency> selectList3 = agencymapper.selectList(wrapper1);
									if(!selectList3.isEmpty()) {
										Double shoushui=(fanqian*0.1)*selectList3.get(0).getChoushuiBili();
										Double choushuiyingde = selectList2.get(0).getUserRechargeIntegral()+shoushui;
										selectList2.get(0).setUserRechargeIntegral(choushuiyingde);
										usersmapper.updateById(selectList2.get(0));
										Accountdetails accountdetails = new Accountdetails();
										accountdetails.setUseridId(selectList2.get(0).getUserId());
										accountdetails.setSymbol(1);
										accountdetails.setAddTime(Hutool.parseDateToString());
										accountdetails.setMoney(shoushui);
										accountdetails.setType(6);
										accountdetailsmapper.insert(accountdetails);

										Double yingjiass = fanqian-shoushui;
										Double s= fangzhus.getUserRechargeIntegral()+yingjiass;
										fangzhus.setUserRechargeIntegral(s);
										usersmapper.updateById(fangzhus);

										EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
										wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
										wrapper12.eq("user_id",selectLists.get(i).getUsersid());
										List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
										selectList31.get(0).setShuying(2);
										getredenvelopesmapper.updateById(selectList31.get(0));
									}
								}
							}
						}else {
							Double s= fangzhus.getUserRechargeIntegral()+fanqian;
							fangzhus.setUserRechargeIntegral(s);
							usersmapper.updateById(fangzhus);
							selectLists.get(i).setShuying(1);
							getredenvelopesmapper.updateById(selectLists.get(i));

							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
							wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
							wrapper12.eq("user_id",selectLists.get(i).getUsersid());
							List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
							selectList3.get(0).setShuying(2);
							getredenvelopesmapper.updateById(selectList3.get(0));
						}
					}

				}else if(index_33.intValue()>index_44.intValue()) {//用户赢
					if(index_33.intValue()<=8 || index_33.intValue()<=9 ) {

						Users yonghuying = usersmapper.selectById(selectLists.get(i).getUsersid());//赢得用户id
						Users fangzhushu = usersmapper.selectById(fangzhu.get(0).getUsersid());//房主输

						Double sss= (Double.parseDouble(selectLists.get(i).getMoney())*2)*0.01;

						Double yonghu= yonghuying.getUserRechargeIntegral()+Double.parseDouble(selectLists.get(i).getMoney());
						Double shu = fangzhushu.getUserRechargeIntegral()-Double.parseDouble(selectLists.get(i).getMoney());

						List<Users> userlist = usersmapper.selectList(new EntityWrapper<Users>().eq("user_invitation_code", yonghuying.getTheHigherTheAgent()));
						if(!userlist.isEmpty()) {
							yonghuying.setUserRechargeIntegral(yonghu-sss);
							fangzhushu.setUserRechargeIntegral(shu);

							usersmapper.updateById(yonghuying);
							usersmapper.updateById(fangzhushu);

							if(userlist.get(0).getUserGrade()<yonghuying.getUserGrade()) {
								EntityWrapper<Agency> wrapp = new EntityWrapper<>();
								wrapp.eq("ageny_class", userlist.get(0).getUserGrade());
								List<Agency> selectList2 = agencymapper.selectList(wrapp);
								selectList2.get(0).getChoushuiBili();
								Double s=sss*selectList2.get(0).getChoushuiBili();
								Double ss = userlist.get(0).getUserRechargeIntegral()+s;
								userlist.get(0).setUserRechargeIntegral(ss);
								usersmapper.updateById(userlist.get(0));

								EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
								wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
								wrapper12.eq("user_id",selectLists.get(i).getUsersid());
								List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
								selectList3.get(0).setShuying(1);
								getredenvelopesmapper.updateById(selectList3.get(0));
							}
						}else {
							yonghuying.setUserRechargeIntegral(yonghu);
							fangzhushu.setUserRechargeIntegral(shu);
							usersmapper.updateById(yonghuying);
							usersmapper.updateById(fangzhushu);

							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
							wrapper12.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
							wrapper12.eq("user_id",selectLists.get(i).getUsersid());
							List<Getredenvelopes> selectList3 = getredenvelopesmapper.selectList(wrapper12);
							selectList3.get(0).setShuying(1);
							getredenvelopesmapper.updateById(selectList3.get(0));
						}
					}
				}
			}*/


		/*else if(lists.size() >1) {//房主赢

			List<Getredenvelopes> selectListw = getredenvelopesmapper.particulars(redenvelopesid);
			for (int j = 0; j < selectListw.size(); j++) {
				for (int j2 = 0; j2 < lists.size(); j2++) {
					if() {}
				}
			}
			System.out.println(lists);
			//房主后两位
			String fangzhusw = fangzhu.get(0).getMoney();
			String[] str_Arrs = fangzhusw.substring(fangzhusw.lastIndexOf('.') + 1, fangzhusw.length()).split("");
			Integer index_11 = Integer.parseInt(str_Arrs[0]);
			Integer index_22 = Integer.parseInt(str_Arrs[1]);
			String fangzhumonye = (index_11+index_22)+"";

			if(fangzhumonye.length()>1) {
				fangzhumonye = fangzhumonye.substring(fangzhumonye.length() - 1, fangzhumonye.length());
			}
			Integer index_44  = Integer.parseInt(fangzhumonye);

			for (int j = 0; j < lists.size(); j++) {
				if(index_44==8 || index_44==9) {
					Double fanhuan= Double.parseDouble(lists.get(j).getMoney())*3;
					Users users = usersmapper.selectById(lists.get(j).getUsersid());
					Double ss = users.getUserRechargeIntegral()-fanhuan;
					users.setUserRechargeIntegral(ss);
					usersmapper.updateById(users);
					Double choushui = fanhuan*0.01;

					EntityWrapper<Users> wrapper= new EntityWrapper<>();
					wrapper.eq("user_invitation_code", users.getTheHigherTheAgent());
					List<Users> selectList2 = usersmapper.selectList(wrapper);
					if(!selectList2.isEmpty()) {
						//自己代理等级是否比自己高
						if(selectList2.get(0).getUserGrade()<selectById2.getUserGrade()) {
							EntityWrapper<Agency> wrapper1 = new EntityWrapper<>();
							wrapper1.eq("ageny_class", selectList2.get(0).getUserGrade());
							List<Agency> selectList3 = agencymapper.selectList(wrapper1);
							Double dengji = selectList3.get(0).getChoushuiBili()*choushui;

							Double s = selectList2.get(0).getUserRechargeIntegral()+dengji;

							selectList2.get(0).setUserRechargeIntegral(s);
							usersmapper.updateById(selectList2.get(0));

							Double ss1 = selectById2.getUserRechargeIntegral()+fanhuan;
							selectById2.setUserRechargeIntegral(ss1);
							usersmapper.updateById(selectById2);

							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
							wrapper12.eq("redenvelopes_id", selectList.get(j).getRedenvelopesId());
							wrapper12.eq("user_id",selectList.get(j).getUsersid());
							List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
							selectList31.get(0).setShuying(1);
							getredenvelopesmapper.updateById(selectList31.get(0));
						}else {
							Double ss1 = selectById2.getUserRechargeIntegral()+fanhuan;
							selectById2.setUserRechargeIntegral(ss1);
							usersmapper.updateById(selectById2);

							EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
							wrapper12.eq("redenvelopes_id", selectList.get(j).getRedenvelopesId());
							wrapper12.eq("user_id",selectList.get(j).getUsersid());
							List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
							selectList31.get(0).setShuying(1);
							getredenvelopesmapper.updateById(selectList31.get(0));
						}
					}else {
						//没有找到上级代理
						Double ss1 = selectById2.getUserRechargeIntegral()+fanhuan;
						selectById2.setUserRechargeIntegral(ss1);
						usersmapper.updateById(selectById2);

						EntityWrapper<Getredenvelopes> wrapper12 = new EntityWrapper<>();
						wrapper12.eq("redenvelopes_id", selectList.get(j).getRedenvelopesId());
						wrapper12.eq("user_id",selectList.get(j).getUsersid());
						List<Getredenvelopes> selectList31 = getredenvelopesmapper.selectList(wrapper12);
						selectList31.get(0).setShuying(1);
						getredenvelopesmapper.updateById(selectList31.get(0));
					}

				}
			}

		}*/


			/*List<Getredenvelopes> selectLists = getredenvelopesmapper.particulars(redenvelopesid);
			List<Getredenvelopes>  fangzhu= getredenvelopesmapper.fangzhu(userid);
			for (int i = 0; i < selectLists.size(); i++) {
				String yingjjias = selectLists.get(i).getMoney();
				String[] str_Arr = yingjjias.substring(yingjjias.lastIndexOf('.') + 1, yingjjias.length()).split("");
				Integer index_1 = Integer.parseInt(str_Arr[0]);
				Integer index_2 = Integer.parseInt(str_Arr[1]);

				String fangzhusw = fangzhu.get(0).getMoney();
				String[] str_Arrs = fangzhusw.substring(fangzhusw.lastIndexOf('.') + 1, fangzhusw.length()).split("");
				Integer index_11 = Integer.parseInt(str_Arrs[0]);
				Integer index_22 = Integer.parseInt(str_Arrs[1]);
				System.out.println("str_Arr"+index_1+index_2+"赢家");
				System.out.println("str_Arrs"+index_11+index_22+"房主");


				if(selectLists.get(i).getRedenvelopesId().intValue()==fangzhu.get(0).getRedenvelopesId().intValue()) {
					selectLists.remove(selectLists.get(i));
				}
				System.out.println(selectLists.get(i).getMoney()+"for循环内的删除金额");
			}*/
			/*System.out.println(fangzhu.get(0).getMoney()+"房主");*/



			/*//赢家

			List<Getredenvelopes> selectLists = getredenvelopesmapper.particulars(redenvelopesid);
			for (int i = 0; i < selectLists.size(); i++) {
				if(selectLists.get(i).getUsersid()==lists.get(0).getUsersid()) {
					selectLists.remove(selectLists.get(i));//循环查找最大值，并且删除

				}
			}

			//lists.get(0).getMoney();//最大金额的money；
			String moneys = lists.get(0).getMoney();
			String[] numArr = moneys.toString().replace(".", "").split("");
			//豹子    1:3
			Accountdetails accountdetails = new Accountdetails();
			if(numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2])) {
				Double ss = Double.parseDouble(moneys);
				Double beishu = ss*3;//翻倍后的价格
				Double jianshu = beishu-ss;//发红包人员应该赔的金额

				Users fahongbaoid = usersmapper.selectById(userid);
				Double jianqu= fahongbaoid.getUserRechargeIntegral()-jianshu;
				fahongbaoid.setUserRechargeIntegral(jianqu);
				usersmapper.updateById(fahongbaoid);//修改发红包人账户积分
				//记录返钱三倍减去豹子金额
				accountdetails.setAddTime(Hutool.parseDateToString());
				accountdetails.setUseridId(userid);
				accountdetails.setSymbol(2);//     -
				accountdetails.setType(4);//红包扣除
				accountdetails.setMoney(jianshu);
				accountdetailsmapper.insert(accountdetails);

				EntityWrapper<Users> wrapper = new EntityWrapper<>();
				wrapper.eq("user_id", lists.get(0).getUsersid());
				List<Users> userziji = usersmapper.selectList(wrapper);

				EntityWrapper<Users> wrapper1 = new EntityWrapper<>();
				wrapper1.eq("user_invitation_code", userziji.get(0).getTheHigherTheAgent());//上级代理邀请码
				List<Users> usershangji = usersmapper.selectList(wrapper);
				if(!usershangji.isEmpty()) {
					if(userziji.get(0).getUserGrade()> usershangji.get(0).getUserGrade()) {
						//usershangji.get(0).getUserGrade();
						EntityWrapper<Agency> wrappere=new EntityWrapper<>();
						wrappere.eq("ageny_class", usershangji.get(0).getUserGrade());
						List<Agency> agency = agencymapper.selectList(wrappere);
						//agency.get(0).getChoushuiBili();
						Double baifenzhiyi= beishu*0.01;//用户赢钱的百分之一
						Double ziji = ss-baifenzhiyi;//自己应得金额
						Double yonghubaifenzhiwu = baifenzhiyi*agency.get(0).getChoushuiBili();//用户赢钱的百分之一分百分之五分给上级代理
						//上级代理应得费用
						Double jifen = usershangji.get(0).getUserRechargeIntegral()+yonghubaifenzhiwu;//用户原本积分加上百分之一的百分之五积分
						usershangji.get(0).setUserRechargeIntegral(jifen);
						usersmapper.updateById(usershangji.get(0));
						//账户明细
						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setUseridId(usershangji.get(0).getUserId());
						accountdetails.setSymbol(1);//     -
						accountdetails.setType(6);//红包扣除
						accountdetails.setMoney(yonghubaifenzhiwu);
						accountdetailsmapper.insert(accountdetails);
						//用户自己应得费用
						Double yingde= userziji.get(0).getUserRechargeIntegral()+ziji;
						userziji.get(0).setUserRechargeIntegral(yingde);
						usersmapper.updateById(userziji.get(0));//修改自己用户应得金额
						//账户明细
						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setUseridId(usershangji.get(0).getUserId());
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(5);//红包扣除
						accountdetails.setMoney(yingde);
						accountdetailsmapper.insert(accountdetails);
					}
				}else {
					//拿自己百分比抽水
					Double baifenzhiyi= beishu*0.01;//用户赢钱的百分之一
					Double ziji = ss-baifenzhiyi;//自己应得金额
					//userziji.get(0).getUserGrade();//自己会员等级
					if(userziji.get(0).getUserGrade()!=0) {
						EntityWrapper<Agency> wrpeer  = new EntityWrapper<>();
						wrpeer.eq("ageny_class", userziji.get(0).getUserGrade());
						List<Agency> selectList2 = agencymapper.selectList(wrpeer);
						Double yingde = baifenzhiyi*selectList2.get(0).getChoushuiBili();

						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setUseridId(userziji.get(0).getUserId());
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(6);//红包分红
						accountdetails.setMoney(yingde);
						accountdetailsmapper.insert(accountdetails);

						Double s= userziji.get(0).getUserRechargeIntegral()+ziji+yingde;
						userziji.get(0).setUserRechargeIntegral(s);
						usersmapper.updateById(userziji.get(0));

						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setUseridId(userziji.get(0).getUserId());
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(5);//红包分红
						accountdetails.setMoney(ziji);
						accountdetailsmapper.insert(accountdetails);
					}else {
						//不是会员
						Double s= userziji.get(0).getUserRechargeIntegral()+ziji;
						userziji.get(0).setUserRechargeIntegral(s);
						usersmapper.updateById(userziji.get(0));

						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setUseridId(userziji.get(0).getUserId());
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(5);//红包分红
						accountdetails.setMoney(ziji);
						accountdetailsmapper.insert(accountdetails);
					}
				}

			}else {
				String[] str_Arr = moneys.substring(moneys.lastIndexOf('.') + 1, moneys.length()).split("");
				Integer index_1 = Integer.parseInt(str_Arr[0]);
				Integer index_2 = Integer.parseInt(str_Arr[1]);
				String sum = (index_1 + index_2) + "";
				if (sum.length() > 1) {
					sum = sum.substring(sum.length() - 1, sum.length());
				}
				Integer sss = Integer.parseInt(sum);
				//1:1
				if(sss!=8 && sss !=9) {
					Double ss = Double.parseDouble(moneys);
					EntityWrapper<Users> wrapper = new EntityWrapper<>();
					wrapper.eq("user_id", lists.get(0).getUsersid());
					List<Users> userziji = usersmapper.selectList(wrapper);

					EntityWrapper<Users> wrapper1 = new EntityWrapper<>();
					wrapper1.eq("user_invitation_code", userziji.get(0).getTheHigherTheAgent());//上级代理邀请码
					List<Users> usershangji = usersmapper.selectList(wrapper);
					if(!usershangji.isEmpty()) {
						if(userziji.get(0).getUserGrade()> usershangji.get(0).getUserGrade()) {
							EntityWrapper<Agency> wrappere=new EntityWrapper<>();
							wrappere.eq("ageny_class", usershangji.get(0).getUserGrade());
							List<Agency> agency = agencymapper.selectList(wrappere);
							//agency.get(0).getChoushuiBili();
							Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
							Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九
							Double yonghubaifenzhiwu = baifenzhiyi*agency.get(0).getChoushuiBili();//用户赢钱的百分之一分百分之五分给上级代理

							//上级代理应得费用
							Double jifen = usershangji.get(0).getUserRechargeIntegral()+yonghubaifenzhiwu;//用户原本积分加上百分之一的百分之五积分
							usershangji.get(0).setUserRechargeIntegral(jifen);
							usersmapper.updateById(usershangji.get(0));
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(usershangji.get(0).getUserId());
							accountdetails.setSymbol(1);//     -
							accountdetails.setType(6);//红包扣除
							accountdetails.setMoney(yonghubaifenzhiwu);
							accountdetailsmapper.insert(accountdetails);

							//用户自己应得费用
							Double yingde= userziji.get(0).getUserRechargeIntegral()+ziji;
							userziji.get(0).setUserRechargeIntegral(yingde);
							usersmapper.updateById(userziji.get(0));//修改自己用户应得金额
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(userziji.get(0).getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(yingde);
							accountdetailsmapper.insert(accountdetails);

						}else {
							//自己等级已经超越之前的上级代理，自己拿自己的分红操作
							EntityWrapper<Agency> wrappere=new EntityWrapper<>();
							wrappere.eq("ageny_class", userziji.get(0).getUserGrade());
							List<Agency> agency = agencymapper.selectList(wrappere);
							//agency.get(0).getChoushuiBili();
							Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
							Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九
							Double yonghubaifenzhiwu = baifenzhiyi*agency.get(0).getChoushuiBili();//用户赢钱的百分之一分百分之五分给上级代理

							//用户自己应得费用
							Double yingde= userziji.get(0).getUserRechargeIntegral()+ziji+yonghubaifenzhiwu;
							userziji.get(0).setUserRechargeIntegral(yingde);
							usersmapper.updateById(userziji.get(0));//修改自己用户应得金额
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(userziji.get(0).getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(yingde);
							accountdetailsmapper.insert(accountdetails);

						}
					}else {
						//没有上级用户，判断是不是会员
						if(userziji.get(0).getUserGrade()==0) {
							Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
							Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九

							Double yingde= userziji.get(0).getUserRechargeIntegral()+ziji;
							userziji.get(0).setUserRechargeIntegral(yingde);
							usersmapper.updateById(userziji.get(0));//修改自己用户应得金额
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(userziji.get(0).getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(ziji);
							accountdetailsmapper.insert(accountdetails);
						}else {
							EntityWrapper<Agency> wrappere=new EntityWrapper<>();
							wrappere.eq("ageny_class", userziji.get(0).getUserGrade());
							List<Agency> agency = agencymapper.selectList(wrappere);
							//agency.get(0).getChoushuiBili();
							Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
							Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九
							Double yonghubaifenzhiwu = baifenzhiyi*agency.get(0).getChoushuiBili();//用户赢钱的百分之一分百分之五分给上级代理

							//用户自己应得费用
							Double yingde= userziji.get(0).getUserRechargeIntegral()+ziji+yonghubaifenzhiwu;
							userziji.get(0).setUserRechargeIntegral(yingde);
							usersmapper.updateById(userziji.get(0));//修改自己用户应得金额
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(userziji.get(0).getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(yingde);
							accountdetailsmapper.insert(accountdetails);
						}
					}
				}
				if(sss==8 || sss ==9) {
					Double yy = Double.parseDouble(moneys);
					EntityWrapper<Users> wrapper = new EntityWrapper<>();
					wrapper.eq("user_id", lists.get(0).getUsersid());
					List<Users> userziji = usersmapper.selectList(wrapper);

					EntityWrapper<Users> wrapper1 = new EntityWrapper<>();
					wrapper1.eq("user_invitation_code", userziji.get(0).getTheHigherTheAgent());//上级代理邀请码
					List<Users> usershangji = usersmapper.selectList(wrapper);
					if(!usershangji.isEmpty()) {
						if(userziji.get(0).getUserGrade()> usershangji.get(0).getUserGrade()) {
							//分销表抽水
							EntityWrapper<Agency> wrappere=new EntityWrapper<>();
							wrappere.eq("ageny_class", usershangji.get(0).getUserGrade());
							List<Agency> agency = agencymapper.selectList(wrappere);
							//agency.get(0).getChoushuiBili();
							Double ss = yy*2;
							Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
							Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九
							Double yonghubaifenzhiwu = baifenzhiyi*agency.get(0).getChoushuiBili();//用户赢钱的百分之一分百分之五分给上级代理
							Double kouchu = ss-yy;
							Users selectById = usersmapper.selectById(userid);
							Double kouchus= selectById.getUserRechargeIntegral()-kouchu;//扣除用户1:2赔率
							selectById.setUserRechargeIntegral(kouchus);
							usersmapper.updateById(selectById);
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(selectById.getUserId());
							accountdetails.setSymbol(2);//     +
							accountdetails.setType(4);//红包扣除
							accountdetails.setMoney(kouchu);
							accountdetailsmapper.insert(accountdetails);

							//上级代理分销
							Double shangji = usershangji.get(0).getUserRechargeIntegral()+yonghubaifenzhiwu;
							usershangji.get(0).setUserRechargeIntegral(shangji);
							usersmapper.updateById(usershangji.get(0));
							//账户明细
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(userziji.get(0).getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(6);//红包扣除
							accountdetails.setMoney(yonghubaifenzhiwu);
							accountdetailsmapper.insert(accountdetails);
							//自己得到金额
							Double userziji1 = userziji.get(0).getUserRechargeIntegral()+ziji;
							userziji.get(0).setUserRechargeIntegral(userziji1);
							usersmapper.updateById(userziji.get(0));

							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(userziji.get(0).getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(ziji);
							accountdetailsmapper.insert(accountdetails);
						}else {
							//会员等级已经比上级代理大
							//分销表抽水
							EntityWrapper<Agency> wrappere=new EntityWrapper<>();
							wrappere.eq("ageny_class", userziji.get(0).getUserGrade());
							List<Agency> agency = agencymapper.selectList(wrappere);
							if(!agency.isEmpty()) {
								//agency.get(0).getChoushuiBili();
								Double ss = yy*2;
								Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
								Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九
								Double yonghubaifenzhiwu = baifenzhiyi*agency.get(0).getChoushuiBili();//用户赢钱的百分之一分百分之五分给上级代理
								Double kouchu = ss-yy;//需要另附金额
								Users selectById = usersmapper.selectById(userid);
								Double kouchus= selectById.getUserRechargeIntegral()-kouchu;//扣除用户1:2赔率
								selectById.setUserRechargeIntegral(kouchus);
								usersmapper.updateById(selectById);

								Double yingde = userziji.get(0).getUserRechargeIntegral()+ziji+yonghubaifenzhiwu;
								userziji.get(0).setUserRechargeIntegral(yingde);
								usersmapper.updateById(userziji.get(0));

								accountdetails.setAddTime(Hutool.parseDateToString());
								accountdetails.setUseridId(userziji.get(0).getUserId());
								accountdetails.setSymbol(1);//     +
								accountdetails.setType(5);//红包扣除
								accountdetails.setMoney(ziji);
								accountdetailsmapper.insert(accountdetails);
							}else {
								//agency.get(0).getChoushuiBili();
								Double ss = yy*2;
								Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
								Double ziji = ss-baifenzhiyi;//自己应得金额   自己应得百分之九十九
								Double kouchu = ss-yy;//需要另附金额
								Users selectById = usersmapper.selectById(userid);
								Double kouchus= selectById.getUserRechargeIntegral()-kouchu;//扣除用户1:2赔率
								selectById.setUserRechargeIntegral(kouchus);
								usersmapper.updateById(selectById);

								Double yingde = userziji.get(0).getUserRechargeIntegral()+ziji;
								userziji.get(0).setUserRechargeIntegral(yingde);
								usersmapper.updateById(userziji.get(0));

								accountdetails.setAddTime(Hutool.parseDateToString());
								accountdetails.setUseridId(userziji.get(0).getUserId());
								accountdetails.setSymbol(1);//     +
								accountdetails.setType(5);//红包扣除
								accountdetails.setMoney(ziji);
								accountdetailsmapper.insert(accountdetails);
							}
						}
					}else {
						//没有上级代理信息
						Double ss = yy*2;
						Double baifenzhiyi= ss*0.01;//用户赢钱的百分之一   剩余百分之九十九
						Double wudaile = ss-baifenzhiyi;//没有代理费，自己也不是 会员
						Double kouchu = ss-yy;//需要另附金额
						Users selectById = usersmapper.selectById(userid);
						Double kouchus= selectById.getUserRechargeIntegral()-kouchu;//扣除用户1:2赔率
						selectById.setUserRechargeIntegral(kouchus);
						usersmapper.updateById(selectById);

						Double yingde = userziji.get(0).getUserRechargeIntegral()+wudaile;
						userziji.get(0).setUserRechargeIntegral(yingde);
						usersmapper.updateById(userziji.get(0));

						accountdetails.setAddTime(Hutool.parseDateToString());
						accountdetails.setUseridId(userziji.get(0).getUserId());
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(5);//红包扣除
						accountdetails.setMoney(wudaile);
						accountdetailsmapper.insert(accountdetails);
					}
				}

			}

			List<Getredenvelopes> particulars = getredenvelopesmapper.particulars(redenvelopesid);
			List<Getredenvelopes>  fangzhu= getredenvelopesmapper.fangzhu(userid);
			for (int i = 0; i < particulars.size(); i++) {
				if(particulars.get(i).getRedenvelopesId().intValue()==fangzhu.get(0).getRedenvelopesId().intValue()) {
					particulars.remove(particulars.get(i));
				}
				System.out.println(particulars.get(i).getMoney()+"for循环内的删除金额");
			}
			System.out.println(fangzhu.get(0).getMoney()+"房主");
			Users users = new Users();
			for (int i = 0; i < selectLists.size(); i++) {

				selectLists.get(i).getMoney();
				users.setUserId(selectLists.get(i).getUsersid());
				Users user = usersmapper.selectOne(users);
				Double jifen = user.getUserRechargeIntegral()+Double.parseDouble(selectLists.get(i).getMoney());
				user.setUserRechargeIntegral(jifen);
				usersmapper.updateById(user);

				accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
				accountdetails.setUseridId(user.getUserId());//用户id
				accountdetails.setSymbol(1);//     +
				accountdetails.setType(5);//红包
				accountdetails.setMoney(Double.parseDouble(selectLists.get(i).getMoney()));//赢取金额
				accountdetailsmapper.insert(accountdetails);

				EntityWrapper<Getredenvelopes> wtreppers = new EntityWrapper<>();
				wtreppers.eq("user_id", selectLists.get(i).getUsersid());
				wtreppers.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
				List<Getredenvelopes> selectList2 = getredenvelopesmapper.selectList(wtreppers);
				selectList2.get(0).setShuying(2);
				getredenvelopesmapper.updateById(selectList2.get(0));

			}
			EntityWrapper<Getredenvelopes> wtrepper = new EntityWrapper<>();
			wtrepper.eq("user_id", lists.get(0).getUsersid());
			wtrepper.eq("redenvelopes_id", lists.get(0).getRedenvelopesId());
			List<Getredenvelopes> selectList2 = getredenvelopesmapper.selectList(wtrepper);
			selectList2.get(0).setShuying(1);
			getredenvelopesmapper.updateById(selectList2.get(0));

			listss.addAll(lists);//最大值
			listss.addAll(selectLists);//除去最大领取，剩下金额
			return Body.newInstance(listss);
		*/
		/*List<Getredenvelopes> getsuccess = getredenvelopesmapper.getsuccess(redenvelopesid,userid);
			Getredenvelopes getredenvelopes = new Getredenvelopes();
			getredenvelopes.setGetRedenvelopesId(redenvelopesid);
			getredenvelopes.setMoney(getsuccess.get(0).getMoney()+"");
			getredenvelopes.setNickname(getsuccess.get(0).getNickname());
			getredenvelopes.setUsersid(getsuccess.get(0).getUsersid());
			getredenvelopes.setPortrait(getsuccess.get(0).getPortrait());
			getredenvelopes.setAddstime(getsuccess.get(0).getAddstime());

			List<Getredenvelopes> selectLists = getredenvelopesmapper.particulars(redenvelopesid);
			for (int i = 0; i < selectLists.size(); i++) {
				if(selectLists.get(i).getUsersid()==getsuccess.get(0).getUsersid()) {
					selectLists.remove(selectLists.get(i));//循环查找最大值，并且删除
				}
			}
			EntityWrapper<Getredenvelopes> wtrepper = new EntityWrapper<>();
			wtrepper.eq("user_id", getredenvelopes.getUsersid());
			wtrepper.eq("redenvelopes_id", getredenvelopes.getGetRedenvelopesId());
			List<Getredenvelopes> selectList2e = getredenvelopesmapper.selectList(wtrepper);
			selectList2e.get(0).setShuying(1);
			getredenvelopesmapper.updateById(selectList2e.get(0));

			listss.add(getredenvelopes);//两值相同，房主赢
			listss.addAll(selectLists);//除去房主，剩下最大金额
			String moneys = getredenvelopes.getMoney();
			String[] numArr = moneys.toString().replace(".", "").split("");
			if(numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2])) {
				Double ss = Double.parseDouble(moneys);
				//1:3
				Double beishu = ss*3;//翻倍后的价格
				Double jianshu = beishu-ss;
				//发红包人，找到这个人去他的积分账户减去豹子积分
				Redenvelopes redenvelopes = redenvelopesmapper.selectById(redenvelopesid);
				Users users = usersmapper.selectById(redenvelopes.getUserId());
				Double zongjia= users.getUserRechargeIntegral()-jianshu;
				users.setUserRechargeIntegral(zongjia);
				usersmapper.updateById(users);
				//新增红包支付记录
				Accountdetails accountdetails = new Accountdetails();
				accountdetails.setAddTime(Hutool.parseDateToString());
				accountdetails.setUseridId(users.getUserId());
				accountdetails.setSymbol(2);//     -
				accountdetails.setType(4);//红包扣除
				accountdetails.setMoney(jianshu);
				accountdetailsmapper.insert(accountdetails);

				*//**
				 * 分钱给上级用户
				 *//*
				Double fenqian= beishu*0.01;//拿出赢钱总价的百分之一；
				Double huode= beishu-fenqian;//赢家应得费用
				//查询上级用户
				Users users2 = new Users();
				EntityWrapper<Users> wrapper2 = new EntityWrapper<>();
				wrapper2.eq("user_invitation_code", users.getTheHigherTheAgent());
				List<Users> selectList3 = usersmapper.selectList(wrapper2);
				Users user = selectList3.get(0);
				Users userziji = usersmapper.selectById(getsuccess.get(0).getUsersid());//自己用户
				//如果没有上级代理，直接抽取百分之一
				if(user==null) {

					EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					wrapper.eq("ageny_class", users2.getUserGrade());
					List<Agency> selectList2 = agencymapper.selectList(wrapper);//用户抽成比例
					Agency agency = selectList2.get(0);//第一个
					Double ziji = fenqian*agency.getChoushuiBili();

					Double yingqian= users.getUserRechargeIntegral()+huode+ziji;
					users.setUserRechargeIntegral(yingqian);
					usersmapper.updateById(users);
					accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
					accountdetails.setUseridId(users.getUserId());//用户id
					accountdetails.setSymbol(1);//     +
					accountdetails.setType(5);//红包扣除
					accountdetails.setMoney(huode+ziji);//赢取金额
					accountdetailsmapper.insert(accountdetails);
				}
				//用户会员等级
				if(userziji.getUserGrade() > user.getUserGrade()) {
					//分销    只分销给直属上级，剩下的费用消失；
					EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					wrapper.eq("ageny_class", user.getUserGrade());
					List<Agency> selectList2 = agencymapper.selectList(wrapper);//用户抽成比例
					Agency agency = selectList2.get(0);//第一个
					Double bilis=agency.getChoushuiBili();//上级应得下级分红百分之几
					Double shangjifenhong= fenqian*bilis;//上级应得分红
					//给上级用户相应的金钱，剩下的作废
					Double xiugai = user.getUserRechargeIntegral()+shangjifenhong;
					user.setUserRechargeIntegral(xiugai);
					usersmapper.updateById(user);//增加上级用户积分
					accountdetails.setAddTime(Hutool.parseDateToString());
					accountdetails.setUseridId(user.getUserId());
					accountdetails.setSymbol(1);//     -
					accountdetails.setType(6);//红包分红
					accountdetails.setMoney(jianshu);
					accountdetailsmapper.insert(accountdetails);
					//将红包收入账户
					Double yingqian= users.getUserRechargeIntegral()+huode;
					users.setUserRechargeIntegral(yingqian);
					usersmapper.updateById(users);
					accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
					accountdetails.setUseridId(users.getUserId());//用户id
					accountdetails.setSymbol(1);//     +
					accountdetails.setType(5);//红包扣除
					accountdetails.setMoney(huode);//赢取金额
					accountdetailsmapper.insert(accountdetails);
				}else {
					//已经超越上级代理的会员等级

					EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					wrapper.eq("ageny_class", users2.getUserGrade());
					List<Agency> selectList2 = agencymapper.selectList(wrapper);//用户抽成比例
					Agency agency = selectList2.get(0);//第一个
					Double ziji = fenqian*agency.getChoushuiBili();

					Double yingqian= users.getUserRechargeIntegral()+huode+ziji;
					users.setUserRechargeIntegral(yingqian);
					usersmapper.updateById(users);
					accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
					accountdetails.setUseridId(users.getUserId());//用户id
					accountdetails.setSymbol(1);//     +
					accountdetails.setType(5);//红包扣除
					accountdetails.setMoney(huode+ziji);//赢取金额
					accountdetailsmapper.insert(accountdetails);
				}
			}else {
				String[] str_Arr = moneys.substring(moneys.lastIndexOf('.') + 1, moneys.length()).split("");
				Integer index_1 = Integer.parseInt(str_Arr[0]);
				Integer index_2 = Integer.parseInt(str_Arr[1]);
				String sum = (index_1 + index_2) + "";
				if (sum.length() > 1) {
					sum = sum.substring(sum.length() - 1, sum.length());
				}
				Integer sss = Integer.parseInt(sum);
				if(sss!=8 && sss !=9) {
					//1:1
					Double ss = Double.parseDouble(moneys);
					Double fenqian= ss*0.01;//拿出赢钱总价的百分之一；
					Double huode= ss-fenqian;//赢家应得费用
					//查询上级用户
					Users users2 = new Users();
					users2.setUserId(getredenvelopes.getUsersid());
					Users userziji = usersmapper.selectOne(users2);//自己详细信息
					Accountdetails accountdetails = new Accountdetails();
					if(userziji.getTheHigherTheAgent() != null) {
						//上级用户详细信息
						EntityWrapper<Users> wrapper2 = new EntityWrapper<>();
						wrapper2.eq("user_invitation_code", userziji.getTheHigherTheAgent());
						List<Users> selectList3 = usersmapper.selectList(wrapper2);
						Users users = selectList3.get(0);
						//判断用户是否在上级
						if(userziji.getUserGrade()>users.getUserGrade()) {
							EntityWrapper<Agency> wrapper = new EntityWrapper<>();
							wrapper.eq("ageny_class", users.getUserGrade());
							List<Agency> selectList2 = agencymapper.selectList(wrapper);//用户抽成比例
							Agency agency = selectList2.get(0);
							Double bilis=agency.getChoushuiBili();//上级应得下级分红百分之几
							Double shangjifenhong= fenqian*bilis;//上级应得分红
							//添加用户领取红包百分之一的百分之UserRechargeIntegral；
							Double jifen = users.getUserRechargeIntegral()+shangjifenhong;
							users.setUserRechargeIntegral(jifen);
							usersmapper.updateById(users);
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(users.getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(6);//红包分红
							accountdetails.setMoney(shangjifenhong);
							accountdetailsmapper.insert(accountdetails);
							//添加自己百分之九十九的红包金额
							Double yingqian = userziji.getUserRechargeIntegral()+huode;
							userziji.setUserRechargeIntegral(yingqian);
							usersmapper.updateById(userziji);
							accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
							accountdetails.setUseridId(userziji.getUserId());//用户id
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(huode);//赢取金额
							accountdetailsmapper.insert(accountdetails);
						}else {
							//已经超越上级代理的会员等级
							Double yingqian= userziji.getUserRechargeIntegral()+huode;
							userziji.setUserRechargeIntegral(yingqian);
							usersmapper.updateById(userziji);
							accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
							accountdetails.setUseridId(userziji.getUserId());//用户id
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(6);//红包扣除
							accountdetails.setMoney(huode);//赢取金额
							accountdetailsmapper.insert(accountdetails);
						}
					}else {
						Double yingqian= userziji.getUserRechargeIntegral()+huode;
						userziji.setUserRechargeIntegral(yingqian);
						usersmapper.updateById(userziji);
						accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
						accountdetails.setUseridId(userziji.getUserId());//用户id
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(6);//红包扣除
						accountdetails.setMoney(huode);//赢取金额
						accountdetailsmapper.insert(accountdetails);
					}

				}
				if(sss==8 || sss==9) {
					//1:2
					Double yy = Double.parseDouble(moneys);
					Double ss = yy*2;
					Double fenqian= yy*0.01;//拿出赢钱总价的百分之一；

					Double huode= ss-fenqian;//赢家应得费用
					//查询上级用户
					Users users2 = new Users();
					users2.setUserId(getredenvelopes.getUsersid());
					Users userziji = usersmapper.selectOne(users2);//自己详细信息
					Accountdetails accountdetails = new Accountdetails();
					if(userziji.getTheHigherTheAgent() != null) {
						//上级用户详细信息
						EntityWrapper<Users> wrapper2 = new EntityWrapper<>();
						wrapper2.eq("user_invitation_code", userziji.getTheHigherTheAgent());
						List<Users> selectList3 = usersmapper.selectList(wrapper2);
						Users users = selectList3.get(0);


						//判断用户是否在上级
						if(userziji.getUserGrade()>users.getUserGrade()) {
							EntityWrapper<Agency> wrapper = new EntityWrapper<>();
							wrapper.eq("ageny_class", users.getUserGrade());
							List<Agency> selectList2 = agencymapper.selectList(wrapper);//用户抽成比例
							Agency agency = selectList2.get(0);
							Double bilis=agency.getChoushuiBili();//上级应得下级分红百分之几
							Double shangjifenhong= fenqian*bilis;//上级应得分红
							//添加用户领取红包百分之一的百分之UserRechargeIntegral；
							Double jifen = users.getUserRechargeIntegral()+shangjifenhong;
							users.setUserRechargeIntegral(jifen);
							usersmapper.updateById(users);
							accountdetails.setAddTime(Hutool.parseDateToString());
							accountdetails.setUseridId(users.getUserId());
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(6);//红包分红
							accountdetails.setMoney(shangjifenhong);
							accountdetailsmapper.insert(accountdetails);
							//添加自己百分之九十九的红包金额
							Double yingqian = userziji.getUserRechargeIntegral()+huode;
							userziji.setUserRechargeIntegral(yingqian);
							usersmapper.updateById(userziji);
							accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
							accountdetails.setUseridId(userziji.getUserId());//用户id
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(huode);//赢取金额
							accountdetailsmapper.insert(accountdetails);
						}else {
							//已经超越上级代理的会员等级
							Double yingqian= userziji.getUserRechargeIntegral()+huode;
							userziji.setUserRechargeIntegral(yingqian);
							usersmapper.updateById(userziji);
							accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
							accountdetails.setUseridId(userziji.getUserId());//用户id
							accountdetails.setSymbol(1);//     +
							accountdetails.setType(5);//红包扣除
							accountdetails.setMoney(huode);//赢取金额
							accountdetailsmapper.insert(accountdetails);
						}
					}else {
						Double yingqian= userziji.getUserRechargeIntegral()+huode;
						userziji.setUserRechargeIntegral(yingqian);
						usersmapper.updateById(userziji);
						accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
						accountdetails.setUseridId(userziji.getUserId());//用户id
						accountdetails.setSymbol(1);//     +
						accountdetails.setType(5);//红包扣除
						accountdetails.setMoney(huode);//赢取金额
						accountdetailsmapper.insert(accountdetails);
					}
				}
			}

			*//**
			 * 剩下用户所得积分
			 *//*
			Accountdetails accountdetails = new Accountdetails();
			Users users = new Users();
			for (int i = 0; i < selectLists.size(); i++) {
				selectLists.get(i).getMoney();
				users.setUserId(selectLists.get(i).getUsersid());
				Users user = usersmapper.selectOne(users);
				Double jifen = user.getUserRechargeIntegral()+Double.parseDouble(selectLists.get(i).getMoney());
				user.setUserRechargeIntegral(jifen);
				usersmapper.updateById(user);

				accountdetails.setAddTime(Hutool.parseDateToString());//赢取时间
				accountdetails.setUseridId(user.getUserId());//用户id
				accountdetails.setSymbol(1);//     +
				accountdetails.setType(5);//红包
				accountdetails.setMoney(Double.parseDouble(selectLists.get(i).getMoney()));//赢取金额
				accountdetailsmapper.insert(accountdetails);

				EntityWrapper<Getredenvelopes> wtreppers = new EntityWrapper<>();
				wtreppers.eq("user_id", selectLists.get(i).getUsersid());
				wtreppers.eq("redenvelopes_id", selectLists.get(i).getRedenvelopesId());
				List<Getredenvelopes> selectList2 = getredenvelopesmapper.selectList(wtreppers);
				selectList2.get(0).setShuying(2);
				getredenvelopesmapper.updateById(selectList2.get(0));

			}
			*//**
			 * 修改红包分配比例：查询getredenvelopes表中用户领取的红包，
			 * 拿到房主金额一个一个比较，
			 * 把输的金额筛选出来，
			 * for循环  每一个用户抢的红包金额*2，加到房主账户；
			 *
			 * 第一名，如果赢了，直接扣除房主账户余额；
			 *
			 *//*
			return Body.newInstance(listss);
		*/
	}

	@Override
	public Body getlingquuser(Integer redenvelopesid) {
		List<Getredenvelopes> selectList = getredenvelopesmapper.particulars(redenvelopesid);
		if(!selectList.isEmpty()) {
			return Body.newInstance(selectList);
		}
		return Body.newInstance(501,"查询失败");
	}

	@Override
	public Body getliushui(String yonghuming,Integer leixing,Integer shuxings) {
		List<Getredenvelopes> list = getredenvelopesmapper.selectliushui(yonghuming,leixing,shuxings);
		ResultVo resultVo = AutoPage.work(list);
		return Body.newInstance(resultVo);
	}

	@Override
	public Body usersuccess(Integer redenvelopesid) {
		List<Getredenvelopes> list = getredenvelopesmapper.shuyingusers(redenvelopesid);
		System.out.println(list);
		for (int j = 0; j < list.size(); j++) {
			String  s= list.get(j).getAddTime();
			String sss = s.substring(11);
			list.get(j).setAddTime(sss);
			EntityWrapper<Users> wrapper = new EntityWrapper<>();
			wrapper.eq("user_id", list.get(j).getUserId());
			List<Users> selectList2 = usersmapper.selectList(wrapper);
			list.get(j).setUsers(selectList2.get(0).getUserRechargeIntegral());
		}
		Redenvelopes selectById = redenvelopesmapper.selectById(redenvelopesid);
		Room selectById2 = roommapper.selectById(selectById.getRoomId());
		selectById2.setYouxizhong(1);
		roommapper.updateById(selectById2);

		return Body.newInstance(list);
	}

	@Override
	public Body tuichu(Integer userid,Integer money) {

		Users selectById = usersmapper.selectById(userid);
		if(selectById.getUserRechargeIntegral()<money) {
			return Body.newInstance(501,"您的积分不足");
		}
		return Body.BODY_200;
	}















	/*
	 * EntityWrapper<Getredenvelopes> wrappers = new EntityWrapper<>();
	 * wrappers.eq("redenvelopes_id", redenvelopesid); List<Getredenvelopes>
	 * selectList2 = getredenvelopesmapper.selectList(wrappers); List<String> lisys
	 * = new ArrayList<>(); for(int i=0;i<selectList2.size();i++) { Double diyi=
	 * selectList2.get(i).getGetRedenvelopesMoney(); String diyida = diyi+"";
	 * String[] str_Arr = diyida.substring(diyida.lastIndexOf('.') + 1,
	 * diyida.length()).split(""); Integer index_1 = Integer.parseInt(str_Arr[0]);
	 * Integer index_2 = Integer.parseInt(str_Arr[1]);
	 *
	 * String sum = (index_1 + index_2) + ""; lisys.add(sum); }
	 * System.out.println(lisys);
	 */
	/*
	 * Double diyi = selectList2.get(0).getGetRedenvelopesMoney(); Double dier =
	 * selectList2.get(1).getGetRedenvelopesMoney();
	 *
	 * String diyida = diyi+""; String[] str_Arr =
	 * diyida.substring(diyida.lastIndexOf('.') + 1, diyida.length()).split("");
	 * Integer index_1 = Integer.parseInt(str_Arr[0]); Integer index_2 =
	 * Integer.parseInt(str_Arr[1]); String dierda = dier+""; String[] str_Arr1 =
	 * dierda.substring(dierda.lastIndexOf('.') + 1, dierda.length()).split("");
	 * Integer index_11 = Integer.parseInt(str_Arr1[0]); Integer index_22 =
	 * Integer.parseInt(str_Arr1[1]); String sum = (index_1 + index_2) + ""; String
	 * sum1 = (index_11 + index_22) + "";
	 *
	 * Integer ss = Integer.parseInt(diyida.substring(0, 1)); Integer ss1 =
	 * Integer.parseInt(dierda.substring(0, 1));
	 */

	/*
	 * if(sum.equals(sum1) && ss==ss1){ return Body.newInstance(selectList2+"房主赢哦");
	 * }
	 */

	/*
	 * for (int i = 0; i < list2.size()-1; i++) { for(int j=0;j<list2.size()-i;j++){
	 * if(list2[j]>list2[j+1]){ int tmp=nums[j+1]; nums[j+1]=nums[j]; nums[j]=tmp; }
	 * } }
	 */
	/*
	 * List<BigDecimal> list = Pickup.math(BigDecimal.valueOf(money), num);
	 * Redenvelopes redenvelopes = new Redenvelopes();
	 * redenvelopes.setValueOfPack(money); redenvelopes.setUserId(userid);
	 * redenvelopes.setRoomId(roomid); redenvelopes.setRedEnvelopesNum(num);
	 * redenvelopes.setAddTime(Hutool.parseDateToString()); Integer insert =
	 * redenvelopesmapper.insert(redenvelopes); System.out.println(list);
	 * Collections.sort(list, new Comparator<BigDecimal>() { public int
	 * compare(BigDecimal o1, BigDecimal o2) { if (Pickup.is_baozi(o1) &&
	 * Pickup.is_baozi(o2)) { // 比较数和被比较数都为豹子时处理 return Pickup.getlast(o1) -
	 * Pickup.getlast(o2); } if (Pickup.is_baozi(o1) && !Pickup.is_baozi(o2)) { //
	 * 比较数是豹子和被比较数不为豹子 return 1; } if (!Pickup.is_baozi(o1) && Pickup.is_baozi(o2))
	 * { // 比较数不为豹子 和 被比较数为豹子 return -1; } Integer result = Pickup.getsumlast(o1) -
	 * Pickup.getsumlast(o2); // 末两位求和结果相等 结果处理 比较首位 return result == 0 ?
	 * Pickup.getfirst(o1) - Pickup.getfirst(o2) : result; } });
	 * System.out.println(list+"==============="); Collections.reverse(list); int
	 * conts = num-1; List<Userroom> userroomlist =null; List<Integer> list2 = new
	 * ArrayList<>();
	 *
	 * Users users = new Users();
	 *
	 * for (BigDecimal bigDecimal : list) { int count = list.size()-1;
	 * if(count==conts) { EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
	 * wrapper.eq("room_id", roomid); userroomlist =
	 * userroommapper.selectList(wrapper); for (Userroom userroom : userroomlist) {
	 * users = usersmapper.selectById(userroom.getUserId());
	 * list2.add(users.getChangci()); }
	 *
	 * Bonus bonus = new Bonus();
	 * bonus.setBonusMony(bigDecimal.doubleValue());//每一份的红包
	 * bonus.setAddTime(Hutool.parseDateToString());//添加时间 bonus.setUserId(userid);
	 * bonus.setRoomId(roomid); bonus.setBonusState(0);//未领取
	 * bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());
	 * bonus.setZuidaMoney(2);//最大金额 bonusmapper.insert(bonus); }else { //红包分成num个
	 * Bonus bonus = new Bonus();
	 * bonus.setBonusMony(bigDecimal.doubleValue());//每一份的红包
	 * bonus.setAddTime(Hutool.parseDateToString());//添加时间 bonus.setUserId(userid);
	 * bonus.setRoomId(roomid); bonus.setBonusState(0);//未领取 bonus.setZuidaMoney(0);
	 * bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());
	 * bonusmapper.insert(bonus); } conts++; } if(insert !=null) { //减去用户发红包的钱 users
	 * = usersmapper.selectById(userid); Double jian =
	 * users.getUserRechargeIntegral()-money; users.setUserRechargeIntegral(jian);
	 * usersmapper.updateById(users);
	 *
	 * return Body.newInstance(money+"红包"); }
	 */

	/*
	 * @Override public Body adduserredenvelopes(Integer usersid,Integer
	 * redenvelopesid) { //赢输场次 Users users = usersmapper.selectById(usersid);
	 *
	 * //查询红包 EntityWrapper<Bonus> wrapper = new EntityWrapper<>();
	 * wrapper.eq("hongbao_id", redenvelopesid); List<Bonus> selectList =
	 * bonusmapper.selectList(wrapper); Getredenvelopes getredenvelopes = new
	 * Getredenvelopes(); for (Bonus bonus : selectList) {
	 * if(users.getChangci()!=null && users.getChangci() !=0 &&
	 * bonus.getZuidaMoney() ==2 && bonus.getBonusState() !=1)
	 * {//用户场次不为空，不为0，红包最大未领取状态； getredenvelopes.setUserId(usersid);//用户id
	 * getredenvelopes.setRedenvelopesId(bonus.getBonusId());//红包分配表id
	 * getredenvelopes.setGetRedenvelopesMoney(bonus.getBonusMony());//最大金额id
	 * getredenvelopes.setAddTime(Hutool.parseDateToString());//领取时间
	 * getredenvelopesmapper.insert(getredenvelopes);//新增领取红包
	 *
	 * bonus.setZuidaMoney(1);//已经领取最大红包数量 bonus.setBonusState(1);//已经领取
	 * bonusmapper.updateById(bonus);//修改证明 红包已经被领取，最大红包已经被领取
	 *
	 * Integer lingqu= users.getChangci()-1;//修改用户风控赢取数量 users.setChangci(lingqu);
	 * usersmapper.updateById(users); return
	 * Body.newInstance(bonus.getBonusMony()+"元"); }else if(bonus.getZuidaMoney()
	 * !=1){ if(bonus.getBonusState()==0) {
	 * getredenvelopes.setUserId(usersid);//用户id
	 * getredenvelopes.setRedenvelopesId(bonus.getBonusId());//红包分配表id
	 * getredenvelopes.setGetRedenvelopesMoney(bonus.getBonusMony());//最大金额id
	 * getredenvelopes.setAddTime(Hutool.parseDateToString());//领取时间
	 * getredenvelopesmapper.insert(getredenvelopes);//新增领取红包
	 * bonus.setBonusState(1);//已经领取 bonusmapper.updateById(bonus);//修改证明
	 * 红包已经被领取，最大红包已经被领取 return Body.newInstance(bonus.getBonusMony()+"元"); } } }
	 * return Body.newInstance(501,"领取失败"); }
	 */
	/*
	 * Redenvelopes redenvelopes = new Redenvelopes();
	 * redenvelopes.setValueOfPack(money); redenvelopes.setUserId(userid);
	 * redenvelopes.setRoomId(roomid); redenvelopes.setRedEnvelopesNum(num); Integer
	 * insert = redenvelopesmapper.insert(redenvelopes); Collections.sort(moneys,
	 * new Comparator<BigDecimal>() {
	 *
	 * @Override public int compare(BigDecimal m_1, BigDecimal m_2) { String m1 =
	 * m_1.toString(); String m2 = m_2.toString(); String[] m1s
	 * =m1.substring(m1.lastIndexOf('.')+1, m1.length()).split(""); String[] m2s
	 * =m2.substring(m2.lastIndexOf('.')+1, m2.length()).split(""); // Integer
	 * ss=(Integer.parseInt(m1s[0])+Integer.parseInt(m1s[1]))-(Integer.parseInt(m2s[
	 * 0])+Integer.parseInt(m2s[1])); Integer ss1=
	 * Integer.parseInt(m1s[0])+Integer.parseInt(m1s[1]); Integer sse =
	 * Integer.parseInt(m2s[0])+Integer.parseInt(m2s[1]); String string =
	 * ss1.toString(); String string2 = sse.toString();
	 * System.out.println(string+"---------------------");
	 * System.out.println(string2+"___________________");
	 *
	 * //string2.substring(beginIndex, endIndex) return
	 * (Integer.parseInt(m1s[0])+Integer.parseInt(m1s[1]))-(Integer.parseInt(m2s[0])
	 * +Integer.parseInt(m2s[1])); } }); if (moneys != null) { for (BigDecimal
	 * bigDecimal : moneys) { //BigDecimal装String String string1 =
	 * bigDecimal.toString(); //字符串分割 String substring = string1.substring(2, 3);
	 * String substring2 = string1.substring(3, 4); int a1 =
	 * Integer.parseInt(substring); int a2 = Integer.parseInt(substring2); Integer
	 * ss = a1+a2; if(ss>9) { String sq = ss+""; String substring3 = sq.substring(1,
	 * 2); Bonus bonus = new Bonus();
	 * bonus.setBonusMony(bigDecimal.doubleValue());//每一份的红包
	 * bonus.setAddTime(Hutool.parseDateToString());//添加时间 bonus.setUserId(userid);
	 * bonus.setRoomId(roomid); bonus.setBonusState(0);//未领取
	 * bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());
	 * bonus.setDianshu(Integer.parseInt(substring3));//最大点数
	 * bonusmapper.insert(bonus); }else { Bonus bonus = new Bonus();
	 * bonus.setBonusMony(bigDecimal.doubleValue());//每一份的红包
	 * bonus.setAddTime(Hutool.parseDateToString());//添加时间 bonus.setUserId(userid);
	 * bonus.setRoomId(roomid); bonus.setBonusState(0);//未领取
	 * bonus.setHongbaoId(redenvelopes.getRedEnvelopesId());
	 * bonus.setDianshu(ss);//最大点数 bonusmapper.insert(bonus); } } if(insert !=null)
	 * { return Body.newInstance("发出成功"); } } return Body.newInstance(501,"发红包失败");
	 */
	/*
	 * public static void main(String[] args) { // 1.11, 1.68, 0.60, 6.16, 0.45
	 * List<BigDecimal> list = new ArrayList<>(); list.add(new BigDecimal("1.18"));
	 * list.add(new BigDecimal("1.11")); list.add(new BigDecimal("0.45"));
	 * list.add(new BigDecimal("0.60")); list.add(new BigDecimal("6.66"));
	 * List<BigDecimal> list = Pickup.math(new BigDecimal("10"), 10);
	 * System.out.println(list); Collections.sort(list, new Comparator<BigDecimal>()
	 * {
	 *
	 * @Override public int compare(BigDecimal o1, BigDecimal o2) { if
	 * (Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) { // 比较数和被比较数都为豹子时处理 return
	 * Pickup.getlast(o1) - Pickup.getlast(o2); } if (Pickup.is_baozi(o1) &&
	 * !Pickup.is_baozi(o2)) { // 比较数是豹子和被比较数不为豹子 return 1; } if
	 * (!Pickup.is_baozi(o1) && Pickup.is_baozi(o2)) { // 比较数不为豹子 和 被比较数为豹子 return
	 * -1; } Integer result = Pickup.getsumlast(o1) - Pickup.getsumlast(o2); //
	 * 末两位求和结果相等 结果处理 比较首位 return result == 0 ? Pickup.getfirst(o1) -
	 * Pickup.getfirst(o2) : result; } });
	 * System.out.println(list+"==============="); }
	 */

	/**
	 * 计算每人获得红包金额;最小每人0.01元
	 *
	 * @param mmm
	 *            红包总额
	 * @param number
	 *            人数
	 * @return
	 */
	/*
	 * public static List<BigDecimal> math(BigDecimal mmm, int number) { if
	 * (mmm.doubleValue() < number * 0.01) { return null; } Random random = new
	 * Random(); // 金钱，按分计算 10块等于 1000分 int money =
	 * mmm.multiply(BigDecimal.valueOf(100)).intValue(); // 随机数总额 double count = 0;
	 * // 每人获得随机点数 double[] arrRandom = new double[number]; // 每人获得钱数
	 * List<BigDecimal> arrMoney = new ArrayList<BigDecimal>(number); // 循环人数 随机点
	 * for (int i = 0; i < arrRandom.length; i++) { int r = random.nextInt((number)
	 * * 99) + 1; count += r; arrRandom[i] = r; } // 计算每人拆红包获得金额 int c = 0; for (int
	 * i = 0; i < arrRandom.length; i++) { // 每人获得随机数相加 计算每人占百分比 Double x = new
	 * Double(arrRandom[i] / count); // 每人通过百分比获得金额 int m = (int) Math.floor(x *
	 * money); // 如果获得 0 金额，则设置最小值 1分钱 if (m == 0) { m = 1; } // 计算获得总额 c += m; //
	 * 如果不是最后一个人则正常计算 if (i < arrRandom.length - 1) { arrMoney.add(new
	 * BigDecimal(m).setScale(2).divide(new BigDecimal(100))); } else { //
	 * 如果是最后一个人，则把剩余的钱数给最后一个人 arrMoney.add(new BigDecimal(money - c +
	 * m).setScale(2).divide(new BigDecimal(100).setScale(2))); } } // 随机打乱每人获得金额
	 * Collections.shuffle(arrMoney); return arrMoney ; }
	 */
	/*
	 * [Users(userId=1, userName=刘德华, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685292, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=49880.0, addTime=2019-04-28, upTime=null,
	 * userPhone=17775071173, userNickname=刘德华, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=1), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2)] [Users(userId=1,
	 * userName=刘德华, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685292, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=49880.0, addTime=2019-04-28, upTime=null,
	 * userPhone=17775071173, userNickname=刘德华, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=1), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2)]
	 */

	/*
	 * [Users(userId=1, userName=刘德华, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685292, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=49880.0, addTime=2019-04-28, upTime=null,
	 * userPhone=17775071173, userNickname=刘德华, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=1), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2), Users(userId=4,
	 * userName=迪丽热巴, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685295, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=null, upTime=null,
	 * userPhone=17775071172, userNickname=迪丽热巴, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=5), Users(userId=3,
	 * userName=刘翔, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685294, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071175, userNickname=刘翔, userRobot=1, userImg=null,
	 * theHigherTheAgent=0, userNum=39, changci=80)]////////////////////////////
	 * [Users(userId=1, userName=刘德华, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685292, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=49880.0, addTime=2019-04-28, upTime=null,
	 * userPhone=17775071173, userNickname=刘德华, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=1), Users(userId=2,
	 * userName=周润发, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685293, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071174, userNickname=周润发, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=2), Users(userId=4,
	 * userName=迪丽热巴, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685295, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=null, upTime=null,
	 * userPhone=17775071172, userNickname=迪丽热巴, userRobot=1, userImg=null,
	 * theHigherTheAgent=685294, userNum=50, changci=5), Users(userId=3,
	 * userName=刘翔, userPassword=6B1AA0BAB50A623B5CDE34224EB67573,
	 * userInvitationCode=685294, userGrade=5, userRechargeMoney=50000.0,
	 * userRechargeIntegral=50000.0, addTime=2019-04-30 15:42:11, upTime=null,
	 * userPhone=17775071175, userNickname=刘翔, userRobot=1, userImg=null,
	 * theHigherTheAgent=0, userNum=39,
	 * changci=80)]-----------------------------------
	 */
	/*Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=4, portrait=https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg, nickname=迪丽热巴, money=0.36, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null),
		Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=2, portrait=https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1122649470,955539824&fm=27&gp=0.jpg, nickname=周润发, money=2.61, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null),
			Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=3, portrait=https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3147328226,3569316785&fm=27&gp=0.jpg, nickname=刘翔, money=4.20, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null),
				Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=1, portrait=https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1424809995,4070055505&fm=27&gp=0.jpg, nickname=刘德华, money=2.83, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null)
*/
	/*[Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=4, portrait=https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg, nickname=迪丽热巴, money=0.36, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null),
		Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=2, portrait=https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1122649470,955539824&fm=27&gp=0.jpg, nickname=周润发, money=2.61, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null),
			Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=null, usersid=3, portrait=https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3147328226,3569316785&fm=27&gp=0.jpg, nickname=刘翔, money=4.20, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null)]
*/
	/*[Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=1, usersid=4, portrait=https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg, nickname=迪丽热巴, money=0.36, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null),
		Getredenvelopes(getRedenvelopesId=null, userId=null, redenvelopesId=397, addTime=null, getRedenvelopesMoney=null, shuying=1, usersid=2, portrait=https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1122649470,955539824&fm=27&gp=0.jpg, nickname=周润发, money=0.36, addstime=2019-05-25 14:46:37, sum=null, yonghuming=null, fangjianhao=null, leixing=null, moneysss=null, shuxings=null, shuxingjine=null, fangzhuming=null)]
*/
}
