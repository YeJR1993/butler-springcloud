package com.tuxiaoer.shanghai.common.utils;

/**
 * @author YeJR
 * @version: 2018年5月28日 上午10:07:51
 * 各种错误对象
 *
 */
public class CodeMsg {
	/**
	 * 错误码
	 */
	private int code;
	
	/**
	 * 错误信息
	 */
	private String msg;

	/** 通用的错误码 */
	public static CodeMsg ERROR = new CodeMsg(0, "ERROR");
	public static CodeMsg SUCCESS = new CodeMsg(1, "SUCCESS");

	/** 服务提供者异常*/
	public static CodeMsg PROVIDER_ERROR = new CodeMsg(500100, "provider error");

	/**登录模块 5002XX*/
	public static CodeMsg USER_NOT_EXIST = new CodeMsg(500210, "该用户不存在");
	public static CodeMsg NAME_NOT_MATCH_PASSWORD = new CodeMsg(500211, "用户名密码不匹配");
	public static CodeMsg USER_LOCKED = new CodeMsg(500212, "用户已被冻结");
	public static CodeMsg CODE_INVALID = new CodeMsg(500214, "");
	
	/** 其他异常 */
	public static CodeMsg ANALYSIS_DATE_ERROR = new CodeMsg(500300, "时间解析错误");

	public CodeMsg() {
	}

	public CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * 添加额外信息，例如：BIND_ERROR这种
	 * @param args
	 * @return
	 */
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}

}
