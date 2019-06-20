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
 * @since 2019-05-06
 */
public class Customerservice extends Model<Customerservice> {

    private static final long serialVersionUID = 1L;

    /**
     * 客服id
     */
    @TableId(value = "customerservice_id", type = IdType.AUTO)
    private Integer customerserviceId;
    /**
     * 客服电话
     */
    private String customerservicePhone;
    /**
     * 客服名称
     */
    private String customerserviceName;
    /**
     * 添加时间
     */
    private String addTime;


    public Integer getCustomerserviceId() {
        return customerserviceId;
    }

    public void setCustomerserviceId(Integer customerserviceId) {
        this.customerserviceId = customerserviceId;
    }

    public String getCustomerservicePhone() {
        return customerservicePhone;
    }

    public void setCustomerservicePhone(String customerservicePhone) {
        this.customerservicePhone = customerservicePhone;
    }

    public String getCustomerserviceName() {
        return customerserviceName;
    }

    public void setCustomerserviceName(String customerserviceName) {
        this.customerserviceName = customerserviceName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.customerserviceId;
    }

    @Override
    public String toString() {
        return "Customerservice{" +
        "customerserviceId=" + customerserviceId +
        ", customerservicePhone=" + customerservicePhone +
        ", customerserviceName=" + customerserviceName +
        ", addTime=" + addTime +
        "}";
    }
}
