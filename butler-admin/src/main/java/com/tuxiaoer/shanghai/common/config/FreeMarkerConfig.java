package com.tuxiaoer.shanghai.common.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.tuxiaoer.shanghai.common.tag.MenuTag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


/**
* @author: YeJR 
* @version: 2018年5月31日 上午11:41:28
* freemarker配置
*/
@Configuration
public class FreeMarkerConfig implements InitializingBean {
	
	@Autowired
    protected freemarker.template.Configuration configuration;
	
	@Autowired
    protected MenuTag menuTag;
	
	/**
	 * 自定义标签
	 */
	@PostConstruct
    public void setSharedVariable() {
        // menu即为页面上调用的标签名
        configuration.setSharedVariable("menu", menuTag);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		//可以在页面上使用shiro标签
		configuration.setSharedVariable("shiro", new ShiroTags());
	}

}
