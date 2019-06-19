package com.zcf.common.safe;

import com.zcf.common.utils.DesUtils;

public class TokenUtils {
	public static final String moddle = "D_X";

	/**
	 * 加密用户名+D_X+id
	 * 
	 * @param uid
	 * @param username
	 */
	public static String makeToken(Long uid, String username) {
		String id = uid.toString();
		String source = username + moddle + id;
		String lastStr = "";
		try {
			//lastStr = DesUtils.encrypt(source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastStr;
	}

}
