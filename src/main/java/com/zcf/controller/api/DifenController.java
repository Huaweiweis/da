package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcf.common.json.Body;
import com.zcf.service.DifenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-06-01
 */
@Controller
@RequestMapping("/difen/")
@CrossOrigin
@ResponseBody
public class DifenController {

	@Autowired
	private DifenService difenservice;
	@PostMapping("bijiao")
	public Body bijiao(Integer diifen) {
		if(diifen==null) {
			return Body.newInstance(502,"底分为空");
		}
		return difenservice.bijiao(diifen);
	}
}

