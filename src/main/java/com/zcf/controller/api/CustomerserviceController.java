package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcf.common.json.Body;
import com.zcf.service.CustomerserviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-06
 */
@Controller
@RequestMapping("/customerservice/")
@ResponseBody
@CrossOrigin
public class CustomerserviceController {
	@Autowired
	private CustomerserviceService customerservice;
	@PostMapping("getkefu")
	public Body getkefu(String niname) {
		return customerservice.getkefu(niname);
	}
	@PostMapping("addcustomer")
	public Body addcustomer(Integer customerserviceid, String customerservicephone, String customerservicename) {
		return customerservice.addcustomer(customerserviceid, customerservicephone, customerservicename);
	}
}

