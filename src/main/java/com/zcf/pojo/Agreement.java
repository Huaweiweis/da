package com.zcf.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 协议
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-16
 */
public class Agreement extends Model<Agreement> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "agreement_id", type = IdType.AUTO)
    private Integer agreementId;
    /**
     * 协议内容
     */
    private String agreementName;
    /**
     * 添加时间
     */
    private String agreementTime;
    /**
     * 修改时间
     */
    private String agreementUpdate;


    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getAgreementTime() {
        return agreementTime;
    }

    public void setAgreementTime(String agreementTime) {
        this.agreementTime = agreementTime;
    }

    public String getAgreementUpdate() {
        return agreementUpdate;
    }

    public void setAgreementUpdate(String agreementUpdate) {
        this.agreementUpdate = agreementUpdate;
    }

    @Override
    protected Serializable pkVal() {
        return this.agreementId;
    }

    @Override
    public String toString() {
        return "Agreement{" +
        "agreementId=" + agreementId +
        ", agreementName=" + agreementName +
        ", agreementTime=" + agreementTime +
        ", agreementUpdate=" + agreementUpdate +
        "}";
    }
}
