package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 房间
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-30
 */
@Data
public class Room{


    /**
     * 房间id
     */
    @TableId(value = "room_id", type = IdType.AUTO)
    private Integer roomId;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 房间密码
     */
    private String roomPassword;
    /**
     * 房间倍数
     */
    private Integer roomMulriple;
    /**
     * 创建房间时间
     */
    private String addTime;
    /**
     * 房间人数
     */
    private Integer roomNum;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 是否已经结束房间
     */
    private Integer whetherToEnd;
    /**
     * 房间是否有密码
     */
    private Integer whetherPassword;
    /**
     * 房间积分
     */
    private Double roomIntegral;
    /**
     * 发红包剩余场次
     */
    private Integer residue;
    @TableField(exist=false)
    private String namess;
    @TableField(exist=false)
    private List<Userroom> userroom;
    @TableField(exist=false)
    private String touxiang;
    @TableField(exist=false)
    private Double money;
    @TableField(exist=false)
    private String nicheng;
    
    private Integer youxizhong;

}
