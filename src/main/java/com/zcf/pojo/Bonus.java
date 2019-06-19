package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 发红包，红包金额存放表。
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
@Data
public class Bonus {


    /**
     * 存放id
     */
    @TableId(value = "bonus_id", type = IdType.AUTO)
    private Integer bonusId;
    /**
     * 随机选取金额
     */
    @TableField("Bonus_mony")
    private Double bonusMony;
    /**
     * 房间id
     */
    private Integer roomId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 红包状态，未领取0，已经领取1
     */
    private Integer bonusState;
    /**
     * 红包id
     */
    private int hongbaoId;
    /**
     * 最大值   1
     */
    private Integer zuidaMoney;
    private Integer dianshu;
    @TableField(exist=false)
    private String nicheng;
    @TableField(exist=false)
    private String img;
    @TableField(exist=false)
    private Integer geshu;


 
}
