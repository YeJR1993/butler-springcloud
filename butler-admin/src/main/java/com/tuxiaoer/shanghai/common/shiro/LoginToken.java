package com.tuxiaoer.shanghai.common.shiro;

import com.tuxiaoer.shanghai.modules.system.entity.User;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 重新shiro的UsernamePasswordToken，为其新增验证码属性
 *
 */
public class LoginToken extends UsernamePasswordToken{

	/**
	 * 验证码
	 */
	private String validateCode;

	/**
	 * 用户对象(dubbo下普通类中调用provider不知道怎么调，这里登入的时候直接先查询用户对象放到token中)
	 */
	private User user;


	public LoginToken(String username, String password, boolean rememberMe, String validateCode, User user) {
		super(username, password, rememberMe);
		this.validateCode = validateCode;
		this.user = user;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
