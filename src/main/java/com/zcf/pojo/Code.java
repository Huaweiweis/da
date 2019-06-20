package com.zcf.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 验证码
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-04-28
 */
public class Code extends Model<Code> {

    private static final long serialVersionUID = 1L;

    /**
     * 验证码id
     */
    @TableId(value = "code_id", type = IdType.AUTO)
    private Integer codeId;
    /**
     * 验证码
     */
    private String codeName;
    /**
     * 添加时间
     */
    private String addTine;
    private String upTime;


    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    

    public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getAddTine() {
        return addTine;
    }

    public void setAddTine(String addTine) {
        this.addTine = addTine;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.codeId;
    }

    @Override
    public String toString() {
        return "Code{" +
        "codeId=" + codeId +
        ", codeName=" + codeName +
        ", addTine=" + addTine +
        ", upTime=" + upTime +
        "}";
    }
}
