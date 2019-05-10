package com.tuxiaoer.shanghai.common.persistence;

import com.tuxiaoer.shanghai.common.constant.SystemConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YeJR
 * @version 2018年6月20日 上午10:07:51
 * controller基类
 */
public abstract class BaseController {

	@Autowired
	HttpServletRequest request;
	
	/**
	 * 默认页码
	 */
	private Integer defaultPageNum = 1;
	
	/**
	 * 默认每页条数
	 */
	private Integer defaultPageSize = 10;


	
	/**
	 * 获取分页数据
	 * @return
	 */
	protected Integer getPageDefault(String tag) {
		Session session = SecurityUtils.getSubject().getSession();
		//先从request中获取
		String pageStr = request.getParameter(tag);
		String param = tag + ":" + request.getServletPath();
		//能获取到
		if (StringUtils.isNotBlank(pageStr)) {
			//保存到session中
			session.setAttribute(param, pageStr);
		} else {
			// 若没有，先从session中获取
			if (session.getAttribute(param) == null) {
				if (SystemConstants.PAGE_NUM.equals(tag)){
					return defaultPageNum;
				} else {
					return defaultPageSize;
				}
			} else {
				pageStr = String.valueOf(session.getAttribute(param));
			}
		}
		return Integer.parseInt(pageStr);
	}
	
}
