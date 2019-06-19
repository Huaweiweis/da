package com.zcf.common.result;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class PageResult {
	/**
	 * 总数
	 */
	private long total;

	/**
	 * 数据列表
	 */
	private List rows;

	public PageResult(List rows) {
		if (rows == null) {
			this.total = 0;
		} else {
			this.total = new PageInfo(rows).getTotal();
		}
		this.rows = rows;
	}

	private PageResult(long total, List rows) {
		this.total = total;
		this.rows = rows;
	}

	public static PageResult result(List rows) {
		return new PageResult(rows);
	}

	public static PageResult result(long total, List rows) {
		return new PageResult(total, rows);
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
