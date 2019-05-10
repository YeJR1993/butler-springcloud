package com.tuxiaoer.shanghai.common.redis.rediskey;


import com.tuxiaoer.shanghai.common.constant.SystemConstants;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 每个模块需要定义不同的key，防止在保存到redis时出现key重复覆盖的现象
 */
public class SysModules extends BasePrefix {

	private SysModules(String prefix) {
		super(prefix);
	}

	private SysModules(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	/**
	 * 登录验证码
	 */
	public static SysModules validateCode = new SysModules(SystemConstants.VALIDATECODE_EXPIRETIME, "validateCode");
	
	
}
