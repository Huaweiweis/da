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
 * @since 2019-05-06
 */
@Data
public class Accountdetails{
    /**
     * 账户明细id
     */
    @TableId(value = "account_details_id", type = IdType.AUTO)
    private Integer accountDetailsId;
    /**
     * 用户id
     */
    private Integer useridId;
    /**
     * 类型
     */
    private Integer type;//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红
    private String money;//金额
    private String addTime;//添加时间
    private Integer symbol;//加减符号    1加号，2减号
    
    private Integer beishangfenid;//被上分人id
    @TableField(exist=false)
    private String time;
    @TableField(exist=false)
    private String shangfenren;
    @TableField(exist=false)
    private String xiafenren;
    @TableField(exist=false)
    private Double moneys;
    @TableField(exist=false)
    private Integer types;
    @TableField(exist=false)
    private Integer ids;
    @TableField(exist=false)
    private String nicheng;
    
    
   
}
