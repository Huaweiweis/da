package com.zcf.vo.in;

/**
 * 分页参数公共 vo Created by shenguohao
 */

public class PageVo {
	private Integer page = 1;
	private Integer pageSize = 10;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}