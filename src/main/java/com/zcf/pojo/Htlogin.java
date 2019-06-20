package com.zcf.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-16
 */
public class Htlogin extends Model<Htlogin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ht_id", type = IdType.AUTO)
    private Integer htId;
    private String htPhone;
    private String htPassword;
    private String addTime;


    public Integer getHtId() {
        return htId;
    }

    public void setHtId(Integer htId) {
        this.htId = htId;
    }

    public String getHtPhone() {
        return htPhone;
    }

    public void setHtPhone(String htPhone) {
        this.htPhone = htPhone;
    }

    public String getHtPassword() {
        return htPassword;
    }

    public void setHtPassword(String htPassword) {
        this.htPassword = htPassword;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.htId;
    }

    @Override
    public String toString() {
        return "Htlogin{" +
        "htId=" + htId +
        ", htPhone=" + htPhone +
        ", htPassword=" + htPassword +
        ", addTime=" + addTime +
        "}";
    }
}
