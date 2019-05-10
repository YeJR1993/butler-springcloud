package com.tuxiaoer.shanghai.common.constant;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 系统常量管理类
 *
 */
public class SystemConstants {
	
	/**
	 * shiro密码校验算法：MD5
	 */
	public static final String SHIRO_CREDENTIALSMATCHER = "MD5";
	
	/**
	 * 加密次数：1024
	 */
	public static final int SHIRO_ENCRYPTION_NUMBER  = 1024;
	
	/**
	 * 验证码有效期：3*60
	 */
	public static final int VALIDATECODE_EXPIRETIME  = 3 * 60;
	
	
	/**
	 * 操作成功
	 */
	public static final String  OPERATE_SUCCESS_PAGE_TIP = "操作成功！";
	
	/**
	 * 操作失败
	 */
	public static final String  OPERATE_FAILED_PAGE_TIP = "操作失败！";

	/**
	 * 页码
	 */
	public static final String  PAGE_NUM = "pageNum";

	/**
	 * 每页条数
	 */
	public static final String  PAGE_SIZE = "pageSize";

	
	
}
