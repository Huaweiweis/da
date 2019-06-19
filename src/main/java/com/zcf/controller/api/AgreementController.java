package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcf.common.json.Body;
import com.zcf.service.AgreementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 协议 前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-16
 */
@Controller
@RequestMapping("/agreement/")
@CrossOrigin
@ResponseBody
public class AgreementController {

	@Autowired
	private AgreementService agreementservice;
	@PostMapping("addagreement")
	public Body addagreement(String agreementname) {
		return agreementservice.addagreement(agreementname);
	}
	@PostMapping("updagreement")
	public Body updagreement(String agreementname,Integer agreementid) {
		return agreementservice.updagreement(agreementname, agreementid);
	}
	@PostMapping("getagreement")
	public Body getagreement() {
		return agreementservice.getagreement();
	}
	@PostMapping("delagreement")
	public Body delagreement(Integer agreementid) {
		return agreementservice.delagreement(agreementid);
	}
	@PostMapping("xieyi")
	public Body xieyi() {
		return agreementservice.xieyi();
	}
}

