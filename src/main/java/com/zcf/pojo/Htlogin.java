package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-16
 */
@Data
public class Htlogin {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ht_id", type = IdType.AUTO)
    private Integer htId;
    private String htPhone;
    private String htPassword;
    private String addTime;
}
