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
 * 领取红包记录表
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-09
 */
@Data
public class Getredenvelopes{


    /**
     * 领取红包id
     */
    @TableId(value = "get_redenvelopes_id", type = IdType.AUTO)
    private Integer getRedenvelopesId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 红包id
     */
    private Integer redenvelopesId;
    /**
     * 领取红包时间
     */
    private String addTime;
    /**
     * 领取红包金额
     */
    private Double getRedenvelopesMoney;
    /**
     * 输赢
     */
    
    private Integer shuying;
    
    @TableField(exist=false)
    private Integer usersid;//用户id
    @TableField(exist=false)
    private String portrait;//用户头像
    @TableField(exist=false)
    private String nickname;//用户昵称
    @TableField(exist=false)
    private String money;//用户赢取金钱
    @TableField(exist=false)
    private String addstime;//添加时间
    @TableField(exist=false)
    private String sum;
    
    @TableField(exist=false)
    private String yonghuming;//用户名
    @TableField(exist=false)
    private Integer fangjianhao;//房间编号
    @TableField(exist=false)
    private Integer leixing;//红包类型
    @TableField(exist=false)
    private Double moneysss;//红包金额
    @TableField(exist=false)
    private Integer shuxings;//输赢状态//1赢   2输
    @TableField(exist=false)
    private Double shuxingjine;//输赢金额
    @TableField(exist=false)
    private String fangzhuming;//房主姓名
    @TableField(exist=false)
    private Double users;//用户详细信息
    
}
