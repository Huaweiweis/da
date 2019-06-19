package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 申请表
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-17
 */
@Data
public class Applys {

    @TableId(value = "applys_id", type = IdType.AUTO)
    private Integer applysId;
    /**
     * 提出申请人
     */
    private Integer userId;
    /**
     * 需要上分金额
     */
    private Double money;
    /**
     * 申请时间
     */
    private String addTime;
    /**
     * 修改时间
     */
    private String updTime;
    /**
     * 1上分，2下分
     */
    private Integer type;

    /**
     * 上级代理邀请码
     */
    private String yaoqingma;
    @TableField(exist=false)
    private String namess;
    @TableField(exist=false)
    private String phones;
    

}
