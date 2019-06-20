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
 * @since 2019-06-01
 */
public class Difen extends Model<Difen> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer zuidi;
    private Integer zuida;
    private String addtime;
    private String uptime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZuidi() {
        return zuidi;
    }

    public void setZuidi(Integer zuidi) {
        this.zuidi = zuidi;
    }

    public Integer getZuida() {
        return zuida;
    }

    public void setZuida(Integer zuida) {
        this.zuida = zuida;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Difen{" +
        "id=" + id +
        ", zuidi=" + zuidi +
        ", zuida=" + zuida +
        ", addtime=" + addtime +
        ", uptime=" + uptime +
        "}";
    }
}
