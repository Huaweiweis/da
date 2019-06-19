package com.zcf.pay.AliPays;

/**
 * 支付宝支付所需的必要参数 理想情况下只需配置此处
 */
public class ALiPayConfig {
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088231036962110";

	// appid
	public static String appid = "2018080660965241";

	// 商户支付宝账号
	public static String seller_email = "TZSHJK2018@163.com";

	// 商户真实姓名
	public static String account_name = "北京天泽昇信息咨询有限公司";

	// URL
	public static String url = "https://openapi.alipay.com/gateway.do";
	public static String timeoutExpress = "1000";

	// 商户的私钥RSA
	public static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCBzWVAzQ4XvTHTtjHE0PPGFbJMLCfIqCJ3m2YPBr3MzoxZj0BIL+kJlYnGTkuFaLV4l75IaocwjTQUM0CSkgg1RKGGiccNQ0jBFGbWRPZg3+J/GkxUtwETtc9LJj0BwYog0dQJNEK2/41gszRIYSI04c3ResRd4l+4JpSygLOQVNyDg3vVCo3JSg184Ie8WNFUBGNLTdqBv7lw0OS1U6T+tfosrPUxSSreXzFcbcqCm8UjH+wNjHUmexkGbEqxZ/MPNuIT/tqVKyhiM1eG9oVfGQIPjMNmEMbfhGrjU3uodCjD1uDTjX+qTVje2/JN7OPNVTLek+qIPWetyxk51fX3AgMBAAECggEAPjr23EZzBzaReOrUmYOEG+ey/4n72NujCqzaFUIqJNKxvw5Kx1EQ7KNg1k4bLu6wcgxuEdHg1oob+cNIpfyVKtSojBPp9pXXf5oEY53UcUVlFYLKLaM/RO/ZXjIK9ZiQXIO8oF0Jzypd4YqI+lp3R9sqDdBJ7KjnMyZsC9hgPkWCVnxyoiYU9SRFO3UpTQEKkatXGpvsD5yKVU/KOL2E1ciUUtjuzOqAgvQSKIYE0fGEH68tp26+YRYQUn//Fejr82TgF0jZwpX8v8THeNICEGrIQLz/JjuUQs69eugp494r0qkKnyvxFqWvXmyevh9JvPlLqtkeeAbxi39tOEx3oQKBgQC4JF0LkM23Pd89N2RDaBoXtVQn5v3gmieaOB6Xu7qhN/gN8G4QzphOAARMA9OOoUWEBM7+LiZ+d68MIskoVARQpeohbecxs7XIarOF+/HWNi6q2SvFWZD5Qqx9rAenpVTdHzadkVGFJ3ZpJDNgwMfskkFXj/b3hqg9YVOVYMptLQKBgQC0dIof2BiKD39TjIvWzoGazLDgpLg2TI4qe5aBLlTjKKICCw5Z5H0x4shb6AAJTLyjNOg8p9xJI4AzRsaMI4IPGUhsExnFAtVcI64J0GyoXHeM/bGGYcyqEcEktF2ub1KpNEdgnwgf53UEfQ/neH94pXYlH7VqBKJ7ZabMXUjOMwKBgCi7mn1xdaAAJr6i3yHuCD+QU+JTvXsOfo4ltda4pyr8d3mV4rMDEDvI0yqBei3FzNvMOIGPceJt1SHpG/g4xY+Vc/obFpYu+5ouyDFuLuvEpoQScBVCn8gTkJZoFHVoJCKZwfoO6Cp9NTtCz76qRk81dxDIfsf4WITjX5reg0itAoGBAKWJbpetrT0g4EVtXW5su3mcoflBQXcWHSgdfT/9l4NWZ9+F8uFFeA9GIVQl67qcOUNiKlRSDALP3S+skkBF5m4hXE0/7FjMs5q1ZFa+ap67TDP7GVnT52JvrQUVrAZzOpnbSkr5OAPEWJUIgIlvAUhUSJ/fiZS6sa6O3kcEn9//AoGBAKnyQyXf8YubrZ/rVwS6kFyWA7jHc1Lfp5VOEL9KPvXxK//xBlMxBv6/rE6f4obguglfrVBYNnHpvpPuIlugXpYAEeZ82nmbP11uT2kDcDJQ1rdlUYQPkjHidTTvykm8tbjeC3sBszFcv1XcdPTtHBNrqpNu+vP55PCPB9hwbmMS";
	// 支付宝的公钥 RSA
	public static String ali_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj3j43rhrGapG3ngsr9ncyrbAwGXpPtK+pS6bbIqspdn1j9mF/AGdocNfliQKJhWz7pexZWdsomTN8/uBRWQwRFOlL8XEZWUs2wqQNpFqEJ138rKXIVqlEuOnPy4ucHXvWJaXDnyLWCa18o1SEZLr3S0AH3fW1hldkn4PSzQnD72H5z+PD3eXlxE4NlPUK8zZK+/2gbV+3uXHn/9JZlNsHb1dA2Jli+G/yuolZ4/c5LAqtnciPm5zVHUQ0ryoK/tTicWJpZ5KGV/OyaFBYrGU5wK2KZyAAN2I9zkksSf9FaXsboeMhxyBJazy49kWl+mhFIs17AVb5Y2A09UBKPc9swIDAQAB";
	// 签名方式 (支付回调签名方式)
	public static String pay_sign_type = "RSA2";

	/**
	 * 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
	 * 这里需要测试的话，需要用外网测试。https://www.ngrok.cc/ 这里有免费和付费的，实际上，免费用一下就可以了。
	 */
	public static String notify_url = "http://zcfm.vipgz1.idcfengye.com/Tmall/notify_url";
	// 商品的标题/交易标题/订单标题/订单关键字等。
	public static String subject = "商品购买";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 接口名称 固定为：alipay.trade.app.pay
	public static String method = "alipay.trade.app.pay";

	// 调用的接口版本，固定为：1.0
	public static String version = "1.0";

	// 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
	public static String product_code = "QUICK_MSECURITY_PAY";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

}
