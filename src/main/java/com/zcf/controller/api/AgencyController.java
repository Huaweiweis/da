package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcf.common.json.Body;
import com.zcf.service.AgencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-14
 */
@Controller
@RequestMapping("/agency/")
@ResponseBody
@CrossOrigin
public class AgencyController {
	@Autowired
	private AgencyService agencyservice;
	@PostMapping("addagency")
	public Body addagency(Double choushuiBili, Double topUp, Integer personNum,Integer agencyClass) {
		if(choushuiBili == null) {
			return Body.newInstance(502,"抽水比例为空");
		}
		if(topUp == null) {
			return Body.newInstance(503,"需要充值金额为空");
		}
		if(personNum == null) {
			return Body.newInstance(504,"人数为空");
		}
		if(agencyClass == null) {
			return Body.newInstance(504,"代理登记为空");
		}
		return agencyservice.addagency(choushuiBili, topUp, personNum,agencyClass);
	}
	@PostMapping("upagency")
	public Body upagency(Integer agencyid, Double choushuibili, Double topup, Integer personnum,Integer agencyClass) {
		if(agencyid==null) {
			return Body.newInstance(505,"编号为空");
		}
		return agencyservice.upagency(agencyid, choushuibili, topup, personnum,agencyClass);
	}
	@PostMapping("getagency")
	public Body getagency() {
		return agencyservice.getagency();
	}
	@PostMapping("delagency")
	public Body delagency(Integer agencyid) {
		if(agencyid==null) {
			return Body.newInstance(505,"分销编号为空");
		}
		return agencyservice.delagency(agencyid);
	}
}

