package com.zcf.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zcf.common.utils.Hutool;
import com.zcf.mapper.AccountdetailsMapper;
import com.zcf.mapper.AgencyMapper;
import com.zcf.mapper.UsersMapper;
import com.zcf.pojo.Accountdetails;
import com.zcf.pojo.Agency;
import com.zcf.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Agencys {

	// TODO 五级分销
	@Autowired
	private UsersMapper usersmapper;
	@Autowired
	private AgencyMapper agencymapper;
	@Autowired
	private AccountdetailsMapper accountdetailsmapper;
	 public void defautTest(Integer userid,Double money,Integer yingjiaid,Double shengyumoney) {
		 Users shangjiyonghu = usersmapper.selectById(userid);//赢家上级代理
		 Users yingjia = usersmapper.selectById(yingjiaid);//赢家用户
		 
		 if(1 == shangjiyonghu.getUserGrade()) {//上级是一级代理时
			 if(6 == yingjia.getUserGrade()) {//用户是六级时，册不是会员
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					List<Agency> selectList = agencymapper.selectList(wrapper);
					double bili = 0.0;
					for (int i = 0; i < selectList.size(); i++) {
						bili+=selectList.get(i).getChoushuiBili();
					}
					Double shangji= bili*money;//上级是一级代理，上级应得百分比
					Double pingtai = money-shangji;//平台应得金额
					
					Users shangjiuser = usersmapper.selectById(userid);
					Double shangjis = shangjiuser.getUserRechargeIntegral()+shangji;
					shangjiuser.setUserRechargeIntegral(shangjis);
					usersmapper.updateById(shangjiuser);//上级应得金额
					
					Accountdetails shangjichoushui = new Accountdetails();
					shangjichoushui.setMoney(shangji+"");
					shangjichoushui.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					shangjichoushui.setAddTime(Hutool.parseDateToString());
					shangjichoushui.setSymbol(1);//加减符号    1加号，2减号
					shangjichoushui.setUseridId(shangjiuser.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);
					
					Accountdetails pingtaichoushui = new Accountdetails();
					pingtaichoushui.setMoney(pingtai+"");
					pingtaichoushui.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtaichoushui.setAddTime(Hutool.parseDateToString());
					pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
					accountdetailsmapper.insert(pingtaichoushui);
					
					Double fangzhus = yingjia.getUserRechargeIntegral()+shengyumoney;
					yingjia.setUserRechargeIntegral(fangzhus);
					usersmapper.updateById(yingjia);//赢家应得
					
					Accountdetails fangzhuying = new Accountdetails();
					fangzhuying.setMoney(shengyumoney+"");
					fangzhuying.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					fangzhuying.setAddTime(Hutool.parseDateToString());
					fangzhuying.setSymbol(1);//加减符号    1加号，2减号
					fangzhuying.setUseridId(yingjia.getUserId());//房主赢 房主id
					accountdetailsmapper.insert(fangzhuying);
					
			 }else if(5 == yingjia.getUserGrade()) {//用户五级代理
				EntityWrapper<Agency> wrappers = new EntityWrapper<>();
				wrappers.notIn("ageny_class", "1,2,3,4");
				List<Agency> selectList3 = agencymapper.selectList(wrappers);
				Double biuli=0.0; 
				for (int i = 0; i < selectList3.size(); i++) {
					biuli+=selectList3.get(i).getChoushuiBili();
				}
				Double yonghushangji = money*biuli;//用户上级应得金额
				shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+yonghushangji);
				usersmapper.updateById(shangjiyonghu);
				
				Accountdetails shangjichoushui = new Accountdetails();//上级代理拿的水费
				shangjichoushui.setMoney(yonghushangji+"");
				shangjichoushui.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				shangjichoushui.setAddTime(Hutool.parseDateToString());
				shangjichoushui.setSymbol(1);//加减符号    1加号，2减号
				shangjichoushui.setUseridId(shangjiyonghu.getUserId());//上级用户id
				accountdetailsmapper.insert(shangjichoushui);
				
				EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				wrapper.eq("ageny_class", yingjia.getUserGrade());
				List<Agency> selectList = agencymapper.selectList(wrapper);
				Double ziji = selectList.get(0).getChoushuiBili()*money;//自己是五级代理自己拿自己百分比抽水
				
				yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);//用户拿自己代理抽水
				usersmapper.updateById(yingjia);
				
				Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				zijifenhong.setMoney(ziji+"");
				zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				zijifenhong.setAddTime(Hutool.parseDateToString());
				zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				accountdetailsmapper.insert(shangjichoushui);

				
				yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);//用户拿自己代理抽水
				usersmapper.updateById(yingjia);
				
				Accountdetails zone = new Accountdetails();//自己拿自己的分红
				zone.setMoney(shengyumoney+"");
				zone.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				zone.setAddTime(Hutool.parseDateToString());
				zone.setSymbol(1);//加减符号    1加号，2减号
				zone.setUseridId(yingjia.getUserId());//上级用户id
				accountdetailsmapper.insert(zone);
				
				Double pingtai = money-shengyumoney-yonghushangji;
				
				Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
				pingtais.setMoney(pingtai+"");
				pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				pingtais.setAddTime(Hutool.parseDateToString());
				pingtais.setSymbol(1);//加减符号    1加号，2减号
				pingtais.setUseridId(yingjia.getUserId());//上级用户id
				accountdetailsmapper.insert(pingtais);
				
				
			 }else if(yingjia.getUserGrade().intValue()==4) {//用户四级代理
				 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "1,2,3");
					List<Agency> selectList3 = agencymapper.selectList(wrappers);
					Double biuli=0.0; 
					for (int i = 0; i < selectList3.size(); i++) {
						biuli+=selectList3.get(i).getChoushuiBili();
					}
					Double yonghushangji = money*biuli;//用户上级应得金额
					shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+yonghushangji);
					usersmapper.updateById(shangjiyonghu);
					
					Accountdetails shangjichoushui = new Accountdetails();//上级代理拿的水费
					shangjichoushui.setMoney(yonghushangji+"");
					shangjichoushui.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					shangjichoushui.setAddTime(Hutool.parseDateToString());
					shangjichoushui.setSymbol(1);//加减符号    1加号，2减号
					shangjichoushui.setUseridId(shangjiyonghu.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);
					
					EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "4,5");
					List<Agency> selectList = agencymapper.selectList(wrapper);
					Double sizichoushui=0.0; 
					for (int i = 0; i < selectList.size(); i++) {
						sizichoushui+=selectList.get(i).getChoushuiBili();
					}
					Double ziji = sizichoushui*money;//自己是五级代理自己拿自己百分比抽水
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);//用户拿自己代理抽水
					usersmapper.updateById(yingjia);
					
					Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
					zijifenhong.setMoney(ziji+"");
					zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					zijifenhong.setAddTime(Hutool.parseDateToString());
					zijifenhong.setSymbol(1);//加减符号    1加号，2减号
					zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);

					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);//用户拿自己代理抽水
					usersmapper.updateById(yingjia);
					
					Accountdetails zone = new Accountdetails();//自己拿自己的分红
					zone.setMoney(shengyumoney+"");
					zone.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					zone.setAddTime(Hutool.parseDateToString());
					zone.setSymbol(1);//加减符号    1加号，2减号
					zone.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(zone);
					
					Double pingtai = money-shengyumoney-yonghushangji;
					
					Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
					pingtais.setMoney(pingtai+"");
					pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtais.setAddTime(Hutool.parseDateToString());
					pingtais.setSymbol(1);//加减符号    1加号，2减号
					pingtais.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(pingtais);
			 }else if(yingjia.getUserGrade().intValue()==3) {//用户三级代理
				 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "1,2");
					List<Agency> selectList3 = agencymapper.selectList(wrappers);
					Double biuli=0.0; 
					for (int i = 0; i < selectList3.size(); i++) {
						biuli+=selectList3.get(i).getChoushuiBili();
					}
					Double yonghushangji = money*biuli;//用户上级应得金额
					shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+yonghushangji);
					usersmapper.updateById(shangjiyonghu);
					
					Accountdetails shangjichoushui = new Accountdetails();//上级代理拿的水费
					shangjichoushui.setMoney(yonghushangji+"");
					shangjichoushui.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					shangjichoushui.setAddTime(Hutool.parseDateToString());
					shangjichoushui.setSymbol(1);//加减符号    1加号，2减号
					shangjichoushui.setUseridId(shangjiyonghu.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);
					
					EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "3,4,5");
					List<Agency> selectList = agencymapper.selectList(wrapper);
					Double sizichoushui=0.0; 
					for (int i = 0; i < selectList.size(); i++) {
						sizichoushui+=selectList.get(i).getChoushuiBili();
					}
					Double ziji = sizichoushui*money;//自己是五级代理自己拿自己百分比抽水
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);//用户拿自己代理抽水
					usersmapper.updateById(yingjia);
					
					Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
					zijifenhong.setMoney(ziji+"");
					zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					zijifenhong.setAddTime(Hutool.parseDateToString());
					zijifenhong.setSymbol(1);//加减符号    1加号，2减号
					zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);

					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);//用户拿自己代理抽水
					usersmapper.updateById(yingjia);
					
					Accountdetails zone = new Accountdetails();//自己拿自己的分红
					zone.setMoney(shengyumoney+"");
					zone.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					zone.setAddTime(Hutool.parseDateToString());
					zone.setSymbol(1);//加减符号    1加号，2减号
					zone.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(zone);
					
					Double pingtai = money-shengyumoney-yonghushangji;
					
					Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
					pingtais.setMoney(pingtai+"");
					pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtais.setAddTime(Hutool.parseDateToString());
					pingtais.setSymbol(1);//加减符号    1加号，2减号
					pingtais.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(pingtais);
					
			 }else if(yingjia.getUserGrade().intValue()==2) {//用户二级代理
				 	EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "1");
					List<Agency> selectList3 = agencymapper.selectList(wrappers);
					Double biuli=0.0; 
					for (int i = 0; i < selectList3.size(); i++) {
						biuli+=selectList3.get(i).getChoushuiBili();
					}
					Double yonghushangji = money*biuli;//用户上级应得金额
					shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+yonghushangji);
					usersmapper.updateById(shangjiyonghu);
					
					Accountdetails shangjichoushui = new Accountdetails();//上级代理拿的水费
					shangjichoushui.setMoney(yonghushangji+"");
					shangjichoushui.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					shangjichoushui.setAddTime(Hutool.parseDateToString());
					shangjichoushui.setSymbol(1);//加减符号    1加号，2减号
					shangjichoushui.setUseridId(shangjiyonghu.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);
					
					EntityWrapper<Agency> wrapper = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "2,3,4,5");
					List<Agency> selectList = agencymapper.selectList(wrapper);
					Double sizichoushui=0.0; 
					for (int i = 0; i < selectList.size(); i++) {
						sizichoushui+=selectList.get(i).getChoushuiBili();
					}
					Double ziji = sizichoushui*money;//自己是五级代理自己拿自己百分比抽水
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);//用户拿自己代理抽水
					usersmapper.updateById(yingjia);
					
					Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
					zijifenhong.setMoney(ziji+"");
					zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					zijifenhong.setAddTime(Hutool.parseDateToString());
					zijifenhong.setSymbol(1);//加减符号    1加号，2减号
					zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(shangjichoushui);

					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);//用户拿自己代理抽水
					usersmapper.updateById(yingjia);
					
					Accountdetails zone = new Accountdetails();//自己拿自己的分红
					zone.setMoney(shengyumoney+"");
					zone.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					zone.setAddTime(Hutool.parseDateToString());
					zone.setSymbol(1);//加减符号    1加号，2减号
					zone.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(zone);
					
					Double pingtai = money-shengyumoney-yonghushangji;
					
					Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
					pingtais.setMoney(pingtai+"");
					pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtais.setAddTime(Hutool.parseDateToString());
					pingtais.setSymbol(1);//加减符号    1加号，2减号
					pingtais.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(pingtais);
					
			 }else if(yingjia.getUserGrade().intValue()==1) {//用户也是一级代理
				EntityWrapper<Agency> wrappers = new EntityWrapper<>();
				wrappers.notIn("ageny_class", "1,2,3,4,5");
				List<Agency> selectList3 = agencymapper.selectList(wrappers);
				Double choushui = 0.0;
				for (int i = 0; i < selectList3.size(); i++) {
					choushui+=selectList3.get(i).getChoushuiBili();
				}
				Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
				
				yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
				usersmapper.updateById(yingjia);
				
				Accountdetails yingjiass = new Accountdetails();//自己拿自己的分红
				yingjiass.setMoney(yingjias+"");
				yingjiass.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				yingjiass.setAddTime(Hutool.parseDateToString());
				yingjiass.setSymbol(1);//加减符号    1加号，2减号
				yingjiass.setUseridId(yingjia.getUserId());//上级用户id
				accountdetailsmapper.insert(yingjiass);
				
				
				yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				usersmapper.updateById(yingjia);
				
				Accountdetails yingjiasss = new Accountdetails();//自己拿自己的分红
				yingjiasss.setMoney(shengyumoney+"");
				yingjiasss.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				yingjiasss.setAddTime(Hutool.parseDateToString());
				yingjiasss.setSymbol(1);//加减符号    1加号，2减号
				yingjiasss.setUseridId(yingjia.getUserId());//上级用户id
				accountdetailsmapper.insert(yingjiasss);
				
				Double pingtais =money-yingjias;
				Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				pingtai.setMoney(pingtais+"");
				pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				pingtai.setAddTime(Hutool.parseDateToString());
				pingtai.setSymbol(1);//加减符号    1加号，2减号
				accountdetailsmapper.insert(yingjiasss);
			 }
			 
		 }else if(shangjiyonghu.getUserGrade().intValue()==2) {//上级是二级代理时,判断有没有上级代理
			 if(yingjia.getUserGrade().intValue()==1) {//用户代理等级超越上级用户等级
				 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "1,2,3,4,5");
					List<Agency> selectList3 = agencymapper.selectList(wrappers);
					Double choushui = 0.0;
					for (int i = 0; i < selectList3.size(); i++) {
						choushui+=selectList3.get(i).getChoushuiBili();
					}
					Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
					usersmapper.updateById(yingjia);
					
					Accountdetails yingjiass = new Accountdetails();//自己拿自己的分红
					yingjiass.setMoney(yingjias+"");
					yingjiass.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					yingjiass.setAddTime(Hutool.parseDateToString());
					yingjiass.setSymbol(1);//加减符号    1加号，2减号
					yingjiass.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(yingjiass);
					
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					usersmapper.updateById(yingjia);
					
					Accountdetails yingjiasss = new Accountdetails();//自己拿自己的分红
					yingjiasss.setMoney(shengyumoney+"");
					yingjiasss.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					yingjiasss.setAddTime(Hutool.parseDateToString());
					yingjiasss.setSymbol(1);//加减符号    1加号，2减号
					yingjiasss.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(yingjiasss);
					
					Double pingtais =money-yingjias;
					Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
					pingtai.setMoney(pingtais+"");
					pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtai.setAddTime(Hutool.parseDateToString());
					pingtai.setSymbol(1);//加减符号    1加号，2减号
					accountdetailsmapper.insert(yingjiasss);
			 }else if(yingjia.getUserGrade().intValue()==2) {//用户代理等级和上级代理等级相等    自己拿自己的抽水，判断上级等级多少
				 EntityWrapper<Users> wrapper =  new EntityWrapper<>();
				 wrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList = usersmapper.selectList(wrapper);
				 if(!selectList.isEmpty()) {//有上级代理
					 if(selectList.get(0).getUserGrade()<shangjiyonghu.getUserGrade()) {//上级的上级比上级代理等级高
						 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
						 shangjishangji.eq("ageny_class", selectList.get(0).getUserGrade());
						 List<Agency> selectList2 = agencymapper.selectList(shangjishangji);
						 Double shangjishangjichosuhui = selectList2.get(0).getChoushuiBili()*money;//上级的上级比上级等级高，拿上级抽水
						 
						 selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangjichosuhui);
						 usersmapper.updateById(selectList.get(0));//上级的上级抽水
						 
						 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
						 yingjiachoushui.notIn("ageny_class", "2,3,4,5");
						 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
						 Double ziji = 0.0;
						 for (int i = 0; i < selectList3.size(); i++) {
							 ziji+=selectList3.get(i).getChoushuiBili();
						}
						 Double yingjiayingde = ziji*money;
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjiayingde);
						 usersmapper.updateById(yingjia);
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails yingjiass = new Accountdetails();//上级上级的分红
						 yingjiass.setMoney(shangjishangjichosuhui+"");
					   	 yingjiass.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 yingjiass.setAddTime(Hutool.parseDateToString());
						 yingjiass.setSymbol(1);//加减符号    1加号，2减号
						 yingjiass.setUseridId(selectList.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(yingjiass);
						 
						 Accountdetails fenhong = new Accountdetails();//用户应得金额
						 fenhong.setMoney(yingjiayingde+"");
						 fenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 fenhong.setAddTime(Hutool.parseDateToString());
						 fenhong.setSymbol(1);//加减符号    1加号，2减号
						 fenhong.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(fenhong);
						 
						 Accountdetails qianghongbao = new Accountdetails();//用户应得金额
						 qianghongbao.setMoney(shengyumoney+"");
						 qianghongbao.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianghongbao.setAddTime(Hutool.parseDateToString());
						 qianghongbao.setSymbol(1);//加减符号    1加号，2减号
						 qianghongbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianghongbao);
						 
						 Double pingtai =money-yingjiayingde-shangjishangjichosuhui;//平台应得金额
						 Accountdetails pingtais = new Accountdetails();//用户应得金额
						 pingtais.setMoney(pingtai+"");
						 pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtais.setAddTime(Hutool.parseDateToString());
						 pingtais.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(qianghongbao);
						 
					 }else {//上级等级没有自己等级高
						 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
						 yingjiachoushui.notIn("ageny_class", "2,3,4,5");
						 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
						 Double ziji = 0.0;
						 for (int i = 0; i < selectList3.size(); i++) {
							 ziji+=selectList3.get(i).getChoushuiBili();
						}
						 Double yingjiayingde = ziji*money;
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjiayingde);
						 usersmapper.updateById(yingjia);
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails fenhong = new Accountdetails();//用户应得金额
						 fenhong.setMoney(yingjiayingde+"");
						 fenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 fenhong.setAddTime(Hutool.parseDateToString());
						 fenhong.setSymbol(1);//加减符号    1加号，2减号
						 fenhong.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(fenhong);
						 
						 Accountdetails qianghongbao = new Accountdetails();//用户应得金额
						 qianghongbao.setMoney(shengyumoney+"");
						 qianghongbao.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianghongbao.setAddTime(Hutool.parseDateToString());
						 qianghongbao.setSymbol(1);//加减符号    1加号，2减号
						 qianghongbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianghongbao);
						 
						 Double pingtai =money-yingjiayingde;//平台应得金额
						 Accountdetails pingtais = new Accountdetails();//用户应得金额
						 pingtais.setMoney(pingtai+"");
						 pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtais.setAddTime(Hutool.parseDateToString());
						 pingtais.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(qianghongbao);
					 }
				 }else {//没有上级代理
					 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
					 yingjiachoushui.notIn("ageny_class", "2,3,4,5");
					 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
					 Double ziji = 0.0;
					 for (int i = 0; i < selectList3.size(); i++) {
						 ziji+=selectList3.get(i).getChoushuiBili();
					}
					 Double yingjiayingde = ziji*money;
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjiayingde);
					 usersmapper.updateById(yingjia);
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails fenhong = new Accountdetails();//用户应得金额
					 fenhong.setMoney(yingjiayingde+"");
					 fenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 fenhong.setAddTime(Hutool.parseDateToString());
					 fenhong.setSymbol(1);//加减符号    1加号，2减号
					 fenhong.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(fenhong);
					 
					 Accountdetails qianghongbao = new Accountdetails();//用户应得金额
					 qianghongbao.setMoney(shengyumoney+"");
					 qianghongbao.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qianghongbao.setAddTime(Hutool.parseDateToString());
					 qianghongbao.setSymbol(1);//加减符号    1加号，2减号
					 qianghongbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qianghongbao);
					 
					 Double pingtai =money-yingjiayingde;//平台应得金额
					 Accountdetails pingtais = new Accountdetails();//用户应得金额
					 pingtais.setMoney(pingtai+"");
					 pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 pingtais.setAddTime(Hutool.parseDateToString());
					 pingtais.setSymbol(1);//加减符号    1加号，2减号
					 accountdetailsmapper.insert(qianghongbao);
				 }
				 
			 }else if(yingjia.getUserGrade().intValue()==3) {//上级可以拿自己抽水’
				 
				 EntityWrapper<Users> entityWrapper  = new EntityWrapper<>();//2
				 entityWrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList = usersmapper.selectList(entityWrapper);
				 if(!selectList.isEmpty()) {//上级有上级代理
					 if(selectList.get(0).getUserGrade()>selectList.get(0).getUserGrade()) {//上级代理没有上级的上级代理等级高
						 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
						 yingjiachoushui.eq("ageny_class", selectList.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
						 Double shangjishangjichosuhui = selectList3.get(0).getChoushuiBili()*money;//上级的上级
						 selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangjichosuhui);				 
						 usersmapper.updateById(selectList.get(0));
						 
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.eq("ageny_class", shangjiyonghu.getUserGrade());
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjichosuhui = shangjia.get(0).getChoushuiBili()*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "3,4,5");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjishanjia = new Accountdetails();//上级上级
						 shangjishanjia.setMoney(shangjishangjichosuhui+"");
						 shangjishanjia.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishanjia.setAddTime(Hutool.parseDateToString());
						 shangjishanjia.setSymbol(1);//加减符号    1加号，2减号
						 shangjishanjia.setUseridId(selectList.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishanjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-shangjishangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
						 
					 }else {
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.eq("ageny_class", shangjiyonghu.getUserGrade());
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjichosuhui = shangjia.get(0).getChoushuiBili()*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "3,4,5");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
					 }
					 
				 }else {//查不到上级的上级用户
					 EntityWrapper<Agency> shangji = new EntityWrapper<>();
					 shangji.eq("ageny_class", shangjiyonghu.getUserGrade());
					 List<Agency> shangjia = agencymapper.selectList(shangji);
					 Double shangjichosuhui = shangjia.get(0).getChoushuiBili()*money;//自己的上级代理
					 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
					 usersmapper.updateById(shangjiyonghu);
					 
					 EntityWrapper<Agency> ziji = new EntityWrapper<>();
					 ziji.notIn("ageny_class", "3,4,5");
					 List<Agency> zijichou = agencymapper.selectList(ziji);
					 Double chsouhui= 0.0;
					 for (int i = 0; i < zijichou.size(); i++) {
						 chsouhui+=zijichou.get(i).getChoushuiBili();
					}
					 Double zijichoushui = chsouhui*money;//自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails shangjhi = new Accountdetails();//上级
					 shangjhi.setMoney(shangjichosuhui+"");
					 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 shangjhi.setAddTime(Hutool.parseDateToString());
					 shangjhi.setSymbol(1);//加减符号    1加号，2减号
					 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
					 accountdetailsmapper.insert(shangjhi);
					 
					 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
					 zijichoushuis.setMoney(zijichoushui+"");
					 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 zijichoushuis.setAddTime(Hutool.parseDateToString());
					 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
					 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(zijichoushuis);
					 
					 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
					 qianhobgbao.setMoney(shengyumoney+"");
					 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qianhobgbao.setAddTime(Hutool.parseDateToString());
					 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
					 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qianhobgbao);
					 
					 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
					 
					 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
					 pingtaichoushui.setMoney(pingtai+"");
					 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 pingtaichoushui.setAddTime(Hutool.parseDateToString());
					 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
					 accountdetailsmapper.insert(pingtaichoushui);
				 }
				 
			 }else if(yingjia.getUserGrade().intValue()==4) {//上级可以拿自己抽水
				 EntityWrapper<Users> entityWrapper  = new EntityWrapper<>();//2
				 entityWrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList = usersmapper.selectList(entityWrapper);
				 if(!selectList.isEmpty()) {//上级有上级代理
					 if(selectList.get(0).getUserGrade()>selectList.get(0).getUserGrade()) {//上级代理没有上级的上级代理等级高
						 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
						 yingjiachoushui.eq("ageny_class", selectList.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
						 Double shangjishangjichosuhui = selectList3.get(0).getChoushuiBili()*money;//上级的上级
						 selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangjichosuhui);				 
						 usersmapper.updateById(selectList.get(0));
						 
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.notIn("ageny_class", "2,3");
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjis = 0.0;
						 for (int i = 0; i < shangjia.size(); i++) {
							 shangjis +=shangjia.get(i).getChoushuiBili();
						}
						 Double shangjichosuhui = shangjis*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "4,5");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjishanjia = new Accountdetails();//上级上级
						 shangjishanjia.setMoney(shangjishangjichosuhui+"");
						 shangjishanjia.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishanjia.setAddTime(Hutool.parseDateToString());
						 shangjishanjia.setSymbol(1);//加减符号    1加号，2减号
						 shangjishanjia.setUseridId(selectList.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishanjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-shangjishangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
						 
					 }else {
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.notIn("ageny_class", "2,3");
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjis = 0.0;
						 for (int i = 0; i < shangjia.size(); i++) {
							 shangjis +=shangjia.get(i).getChoushuiBili();
						}
						 Double shangjichosuhui = shangjis*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "4,5");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
					 }
					 
				 }else {//查不到上级的上级用户
					 EntityWrapper<Agency> shangji = new EntityWrapper<>();
					 shangji.notIn("ageny_class", "2,3");
					 List<Agency> shangjia = agencymapper.selectList(shangji);
					 Double shangjis = 0.0;
					 for (int i = 0; i < shangjia.size(); i++) {
						 shangjis +=shangjia.get(i).getChoushuiBili();
					}
					 Double shangjichosuhui = shangjis*money;//自己的上级代理
					 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
					 usersmapper.updateById(shangjiyonghu);
					 
					 EntityWrapper<Agency> ziji = new EntityWrapper<>();
					 ziji.notIn("ageny_class", "4,5");
					 List<Agency> zijichou = agencymapper.selectList(ziji);
					 Double chsouhui= 0.0;
					 for (int i = 0; i < zijichou.size(); i++) {
						 chsouhui+=zijichou.get(i).getChoushuiBili();
					}
					 Double zijichoushui = chsouhui*money;//自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails shangjhi = new Accountdetails();//上级
					 shangjhi.setMoney(shangjichosuhui+"");
					 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 shangjhi.setAddTime(Hutool.parseDateToString());
					 shangjhi.setSymbol(1);//加减符号    1加号，2减号
					 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
					 accountdetailsmapper.insert(shangjhi);
					 
					 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
					 zijichoushuis.setMoney(zijichoushui+"");
					 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 zijichoushuis.setAddTime(Hutool.parseDateToString());
					 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
					 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(zijichoushuis);
					 
					 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
					 qianhobgbao.setMoney(shengyumoney+"");
					 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qianhobgbao.setAddTime(Hutool.parseDateToString());
					 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
					 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qianhobgbao);
					 
					 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
					 
					 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
					 pingtaichoushui.setMoney(pingtai+"");
					 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 pingtaichoushui.setAddTime(Hutool.parseDateToString());
					 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
					 accountdetailsmapper.insert(pingtaichoushui);
				 }
				 
			 
			 }else if(yingjia.getUserGrade().intValue()==5) {//上级可以拿自己抽水
				 EntityWrapper<Users> entityWrapper  = new EntityWrapper<>();//2
				 entityWrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList = usersmapper.selectList(entityWrapper);
				 if(!selectList.isEmpty()) {//上级有上级代理
					 if(selectList.get(0).getUserGrade()>selectList.get(0).getUserGrade()) {//上级代理没有上级的上级代理等级高
						 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
						 yingjiachoushui.eq("ageny_class", selectList.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
						 Double shangjishangjichosuhui = selectList3.get(0).getChoushuiBili()*money;//上级的上级
						 selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangjichosuhui);				 
						 usersmapper.updateById(selectList.get(0));
						 
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.notIn("ageny_class", "2,3,4");
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjis = 0.0;
						 for (int i = 0; i < shangjia.size(); i++) {
							 shangjis +=shangjia.get(i).getChoushuiBili();
						}
						 Double shangjichosuhui = shangjis*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "5");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjishanjia = new Accountdetails();//上级上级
						 shangjishanjia.setMoney(shangjishangjichosuhui+"");
						 shangjishanjia.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishanjia.setAddTime(Hutool.parseDateToString());
						 shangjishanjia.setSymbol(1);//加减符号    1加号，2减号
						 shangjishanjia.setUseridId(selectList.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishanjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-shangjishangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
						 
					 }else {
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.notIn("ageny_class", "2,3,4");
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjis = 0.0;
						 for (int i = 0; i < shangjia.size(); i++) {
							 shangjis +=shangjia.get(i).getChoushuiBili();
						}
						 Double shangjichosuhui = shangjis*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "5");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
					 }
					 
				 }else {//查不到上级的上级用户
					 EntityWrapper<Agency> shangji = new EntityWrapper<>();
					 shangji.notIn("ageny_class", "2,3,4");
					 List<Agency> shangjia = agencymapper.selectList(shangji);
					 Double shangjis = 0.0;
					 for (int i = 0; i < shangjia.size(); i++) {
						 shangjis +=shangjia.get(i).getChoushuiBili();
					}
					 Double shangjichosuhui = shangjis*money;//自己的上级代理
					 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
					 usersmapper.updateById(shangjiyonghu);
					 
					 EntityWrapper<Agency> ziji = new EntityWrapper<>();
					 ziji.notIn("ageny_class", "5");
					 List<Agency> zijichou = agencymapper.selectList(ziji);
					 Double chsouhui= 0.0;
					 for (int i = 0; i < zijichou.size(); i++) {
						 chsouhui+=zijichou.get(i).getChoushuiBili();
					}
					 Double zijichoushui = chsouhui*money;//自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails shangjhi = new Accountdetails();//上级
					 shangjhi.setMoney(shangjichosuhui+"");
					 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 shangjhi.setAddTime(Hutool.parseDateToString());
					 shangjhi.setSymbol(1);//加减符号    1加号，2减号
					 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
					 accountdetailsmapper.insert(shangjhi);
					 
					 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
					 zijichoushuis.setMoney(zijichoushui+"");
					 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 zijichoushuis.setAddTime(Hutool.parseDateToString());
					 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
					 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(zijichoushuis);
					 
					 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
					 qianhobgbao.setMoney(shengyumoney+"");
					 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qianhobgbao.setAddTime(Hutool.parseDateToString());
					 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
					 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qianhobgbao);
					 
					 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
					 
					 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
					 pingtaichoushui.setMoney(pingtai+"");
					 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 pingtaichoushui.setAddTime(Hutool.parseDateToString());
					 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
					 accountdetailsmapper.insert(pingtaichoushui);
				 }
			 }else if(yingjia.getUserGrade().intValue()==6) {//上级可以拿自己抽水
				 EntityWrapper<Users> entityWrapper  = new EntityWrapper<>();//2
				 entityWrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList = usersmapper.selectList(entityWrapper);
				 if(!selectList.isEmpty()) {//上级有上级代理
					 if(selectList.get(0).getUserGrade()>selectList.get(0).getUserGrade()) {//上级代理没有上级的上级代理等级高
						 EntityWrapper<Agency> yingjiachoushui = new EntityWrapper<>();
						 yingjiachoushui.eq("ageny_class", selectList.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(yingjiachoushui);
						 Double shangjishangjichosuhui = selectList3.get(0).getChoushuiBili()*money;//上级的上级
						 selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangjichosuhui);				 
						 usersmapper.updateById(selectList.get(0));
						 
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.notIn("ageny_class", "2,3,4,5");
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjis = 0.0;
						 for (int i = 0; i < shangjia.size(); i++) {
							 shangjis +=shangjia.get(i).getChoushuiBili();
						}
						 Double shangjichosuhui = shangjis*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "6");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjishanjia = new Accountdetails();//上级上级
						 shangjishanjia.setMoney(shangjishangjichosuhui+"");
						 shangjishanjia.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishanjia.setAddTime(Hutool.parseDateToString());
						 shangjishanjia.setSymbol(1);//加减符号    1加号，2减号
						 shangjishanjia.setUseridId(selectList.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishanjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-shangjishangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
						 
					 }else {
						 EntityWrapper<Agency> shangji = new EntityWrapper<>();
						 shangji.notIn("ageny_class", "2,3,4,5");
						 List<Agency> shangjia = agencymapper.selectList(shangji);
						 Double shangjis = 0.0;
						 for (int i = 0; i < shangjia.size(); i++) {
							 shangjis +=shangjia.get(i).getChoushuiBili();
						}
						 Double shangjichosuhui = shangjis*money;//自己的上级代理
						 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
						 usersmapper.updateById(shangjiyonghu);
						 
						 EntityWrapper<Agency> ziji = new EntityWrapper<>();
						 ziji.notIn("ageny_class", "6");
						 List<Agency> zijichou = agencymapper.selectList(ziji);
						 Double chsouhui= 0.0;
						 for (int i = 0; i < zijichou.size(); i++) {
							 chsouhui+=zijichou.get(i).getChoushuiBili();
						}
						 Double zijichoushui = chsouhui*money;//自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjhi = new Accountdetails();//上级
						 shangjhi.setMoney(shangjichosuhui+"");
						 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjhi.setAddTime(Hutool.parseDateToString());
						 shangjhi.setSymbol(1);//加减符号    1加号，2减号
						 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjhi);
						 
						 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
						 zijichoushuis.setMoney(zijichoushui+"");
						 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 zijichoushuis.setAddTime(Hutool.parseDateToString());
						 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
						 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(zijichoushuis);
						 
						 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
						 qianhobgbao.setMoney(shengyumoney+"");
						 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qianhobgbao.setAddTime(Hutool.parseDateToString());
						 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
						 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qianhobgbao);
						 
						 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
						 
						 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
						 pingtaichoushui.setMoney(pingtai+"");
						 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 pingtaichoushui.setAddTime(Hutool.parseDateToString());
						 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
						 accountdetailsmapper.insert(pingtaichoushui);
					 }
					 
				 }else {//查不到上级的上级用户
					 EntityWrapper<Agency> shangji = new EntityWrapper<>();
					 shangji.notIn("ageny_class", "2,3,4,5");
					 List<Agency> shangjia = agencymapper.selectList(shangji);
					 Double shangjis = 0.0;
					 for (int i = 0; i < shangjia.size(); i++) {
						 shangjis +=shangjia.get(i).getChoushuiBili();
					}
					 Double shangjichosuhui = shangjis*money;//自己的上级代理
					 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjichosuhui);
					 usersmapper.updateById(shangjiyonghu);
					 
					 EntityWrapper<Agency> ziji = new EntityWrapper<>();
					 ziji.notIn("ageny_class", "6");
					 List<Agency> zijichou = agencymapper.selectList(ziji);
					 Double chsouhui= 0.0;
					 for (int i = 0; i < zijichou.size(); i++) {
						 chsouhui+=zijichou.get(i).getChoushuiBili();
					}
					 Double zijichoushui = chsouhui*money;//自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails shangjhi = new Accountdetails();//上级
					 shangjhi.setMoney(shangjichosuhui+"");
					 shangjhi.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 shangjhi.setAddTime(Hutool.parseDateToString());
					 shangjhi.setSymbol(1);//加减符号    1加号，2减号
					 shangjhi.setUseridId(shangjiyonghu.getUserId());//上级用户id
					 accountdetailsmapper.insert(shangjhi);
					 
					 Accountdetails zijichoushuis = new Accountdetails();//自己拿自己分红
					 zijichoushuis.setMoney(zijichoushui+"");
					 zijichoushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 zijichoushuis.setAddTime(Hutool.parseDateToString());
					 zijichoushuis.setSymbol(1);//加减符号    1加号，2减号
					 zijichoushuis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(zijichoushuis);
					 
					 Accountdetails qianhobgbao = new Accountdetails();//自己抢红包剩下的钱
					 qianhobgbao.setMoney(shengyumoney+"");
					 qianhobgbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qianhobgbao.setAddTime(Hutool.parseDateToString());
					 qianhobgbao.setSymbol(1);//加减符号    1加号，2减号
					 qianhobgbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qianhobgbao);
					 
					 Double pingtai = money-shangjichosuhui-zijichoushui;//平台抽水
					 
					 Accountdetails pingtaichoushui = new Accountdetails();//自己抢红包剩下的钱
					 pingtaichoushui.setMoney(pingtai+"");
					 pingtaichoushui.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 pingtaichoushui.setAddTime(Hutool.parseDateToString());
					 pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
					 accountdetailsmapper.insert(pingtaichoushui);
				 }
			 }
		 }else if(shangjiyonghu.getUserGrade().intValue()==3) {//上级是三级代理时,判断有没有上级代理
			 if(yingjia.getUserGrade().intValue()==1) {//自己拿自己抽水
				 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "1,2,3,4,5");
					List<Agency> selectList3 = agencymapper.selectList(wrappers);
					Double choushui = 0.0;
					for (int i = 0; i < selectList3.size(); i++) {
						choushui+=selectList3.get(i).getChoushuiBili();
					}
					Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
					usersmapper.updateById(yingjia);
					
					Accountdetails yingjiass = new Accountdetails();//自己拿自己的分红
					yingjiass.setMoney(yingjias+"");
					yingjiass.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					yingjiass.setAddTime(Hutool.parseDateToString());
					yingjiass.setSymbol(1);//加减符号    1加号，2减号
					yingjiass.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(yingjiass);
					
					
					yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					usersmapper.updateById(yingjia);
					
					Accountdetails yingjiasss = new Accountdetails();//自己拿自己的分红
					yingjiasss.setMoney(shengyumoney+"");
					yingjiasss.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					yingjiasss.setAddTime(Hutool.parseDateToString());
					yingjiasss.setSymbol(1);//加减符号    1加号，2减号
					yingjiasss.setUseridId(yingjia.getUserId());//上级用户id
					accountdetailsmapper.insert(yingjiasss);
					
					Double pingtais =money-yingjias;
					Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
					pingtai.setMoney(pingtais+"");
					pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtai.setAddTime(Hutool.parseDateToString());
					pingtai.setSymbol(1);//加减符号    1加号，2减号
					accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==2) {//自己拿自己抽水
				 EntityWrapper<Users> wrapper = new EntityWrapper<>();
				 wrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList = usersmapper.selectList(wrapper);
				 if(selectList.isEmpty()) {
					 if(yingjia.getUserGrade().intValue()>selectList.get(0).getUserGrade().intValue()) {//上级用户的上级为一级代理
						 EntityWrapper<Agency> bili = new EntityWrapper<>();
						 bili.eq("ageny_class", selectList.get(0).getUserGrade());
						 List<Agency> selectList2 = agencymapper.selectList(bili);
						 Double shangjishangji = selectList2.get(0).getChoushuiBili()*money;//上级的上级是一级代理
						 
						 selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangji);
						 usersmapper.updateById(selectList.get(0));
						 
						 
						 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
						 wrappers.notIn("ageny_class", "2,3,4,5");
						 List<Agency> selectList3 = agencymapper.selectList(wrappers);
						 Double choushui = 0.0;
						 for (int i = 0; i < selectList3.size(); i++) {
							 choushui+=selectList3.get(i).getChoushuiBili();
						 }
						 Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
						 shangjishangjis.setMoney(shangjishangji+"");
						 shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjis.setAddTime(Hutool.parseDateToString());
						 shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjis);
						 
						 Accountdetails choushuis = new Accountdetails();//自己拿自己的分红
						 choushuis.setMoney(yingjias+"");
						 choushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 choushuis.setAddTime(Hutool.parseDateToString());
						 choushuis.setSymbol(1);//加减符号    1加号，2减号
						 choushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(choushuis);
						 
						 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
						 qiangbao.setMoney(shengyumoney+"");
						 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qiangbao.setAddTime(Hutool.parseDateToString());
						 qiangbao.setSymbol(1);//加减符号    1加号，2减号
						 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qiangbao);
						 
						 Double pingtais =money-shangjishangji-yingjias;
							Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
							pingtai.setMoney(pingtais+"");
							pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							pingtai.setAddTime(Hutool.parseDateToString());
							pingtai.setSymbol(1);//加减符号    1加号，2减号
							accountdetailsmapper.insert(pingtai);
						 
					 }else {//上级用户没有达到二级，赢家自己拿自己
						 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
						 wrappers.notIn("ageny_class", "2,3,4,5");
						 List<Agency> selectList3 = agencymapper.selectList(wrappers);
						 Double choushui = 0.0;
						 for (int i = 0; i < selectList3.size(); i++) {
							 choushui+=selectList3.get(i).getChoushuiBili();
						 }
						 Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
						 usersmapper.updateById(yingjia);
						 
						 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
						 usersmapper.updateById(yingjia);
						 
						 Accountdetails choushuis = new Accountdetails();//自己拿自己的分红
						 choushuis.setMoney(yingjias+"");
						 choushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 choushuis.setAddTime(Hutool.parseDateToString());
						 choushuis.setSymbol(1);//加减符号    1加号，2减号
						 choushuis.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(choushuis);
						 
						 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
						 qiangbao.setMoney(shengyumoney+"");
						 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 qiangbao.setAddTime(Hutool.parseDateToString());
						 qiangbao.setSymbol(1);//加减符号    1加号，2减号
						 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
						 accountdetailsmapper.insert(qiangbao);
						 
						 Double pingtais =money-yingjias;
							Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
							pingtai.setMoney(pingtais+"");
							pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							pingtai.setAddTime(Hutool.parseDateToString());
							pingtai.setSymbol(1);//加减符号    1加号，2减号
							accountdetailsmapper.insert(pingtai);
					 }
				 }else {
					 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					 wrappers.notIn("ageny_class", "2,3,4,5");
					 List<Agency> selectList3 = agencymapper.selectList(wrappers);
					 Double choushui = 0.0;
					 for (int i = 0; i < selectList3.size(); i++) {
						 choushui+=selectList3.get(i).getChoushuiBili();
					 }
					 Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails choushuis = new Accountdetails();//自己拿自己的分红
					 choushuis.setMoney(yingjias+"");
					 choushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 choushuis.setAddTime(Hutool.parseDateToString());
					 choushuis.setSymbol(1);//加减符号    1加号，2减号
					 choushuis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(choushuis);
					 
					 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
					 qiangbao.setMoney(shengyumoney+"");
					 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qiangbao.setAddTime(Hutool.parseDateToString());
					 qiangbao.setSymbol(1);//加减符号    1加号，2减号
					 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qiangbao);
					 
					 Double pingtais =money-yingjias;
						Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
						pingtai.setMoney(pingtais+"");
						pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						pingtai.setAddTime(Hutool.parseDateToString());
						pingtai.setSymbol(1);//加减符号    1加号，2减号
						accountdetailsmapper.insert(pingtai);
				 }
				 
			 }else if(yingjia.getUserGrade().intValue()==3) {//自己拿自己抽水 判断是否有上级用户，是否比自己等级高
				EntityWrapper<Users> wrapper = new EntityWrapper<>();
				wrapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				List<Users> selectList = usersmapper.selectList(wrapper);
				
				Double shangjishangjishangjichoushui = 0.0;
				Double shangjishangji = 0.0;
				
				if(!selectList.isEmpty()) {//上级有上级代理
					if(selectList.get(0).getUserGrade()<yingjia.getUserGrade()) {//上级用户的上级是否比上级等级高
						EntityWrapper<Users> wrapper2 = new EntityWrapper<>();
						wrapper2.eq("user_invitation_code", selectList.get(0).getTheHigherTheAgent());
						List<Users> selectList2 = usersmapper.selectList(wrapper2);
						if(!selectList2.isEmpty()) {
							EntityWrapper<Agency> wraooer = new EntityWrapper<>();
							wraooer.eq("ageny_class", selectList2.get(0).getUserGrade());
							List<Agency> selectList3 = agencymapper.selectList(wraooer);
							shangjishangji= selectList3.get(0).getChoushuiBili()*money;//上级的上级用户
							selectList.get(0).setUserRechargeIntegral(selectList.get(0).getUserRechargeIntegral()+shangjishangji);
							usersmapper.updateById(selectList.get(0));
							
							Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
							shangjishangjis.setMoney(shangjishangji+"");
							shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							shangjishangjis.setAddTime(Hutool.parseDateToString());
							shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
							shangjishangjis.setUseridId(selectList.get(0).getUserId());//上级用户id
							accountdetailsmapper.insert(shangjishangjis);
							
						}
					}
					EntityWrapper<Users> wrapper2 = new EntityWrapper<>();
					wrapper2.eq("user_invitation_code", selectList.get(0).getTheHigherTheAgent());
					List<Users> selectList2 = usersmapper.selectList(wrapper2);
					if(!selectList2.isEmpty()) {
						if(selectList.get(0).getUserGrade()<yingjia.getUserGrade()) {//上级的上级比自己等级高
							EntityWrapper<Agency> wraooer = new EntityWrapper<>();
							wraooer.eq("ageny_class", selectList.get(0).getUserGrade());
							List<Agency> selectList3 = agencymapper.selectList(wraooer);
							Double shangjishangjishangji = selectList3.get(0).getChoushuiBili()*money;
							shangjishangjishangjichoushui = shangjishangjishangji*money;
							selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+shangjishangjishangjichoushui);
							usersmapper.updateById(selectList2.get(0));
							
							Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
							shangjishangjis.setMoney(shangjishangjishangjichoushui+"");
							shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							shangjishangjis.setAddTime(Hutool.parseDateToString());
							shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
							shangjishangjis.setUseridId(selectList2.get(0).getUserId());//上级用户id
							accountdetailsmapper.insert(shangjishangjis);
						}
					}
					EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					wrappers.notIn("ageny_class", "3,4,5");
					List<Agency> selectList3 = agencymapper.selectList(wrappers);
					 Double choushui = 0.0;
					 for (int i = 0; i < selectList3.size(); i++) {
						 choushui+=selectList3.get(i).getChoushuiBili();
					 }
					 Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
					 shangjishangjis.setMoney(yingjias+"");
					 shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 shangjishangjis.setAddTime(Hutool.parseDateToString());
					 shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
					 shangjishangjis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(shangjishangjis);
					 
					 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
					 qiangbao.setMoney(shengyumoney+"");
					 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qiangbao.setAddTime(Hutool.parseDateToString());
					 qiangbao.setSymbol(1);//加减符号    1加号，2减号
					 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qiangbao);
					
					Double pingtai = money-shangjishangjishangjichoushui-shangjishangji;
					Accountdetails pingtias = new Accountdetails();//自己拿自己的分红
					pingtias.setMoney(pingtai+"");
					pingtias.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					pingtias.setAddTime(Hutool.parseDateToString());
					pingtias.setSymbol(1);//加减符号    1加号，2减号
					accountdetailsmapper.insert(pingtias);
					
					
				}else {//没有上级代理
					 EntityWrapper<Agency> wrappers = new EntityWrapper<>();
					 wrappers.notIn("ageny_class", "3,4,5");
					 List<Agency> selectList3 = agencymapper.selectList(wrappers);
					 Double choushui = 0.0;
					 for (int i = 0; i < selectList3.size(); i++) {
						 choushui+=selectList3.get(i).getChoushuiBili();
					 }
					 Double yingjias = choushui*money;//用户和上级都是一级代理，抽水给自己
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+yingjias);
					 usersmapper.updateById(yingjia);
					 
					 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
					 usersmapper.updateById(yingjia);
					 
					 Accountdetails choushuis = new Accountdetails();//自己拿自己的分红
					 choushuis.setMoney(yingjias+"");
					 choushuis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 choushuis.setAddTime(Hutool.parseDateToString());
					 choushuis.setSymbol(1);//加减符号    1加号，2减号
					 choushuis.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(choushuis);
					 
					 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
					 qiangbao.setMoney(shengyumoney+"");
					 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 qiangbao.setAddTime(Hutool.parseDateToString());
					 qiangbao.setSymbol(1);//加减符号    1加号，2减号
					 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
					 accountdetailsmapper.insert(qiangbao);
					 
					 Double pingtais =money-yingjias;
					 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
					 pingtai.setMoney(pingtais+"");
					 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
					 pingtai.setAddTime(Hutool.parseDateToString());
					 pingtai.setSymbol(1);//加减符号    1加号，2减号
					 accountdetailsmapper.insert(pingtai);
				}
			 }else if(yingjia.getUserGrade().intValue()==4) {//上级可以拿自己抽水
				 //上级代理抽水
				 EntityWrapper<Agency> wrapper =new EntityWrapper<>();
				 wrapper.eq("choushui_bili", shangjiyonghu.getUserGrade());
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double zijishangji = selectList.get(0).getChoushuiBili()*money;
				 
				 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+zijishangji);
				 usersmapper.updateById(shangjiyonghu);
				 
				 Accountdetails shangji = new Accountdetails();//自己拿自己的分红
				 shangji.setMoney(zijishangji+"");
				 shangji.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 shangji.setAddTime(Hutool.parseDateToString());
				 shangji.setSymbol(1);//加减符号    1加号，2减号
				 shangji.setUseridId(shangjiyonghu.getUserId());//上级用户id
				 accountdetailsmapper.insert(shangji);
				 
				 
				 //上级的上级代理抽水
				 EntityWrapper<Users> wrtapper = new EntityWrapper<>();
				 wrtapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList2 = usersmapper.selectList(wrtapper);
				 Double shangjishangji = 0.0;
				 Double shangjishangjishangji=0.0;
				 if(selectList2.isEmpty()) {
					 if(selectList2.get(0).getUserGrade()<yingjia.getUserGrade()) {//上级的上级比自己等级高
						 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
						 wrappers.eq("choushui_bili", selectList2.get(0).getUserGrade());
						 List<Agency> selectLists = agencymapper.selectList(wrappers);
						 shangjishangji= selectLists.get(0).getChoushuiBili()*money;
						 
						 selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+shangjishangji);
						 usersmapper.updateById(selectList2.get(0));
						 
						 
						 Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
						 shangjishangjis.setMoney(shangjishangji+"");
						 shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjis.setAddTime(Hutool.parseDateToString());
						 shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjis.setUseridId(selectList2.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjis);
					 }
				 }
				 //上级的上级的上级代理抽水
				 EntityWrapper<Users> wrtapperss = new EntityWrapper<>();
				 wrtapperss.eq("user_invitation_code", selectList2.get(0).getTheHigherTheAgent());
				 List<Users> selectList2ss = usersmapper.selectList(wrtapperss);
				 if(selectList2ss.isEmpty()) {
					 if(selectList2ss.get(0).getUserGrade()<yingjia.getUserGrade()) {//上级的上级比自己等级高
						 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
						 wrappers.eq("choushui_bili", selectList2ss.get(0).getUserGrade());
						 List<Agency> selectLists = agencymapper.selectList(wrappers);
						 shangjishangjishangji= selectLists.get(0).getChoushuiBili()*money;
						 
						 selectList2ss.get(0).setUserRechargeIntegral(selectList2ss.get(0).getUserRechargeIntegral()+shangjishangjishangji);
						 usersmapper.updateById(selectList2ss.get(0));
						 
						 Accountdetails shangjishangjishangjiss = new Accountdetails();//自己拿自己的分红
						 shangjishangjishangjiss.setMoney(shangjishangjishangji+"");
						 shangjishangjishangjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjishangjiss.setAddTime(Hutool.parseDateToString());
						 shangjishangjishangjiss.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjishangjiss.setUseridId(selectList2ss.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjishangjiss);
					 }
				 }
				 EntityWrapper<Agency> ziji = new EntityWrapper<>();
				 ziji.notIn("ageny_class", "4,5");
				 List<Agency> zijis = agencymapper.selectList(ziji);
				 Double zijiss =0.0;  
				 for (int i = 0; i < zijis.size(); i++) {
					 zijiss+=zijis.get(i).getChoushuiBili();
				}
				 Double zijichoushui= zijiss*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
				 usersmapper.updateById(yingjia);
				 
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(zijichoushui+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 
				 
				 Double pingtai= money-zijichoushui-zijishangji-shangjishangji-shangjishangjishangji;
				 
				 Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
				 pingtais.setMoney(pingtai+"");
				 pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtais.setAddTime(Hutool.parseDateToString());
				 pingtais.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtais);
				 
				 
			 }else if(yingjia.getUserGrade().intValue()==5) {//上级可以拿自己抽水
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "3,4");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double shangji = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 shangji+=selectList.get(i).getChoushuiBili();
				}
				 Double shangjis= shangji*money;//上级应得抽水
				 
				 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjis);
				 usersmapper.updateById(shangjiyonghu);
				 
				 Accountdetails shangjiss = new Accountdetails();//自己拿自己的分红
				 shangjiss.setMoney(shangjis+"");
				 shangjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 shangjiss.setAddTime(Hutool.parseDateToString());
				 shangjiss.setSymbol(1);//加减符号    1加号，2减号
				 shangjiss.setUseridId(shangjiyonghu.getUserId());//上级用户id
				 accountdetailsmapper.insert(shangjiss);
				 
				 //上级的上级代理抽水
				 EntityWrapper<Users> wrtapper = new EntityWrapper<>();
				 wrtapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList2 = usersmapper.selectList(wrtapper);
				 Double shangjishangji = 0.0;
				 Double shangjishangjishangji=0.0;
				 if(selectList2.isEmpty()) {
					 if(selectList2.get(0).getUserGrade()<yingjia.getUserGrade()) {//上级的上级比自己等级高
						 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
						 wrappers.eq("choushui_bili", selectList2.get(0).getUserGrade());
						 List<Agency> selectLists = agencymapper.selectList(wrappers);
						 shangjishangji= selectLists.get(0).getChoushuiBili()*money;
						 
						 selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+shangjishangji);
						 usersmapper.updateById(selectList2.get(0));
						 
						 Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
						 shangjishangjis.setMoney(shangjishangji+"");
						 shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjis.setAddTime(Hutool.parseDateToString());
						 shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjis.setUseridId(selectList2.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjis);
					 }
				 }
				 //上级的上级的上级代理抽水
				 EntityWrapper<Users> wrtapperss = new EntityWrapper<>();
				 wrtapperss.eq("user_invitation_code", selectList2.get(0).getTheHigherTheAgent());
				 List<Users> selectList2ss = usersmapper.selectList(wrtapperss);
				 if(selectList2ss.isEmpty()) {
					 if(selectList2ss.get(0).getUserGrade()<yingjia.getUserGrade()) {//上级的上级比自己等级高
						 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
						 wrappers.eq("choushui_bili", selectList2ss.get(0).getUserGrade());
						 List<Agency> selectLists = agencymapper.selectList(wrappers);
						 shangjishangjishangji= selectLists.get(0).getChoushuiBili()*money;
						 
						 selectList2ss.get(0).setUserRechargeIntegral(selectList2ss.get(0).getUserRechargeIntegral()+shangjishangjishangji);
						 usersmapper.updateById(selectList2ss.get(0));
						 
						 Accountdetails shangjishangjishangjiss = new Accountdetails();//自己拿自己的分红
						 shangjishangjishangjiss.setMoney(shangjishangjishangji+"");
						 shangjishangjishangjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjishangjiss.setAddTime(Hutool.parseDateToString());
						 shangjishangjishangjiss.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjishangjiss.setUseridId(selectList2ss.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjishangjiss);
					 }
				 }
				 
				 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
				 wrappers.eq("choushui_bili", yingjia.getUserGrade());
				 List<Agency> selectLists = agencymapper.selectList(wrappers);
				 
				 Double zijichoushui =selectLists.get(0).getChoushuiBili()*money;
				
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijichoushui);
				 usersmapper.updateById(yingjia);//自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己抢包赢得
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(zijichoushui+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-zijichoushui-shangjishangjishangji-shangjishangji-shangjis;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
				 
			 }else if(yingjia.getUserGrade().intValue()==6) {//上级可以拿自己抽水
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "3,4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double shangji = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 shangji+=selectList.get(i).getChoushuiBili();
				}
				 Double shangjis= shangji*money;//上级应得抽水
				 
				 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjis);
				 usersmapper.updateById(shangjiyonghu);
				 
				 Accountdetails shangjiss = new Accountdetails();//自己拿自己的分红
				 shangjiss.setMoney(shangjis+"");
				 shangjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 shangjiss.setAddTime(Hutool.parseDateToString());
				 shangjiss.setSymbol(1);//加减符号    1加号，2减号
				 shangjiss.setUseridId(shangjiyonghu.getUserId());//上级用户id
				 accountdetailsmapper.insert(shangjiss);
				 
				 //上级的上级代理抽水
				 EntityWrapper<Users> wrtapper = new EntityWrapper<>();
				 wrtapper.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList2 = usersmapper.selectList(wrtapper);
				 Double shangjishangji = 0.0;
				 Double shangjishangjishangji=0.0;
				 if(selectList2.isEmpty()) {
					 if(selectList2.get(0).getUserGrade()<shangjiyonghu.getUserGrade()) {//上级的上级比自己等级高
						 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
						 wrappers.eq("choushui_bili", selectList2.get(0).getUserGrade());
						 List<Agency> selectLists = agencymapper.selectList(wrappers);
						 shangjishangji= selectLists.get(0).getChoushuiBili()*money;
						 
						 selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+shangjishangji);
						 usersmapper.updateById(selectList2.get(0));
						 
						 Accountdetails shangjishangjis = new Accountdetails();//自己拿自己的分红
						 shangjishangjis.setMoney(shangjishangji+"");
						 shangjishangjis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjis.setAddTime(Hutool.parseDateToString());
						 shangjishangjis.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjis.setUseridId(selectList2.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjis);
					 }
				 }
				 //上级的上级的上级代理抽水
				 EntityWrapper<Users> wrtapperss = new EntityWrapper<>();
				 wrtapperss.eq("user_invitation_code", selectList2.get(0).getTheHigherTheAgent());
				 List<Users> selectList2ss = usersmapper.selectList(wrtapperss);
				 if(selectList2ss.isEmpty()) {
					 if(selectList2ss.get(0).getUserGrade()<selectList2.get(0).getUserGrade()) {//上级的上级比自己等级高
						 EntityWrapper<Agency> wrappers =new EntityWrapper<>();
						 wrappers.eq("choushui_bili", selectList2ss.get(0).getUserGrade());
						 List<Agency> selectLists = agencymapper.selectList(wrappers);
						 shangjishangjishangji= selectLists.get(0).getChoushuiBili()*money;
						 
						 selectList2ss.get(0).setUserRechargeIntegral(selectList2ss.get(0).getUserRechargeIntegral()+shangjishangjishangji);
						 usersmapper.updateById(selectList2ss.get(0));
						 
						 Accountdetails shangjishangjishangjiss = new Accountdetails();//自己拿自己的分红
						 shangjishangjishangjiss.setMoney(shangjishangjishangji+"");
						 shangjishangjishangjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 shangjishangjishangjiss.setAddTime(Hutool.parseDateToString());
						 shangjishangjishangjiss.setSymbol(1);//加减符号    1加号，2减号
						 shangjishangjishangjiss.setUseridId(selectList2ss.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(shangjishangjishangjiss);
					 }
				 }
				
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己抢包赢得
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-shangjishangjishangji-shangjishangji-shangjis;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }
		 }else if(shangjiyonghu.getUserGrade().intValue()==4) {//上级是四级代理时,判断有没有上级代理
			 if(yingjia.getUserGrade().intValue()==1) {//用户一级代理
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
				 
			 }else if(yingjia.getUserGrade().intValue()==2){//用户二级代理
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "2,3,4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==3){//用户三级代理
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "3,4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==4){//用户四级代理    不分红上级代理
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==5){//用户五级代理 上级是四级代理  可以拿自己的代理
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.eq("ageny_class", shangjiyonghu.getUserGrade());
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double shangjia = selectList.get(0).getChoushuiBili()*money;//上级代理
				 
				 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjia);
				 usersmapper.updateById(shangjiyonghu);//上级
				 
				 Accountdetails shangji = new Accountdetails();//自己拿自己的分红
				 shangji.setMoney(shangjia+"");
				 shangji.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 shangji.setAddTime(Hutool.parseDateToString());
				 shangji.setSymbol(1);//加减符号    1加号，2减号
				 shangji.setUseridId(shangjiyonghu.getUserId());//上级用户id
				 accountdetailsmapper.insert(shangji);
				 
				 EntityWrapper<Users> wraooer = new EntityWrapper<>();//三级
				 wraooer.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList2 = usersmapper.selectList(wraooer);
				 Double sanjis= 0.0;
				 Double erjis = 0.0;
				 Double yiges = 0.0;
				 if(!selectList2.isEmpty()) {
					 if(shangjiyonghu.getUserGrade()>selectList2.get(0).getUserGrade()) {
						 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
						 shangjishangji.eq("ageny_class", selectList2.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
						 sanjis= selectList3.get(0).getChoushuiBili()*money;//三级抽水总额
						 
						 selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+sanjis);
						 usersmapper.updateById(selectList2.get(0));//上级
						 
						 Accountdetails sanji = new Accountdetails();//自己拿自己的分红
						 sanji.setMoney(sanjis+"");
						 sanji.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 sanji.setAddTime(Hutool.parseDateToString());
						 sanji.setSymbol(1);//加减符号    1加号，2减号
						 sanji.setUseridId(selectList2.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(sanji);
						 
					 }
					 EntityWrapper<Users> erji = new EntityWrapper<>();//二级
					 erji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
					 List<Users> erjilist = usersmapper.selectList(erji);
					 if(!erjilist.isEmpty()) {
						 if(selectList2.get(0).getUserGrade()>erjilist.get(0).getUserGrade()) {
							 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
							 shangjishangji.eq("ageny_class", erjilist.get(0).getUserGrade());
							 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
							 erjis= selectList3.get(0).getChoushuiBili()*money;//二级级抽水总额
							 
							 erjilist.get(0).setUserRechargeIntegral(erjilist.get(0).getUserRechargeIntegral()+erjis);
							 usersmapper.updateById(erjilist.get(0));//上级
							 
							 Accountdetails erjiss = new Accountdetails();//自己拿自己的分红
							 erjiss.setMoney(erjis+"");
							 erjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							 erjiss.setAddTime(Hutool.parseDateToString());
							 erjiss.setSymbol(1);//加减符号    1加号，2减号
							 erjiss.setUseridId(erjilist.get(0).getUserId());//上级用户id
							 accountdetailsmapper.insert(erjiss);
							 
						 }
						 EntityWrapper<Users> yiji = new EntityWrapper<>();//一级
						 yiji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
						 List<Users> yijilisy = usersmapper.selectList(yiji);
						 if(!selectList2.isEmpty()) {
							 if(erjilist.get(0).getUserGrade()>yijilisy.get(0).getUserGrade()) {
								 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
								 shangjishangji.eq("ageny_class", yijilisy.get(0).getUserGrade());
								 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
								 yiges= selectList3.get(0).getChoushuiBili()*money;//一级抽水总额
								 
								 yijilisy.get(0).setUserRechargeIntegral(yijilisy.get(0).getUserRechargeIntegral()+yiges);
								 usersmapper.updateById(yijilisy.get(0));//上级
								 
								 Accountdetails yijis = new Accountdetails();//自己拿自己的分红
								 yijis.setMoney(yiges+"");
								 yijis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
								 yijis.setAddTime(Hutool.parseDateToString());
								 yijis.setSymbol(1);//加减符号    1加号，2减号
								 yijis.setUseridId(yijilisy.get(0).getUserId());//上级用户id
								 accountdetailsmapper.insert(yijis);
								 
							 }
						 }
					 }
				 }
				 EntityWrapper<Agency> ziji = new EntityWrapper<>();
				 ziji.eq("ageny_class", 5);
				 List<Agency> zijilist = agencymapper.selectList(ziji);
				 Double zijimoney = zijilist.get(0).getChoushuiBili()*money;//自己是五级代理  自己拿自己的代理抽水
				 
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+zijimoney);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtai = money-shangjia-sanjis-erjis-yiges;//平台所得
				 
				 Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
				 pingtais.setMoney(pingtai+"");
				 pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtais.setAddTime(Hutool.parseDateToString());
				 pingtais.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtais);
				 
				 
			 }else if(yingjia.getUserGrade().intValue()==6){
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "5,6");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double shangjibli= 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 shangjibli+=selectList.get(i).getChoushuiBili();
				}
				 Double shangjias = shangjibli*money;//上级代理
				 
				 shangjiyonghu.setUserRechargeIntegral(shangjiyonghu.getUserRechargeIntegral()+shangjias);
				 usersmapper.updateById(shangjiyonghu);//上级
				 
				 Accountdetails shangji = new Accountdetails();//自己拿自己的分红
				 shangji.setMoney(shangjias+"");
				 shangji.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 shangji.setAddTime(Hutool.parseDateToString());
				 shangji.setSymbol(1);//加减符号    1加号，2减号
				 shangji.setUseridId(shangjiyonghu.getUserId());//上级用户id
				 accountdetailsmapper.insert(shangji);
				 
				 EntityWrapper<Users> wraooer = new EntityWrapper<>();//三级
				 wraooer.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList2 = usersmapper.selectList(wraooer);
				 Double sanjis= 0.0;
				 Double erjis = 0.0;
				 Double yiges = 0.0;
				 if(!selectList2.isEmpty()) {
					 if(shangjiyonghu.getUserGrade()>selectList2.get(0).getUserGrade()) {
						 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
						 shangjishangji.eq("ageny_class", selectList2.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
						 sanjis= selectList3.get(0).getChoushuiBili()*money;//三级抽水总额
						 
						 selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+sanjis);
						 usersmapper.updateById(selectList2.get(0));//上级
						 
						 Accountdetails sanji = new Accountdetails();//自己拿自己的分红
						 sanji.setMoney(sanjis+"");
						 sanji.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 sanji.setAddTime(Hutool.parseDateToString());
						 sanji.setSymbol(1);//加减符号    1加号，2减号
						 sanji.setUseridId(selectList2.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(sanji);
						 
					 }
					 EntityWrapper<Users> erji = new EntityWrapper<>();//二级
					 erji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
					 List<Users> erjilist = usersmapper.selectList(erji);
					 if(!erjilist.isEmpty()) {
						 if(selectList2.get(0).getUserGrade()>erjilist.get(0).getUserGrade()) {
							 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
							 shangjishangji.eq("ageny_class", erjilist.get(0).getUserGrade());
							 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
							 erjis= selectList3.get(0).getChoushuiBili()*money;//二级级抽水总额
							 
							 erjilist.get(0).setUserRechargeIntegral(erjilist.get(0).getUserRechargeIntegral()+erjis);
							 usersmapper.updateById(erjilist.get(0));//上级
							 
							 Accountdetails erjiss = new Accountdetails();//自己拿自己的分红
							 erjiss.setMoney(erjis+"");
							 erjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							 erjiss.setAddTime(Hutool.parseDateToString());
							 erjiss.setSymbol(1);//加减符号    1加号，2减号
							 erjiss.setUseridId(erjilist.get(0).getUserId());//上级用户id
							 accountdetailsmapper.insert(erjiss);
							 
						 }
						 EntityWrapper<Users> yiji = new EntityWrapper<>();//一级
						 yiji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
						 List<Users> yijilisy = usersmapper.selectList(yiji);
						 if(!selectList2.isEmpty()) {
							 if(erjilist.get(0).getUserGrade()>yijilisy.get(0).getUserGrade()) {
								 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
								 shangjishangji.eq("ageny_class", yijilisy.get(0).getUserGrade());
								 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
								 yiges= selectList3.get(0).getChoushuiBili()*money;//一级抽水总额
								 
								 yijilisy.get(0).setUserRechargeIntegral(yijilisy.get(0).getUserRechargeIntegral()+yiges);
								 usersmapper.updateById(yijilisy.get(0));//上级
								 
								 Accountdetails yijis = new Accountdetails();//自己拿自己的分红
								 yijis.setMoney(yiges+"");
								 yijis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
								 yijis.setAddTime(Hutool.parseDateToString());
								 yijis.setSymbol(1);//加减符号    1加号，2减号
								 yijis.setUseridId(yijilisy.get(0).getUserId());//上级用户id
								 accountdetailsmapper.insert(yijis);
								 
							 }
						 }
					 }
				 }
				 
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtai = money-shangjias-sanjis-erjis-yiges;//平台所得
				 
				 Accountdetails pingtais = new Accountdetails();//自己拿自己的分红
				 pingtais.setMoney(pingtai+"");
				 pingtais.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtais.setAddTime(Hutool.parseDateToString());
				 pingtais.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtais);
			 }
		 }else if(shangjiyonghu.getUserGrade().intValue()==5) {//上级是五级代理时,判断有没有上级代理
			 if(yingjia.getUserGrade().intValue()==1) {
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==2) {
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "2,3,4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==3) {
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "3,4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==4) {
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "3,4,5");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 Double pingtaichoushui = money-ziji;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushui+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==5) {
				 EntityWrapper<Agency> wrapper = new EntityWrapper<>();
				 wrapper.notIn("ageny_class", "5,6");
				 List<Agency> selectList = agencymapper.selectList(wrapper);
				 Double choushui = 0.0;
				 for (int i = 0; i < selectList.size(); i++) {
					 choushui+=selectList.get(i).getChoushuiBili();
				 }
				 Double ziji = choushui*money;
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+ziji);
				 usersmapper.updateById(yingjia);//自己拿自己分红
				 yingjia.setUserRechargeIntegral(yingjia.getUserRechargeIntegral()+shengyumoney);
				 usersmapper.updateById(yingjia);//自己拿自己抢的包
				 
				 Accountdetails zijifenhong = new Accountdetails();//自己拿自己的分红
				 zijifenhong.setMoney(ziji+"");
				 zijifenhong.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 zijifenhong.setAddTime(Hutool.parseDateToString());
				 zijifenhong.setSymbol(1);//加减符号    1加号，2减号
				 zijifenhong.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(zijifenhong);
				 
				 Accountdetails qiangbao = new Accountdetails();//自己拿自己的分红
				 qiangbao.setMoney(shengyumoney+"");
				 qiangbao.setType(5);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 qiangbao.setAddTime(Hutool.parseDateToString());
				 qiangbao.setSymbol(1);//加减符号    1加号，2减号
				 qiangbao.setUseridId(yingjia.getUserId());//上级用户id
				 accountdetailsmapper.insert(qiangbao);
				 
				 EntityWrapper<Users> wraooer = new EntityWrapper<>();//四级
				 wraooer.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> selectList2 = usersmapper.selectList(wraooer);
				 Double sanjis= 0.0;
				 Double erjis = 0.0;
				 Double yiges = 0.0;
				 Double yijishangj = 0.0;
				 if(!selectList2.isEmpty()) {
					 if(shangjiyonghu.getUserGrade()>selectList2.get(0).getUserGrade()) {
						 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
						 shangjishangji.eq("ageny_class", selectList2.get(0).getUserGrade());
						 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
						 sanjis= selectList3.get(0).getChoushuiBili()*money;//三级抽水总额
						 
						 selectList2.get(0).setUserRechargeIntegral(selectList2.get(0).getUserRechargeIntegral()+sanjis);
						 usersmapper.updateById(selectList2.get(0));//上级
						 
						 Accountdetails sanji = new Accountdetails();//自己拿自己的分红
						 sanji.setMoney(sanjis+"");
						 sanji.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
						 sanji.setAddTime(Hutool.parseDateToString());
						 sanji.setSymbol(1);//加减符号    1加号，2减号
						 sanji.setUseridId(selectList2.get(0).getUserId());//上级用户id
						 accountdetailsmapper.insert(sanji);
						 
					 }
					 EntityWrapper<Users> erji = new EntityWrapper<>();//三级
					 erji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
					 List<Users> erjilist = usersmapper.selectList(erji);
					 if(!erjilist.isEmpty()) {
						 if(selectList2.get(0).getUserGrade()>erjilist.get(0).getUserGrade()) {
							 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
							 shangjishangji.eq("ageny_class", erjilist.get(0).getUserGrade());
							 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
							 erjis= selectList3.get(0).getChoushuiBili()*money;//二级级抽水总额
							 
							 erjilist.get(0).setUserRechargeIntegral(erjilist.get(0).getUserRechargeIntegral()+erjis);
							 usersmapper.updateById(erjilist.get(0));//上级
							 
							 Accountdetails erjiss = new Accountdetails();//自己拿自己的分红
							 erjiss.setMoney(erjis+"");
							 erjiss.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
							 erjiss.setAddTime(Hutool.parseDateToString());
							 erjiss.setSymbol(1);//加减符号    1加号，2减号
							 erjiss.setUseridId(erjilist.get(0).getUserId());//上级用户id
							 accountdetailsmapper.insert(erjiss);
							 
						 }
						 EntityWrapper<Users> yiji = new EntityWrapper<>();//二级
						 yiji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
						 List<Users> yijilisy = usersmapper.selectList(yiji);
						 if(!selectList2.isEmpty()) {
							 if(erjilist.get(0).getUserGrade()>yijilisy.get(0).getUserGrade()) {
								 EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
								 shangjishangji.eq("ageny_class", yijilisy.get(0).getUserGrade());
								 List<Agency> selectList3 = agencymapper.selectList(shangjishangji);
								 yiges= selectList3.get(0).getChoushuiBili()*money;//一级抽水总额
								 
								 yijilisy.get(0).setUserRechargeIntegral(yijilisy.get(0).getUserRechargeIntegral()+yiges);
								 usersmapper.updateById(yijilisy.get(0));//上级
								 
								 Accountdetails yijis = new Accountdetails();//自己拿自己的分红
								 yijis.setMoney(yiges+"");
								 yijis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
								 yijis.setAddTime(Hutool.parseDateToString());
								 yijis.setSymbol(1);//加减符号    1加号，2减号
								 yijis.setUseridId(yijilisy.get(0).getUserId());//上级用户id
								 accountdetailsmapper.insert(yijis);
								 
							 }
							 EntityWrapper<Users> yijishangji = new EntityWrapper<>();//一级
							 yijishangji.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
							 List<Users> yijishangjis = usersmapper.selectList(yijishangji);
							 if(!yijishangjis.isEmpty()) {
								 if(erjilist.get(0).getUserGrade()>yijishangjis.get(0).getUserGrade()) {
									 EntityWrapper<Agency> yijishangjiss = new EntityWrapper<>();
									 yijishangjiss.eq("ageny_class", yijishangjis.get(0).getUserGrade());
									 List<Agency> selectList3 = agencymapper.selectList(yijishangjiss);
									 yijishangj= selectList3.get(0).getChoushuiBili()*money;//一级抽水总额
									 
									 yijishangjis.get(0).setUserRechargeIntegral(yijishangjis.get(0).getUserRechargeIntegral()+yijishangj);
									 usersmapper.updateById(yijishangjis.get(0));//上级
									 
									 Accountdetails yijis = new Accountdetails();//自己拿自己的分红
									 yijis.setMoney(yijishangj+"");
									 yijis.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
									 yijis.setAddTime(Hutool.parseDateToString());
									 yijis.setSymbol(1);//加减符号    1加号，2减号
									 yijis.setUseridId(yijishangjis.get(0).getUserId());//上级用户id
									 accountdetailsmapper.insert(yijis);
								 }
						 }
					 }
				 }
				 Double pingtaichoushuis = money-yijishangj-sanjis-erjis-yiges;
				 Accountdetails pingtai = new Accountdetails();//自己拿自己的分红
				 pingtai.setMoney(pingtaichoushuis+"");
				 pingtai.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
				 pingtai.setAddTime(Hutool.parseDateToString());
				 pingtai.setSymbol(1);//加减符号    1加号，2减号
				 accountdetailsmapper.insert(pingtai);
			 }else if(yingjia.getUserGrade().intValue()==6) {
				 EntityWrapper<Agency> shangj = new EntityWrapper<>();
				 shangj.eq("ageny_class", shangjiyonghu.getUserGrade());
				 List<Agency> shangjilist = agencymapper.selectList(shangj);
				 
				 Double shangjichoushui = shangjilist.get(0).getChoushuiBili()*money;//用户上级抽数金额   五级
				 
				 EntityWrapper<Users> sijiuser = new EntityWrapper<>();
				 sijiuser.eq("user_invitation_code", shangjiyonghu.getTheHigherTheAgent());
				 List<Users> sijilist = usersmapper.selectList(sijiuser);
				 
				 if(!sijilist.isEmpty()) {
					 if(sijilist.get(0).getUserGrade()<shangjiyonghu.getUserGrade()) {
						 EntityWrapper<Agencys> sijiwrapper = new EntityWrapper<>();
//						 sijiwrapper
					 }
					
				 }
				 
			 }
		 }
		 
		 }
		 
	}
}
		 
		 
		 
		 
		 
		 
		 
		 
//		switch (selectById.getUserGrade()) {
//		case 1://上级是一级代理
//			if(yingjia.getUserGrade().intValue()==6) {
//				EntityWrapper<Agency> wrapper = new EntityWrapper<>();
//				List<Agency> selectList = agencymapper.selectList(wrapper);
//				double bili = 0.0;
//				for (int i = 0; i < selectList.size(); i++) {
//					bili+=selectList.get(i).getChoushuiBili();
//				}
//				Double shangji= bili*money;//上级是一级代理，上级应得百分比
//				Double pingtai = money-shangji;//平台应得金额
//				
//				Users shangjiuser = usersmapper.selectById(userid);
//				Double shangjis = shangjiuser.getUserRechargeIntegral()+shangji;
//				shangjiuser.setUserRechargeIntegral(shangjis);
//				usersmapper.updateById(shangjiuser);//上级应得金额
//				
//				Accountdetails shangjichoushui = new Accountdetails();
//				shangjichoushui.setMoney(shangji+"");
//				shangjichoushui.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//				shangjichoushui.setAddTime(Hutool.parseDateToString());
//				shangjichoushui.setSymbol(1);//加减符号    1加号，2减号
//				shangjichoushui.setUseridId(shangjiuser.getUserId());//上级用户id
//				accountdetailsmapper.insert(shangjichoushui);
//				
//				Accountdetails pingtaichoushui = new Accountdetails();
//				pingtaichoushui.setMoney(pingtai+"");
//				pingtaichoushui.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//				pingtaichoushui.setAddTime(Hutool.parseDateToString());
//				pingtaichoushui.setSymbol(1);//加减符号    1加号，2减号
//				accountdetailsmapper.insert(pingtaichoushui);
//				
//				Double fangzhus = yingjia.getUserRechargeIntegral()+shengyumoney;
//				yingjia.setUserRechargeIntegral(fangzhus);
//				usersmapper.updateById(yingjia);//赢家应得
//				
//				System.out.println("拿所有人代理------------------------百分之四十五");
//			}if(yingjia.getUserGrade().intValue()==2) {
//				
//			}
//			
//			break;
//		case 2://上级是二级代理  判断是否有上级代理
//			EntityWrapper<Users> wrapper2 = new EntityWrapper<>();
//			wrapper2.eq("user_invitation_code", selectById.getTheHigherTheAgent());
//			List<Users> selectList2 = usersmapper.selectList(wrapper2);
//			if(!selectList2.isEmpty()) {//自己上级代理还有上级代理
//				
//				if(selectList2.get(0).getUserGrade()<selectById.getUserGrade()) {//上级用户是一级代理
//					EntityWrapper<Agency> shangjishangji = new EntityWrapper<>();
//					shangjishangji.eq("ageny_class", selectList2.get(0).getUserGrade());
//					List<Agency> choushui = agencymapper.selectList(shangjishangji);
//					
//					Double shangjishangjis = money*choushui.get(0).getChoushuiBili();//上级的上级应得金额
//					
//					//用户上级的上级应得金额
//					Double shangjishangjiss = selectList2.get(0).getUserRechargeIntegral()+shangjishangjis;
//					selectList2.get(0).setUserRechargeIntegral(shangjishangjiss);
//					usersmapper.updateById(selectList2.get(0));
//					
//					Accountdetails shangjichoushui1 = new Accountdetails();
//					shangjichoushui1.setMoney(shangjishangjis+"");
//					shangjichoushui1.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//					shangjichoushui1.setAddTime(Hutool.parseDateToString());
//					shangjichoushui1.setSymbol(1);//加减符号    1加号，2减号
//					shangjichoushui1.setUseridId(selectList2.get(0).getUserId());//上级用户id
//					accountdetailsmapper.insert(shangjichoushui1);
//					
//					
//					EntityWrapper<Agency> wrappers = new EntityWrapper<>();
//					wrappers.notIn("ageny_class", "1");
//					List<Agency> selectList3 = agencymapper.selectList(wrappers);
//					double shengyu = 0.0;
//					for (int i = 0; i < selectList3.size(); i++) {
//						shengyu+=selectList3.get(i).getChoushuiBili();
//					}
//					//用户上级应得金额
//					Double shangyus =  money*shengyu;//剩余积分
//					//用户自己上级应得金额
//					Double shangji1= selectById.getUserRechargeIntegral()+shangyus;//上级应得
//					selectById.setUserRechargeIntegral(shangji1);
//					usersmapper.updateById(selectById);
//					Accountdetails shangjichoushui2 = new Accountdetails();
//					shangjichoushui2.setMoney(shangyus+"");
//					shangjichoushui2.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//					shangjichoushui2.setAddTime(Hutool.parseDateToString());
//					shangjichoushui2.setSymbol(1);//加减符号    1加号，2减号
//					shangjichoushui2.setUseridId(selectById.getUserId());//上级用户id
//					accountdetailsmapper.insert(shangjichoushui2);
//					
//					//剩余分红是平台应得
//					Double pingtai2= money-shangyus-shangjishangjis;
//					Accountdetails pingtaichoushui3 = new Accountdetails();
//					pingtaichoushui3.setMoney(pingtai2+"");
//					pingtaichoushui3.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//					pingtaichoushui3.setAddTime(Hutool.parseDateToString());
//					pingtaichoushui3.setSymbol(1);//加减符号    1加号，2减号
//					accountdetailsmapper.insert(pingtaichoushui3);
//				}else {
//					//上级的上级代理，没有上级代理等级高
//					EntityWrapper<Agency> wrappers = new EntityWrapper<>();
//					wrappers.notIn("ageny_class", "1");
//					List<Agency> selectList3 = agencymapper.selectList(wrappers);
//					double shengyu = 0.0;
//					for (int i = 0; i < selectList3.size(); i++) {
//						shengyu+=selectList3.get(i).getChoushuiBili();
//					}
//					//用户上级应得金额
//					Double shangyus =  money*shengyu;//剩余积分
//					//用户自己上级应得金额
//					Double shangji1= selectById.getUserRechargeIntegral()+shangyus;//上级应得
//					selectById.setUserRechargeIntegral(shangji1);
//					usersmapper.updateById(selectById);
//					Accountdetails shangjichoushui2 = new Accountdetails();
//					shangjichoushui2.setMoney(shangyus+"");
//					shangjichoushui2.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//					shangjichoushui2.setAddTime(Hutool.parseDateToString());
//					shangjichoushui2.setSymbol(1);//加减符号    1加号，2减号
//					shangjichoushui2.setUseridId(selectById.getUserId());//上级用户id
//					accountdetailsmapper.insert(shangjichoushui2);
//					
//					//剩余分红是平台应得
//					Double pingtai2= money-shangyus;
//					Accountdetails pingtaichoushui3 = new Accountdetails();
//					pingtaichoushui3.setMoney(pingtai2+"");
//					pingtaichoushui3.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//					pingtaichoushui3.setAddTime(Hutool.parseDateToString());
//					pingtaichoushui3.setSymbol(1);//加减符号    1加号，2减号
//					accountdetailsmapper.insert(pingtaichoushui3);
//				}
//			}else {
//				EntityWrapper<Agency> wrappers = new EntityWrapper<>();
//				wrappers.notIn("ageny_class", "1");
//				List<Agency> selectList3 = agencymapper.selectList(wrappers);
//				double shengyu = 0.0;
//				for (int i = 0; i < selectList3.size(); i++) {
//					shengyu+=selectList3.get(i).getChoushuiBili();
//				}
//				//用户上级应得金额
//				Double shangyus =  money*shengyu;//剩余积分
//				//用户自己上级应得金额
//				Double shangji1= selectById.getUserRechargeIntegral()+shangyus;//上级应得
//				selectById.setUserRechargeIntegral(shangji1);
//				usersmapper.updateById(selectById);
//				Accountdetails shangjichoushui2 = new Accountdetails();
//				shangjichoushui2.setMoney(shangyus+"");
//				shangjichoushui2.setType(6);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//				shangjichoushui2.setAddTime(Hutool.parseDateToString());
//				shangjichoushui2.setSymbol(1);//加减符号    1加号，2减号
//				shangjichoushui2.setUseridId(selectById.getUserId());//上级用户id
//				accountdetailsmapper.insert(shangjichoushui2);
//				
//				//剩余分红是平台应得
//				Double pingtai2= money-shangyus;
//				Accountdetails pingtaichoushui3 = new Accountdetails();
//				pingtaichoushui3.setMoney(pingtai2+"");
//				pingtaichoushui3.setType(7);//1上分，2下分，3发红包,4红包扣除，5抢红包 6分红 7平台
//				pingtaichoushui3.setAddTime(Hutool.parseDateToString());
//				pingtaichoushui3.setSymbol(1);//加减符号    1加号，2减号
//				accountdetailsmapper.insert(pingtaichoushui3);
//			}
//			System.out.println("拿2345级别代理------------------------百分之四十");
//			break;
//		case 3://用户是三级代理
//			EntityWrapper<Users> wrapper3 = new EntityWrapper<>();
//			wrapper3.eq("user_invitation_code", selectById.getTheHigherTheAgent());
//			List<Users> selectList3 = usersmapper.selectList(wrapper3);
//			if(!selectList3.isEmpty()) {//自己上级代理还有上级代理
//				EntityWrapper<Users> wrapper4 = new EntityWrapper<>();
//				wrapper4.eq("user_invitation_code", selectList3.get(0).getTheHigherTheAgent());
//				List<Users> selectList4 = usersmapper.selectList(wrapper3);
//				if(!selectList4.isEmpty()){//二级代理还有上级
//					if(selectList4.get(0).getUserGrade()<selectList3.get(0).getUserGrade()) {//上级的上级比上级代理等级高
//						EntityWrapper<Agency> wrappers = new EntityWrapper<>();
//						wrappers.eq("choushui_bili", selectList4.get(0).getUserGrade());
//						List<Agency> selectList5 = agencymapper.selectList(wrappers);
//						Double yiji = selectList5.get(0).getChoushuiBili()*money;//一级代理 应得金额
//						selectList4.get(0).setUserRechargeIntegral(selectList4.get(0).getUserRechargeIntegral()+yiji);
//						usersmapper.updateById(selectList4.get(0));
//						
//						EntityWrapper<Agency> wrappers2 = new EntityWrapper<>();
//						wrappers2.eq("choushui_bili", selectList3.get(0).getUserGrade());
//						List<Agency> selectList6 = agencymapper.selectList(wrappers2);
//						Double erji = selectList6.get(0).getChoushuiBili()*money;//二级代理 应得金额
//						selectList3.get(0).setUserRechargeIntegral(selectList3.get(0).getUserRechargeIntegral()+erji);
//						usersmapper.updateById(selectList3.get(0));
//						
//						EntityWrapper<Agency> wrappe = new EntityWrapper<>();
//						wrappe.eq("choushui_bili", selectById.getUserGrade());
//						List<Agency> selectList7 = agencymapper.selectList(wrappe);
//						Double sanji = selectList7.get(0).getChoushuiBili()*money;//三级代理 应得金额
//						selectById.setUserRechargeIntegral(selectById.getUserRechargeIntegral()+sanji);
//						usersmapper.updateById(selectById);
//						
//						Double pingtais = money-yiji-erji-sanji;
//						
//						
//						
//					}
//				}else {//二级代理没有上级
//					
//				}
//			}else {//二级代理没有上级
//				
//			}
//			
//			System.out.println("拿345级别代------------------------理百分之三十五");
//			break;
//		case 4:
//			System.out.println("拿45级别代理------------------------百分之三十");
//			break;
//		case 5:
//			System.out.println("拿5级别代理------------------------百分之二十五");
//			break;
//		case 6:
//			System.out.println("拿2345级别代理自己不是代理，------------------------平台拿百分之一的代理水费");
//			break;
//		default:
//			break;
//		}
//	}
//}
