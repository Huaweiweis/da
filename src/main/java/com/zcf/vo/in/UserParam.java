package com.zcf.vo.in;

/**
 * 用户封装参数类
 * 
 * @author shenguohao
 *
 */

public class UserParam extends PageVo {
	private String token;
	private String name;
	private String phone;
	private String level;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}