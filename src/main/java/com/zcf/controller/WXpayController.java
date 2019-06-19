//package com.zcf.controller;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.jdom2.JDOMException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.alipay.api.internal.util.StringUtils;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.zcf.common.json.Body;
//import com.zcf.common.utils.DateUtils;
//import com.zcf.common.utils.Hutool;
//import com.zcf.mapper.ForumMapper;
//import com.zcf.mapper.LogMapper;
//import com.zcf.mapper.OrdersMapper;
//import com.zcf.mapper.PreorderMapper;
//import com.zcf.mapper.ProductMapper;
//import com.zcf.mapper.UserMapper;
//import com.zcf.mapper.ZuheMapper;
//import com.zcf.pay.WXpay.ConstantUtil;
//import com.zcf.pay.WXpay.Md5Util;
//import com.zcf.pay.WXpay.PrepayIdRequestHandler;
//import com.zcf.pay.WXpay.WXUtil;
//import com.zcf.pay.WXpay.WxNotifyParam;
//import com.zcf.pay.WXpay.XMLUtil;
//import com.zcf.pojo.Code_order;
//import com.zcf.pojo.Forum;
//import com.zcf.pojo.Log;
//import com.zcf.pojo.Orders;
//import com.zcf.pojo.Preorder;
//import com.zcf.pojo.Product;
//import com.zcf.pojo.User;
//import com.zcf.pojo.Zuhe;
//import com.zcf.serviceIMP.Code_orderServiceImpl;
//
//@Controller
//@RequestMapping("/weixin")
//public class WXpayController {
//	@Autowired
//	OrdersMapper ordersMapper;
//	@Autowired
//	UserMapper userMapper;
//	@Autowired
//	LogMapper logMapper;
//	@Autowired
//	Code_orderServiceImpl code_orderServiceImpl;
//	@Autowired
//	ProductMapper productMapper;
//	@Autowired
//	PreorderMapper preorderMapper;
//	@Autowired
//	ZuheMapper zuheMapper;
//	@Autowired
//	ForumMapper forumMapper;
//	private static final Logger logger = LoggerFactory
//			.getLogger(WXpayController.class);
//
//	/**
//	 * 初始化微信支付
//	 *
//	 * // * @param request // * @param response // * @param orderId // * @return
//	 */
//	// @RequestMapping("/pay/wx")
//	// @ResponseBody
//	public WxNotifyParam initWx(HttpServletRequest request,
//			HttpServletResponse response, Orders order) {
//		// Map<String, Object> map = new HashMap<String, Object>();
//		// 获取生成预支付订单的请求类
//		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(
//				request, response);
//		String totalFee = String.valueOf((int) (order.getPrice() * 100));// 微信支付是
//																			// 分--
//		// 上线后，将此代码放开
//		prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
//		prepayReqHandler.setParameter("body", ConstantUtil.BODY);
//		prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
//		String nonce_str = WXUtil.getNonceStr();
//		prepayReqHandler.setParameter("nonce_str", nonce_str);
//		prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
//		// String out_trade_no = userOrder.getOrderNo();
//		String out_trade_no = order.getOrder_id() + "";
//		prepayReqHandler.setParameter("out_trade_no", out_trade_no);
//		// request.getRemoteAddr()
//		// "14.23.150.211"
//		prepayReqHandler.setParameter("spbill_create_ip", "47.100.110.119");
//		String timestamp = WXUtil.getTimeStamp();
//		prepayReqHandler.setParameter("time_start", timestamp);
//		// logger.info("金额" + totalFee);
//		prepayReqHandler.setParameter("total_fee", totalFee);
//		prepayReqHandler.setParameter("trade_type", "APP");
//		/**
//		 * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
//		 */
//		prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
//		prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
//		String prepayid;
//		WxNotifyParam param = new WxNotifyParam();
//		try {
//			prepayid = prepayReqHandler.sendPrepay();
//			// 若获取prepayid成功，将相关信息返回客户端
//			if (prepayid != null && !prepayid.equals("")) {
//				String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr="
//						+ nonce_str + "&package=Sign=WXPay&partnerid="
//						+ ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid
//						+ "&timestamp=" + timestamp + "&key="
//						+ ConstantUtil.APP_KEY;
//				/**
//				 * 签名方式与上面类似
//				 */
//				param.setPrepayId(prepayid);
//				param.setSign(Md5Util.MD5Encode(signs, "utf8").toUpperCase());
//				param.setAppId(ConstantUtil.APP_ID);
//				// 等于请求prepayId时的time_start
//				param.setTimeStamp(timestamp);
//				// 与请求prepayId时值一致
//				param.setNonceStr(nonce_str);
//				// 固定常量
//				param.setPackages("Sign=WXPay");
//				param.setPartnerId(ConstantUtil.PARTNER_ID);
//				logger.info("-----------》创建微信订单成功: " + param);
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			return null;
//		}
//		return param;
//	}
//
//	/**
//	 * 初始化微信支付
//	 *
//	 * // * @param request // * @param response // * @param orderId // * @return
//	 */
//	// @RequestMapping("/pay/wx")
//	// @ResponseBody
//	public WxNotifyParam initWxgwc(HttpServletRequest request,
//			HttpServletResponse response, Preorder pe) {
//		// Map<String, Object> map = new HashMap<String, Object>();
//		// 获取生成预支付订单的请求类
//		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(
//				request, response);
//		String totalFee = String.valueOf((int) (Double.parseDouble(pe
//				.getTotalmoney()) * 100));// 微信支付是
//		// 分--
//		// 上线后，将此代码放开
//		prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
//		prepayReqHandler.setParameter("body", ConstantUtil.BODY);
//		prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
//		String nonce_str = WXUtil.getNonceStr();
//		prepayReqHandler.setParameter("nonce_str", nonce_str);
//		prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
//		// String out_trade_no = userOrder.getOrderNo();
//		String out_trade_no = pe.getPreid() + "";
//		prepayReqHandler.setParameter("out_trade_no", out_trade_no);
//		// request.getRemoteAddr()
//		// "14.23.150.211"
//		prepayReqHandler.setParameter("spbill_create_ip", "47.100.110.119");
//		String timestamp = WXUtil.getTimeStamp();
//		prepayReqHandler.setParameter("time_start", timestamp);
//		// logger.info("金额" + totalFee);
//		prepayReqHandler.setParameter("total_fee", totalFee);
//		prepayReqHandler.setParameter("trade_type", "APP");
//		/**
//		 * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
//		 */
//		prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
//		prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
//		String prepayid;
//		WxNotifyParam param = new WxNotifyParam();
//		try {
//			prepayid = prepayReqHandler.sendPrepay();
//			// 若获取prepayid成功，将相关信息返回客户端
//			if (prepayid != null && !prepayid.equals("")) {
//				String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr="
//						+ nonce_str + "&package=Sign=WXPay&partnerid="
//						+ ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid
//						+ "&timestamp=" + timestamp + "&key="
//						+ ConstantUtil.APP_KEY;
//				/**
//				 * 签名方式与上面类似
//				 */
//				param.setPrepayId(prepayid);
//				param.setSign(Md5Util.MD5Encode(signs, "utf8").toUpperCase());
//				param.setAppId(ConstantUtil.APP_ID);
//				// 等于请求prepayId时的time_start
//				param.setTimeStamp(timestamp);
//				// 与请求prepayId时值一致
//				param.setNonceStr(nonce_str);
//				// 固定常量
//				param.setPackages("Sign=WXPay");
//				param.setPartnerId(ConstantUtil.PARTNER_ID);
//				logger.info("-----------》创建微信订单成功: " + param);
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			return null;
//		}
//		return param;
//	}
//
//	/**
//	 * 打赏
//	 *
//	 * // * @param request // * @param response // * @param orderId // * @return
//	 */
//	// @RequestMapping("/pay/wx")
//	// @ResponseBody
//	public WxNotifyParam initWxdashang(HttpServletRequest request,
//			HttpServletResponse response, String logid, String money) {
//		// Map<String, Object> map = new HashMap<String, Object>();
//		// 获取生成预支付订单的请求类
//		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(
//				request, response);
//		String totalFee = String
//				.valueOf((int) (Double.parseDouble(money) * 100));// 微信支付是
//		// 分--
//		// 上线后，将此代码放开
//		prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
//		prepayReqHandler.setParameter("body", ConstantUtil.BODY);
//		prepayReqHandler.setParameter("mch_id", ConstantUtil.MCH_ID);
//		String nonce_str = WXUtil.getNonceStr();
//		prepayReqHandler.setParameter("nonce_str", nonce_str);
//		prepayReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL);
//		// String out_trade_no = userOrder.getOrderNo();
//		String out_trade_no = logid + "";
//		prepayReqHandler.setParameter("out_trade_no", out_trade_no);
//		// request.getRemoteAddr()
//		// "14.23.150.211"
//		prepayReqHandler.setParameter("spbill_create_ip", "47.100.110.119");
//		String timestamp = WXUtil.getTimeStamp();
//		prepayReqHandler.setParameter("time_start", timestamp);
//		// logger.info("金额" + totalFee);
//		prepayReqHandler.setParameter("total_fee", totalFee);
//		prepayReqHandler.setParameter("trade_type", "APP");
//		/**
//		 * 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
//		 */
//		prepayReqHandler.setParameter("sign", prepayReqHandler.createMD5Sign());
//		prepayReqHandler.setGateUrl(ConstantUtil.GATEURL);
//		String prepayid;
//		WxNotifyParam param = new WxNotifyParam();
//		try {
//			prepayid = prepayReqHandler.sendPrepay();
//			// 若获取prepayid成功，将相关信息返回客户端
//			if (prepayid != null && !prepayid.equals("")) {
//				String signs = "appid=" + ConstantUtil.APP_ID + "&noncestr="
//						+ nonce_str + "&package=Sign=WXPay&partnerid="
//						+ ConstantUtil.PARTNER_ID + "&prepayid=" + prepayid
//						+ "&timestamp=" + timestamp + "&key="
//						+ ConstantUtil.APP_KEY;
//				/**
//				 * 签名方式与上面类似
//				 */
//				param.setPrepayId(prepayid);
//				param.setSign(Md5Util.MD5Encode(signs, "utf8").toUpperCase());
//				param.setAppId(ConstantUtil.APP_ID);
//				// 等于请求prepayId时的time_start
//				param.setTimeStamp(timestamp);
//				// 与请求prepayId时值一致
//				param.setNonceStr(nonce_str);
//				// 固定常量
//				param.setPackages("Sign=WXPay");
//				param.setPartnerId(ConstantUtil.PARTNER_ID);
//				logger.info("-----------》创建微信订单成功: " + param);
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			return null;
//		}
//		return param;
//	}
//
//	/**
//	 * 接收微信支付回调通知
//	 *
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//
//	@RequestMapping("/wx/notify_url")
//	public void getTenPayNotif(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>进入微信回调方法");
//		PrintWriter writer = response.getWriter();
//		InputStream inStream = request.getInputStream();
//		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		while ((len = inStream.read(buffer)) != -1) {
//			outSteam.write(buffer, 0, len);
//		}
//		outSteam.close();
//		inStream.close();
//		String result = new String(outSteam.toByteArray(), "utf-8");
//		logger.info("微信回调支付通知结果：" + result);
//		Map<String, String> map = null;
//		try {
//			/**
//			 * 解析微信通知返回的信息
//			 */
//			map = XMLUtil.doXMLParse(result);
//		} catch (JDOMException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.info("- - - - - - - - -");
//		logger.info("= = = = = = = = =:" + map);
//		// 若支付成功，则告知微信服务器收到通知
//		if (map.get("return_code").equals("SUCCESS")) {
//			if (map.get("result_code").equals("SUCCESS")) {
//				logger.info("微信充值会员体系成功！");
//				String out_trade_no = map.get("out_trade_no");
//				Preorder pr = preorderMapper.selectById(out_trade_no);
//				if (pr != null) {
//					// 购物车多个付款
//					String[] orders = pr.getOrderid().split(",");
//					for (int i = 0; i < orders.length; i++) {
//						Orders order = ordersMapper.selectById(orders[i]);
//						order.setPaytime(DateUtils.formatTimeNow());// 回调成功得时候
//																	// 执行
//						order.setStatus("2");
//						ordersMapper.updateById(order);
//						User user = userMapper.selectById(order.getUser_id());
//						Product p = productMapper.selectById(order
//								.getProduct_id());
//						// 添加金币支付日志
//						Log log = new Log();
//						log.setAddtime(DateUtils.formatTimeNow());
//						log.setBalance((int) (order.getPrice() * 100));
//						log.setBalance_after(user.getBalance());
//						log.setBalance_after((int) (user.getBalance() - (order
//								.getPrice() * 100)));
//						log.setType("2");
//						log.setPaytype("weixin");
//						log.setBalance_type("balance");
//						log.setLog_id(Hutool.getId());
//						logMapper.insert(log);
//						user.setIntegral(Integer.parseInt(p.getPro_integral())
//								+ user.getIntegral());
//						user.setBalance((int) (user.getBalance() - (order
//								.getPrice() * 100)));
//						userMapper.updateById(user);
//						Code_order code = null;
//						if ("1".equals(p.getPro_status())) {
//							// 单件商品形成二维码
//							code = code_orderServiceImpl.addCode(
//									order.getOrder_id(), order.getProduct_id(),
//									"1", p.getPro_status());
//						} else {
//							// 组合商品形成二位码
//							EntityWrapper<Zuhe> ew = new EntityWrapper<Zuhe>();
//							ew.eq("parent_id", order.getProduct_id());
//							List<Zuhe> list = zuheMapper.selectList(ew);
//							List<Code_order> codes = null;
//							if (list.size() > 0) {
//								for (int j = 0; j < list.size(); j++) {
//									code = code_orderServiceImpl.addCode(order
//											.getOrder_id(), list.get(i)
//											.getProduct_id(), list.get(i)
//											.getNum() + "", p.getPro_status());
//									codes.add(code);
//
//								}
//							}
//
//						}
//					}
//
//				} else {
//					Orders order = ordersMapper.selectById(out_trade_no);
//					if (order != null) {
//						// 单个订单付款、直接付款
//						order.setPaytime(DateUtils.formatTimeNow());// 回调成功得时候
//																	// 执行
//						order.setStatus("2");
//						ordersMapper.updateById(order);
//						User user = userMapper.selectById(order.getUser_id());
//						Product p = productMapper.selectById(order
//								.getProduct_id());
//						// 添加金币支付日志
//						Log log = new Log();
//						log.setAddtime(DateUtils.formatTimeNow());
//						log.setBalance((int) (order.getPrice() * 100));
//						log.setBalance_after(user.getBalance());
//						log.setBalance_after((int) (user.getBalance() - (order
//								.getPrice() * 100)));
//						log.setType("2");
//						log.setPaytype("weixin");
//						log.setBalance_type("balance");
//						log.setLog_id(Hutool.getId());
//						logMapper.insert(log);
//						user.setIntegral(Integer.parseInt(p.getPro_integral())
//								+ user.getIntegral());
//						user.setBalance((int) (user.getBalance() - (order
//								.getPrice() * 100)));
//						userMapper.updateById(user);
//						Code_order code = null;
//						if ("1".equals(p.getPro_status())) {
//							// 单件商品形成二维码
//							code = code_orderServiceImpl.addCode(
//									order.getOrder_id(), order.getProduct_id(),
//									"1", p.getPro_status());
//						} else {
//							// 组合商品形成二位码
//							EntityWrapper<Zuhe> ew = new EntityWrapper<Zuhe>();
//							ew.eq("parent_id", order.getProduct_id());
//							List<Zuhe> list = zuheMapper.selectList(ew);
//							List<Code_order> codes = null;
//							if (list.size() > 0) {
//								for (int i = 0; i < list.size(); i++) {
//									code = code_orderServiceImpl.addCode(order
//											.getOrder_id(), list.get(i)
//											.getProduct_id(), list.get(i)
//											.getNum() + "", p.getPro_status());
//									codes.add(code);
//								}
//							}
//						}
//					} else {
//						Log log = logMapper.selectById(out_trade_no);
//						if (log != null) {
//							// 打赏
//							if (!StringUtils.isEmpty(log.getForum_id())) {
//								Forum forum = forumMapper.selectById(log
//										.getForum_id());
//								User user = userMapper.selectById(forum
//										.getUser_id());
//								Log ByforumUserid = new Log();
//								// Log ByforumUserid =
//								// logServiceImpl.getLog(forum.getUser_id());
//								ByforumUserid.setBalance_befor(user
//										.getBalance());
//								// 发帖人金币+100
//								ByforumUserid.setBalance(user.getBalance()
//										+ log.getBalance());
//								ByforumUserid.setBalance_after(user
//										.getBalance() + log.getBalance());
//								ByforumUserid.setBalance_befor(user
//										.getBalance());
//								ByforumUserid.setLog_id(Hutool.getId());
//								ByforumUserid.setAddtime(Hutool
//										.parseDateToString());
//								ByforumUserid.setType("3");
//								ByforumUserid.setPaytype("jinbi");
//								ByforumUserid.setUser_id(user.getUser_id());
//
//								int count = logMapper.insert(ByforumUserid);
//								if (count > 1) {
//									user.setBalance(user.getBalance()
//											+ log.getBalance());
//									userMapper.updateById(user);
//								}
//							} else {
//								// 充值
//								User user = userMapper.selectById(log
//										.getUser_id());
//								user.setBalance(log.getBalance()+user.getBalance());
//								userMapper.updateById(user);
//							}
//
//						}
//					}
//
//				}
//
//				// if(userorder!=null){
//				//
//				// }else{
//				// logger.error("微信支付回调订单不存在");
//				// }
//			} else {
//				String out_trade_no = map.get("out_trade_no");
//				Log log = logMapper.selectById(out_trade_no);
//				if (log != null) {
//					logMapper.deleteById(out_trade_no);
//				}
//				logger.error("微信支付回调失败");
//			}
//		}
//	}
//
//	public static void main(String args[]) {
//		String notifyStr = XMLUtil.setXML("SUCCESS", "");
//		System.out.println(notifyStr);
//	}
//}
