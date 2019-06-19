package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-05
 */
@Data
public class Userroom{


    @TableId(value = "user_room_id", type = IdType.AUTO)
    private Integer userRoomId;
    private Integer userId;
    private Integer roomId;
    private String addTime;
    @TableField(exist=false)
    private List<Users> roomuser;
    @TableField(exist=false)
    private String shulaing;
    @TableField(exist=false)
    private String fangjianming;
    @TableField(exist=false)
    private Integer fangjianbianhao;
    @TableField(exist=false)
    private String nicheng;
    @TableField(exist=false)
    private Integer yonghuid;
    @TableField(exist=false)
    private String touxiang;
    
    @TableField(exist=false)
    private List<Users> userrooms;
    @TableField(exist=false)
    private Room room;
    @TableField(exist=false)
    private Integer jifen;

}
