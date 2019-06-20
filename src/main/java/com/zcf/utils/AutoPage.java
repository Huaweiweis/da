package com.zcf.utils;

import com.zcf.common.result.PageResult;
import com.zcf.common.result.ResultVo;

import java.util.List;

public class AutoPage<T> {
	public static <T>ResultVo work(List<T> list) {
		PageResult result = PageResult.result(list);//paramList)
		// 返回的vo
		ResultVo resultVo = new ResultVo();
		// 返回总条数
		resultVo.setTotal(result.getTotal());
		// 返回结果集合
		resultVo.setList(result.getRows());
		
		return resultVo;
	}
}
