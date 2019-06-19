package com.zcf.service.impl;

import com.zcf.pojo.Accountdetails;
import com.zcf.pojo.Agency;
import com.zcf.pojo.Applys;
import com.zcf.pojo.Code;
import com.zcf.pojo.Concurnaysay;
import com.zcf.pojo.Customerservice;
import com.zcf.pojo.Htlogin;
import com.zcf.pojo.Room;
import com.zcf.pojo.Userroom;
import com.zcf.pojo.Users;
import com.zcf.common.code.ShareCodeUtil;
import com.zcf.common.json.Body;
import com.zcf.common.result.ResultVo;
import com.zcf.common.utils.Hutool;
import com.zcf.common.utils.MD5Util;
import com.zcf.mapper.AccountdetailsMapper;
import com.zcf.mapper.AgencyMapper;
import com.zcf.mapper.ApplysMapper;
import com.zcf.mapper.CodeMapper;
import com.zcf.mapper.ConcurnaysayMapper;
import com.zcf.mapper.CustomerserviceMapper;
import com.zcf.mapper.HtloginMapper;
import com.zcf.mapper.RoomMapper;
import com.zcf.mapper.UserroomMapper;
import com.zcf.mapper.UsersMapper;
import com.zcf.service.UsersService;
import com.zcf.utils.AutoPage;
import com.zcf.utils.CloudSMS;
import com.zcf.utils.GenerateName;
import com.zcf.utils.UploadImgUtils;


import com.alipay.api.internal.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-28
 */
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

	private static String Url = "http://106.wx96.com/webservice/sms.php?method=Submit";
	@Autowired
	private UsersMapper usersmapper;
	@Autowired
	private CodeMapper codemapper;
	@Autowired
	private AccountdetailsMapper accountdetailsmapper;
	@Autowired
	private CustomerserviceMapper customerservicemapper;
	@Autowired
	private HtloginMapper htloginmapper;
	@Autowired
	private RoomMapper roommapper;
	@Autowired
	private ApplysMapper applysmapper;
	@Autowired
	private UserroomMapper userroommapper;
	@Autowired
	private AgencyMapper agencymapper;
	@Autowired
	private ConcurnaysayMapper concurnaysaymapper;
	@Override
	/**
	 * 用户登录
	 */
	public Body login(String userphone, String userpassword) {
		Users users = new Users();
		users.setUserPhone(userphone);
		Users selectOne = usersmapper.selectOne(users);//查询一条数据，因为手机号是唯一
		if(selectOne!=null) {
			if(selectOne.getUserPassword().equals(MD5Util.MD5EncodeUtf8(userpassword))) {//查出的密码和输入的密码是否一致
				return Body.newInstance(selectOne);//返回用户信息
			}else {
				return Body.newInstance(506,"密码错误");
			}
		}
		return Body.newInstance(501,"用户名不存在");
	}
	@Override
	public Body codes(String phone) {
		
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		int mobile_code = (int)((Math.random()*9+1)*100000);
	    String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "C34462221"), //用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
			    new NameValuePair("password", "65a6548ad8a8f0961e10cdc6cbaa9ac6"),  //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
			   // new NameValuePair("password", MD5Util.MD5EncodeUtf8("123456")),
			    new NameValuePair("mobile", phone), 
			    new NameValuePair("content", content),
		};
		System.out.println("手机号》》"+phone);
		
		try {
			method.setRequestBody(data);
			client.executeMethod(method);
			String SubmitResult =method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			 if("2".equals(code)){
				System.out.println("短信提交成功");
				
				Code code1 = new Code();
				code1.setCodeName(mobile_code+"");//验证码
				code1.setAddTine(Hutool.parseDateToString());//添加时间
				codemapper.insert(code1);
				return Body.newInstance(mobile_code);
			}else {
				return Body.newInstance(501,msg);
						
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return Body.BODY_200;
	}
		
		
		
		
		/*String cerify = String.valueOf(new Random().nextInt(899999)+100000);
		try {
			CloudSMS.sendIdentifyingCode(phone, cerify, CloudSMS.identifyingTempleteCode);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/**
		 * 存入数据库
		 */
		/*Code code = new Code();
		code.setCodeName(cerify);//验证码
		code.setAddTine(Hutool.parseDateToString());//添加时间
		codemapper.insert(code);
		return Body.newInstance(cerify);*/
		
	@Override
	public Body register(String phone, String password, String code,String theHigherTheAgent) {
		/*EntityWrapper<Users> wrapps= new EntityWrapper<>();
		wrapps.eq("user_invitation_code", theHigherTheAgent);
		List<Users> selectList3 = usersmapper.selectList(wrapps);
		if(!selectList3.isEmpty()) {
			selectList3.get(0);
		}*/
		
		EntityWrapper<Users> wraaper = new EntityWrapper<>();
		wraaper.eq("user_phone", phone);
		List<Users> selectList = usersmapper.selectList(wraaper);//查询数据库是否有这个手机号
		if(selectList.isEmpty()) {
			EntityWrapper<Code> wtapper = new EntityWrapper<>();
			wtapper.eq("code_name", code);
			List<Code> selectList2 = codemapper.selectList(wtapper);
			if(selectList2.isEmpty()) {
				return Body.newInstance(507,"验证码错误");
			}
			Code code2 = selectList2.get(0);
			if(code2.getCodeName().equals(code) && StringUtils.isEmpty(theHigherTheAgent)) {
				Users users = new Users();
				users.setUserPhone(phone);
				users.setUserPassword(MD5Util.MD5EncodeUtf8(password));
				users.setUserNickname("请修改昵称");//昵称
				users.setUserRobot(0);//真人；
				users.setUserInvitationCode(ShareCodeUtil.toSerialCode() + new Random().nextInt(999));
				users.setAddTime(Hutool.parseDateToString());
				Integer insert = usersmapper.insert(users);
				if(insert>0) {
					return Body.newInstance("注册成功");
				}
			}
			if(code2.getCodeName().equals(code)){
				Users users = new Users();
				users.setUserPhone(phone);
				users.setUserPassword(MD5Util.MD5EncodeUtf8(password));
				users.setUserNickname("请修改昵称");//昵称
				users.setUserRobot(0);//真人；
				users.setUserInvitationCode(ShareCodeUtil.toSerialCode() + new Random().nextInt(999));
				users.setTheHigherTheAgent(theHigherTheAgent);
				users.setAddTime(Hutool.parseDateToString());
				Integer insert = usersmapper.insert(users);
				if(insert>0) {
					return Body.newInstance("注册成功");
				}
			}
		}
		return Body.newInstance(501,"此手机号已被注册");
	}
	@Override
	public Body resetpassword(String code, String phone, String password) {
		EntityWrapper<Code> wrapper = new EntityWrapper<>();
		wrapper.eq("code_name", code);
		List<Code> selectList = codemapper.selectList(wrapper);
		Code code2 = selectList.get(0);
		if(code2.getCodeName().equals(code)) {
			EntityWrapper<Users> wtapper = new EntityWrapper<>();
			wtapper.eq("user_phone", phone);
			List<Users> selectList2 = usersmapper.selectList(wtapper);
			Users users2 = selectList2.get(0);
			users2.setUserPassword(password);
			Integer updateById2 = usersmapper.updateById(users2);
			if(updateById2!=0) {
				return Body.newInstance("重置密码成功");
			}
			
		}
		return Body.newInstance(501,"验证码错误");
	}
	@Override
	public Body addRobot(Integer num, Double money,String nickname) {
		for (int i = 0; i < num; i++) {//循环添加机器人
			Users users = new Users();
			
			users.setUserNickname(GenerateName.getName());
			users.setUserRobot(1);//机器人
			users.setUserRechargeIntegral(money);
			users.setAddTime(Hutool.parseDateToString());//添加时间
			users.setUserImg("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=480194109,2955193021&fm=27&gp=0.jpg");
			usersmapper.insert(users);
		}
		return Body.newInstance("添加成功");
	}
	@Override
	public Body invitationcode(Integer userid) {
		Users users = usersmapper.selectById(userid);
		if(Integer.parseInt(users.getTheHigherTheAgent())==0) {
			return Body.newInstance(501,"没有输入邀请码");
		}
		return Body.newInstance("有邀请码");
	}
	@Override
	public Body getagency(String thehighertheagent) {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.eq("user_invitation_code", thehighertheagent);
		List<Users> selectList = usersmapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			return Body.newInstance(selectList);
		}
		return Body.newInstance(501,"刷新重试");
	}
	@Override
	public Body getxjagency(String userinvitationcode) {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.eq("the_higher_the_agent", userinvitationcode);
		List<Users> selectList = usersmapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			return Body.newInstance(selectList);
		}
		return Body.newInstance(501,"刷新重试");
		
	}
	@Override
	public Body shangfen(Integer userid, Integer shangfenid,Double money) {
		//上分用户
		Users userss = usersmapper.selectById(userid);
		if(userss.getUserRechargeIntegral()>=money) {
			Users users = usersmapper.selectById(shangfenid);
			
			Double errandmoneys = users.getUserRechargeIntegral();
			errandmoneys=errandmoneys == null ? errandmoneys=0.00:errandmoneys;
			
			Double ss=errandmoneys+money;
			users.setUserRechargeIntegral(ss);
			Integer updateById = usersmapper.updateById(users);
			//修改用户等级
			EntityWrapper<Agency> wrapper = new EntityWrapper<>();
			List<Agency> selectList = agencymapper.selectList(wrapper);
			for (int i = 0; i < selectList.size(); i++) {
				if(selectList.get(i).getTopUp()<users.getUserGrade()) {
					users.setUserGrade(selectList.get(i).getAgenyClass());
					usersmapper.updateById(users);
				}
			}
			//上分代理
			Double sss=userss.getUserRechargeIntegral()-money;
			userss.setUserRechargeIntegral(sss);
			Integer updateById2 = usersmapper.updateById(userss);
			
			Accountdetails accountdetails = new Accountdetails();//添加记录
			Accountdetails accountdetails2 = new Accountdetails();//添加记录
			if(updateById2 !=0 && updateById !=0) {
				//上级代理应得-100
				accountdetails.setMoney(money+"");
				accountdetails.setUseridId(userid);
				accountdetails.setType(2);//type;//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
				accountdetails.setSymbol(2);//加减符号    1加号，2减号
				accountdetails.setAddTime(Hutool.parseDateToString());
				accountdetailsmapper.insert(accountdetails);	
				//下级加100
				accountdetails2.setMoney(money+"");
				accountdetails2.setUseridId(shangfenid);
				accountdetails2.setType(1);
				accountdetails2.setSymbol(1);//0    +号
				accountdetails2.setAddTime(Hutool.parseDateToString());
				accountdetailsmapper.insert(accountdetails2);
				return Body.newInstance("上分成功");
			}
		}
		return Body.newInstance(501,"您的积分不足");
	}
	@Override
	public Body xiafen(Integer userid, Integer shangfenid, Double money) {
		Users xiafen = usersmapper.selectById(shangfenid);
		if(xiafen.getUserRechargeIntegral()>=money) {
			Double xfmoney=xiafen.getUserRechargeIntegral()-money;//下分金额
			
			xiafen.setUserRechargeIntegral(xfmoney);
			Integer xiafens = usersmapper.updateById(xiafen);
			
			Users shangfen = usersmapper.selectById(userid);
			Double sss = money-3;
			Double errandmoneys = shangfen.getUserRechargeIntegral();
			errandmoneys=errandmoneys == null ? errandmoneys=0.00:errandmoneys;
			Double sfmoney = errandmoneys+sss;//上分金额
			
			shangfen.setUserRechargeIntegral(sfmoney);
			Integer shangfens = usersmapper.updateById(shangfen);
			Accountdetails accountdetails = new Accountdetails();//添加记录
			Accountdetails accountdetails2 = new Accountdetails();//添加记录
			if(xiafens>0 && shangfens>0) {
				//上级代理应得97
				accountdetails.setMoney(sss+"");
				accountdetails.setUseridId(userid);
				accountdetails.setType(1);//上分  97
				accountdetails.setSymbol(1);//   -
				accountdetails.setAddTime(Hutool.parseDateToString());
				accountdetailsmapper.insert(accountdetails);
				//下级  扣除一百
				accountdetails2.setMoney(money+"");
				accountdetails2.setUseridId(shangfenid);
				accountdetails2.setType(2);//下分
				accountdetails2.setSymbol(2);   //2    -
				accountdetails2.setAddTime(Hutool.parseDateToString());
				accountdetailsmapper.insert(accountdetails2);
				
				return Body.newInstance("下分成功");
			}
		}
		return Body.newInstance(501,"下分积分不足");
	}
	@Override
	public Body detail(Integer userid) {
		List<Accountdetails> selectList = accountdetailsmapper.selectListsss(userid);
		
		if(!selectList.isEmpty()) {
			/*for (int i = 0; i < selectList.size(); i++) {
				if(selectList.get(i).getType()!=1 || selectList.get(i).getType()!=2) {
					selectList.remove(selectList.get(i));
				}
			}*/
			return Body.newInstance(selectList);
		}
		return Body.newInstance(501,"您还没有账户明细");
	}
	@Override
	public Body servicess() {
		EntityWrapper<Customerservice> wrapper = new EntityWrapper<>();
		List<Customerservice> selectList = customerservicemapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			return Body.newInstance(selectList);
		}
		return Body.newInstance(selectList);
	}
	@Override
	public Body addinvitationcode(Integer userid, String invitationcode) {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.eq("user_invitation_code", invitationcode);
		List<Users> list = usersmapper.selectList(wrapper);
		Users users = list.get(0);//邀请码用户
		if(users.getUserNum()<=0) {
			return Body.newInstance(501,"线下人数已经超出名额");
		}
		EntityWrapper<Users>  ew = new EntityWrapper<>();
		ew.eq("user_id", userid);
		ew.setSqlSelect("user_id userid,the_higher_the_agent  theHigherTheAgent");
		Users userss = usersmapper.selectList(ew).get(0);
		if(userss.getTheHigherTheAgent().equals("0")) {
			userss.setTheHigherTheAgent(invitationcode);
			Integer updateById = usersmapper.updateById(userss);
			Integer num = users.getUserNum()-1;
			users.setUserNum(num);
			Integer updateById2 = usersmapper.updateById(users);
			if(updateById !=null && updateById2 !=null) {
				return Body.newInstance("成为下级用户");
			}
		}
		return Body.newInstance(502,"已经有上级用户");
	}
	@Override
	public Body htlogin(String phone, String password) {
		EntityWrapper<Htlogin> wrapper = new EntityWrapper<>();
		wrapper.eq("ht_phone", phone);
		List<Htlogin> selectList = htloginmapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			if(selectList.get(0).getHtPassword().equals(MD5Util.MD5EncodeUtf8(password))) {
				return Body.newInstance(selectList);
			}
		}
		return Body.newInstance(501,"未查到该管理员，请去注册");
	}
	@Override
	public Body htregister(String phone, String password, String code) {
		EntityWrapper<Htlogin> wrapper = new EntityWrapper<>();
		wrapper.eq("ht_phone", phone);
		List<Htlogin> selectList = htloginmapper.selectList(wrapper);
		if(selectList.isEmpty()){
			Code codes = new Code();
			codes.setCodeName(code);
			Code selectOne = codemapper.selectOne(codes);
			if(selectOne!=null) {
				Htlogin htlogin = new Htlogin();
				htlogin.setHtPhone(phone);
				htlogin.setHtPassword(MD5Util.MD5EncodeUtf8(password));
				htlogin.setAddTime(Hutool.parseDateToString());
				Integer insert = htloginmapper.insert(htlogin);
				if(insert != null) {
					return Body.newInstance("注册成功");
				}
			}else {
				return Body.newInstance(502,"验证码错误");
			}
		}
		return Body.newInstance(501,"手机号已经被注册");
	}
	@Override
	public Body getuserfk(String usernickname,String phone) {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.like("user_Nickname", usernickname);
		wrapper.like("user_Phone", phone);
		wrapper.eq("user_robot", 0);
		List<Users> selectList = usersmapper.selectList(wrapper);
		ResultVo resultVo = AutoPage.work(selectList);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body upfengkong(Integer userid, Integer fengkong) {
		Users users = usersmapper.selectById(userid);
		if(users != null) {
			users.setChangci(fengkong);
			usersmapper.updateById(users);
			return Body.newInstance("修改成功");
		}
		return Body.newInstance(501,"修改失敗");
	}
	@Override
	public Body deljiqiren(Integer userid) {
		Users users = usersmapper.selectById(userid);
		Integer deleteById = usersmapper.deleteById(users.getUserId());
		if(deleteById!= null) {
			return Body.newInstance("删除成功");
		}
		return Body.newInstance(501,"删除失败"); 
	}
	@Override
	public Body getshangxia(String nicheng) {
		List<Accountdetails> ss = accountdetailsmapper.selectByIds(nicheng);
		for (int i = 0; i < ss.size(); i++) {
			String yyy = ss.get(i).getMoney();
			if(yyy.length()>6) {
				yyy = yyy.substring(0,6);
				
			}
			ss.get(i).setMoney(yyy);
			
		}
		ResultVo resultVo = AutoPage.work(ss);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body getroomtype(String fangjianname) {
		List<Room> list = roommapper.selectByIds(fangjianname);
		ResultVo resultVo = AutoPage.work(list);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body submitshangfen(Integer userid, Double money,String Shangjiyaoqingma,Integer num) {
		if(num==1) {
			Applys applys = new Applys();
			applys.setUserId(userid);//申请人
			applys.setMoney(money);//金额
			applys.setYaoqingma(Shangjiyaoqingma);//上级代理邀请码
			applys.setAddTime(Hutool.parseDateToString());//申请时间
			applys.setType(1);//上分
			Integer insert = applysmapper.insert(applys);
			if(insert != null) {
				return Body.newInstance("等待审核");
			}
		}else if(num==2) {
			Applys applys = new Applys();
			applys.setUserId(userid);//申请人
			applys.setMoney(money);//金额
			applys.setYaoqingma(Shangjiyaoqingma);//上级代理邀请码
			applys.setAddTime(Hutool.parseDateToString());//申请时间
			applys.setType(2);//下分
			Integer insert = applysmapper.insert(applys);
			if(insert != null) {
				return Body.newInstance("等待审核");
			}
		}
		return Body.newInstance(501,"审核失败");
	}
	@Override
	public Body getsubmitshangfen(String yaoqingma) {
		List<Applys> list = applysmapper.selectByIdy(yaoqingma);
		if(!list.isEmpty()) {
			return Body.newInstance(list);
		}
		return Body.newInstance(501,"没有申请");
	}
	@Override
	public Body getdifenroom(String roomid) {
		EntityWrapper<Room> wrapper = new EntityWrapper<>();
		wrapper.like("room_id", roomid);
		List<Room> selectList = roommapper.selectList(wrapper);
		ResultVo resultVo = AutoPage.work(selectList);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body updifen(Integer roomid, Integer roomintegral) {
		Room room = roommapper.selectById(roomid);
		if(room != null) {
			room.setResidue(roomintegral);
			Integer updateById = roommapper.updateById(room);
			if(updateById != null) {
				return Body.newInstance("修改成功");
			}
		}
		return Body.newInstance(501,"修改失败");
	}
	@Override
	public Body getuserid(Integer userid) {
		Users selectById = usersmapper.selectById(userid);
		if(selectById != null) {
			return Body.newInstance(selectById);
		}
		return Body.newInstance(501,"查询失败");
	}
	public Body updateuser(String niname,MultipartFile[] file,Integer userid) {
		Users users = usersmapper.selectById(userid);
		String files = null;
		if(file!=null && file.length!= 0) {
			try {
				files = UploadImgUtils.uploadFiles(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		users.setUserNickname(niname);
		users.setUserImg(files);
		Integer updateById = usersmapper.updateById(users);
		if(updateById != null) {
			return Body.newInstance(users);
		}
		return Body.newInstance(501,"修改失败");
	}
	@Override
	public Body getroomusers(Integer roomid) {
		
		List<Userroom> list =  userroommapper.selectfangzhu(roomid);//房间
		list.get(0).setUserrooms(userroommapper.selectchengyuan(roomid));
		
		/* WebSocket websocket=new WebSocket(); 
		 Map<String, Object> msg=new HashMap<>(); 
		 msg.put("list", list);*/
		 
		/*list.get(0).setRoomuser(usersmapper.getusers(roomid));
		WebSocket websocket=new WebSocket(); 
		Map<String, Object> msg=new HashMap<>(); 
		msg.put("list", list); 
		websocket.onMessage(JsonUtils.objectToJson(list)); */
		
		/*EntityWrapper<Userroom> wrapper = new EntityWrapper<>();
		wrapper.eq("room_id", roomid);
		List<Userroom> list = userroommapper.selectList(wrapper);
		List<Users> Listss = new ArrayList<>();
		
		if(!list.isEmpty()) {
			Room room = new Room();
			room.setUserId(roomid);
			Room selectOne2 = roommapper.selectOne(room);
			selectOne2.setUserroom(list);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).getUserId();
				EntityWrapper<Users> wtapper= new EntityWrapper<>();
				wtapper.eq("user_id", list.get(i).getUserId());
				List<Users> Lists = usersmapper.selectList(wtapper);
				Listss.addAll(Lists);
			}
			WebSocket websocket=new WebSocket(); 
			Map<String, Object> msg=new HashMap<>(); 
			msg.put("Listss", Listss); 
			websocket.onMessage(JsonUtils.objectToJson(msg)); 
			return Body.newInstance(Listss);
		}*/
		return Body.newInstance(list);
	}
	@Override
	public Body wxlogin(String niname, MultipartFile[] file, String openid) {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.eq("user_wx", openid);
		List<Users> selectList = usersmapper.selectList(wrapper);
		if(!selectList.isEmpty()) {
			return Body.newInstance(selectList);
		}else {
			String files = null;
			if(file!=null && file.length!= 0) {
				try {
					files = UploadImgUtils.uploadFiles(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Users users = new Users();
			users.setUserWx(openid);
			users.setUserNickname(niname);
			users.setUserImg(files);
			usersmapper.insert(users);
		}
		return Body.newInstance(501,"注册失败");
	}
	@Override
	public Body Forget_Password(String phone, String password, String code) {
		EntityWrapper<Code> wrapper = new EntityWrapper<>();
		wrapper.eq("code_name", code);
		List<Code> selectList = codemapper.selectList(wrapper);
		if(selectList.isEmpty()) {
			return Body.newInstance(501,"验证码错误");
		}
		Users users = new Users();
		users.setUserPhone(phone);
		Users selectOne = usersmapper.selectOne(users);
		if(selectOne!=null) {
			selectOne.setUserPassword(MD5Util.MD5EncodeUtf8(password));
			Integer updateById = usersmapper.updateById(selectOne);
			if(updateById!= null) {
				return Body.newInstance("修改密码成功");
			}
		}
		
		return Body.newInstance(502,"修改失败");
	}
	@Override
	public Body chongzhi(Integer userid, Double money) {
		Users selectById = usersmapper.selectById(userid);
		//添加用户积分
		Double jifen= selectById.getUserRechargeIntegral();
		jifen=jifen == null ? jifen=0.00:jifen;
		Double jifens = jifen+money;
		selectById.setUserRechargeIntegral(jifens);
		//添加用户金额
		Double  moneys= selectById.getUserRechargeMoney();
		moneys=moneys == null ? moneys=0.00:moneys;
		Double ss = moneys+money;
		selectById.setUserRechargeMoney(ss);
		usersmapper.updateById(selectById);
		
		EntityWrapper<Agency> wrapper = new EntityWrapper<>();
		List<Agency> selectList = agencymapper.selectList(wrapper);
		Integer sss = 0;
		for (int i = 0; i < selectList.size(); i++) {
			if(selectList.get(i).getTopUp()>selectById.getUserRechargeMoney()) {
				
				//修改等级
				sss =selectList.get(i).getAgencyId();
				System.out.println(selectList.get(i).getAgencyId()+"sssssssssssssssssssssssss");
				
			}
		}
		Users selectByIds = usersmapper.selectById(userid);
		selectByIds.setUserGrade(sss);
		usersmapper.updateById(selectByIds);
		
		Accountdetails accountdetails = new Accountdetails();
		accountdetails.setMoney(money+"");
		accountdetails.setType(8);//平台充值
		accountdetails.setAddTime(Hutool.parseDateToString());
		Integer insert = accountdetailsmapper.insert(accountdetails);
		
		
		
		if(insert!= null) {
			return Body.newInstance("充值成功");
		}
		
		return Body.newInstance(501,"充值失败");
	}
	@Override
	public Body xiafenmoney(Integer userid, Double money) {
		
		Users selectById = usersmapper.selectById(userid);
		if(selectById.getUserRechargeIntegral()>money) {
			Double xiafen= selectById.getUserRechargeIntegral()-money;
			selectById.setUserRechargeIntegral(xiafen);
			usersmapper.updateById(selectById);
			
			Accountdetails accountdetails = new Accountdetails();
			accountdetails.setUseridId(userid);
			accountdetails.setType(9);
			accountdetails.setMoney(money+"");
			accountdetails.setAddTime(Hutool.parseDateToString());
			Integer insert = accountdetailsmapper.insert(accountdetails);
			if(insert != null && selectById != null) {
				return Body.newInstance("下分成功");
			}
		}
		return Body.newInstance(505,"修改失败");
	}
	@Override
	public Body choushuijifen(Integer userid) {
		List<Accountdetails> list =  accountdetailsmapper.choushuijifen(userid);
		Double sss = 0.0;
		String valueOf = null;
		if(!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				double b = Double.valueOf(list.get(i).getMoney());
				sss+= b;
			}
		}
		valueOf =String.valueOf(sss);
		if(valueOf.length()>6) {
			valueOf = String.valueOf(valueOf).substring(0,5);
		}
		return Body.newInstance(valueOf);
	}
	@Override
	public Body xiaohao(Integer userid) {
		EntityWrapper<Accountdetails> wrapper= new EntityWrapper<>();
		wrapper.eq("userid_id", userid);
		wrapper.where("type in (3,4,5,6)");
		wrapper.orderBy("add_time");
		List<Accountdetails> list = accountdetailsmapper.selectList(wrapper);
		if(!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				String ss = list.get(i).getAddTime();
				String sss = ss.substring(11);
				String yyy = list.get(i).getMoney();
				if(list.get(i).getMoney().length()>7) {
					yyy = list.get(i).getMoney().substring(0,6);
				}
				list.get(i).setAddTime(sss);
				list.get(i).setMoney(yyy);
				if(list.get(i).getMoney().equals("0.0")) {
					list.remove(list.get(i));
					System.out.println("删除0.0");
				}
			}
			return Body.newInstance(list);
		}
		return Body.newInstance("没有对局消耗");
	}
	@Override
	public Body jiqiren() {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.eq("user_robot", 1);
		List<Users> selectList = usersmapper.selectList(wrapper);
		ResultVo resultVo = AutoPage.work(selectList);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body dengjis(Integer userid, Integer dengji) {
		Users user = usersmapper.selectById(userid);
		user.setUserGrade(dengji);
		Integer updateById = usersmapper.updateById(user);
		if(updateById!= null) {
			return Body.newInstance("会员等级重置成功");
		}
		return Body.newInstance(501,"会员等级重置失败");
	}
	@Override
	public Body shangxiafen(String nicheng) {
		List<Concurnaysay> list = concurnaysaymapper.jilu(nicheng);
		ResultVo resultVo = AutoPage.work(list);
		return Body.newInstance(resultVo);
	}
	@Override
	public Body adduser(String phone, String password, String code) {
		EntityWrapper<Users> wrapper = new EntityWrapper<>();
		wrapper.eq("user_phone", phone);
		List<Users> user = usersmapper.selectList(wrapper);
		if(!user.isEmpty()) {
			return Body.newInstance(501,"此手机号已经是会员");
		}
		if(StringUtils.isEmpty(code)) {
			Users users = new Users();
			users.setUserPhone(phone);
			users.setUserPassword(MD5Util.MD5EncodeUtf8(password));
			users.setUserInvitationCode(ShareCodeUtil.toSerialCode() + new Random().nextInt(999));
			users.setUserNickname("请修改昵称");//昵称
			users.setUserRobot(0);
			Integer insert = usersmapper.insert(users);
			if(insert>0) {
				return Body.newInstance("新增会员成功");
			}
		}else {
			Users users = new Users();
			users.setUserPhone(phone);
			users.setUserPassword(MD5Util.MD5EncodeUtf8(password));
			users.setTheHigherTheAgent(code);
			users.setUserInvitationCode(ShareCodeUtil.toSerialCode() + new Random().nextInt(999));
			users.setUserNickname("请修改昵称");//昵称
			users.setUserRobot(0);
			Integer insert = usersmapper.insert(users);
			if(insert>0) {
				return Body.newInstance("新增会员成功");
			}
		}
		return Body.newInstance(502,"新增会员失败");
		
	}
	@Override
	public Body pingtai() {
		EntityWrapper<Accountdetails> wrapper= new EntityWrapper<>();
		wrapper.where("type in (7,8,9)");
		wrapper.orderBy("add_time");
		List<Accountdetails> list = accountdetailsmapper.selectList(wrapper);
		if(!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				String yyy = list.get(i).getMoney();
				if(list.get(i).getMoney().length()>7) {
					yyy = list.get(i).getMoney().substring(0,6);
				}
				list.get(i).setMoney(yyy);
				if(list.get(i).getMoney().equals("0.0")) {
					list.remove(list.get(i));
					System.out.println("删除0.0");
				}
			}
			ResultVo resultVo = AutoPage.work(list);
			return Body.newInstance(resultVo);
		}
		return Body.newInstance("没有对局消耗");
	}
	public void defautTest(Integer userid,Double money) {
		 int agency = userid;
		 Double s = money;
		 usersmapper.selectById("");
		switch (agency) {
		case 1:
			System.out.println(s);
			System.out.println("拿所有人代理------------------------百分之四十五");
			break;
		case 2:
			System.out.println("拿2345级别代理------------------------百分之四十");
			break;
		case 3:
			System.out.println("拿345级别代------------------------理百分之三十五");
			break;
		case 4:
			System.out.println("拿45级别代理------------------------百分之三十");
			break;
		case 5:
			System.out.println("拿5级别代理------------------------百分之二十五");
			break;
		case 6:
			System.out.println("拿2345级别代理自己不是代理，------------------------平台拿百分之一的代理水费");
			break;
		default:
			break;
		}
	}
}
