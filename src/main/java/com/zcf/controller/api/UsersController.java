package com.zcf.controller.api;


import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.zcf.common.json.Body;
import com.zcf.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * user controller
 */
@CrossOrigin
@ResponseBody
@RestController("/users")
public class UsersController {

    @Autowired
    private UsersService usersservice;

    /**
     * 用户登录
     *
     * @param userphone
     * @param userpassword
     * @return
     */
    @PostMapping("login")
    public Body login(String userphone, String userpassword) {
        if (StringUtils.isEmpty(userphone)) {
            return Body.newInstance(501, "手机号为空");
        }
        if (StringUtils.isEmpty(userpassword)) {
            return Body.newInstance(502, "密码为空");
        }
        return usersservice.login(userphone, userpassword);
    }

    /**
     * 手机发验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("codes")
    public Body codes(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return Body.newInstance(501, "手机号为空");
        }
        return usersservice.codes(phone);
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @PostMapping("register")
    public Body register(String phone, String password, String code, String theHigherTheAgent) {
        if (StringUtils.isEmpty(phone)) {
            return Body.newInstance(501, "手机号为空");
        }
        if (StringUtils.isEmpty(password)) {
            return Body.newInstance(502, "手机号密码");
        }
        if (StringUtils.isEmpty(code)) {
            return Body.newInstance(503, "验证码");
        }
        return usersservice.register(phone, password, code, theHigherTheAgent);
    }

    /**
     * 重置密码
     *
     * @param code
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("resetpassword")
    public Body resetpassword(String code, String phone, String password) {
        if (StringUtils.isEmpty(phone)) {
            return Body.newInstance(501, "手机号为空");
        }
        if (StringUtils.isEmpty(password)) {
            return Body.newInstance(502, "手机号密码");
        }
        if (StringUtils.isEmpty(code)) {
            return Body.newInstance(503, "验证码");
        }
        return usersservice.resetpassword(code, phone, password);
    }

    /**
     * 添加机器人
     *
     * @param num      机器人数量
     * @param money
     * @param nickname
     * @return
     */
    @PostMapping("addRobot")
    public Body addRobot(Integer num, Double money, String nickname) {
        if (num == null) {
            return Body.newInstance(501, "数量为空");
        }
        if (money == null) {
            return Body.newInstance(502, "请出输入积分");
        }

        return usersservice.addRobot(num, money, nickname);
    }

    @PostMapping("invitationcode")
    public Body invitationcode(Integer userid) {
        if (userid == null) {
            return Body.newInstance("用户编号为空");
        }
        return usersservice.invitationcode(userid);
    }

    @PostMapping("getagency")
    public Body getagency(String thehighertheagent) {
        if (StringUtils.isEmpty(thehighertheagent)) {
            return Body.newInstance(502, "上級代理邀请码为空");
        }
        return usersservice.getagency(thehighertheagent);
    }

    @PostMapping("getxjagency")
    public Body getxjagency(String userinvitationcode) {
        if (StringUtils.isEmpty(userinvitationcode)) {
            return Body.newInstance(502, "邀请码为空");
        }
        return usersservice.getxjagency(userinvitationcode);
    }

    @PostMapping("shangfen")
    public Body shangfen(Integer userid, Integer shangfenid, Double money) {
        if (userid == null) {
            return Body.newInstance(502, "上分者id为空");
        }
        if (shangfenid == null) {
            return Body.newInstance(503, "被上分者id为空");
        }
        if (money == null) {
            return Body.newInstance(504, "上分积分为空");
        }

        return usersservice.shangfen(userid, shangfenid, money);
    }

    @PostMapping("xiafen")
    public Body xiafen(Integer userid, Integer shangfenid, Double money) {
        if (userid == null) {
            return Body.newInstance(502, "上分者id为空");
        }
        if (shangfenid == null) {
            return Body.newInstance(503, "被上分者id为空");
        }
        if (money == null) {
            return Body.newInstance(504, "上分积分为空");
        }
        return usersservice.xiafen(userid, shangfenid, money);
    }

    @PostMapping("detail")
    public Body detail(Integer userid) {
        if (userid == null) {
            return Body.newInstance(502, "为捕捉用户编号");
        }
        return usersservice.detail(userid);
    }

    @PostMapping("servicess")
    public Body servicess() {
        return usersservice.servicess();
    }

    @PostMapping("addinvitationcode")
    public Body addinvitationcode(Integer userid, String invitationcode) {
        if (userid == null) {
            return Body.newInstance(503, "用户编号为空");
        }
        if (StringUtils.isEmpty(invitationcode)) {
            return Body.newInstance(504, "邀请码为空");
        }
        return usersservice.addinvitationcode(userid, invitationcode);
    }

    @PostMapping("htlogin")
    public Body htlogin(String phone, String password) {

        return usersservice.htlogin(phone, password);
    }

    @PostMapping("htregister")
    public Body htregister(String phone, String password, String code) {
        return usersservice.htregister(phone, password, code);
    }

    @PostMapping("getuserfk")
    public Body getuserfk(String usernickname, String phone) {
        return usersservice.getuserfk(usernickname, phone);
    }

    @PostMapping("upfengkong")
    public Body upfengkong(Integer userid, Integer fengkong) {
        return usersservice.upfengkong(userid, fengkong);
    }

    @PostMapping("deljiqiren")
    public Body deljiqiren(Integer userid) {
        return usersservice.deljiqiren(userid);
    }

    @PostMapping("getshangxia")
    public Body getshangxia(String nicheng) {
        return usersservice.getshangxia(nicheng);
    }

    @PostMapping("getroomtype")
    public Body getroomtype(String fangjianname) {
        return usersservice.getroomtype(fangjianname);
    }

    @PostMapping("submitshangfen")
    public Body submitshangfen(Integer userid, Double money, String Shangjiyaoqingma, Integer num) {
        if (userid == null) {
            return Body.newInstance(502, "用户编号为空");
        }
        if (money == null) {
            return Body.newInstance(503, "上分金额为空");
        }
        if (StringUtils.isEmpty(Shangjiyaoqingma)) {
            return Body.newInstance(504, "你没有上级代理");
        }
        return usersservice.submitshangfen(userid, money, Shangjiyaoqingma, num);
    }

    @PostMapping("getsubmitshangfen")
    public Body getsubmitshangfen(String yaoqingma) {
        if (StringUtils.isEmpty(yaoqingma)) {
            return Body.newInstance(502, "邀请码为空");
        }
        return usersservice.getsubmitshangfen(yaoqingma);
    }

    @PostMapping("getdifenroom")
    public Body getdifenroom(String roomid) {
        return usersservice.getdifenroom(roomid);
    }

    @PostMapping("updifen")
    public Body updifen(Integer roomid, Integer roomintegral) {
        if (roomintegral != null && roomintegral < 1.00) {
            return Body.newInstance(502, "房间底分不可小于一");
        }
        if (roomintegral != null && roomintegral > 1000.00) {
            return Body.newInstance(503, "房间底分不可大于1000");
        }
        return usersservice.updifen(roomid, roomintegral);
    }

    @PostMapping("getuserid")
    public Body getuserid(Integer userid) {
        if (userid == null) {
            return Body.newInstance(502, "用户编号为空");
        }
        return usersservice.getuserid(userid);
    }

    @PostMapping("updateuser")
    public Body updateuser(String niname, MultipartFile file[], Integer userid) {
        if (userid == null) {
            return Body.newInstance(502, "用户编号为空");
        }
        return usersservice.updateuser(niname, file, userid);
    }

    @PostMapping("getroomusers")
    public Body getroomusers(Integer roomid) {
        if (roomid == null) {
            return Body.newInstance(501, "房间id为空");
        }
        return usersservice.getroomusers(roomid);
    }

    /**
     * 微信登录
     *
     * @param niname
     * @param file
     * @param openid
     * @return
     */
    public Body wxlogin(String niname, MultipartFile[] file, String openid) {
        return usersservice.wxlogin(niname, file, openid);
    }

    @PostMapping("Forget_Password")
    public Body Forget_Password(String phone, String password, String code) {
        if (StringUtils.isEmpty(phone)) {
            return Body.newInstance(503, "手机号为空");
        }
        if (StringUtils.isEmpty(password)) {
            return Body.newInstance(504, "密码为空");
        }
        if (StringUtils.isEmpty(code)) {
            return Body.newInstance(505, "验证码为空");
        }
        return usersservice.Forget_Password(phone, password, code);
    }

    /*@PostMapping("zhenshiuser")
    public Body zhenshiuser(String phone) {
        return usersservice.zhenshiuser(phone);
    }*/
    @PostMapping("chongzhi")
    public Body chongzhi(Integer userid, Double money) {
        return usersservice.chongzhi(userid, money);
    }

    @PostMapping("xiafenmoney")
    public Body xiafenmoney(Integer userid, Double money) {
        return usersservice.xiafenmoney(userid, money);
    }

    @PostMapping("choushuijifen")
    public Body choushuijifen(Integer userid) {
        return usersservice.choushuijifen(userid);
    }

    @PostMapping("xiaohao")
    public Body xiaohao(Integer userid) {
        if (userid == null) {
            return Body.newInstance(501, "用户id为空");
        }
        return usersservice.xiaohao(userid);
    }

    @PostMapping("jiqiren")
    public Body jiqiren() {
        return usersservice.jiqiren();
    }

    @PostMapping("dengjis")
    public Body dengjis(Integer userid, Integer dengji) {
        return usersservice.dengjis(userid, dengji);
    }

    @PostMapping("shangxiafen")
    public Body shangxiafen(String nicheng) {
        return usersservice.shangxiafen(nicheng);
    }

    @PostMapping("adduser")
    public Body adduser(String phone, String password, String code) {
        return usersservice.adduser(phone, password, code);
    }

    @PostMapping("pingtai")
    public Body pingtai() {
        return usersservice.pingtai();
    }

}

