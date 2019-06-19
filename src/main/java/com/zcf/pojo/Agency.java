package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-14
 */
@Data
public class Agency{
    /**
     * 几级代理
     */
    @TableId(value = "agency_id", type = IdType.AUTO)
    private Integer agencyId;
    /**
     * 抽水比例
     */
    private Double choushuiBili;
    /**
     * 充值金额
     */
    private Double topUp;
    /**
     * 线下人数
     */
    private Integer personNum;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 修改时间
     */
    private String updTime;
    /**
     * 代理等级
     */
    @TableField(value="ageny_class")
    private Integer agenyClass;

}
