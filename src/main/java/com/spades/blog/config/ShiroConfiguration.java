/**
 * 
 */
package com.spades.blog.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Olympus_Pactera
 *
 */
@Configuration
public class ShiroConfiguration {

	/**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     *	 注意：单独一个ShiroFilterFactoryBean配置是会报错的
     * 	初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     */
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager ) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		//必须设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//拦截器
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		
		//filterChainDefinitionMap.put("/page/*", "authc");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/security/logoff", "logout");
		
		shiroFilterFactoryBean.setLoginUrl("/#/login");
		
		//未授权界面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return shiroFilterFactoryBean;
		
				
	}
	
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager sessionManager = new DefaultWebSecurityManager();
		
	}
}
