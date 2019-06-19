package com.zcf.service;

import com.zcf.common.json.Body;
import com.zcf.pojo.Users;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-28
 */
public interface UsersService extends IService<Users> {

	/**
	 * 用户登录
	 * @param userphone
	 * @param userpassword
	 * @return
	 */
	Body login(String userphone,String userpassword);
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	Body codes(String phone);
	/**
	 * 用户注册
	 * @param phone
	 * @param password
	 * @param code
	 * @return
	 */
	Body register(String phone,String password,String code,String theHigherTheAgent);
	/**
	 * 重置密码
	 * @param code
	 * @param phone
	 * @param password
	 * @return
	 */
	Body resetpassword(String code,String phone,String password);
	/**
	 * 添加多条机器人（后台）
	 * @param num
	 * @param money
	 * @return
	 */
	Body addRobot(Integer num,Double money,String nickname);
	/**
	 * 判断用户是否有人邀请
	 * @param userid
	 * @return
	 */
	Body invitationcode(Integer userid);
	/**
	 * 查看商上级代理
	 * @param thehighertheagent上级代理邀请码
	 * @return
	 */
	Body getagency(String thehighertheagent);
	/**
	 * 用户查看自己下级代理
	 * @param userinvitationcode,自己邀请码
	 * @return
	 */
	Body getxjagency(String userinvitationcode);
	/**
	 * 上级代理给下级上分
	 * @param userid
	 * @param shangfenid
	 * @param money
	 * @return
	 */
	Body shangfen(Integer userid,Integer shangfenid,Double money);
	/**
	 * 上级代理给下级下分
	 * @param userid
	 * @param shangfenid
	 * @param money
	 * @return
	 */
	Body xiafen(Integer userid,Integer shangfenid,Double money);
	/**
	 * 账户明细
	 * @param userid
	 * @return
	 */
	Body detail(Integer userid);
	/**
	 * 客服电话
	 * @return
	 */
	Body servicess();
	/**
	 * 用户填写邀请码
	 * @param userid
	 * @param invitationcode
	 * @return
	 */
	Body addinvitationcode(Integer userid,String invitationcode);
	/**
	 * 后台管理登录
	 * @param phone
	 * @param password
	 * @return
	 */
	Body htlogin(String phone,String password);
	/**
	 * 后台管理员注册
	 * @param phone
	 * @param password
	 * @param code
	 * @return
	 */
	Body htregister(String phone,String password,String code);
	/**
	 * 会员管理
	 * @param usernickname
	 * @param phone
	 * @return
	 */
	Body getuserfk(String usernickname,String phone);
	/**
	 * 后台管理：管理员修改用户风控
	 * @param userid
	 * @param fengkong
	 * @return
	 */
	Body upfengkong(Integer userid,Integer fengkong);
	/**
	 * 用户删除机器人用户
	 * @param userid
	 * @return
	 */
	Body deljiqiren(Integer userid);
	/**
	 * 后台管理上分下分管理
	 * @return
	 */
	Body getshangxia(String nicheng);
	/**
	 * 后台管理，房间管理；
	 * @return
	 */
	Body getroomtype(String fangjianname);
	/**
	 * 用户提交上分信息
	 * @param userid
	 * @param money
	 * @param shangjiyaoqinggma
	 * @return
	 */
	Body submitshangfen(Integer userid,Double money,String shangjiyaoqinggma,Integer num);
	/**
	 * 用户查看自己申请信息
	 * @param userid
	 * @return
	 */
	Body getsubmitshangfen(String yaoqingma);
	/**
	 * 后台管理：房间底分管理
	 * @return
	 */
	Body getdifenroom(String roomid);
	/**
	 * 后台管理修改房间底分
	 * @param roomid
	 * @param roomintegral
	 * @return
	 */
	Body updifen(Integer roomid, Integer roomintegral);
	
	Body getuserid(Integer userid);
	/**
	 * 修改用户头像
	 * @param niname
	 * @param file
	 * @param userid
	 * @return
	 */
	Body updateuser(String niname,MultipartFile file[],Integer userid);
	
	Body getroomusers(Integer roomid);
	
	Body wxlogin(String niname, MultipartFile file[],String openid);
	//修改密码
	Body Forget_Password(String phone,String password,String code);
	//后台管理上分
	Body chongzhi(Integer userid,Double money);
	//下分//后台管理
	Body xiafenmoney(Integer userid,Double money);
	//抽水总额
	Body choushuijifen(Integer userid);
	//对局消耗
	Body xiaohao(Integer userid);
	
	Body jiqiren();
	/**
	 * 后台管理，重置会员等级
	 * @param userid
	 * @param dengji
	 * @return
	 */
	Body dengjis(Integer userid,Integer dengji);
	
	Body shangxiafen(String nicheng);
	
	Body adduser(String phone,String password,String code);
	
	Body pingtai();
}
