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
 * @since 2019-06-06
 */
@Data
public class Concurnaysay {

    /**
     * id
     */
    @TableId(value = "concurnaysay_id", type = IdType.AUTO)
    private Integer concurnaysayId;
    /**
     * 用户id
     */
    private Integer userid;
    /**
     * 操作类型，上分，下分
     */
    private Integer leixing;
    /**
     * 状态（同意、拒绝）
     */
    private Integer zhuangtai;
    /**
     * 上下分时间
     */
    private String shangxiatime;
    /**
     * 操作人（具体是谁同意或拒绝）
     */
    private Integer caozuoren;
    /**
     * 操作时间（操作人操作时间）
     */
    private String caozuotime;
    
    private Double money;
    @TableField(exist=false)
    private String yonghu;
    @TableField(exist=false)
    private String caozuorenxingm;

}
