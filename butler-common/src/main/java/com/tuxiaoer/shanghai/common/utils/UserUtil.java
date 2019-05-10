package com.tuxiaoer.shanghai.common.utils;

import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
* @author: YeJR 
* @version: 2018年5月11日 下午4:29:21
* 用户工具类
*/
public class UserUtil {
	
	/**
	 * 登录密码加密，使用shiro的盐值加密方法
	 * @param name
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String name, String password) {
		Object salt = ByteSource.Util.bytes(name);
		Object newPassword = new SimpleHash(SystemConstants.SHIRO_CREDENTIALSMATCHER, password, salt, SystemConstants.SHIRO_ENCRYPTION_NUMBER);
		return newPassword.toString();
	}
	

}
