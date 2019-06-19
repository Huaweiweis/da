package com.zcf.common.result;

import java.io.Serializable;
import java.util.List;

/**
 * 封装结果集合
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class ResultVo implements Serializable {

	private static final long serialVersionUID = 7472072673863388869L;
	// 总条数
	private Long total;
	// 集合数据
	private List list;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}