package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcf.common.json.Body;
import com.zcf.service.ConcurnaysayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-06
 */
@Controller
@RequestMapping("/concurnaysay/")
@ResponseBody
@CrossOrigin
public class ConcurnaysayController {

	@Autowired
	private ConcurnaysayService concurnaysayservice;
	@PostMapping("concurnaysays")
	public Body concurnaysays(Integer zhuangtai, Integer caozuorenid, Integer shenqingid) {
		if(zhuangtai==null) {
			return Body.newInstance(502,"同意拒绝状态为空");
		}
		if(caozuorenid==null) {
			return Body.newInstance(503,"操作人id为空");
		}
		if(shenqingid==null) {
			return Body.newInstance(504,"申请id为空");
		}
		return concurnaysayservice.concurnaysays(zhuangtai, caozuorenid, shenqingid);
	}
}

