package com.zcf.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcf.common.json.Body;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.UsersMapper;
import com.zcf.pojo.Bonus;
import com.zcf.pojo.Getredenvelopes;
import com.zcf.pojo.Redenvelopes;
import com.zcf.pojo.Users;
import com.zcf.service.RedenvelopesService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 红包 前端控制器
 * </p>
 *
 * @author Huaweiwei
 * @since 2019-05-07
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/redenvelopes/")
public class RedenvelopesController {
	@Autowired
	private RedenvelopesService RedenvelopesService;
	
	@PostMapping("addredenvelopes")
	public Redenvelopes addredenvelopes(Integer userid, Double money, Integer num, Integer roomid) {
		
		return RedenvelopesService.addredenvelopes(userid, money, num,roomid);
	}
	/**
	 * 用户领取红包
	 * @param usersid
	 * @param redenvelopesid
	 * @return
	 */
	@PostMapping("adduserredenvelopes")
	public Body adduserredenvelopes(Integer usersid,Integer redenvelopesid) {
		
		return RedenvelopesService.adduserredenvelopes(usersid, redenvelopesid);
	}
	@PostMapping("getunclaimed")
	public Body getunclaimed(int redenvelopesid) {
		return RedenvelopesService.getunclaimed(redenvelopesid);
	}
	@PostMapping("getsuccess")
	public Body getsuccess(Integer redenvelopesid,Integer roomid) {
		if(redenvelopesid==null) {
			return Body.newInstance(503,"红包id为空");
		}
		return RedenvelopesService.getsuccess(redenvelopesid,roomid);
	}
	@PostMapping("getlingquuser")
	public Body getlingquuser(Integer redenvelopesid) {
		if(redenvelopesid==null) {
			return Body.newInstance(503,"红包id为空");
		}
		return RedenvelopesService.getlingquuser(redenvelopesid);
	}
	@PostMapping("getliushui")
	public Body getliushui(String yonghuming,Integer leixing,Integer shuxings) {
		return RedenvelopesService.getliushui(yonghuming,leixing,shuxings);
	}
	@PostMapping("usersuccess")
	public Body usersuccess(Integer redenvelopesid) {
		return RedenvelopesService.usersuccess(redenvelopesid);
	}
	@PostMapping("tuichu")
	public Body tuichu(Integer userid,Integer money) {
		return RedenvelopesService.tuichu(userid, money);
	}
	/*@PostMapping("sss")
	public Body sss() {
		Users users = new Users();
		users.setUserInvitationCode("685293");
		Users selectOne = usersmapper.selectOne(users);
		return Body.newInstance(selectOne);
	}*/
	
	public static void main(String[] args) {
		
		String  s= "4.138199999999999";
		String sss = s.substring(0,6);
    	
    	System.out.println(sss);
		/*Double diyi = 1.23;
		Double dier = 1.32;
		
		String diyida = diyi+"";
		String[] str_Arr = diyida.substring(diyida.lastIndexOf('.') + 1, diyida.length()).split("");
		Integer index_1 = Integer.parseInt(str_Arr[0]);
		Integer index_2 = Integer.parseInt(str_Arr[1]);
		String dierda = dier+"";
		String[] str_Arr1 = dierda.substring(dierda.lastIndexOf('.') + 1, dierda.length()).split("");
		Integer index_11 = Integer.parseInt(str_Arr1[0]);
		Integer index_22 = Integer.parseInt(str_Arr1[1]);
		String sum = (index_1 + index_2) + "";
		String sum1 = (index_11 + index_22) + "";

		Integer ss = Integer.parseInt(diyida.substring(0, 1));
		Integer ss1 = Integer.parseInt(dierda.substring(0, 1));
		
		System.out.println(sum);
		System.out.println(sum1);
		System.out.println(ss);*/
		/*String[] numArr = num.toString().replace(".", "").split("");
		return numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2]);*/
		/*String moneys = "1.12";
		Double ss = 0.00;
		String sum = null;
		String[] numArr = moneys.toString().replace(".", "").split("");
		if(numArr[0].equals(numArr[1]) && numArr[1].equals(numArr[2])) {
			ss = Double.parseDouble(moneys);
		}else {
			String[] str_Arr = moneys.substring(moneys.lastIndexOf('.') + 1, moneys.length()).split("");
			Integer index_1 = Integer.parseInt(str_Arr[0]);
			Integer index_2 = Integer.parseInt(str_Arr[1]);
			sum = (index_1 + index_2) + "";
			if (sum.length() > 1) {
				sum = sum.substring(sum.length() - 1, sum.length());
			}
		}*/
		double bilis= 5/100;
		String arr[]= {"再出发","孤独码农","再出发孤独码农"}; 
		System.out.println("最后一个值："+arr[arr.length-1]);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date now = new Date();
		  System.out.println("当前时间：" + sdf.format(now));
		  
		  Calendar nowTime = Calendar.getInstance();
		  nowTime.add(Calendar.MINUTE, 3);
		  System.out.println(sdf.format(nowTime.getTime()));
		
	}
}

