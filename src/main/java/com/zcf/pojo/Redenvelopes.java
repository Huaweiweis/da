package com.zcf.pojo;

import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;

/**
 * <p>
 * 红包
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-09
 */
@Data
public class Redenvelopes{

    /**
     * 红包id
     */
    @TableId(value = "red_envelopes_id", type = IdType.AUTO)
    private Integer redEnvelopesId;
    /**
     * 房间id
     */
    private Integer roomId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 红包金额
     */
    private Double valueOfPack;
    private Integer redEnvelopesNum;
    private String addTime;
    /**
     * 红包状态
     */
    private Integer redEnvelopesState;

}
