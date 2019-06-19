package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-28
 */
@Data
public class Users  {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户邀请码
     */
    private String userInvitationCode;
    /**
     * 用户会员等级
     */
    private Integer userGrade;
    /**
     * 用户充值金额
     */
    private Double userRechargeMoney;
    /**
     * 用户账户积分
     */
    private Double userRechargeIntegral;
    private String addTime;
    private String upTime;
    private String userPhone;//用户手机号
    private String userNickname;//用户昵称
    private Integer userRobot;//是否机器人
    private String userImg;//用户头像
    @TableField(value="the_higher_the_agent")
    private String theHigherTheAgent;//上級代理邀请码
    private Integer userNum;//可邀请人数
    
    private Integer changci;//胜利场次
    @TableField(exist=false)
    private List<Room> roomuser;
    
    private String userWx;


  
}
