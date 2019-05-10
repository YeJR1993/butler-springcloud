package com.tuxiaoer.shanghai.common.exception;


import com.tuxiaoer.shanghai.common.utils.CodeMsg;

/**
 * @author: YeJR
 * @version: 2018年6月1日 下午2:47:25
 * 自定义错误异常
 */
public class LoginException extends RuntimeException {


	private CodeMsg codeMsg;

	public LoginException(CodeMsg codeMsg) {
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
}
