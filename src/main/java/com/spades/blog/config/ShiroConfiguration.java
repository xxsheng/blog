/**
 * 
 */
package com.spades.blog.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

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

		//filterChainDefinitionMap.put("/page/*", "authc");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/security/logoff", "logout");
		
		shiroFilterFactoryBean.setLoginUrl("/admin/login");
		shiroFilterFactoryBean.setSuccessUrl("/article");
		//未授权界面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		//拦截器
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		
		 //开放登陆接口
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/admin/dologin", "anon");
        //filterChainDefinitionMap.put("/acticle/", "anon");
		filterChainDefinitionMap.put("/acticle/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/bootstrap/**", "anon");
		filterChainDefinitionMap.put("/jquery-3.2.1.min.js", "anon");
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/admin/**", "authc");
		
		
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		System.out.println("Shiro拦截器工厂类注入成功");
		return shiroFilterFactoryBean;
		
				
	}
	
    /**
     * 身份认证Realm，此处的注入不可以缺少。否则会在UserRealm中注入对象会报空指针.
     * @return
     */
    @Bean
    public MyRealm myShiroRealm(  ){
        MyRealm myShiroRealm = new MyRealm();
       // myShiroRealm.setCredentialsMatcher(  hashedCredentialsMatcher() );
        return myShiroRealm;
    }
	
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		
		securityManager.setRealm(myShiroRealm());
		securityManager.setSessionManager(sessionManager());
		securityManager.setCacheManager(ehCacheManager());
		
		return securityManager;
	}

	@Bean
	public SessionManager sessionManager() {
		
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new MySessionListener());
		sessionManager.setSessionListeners(listeners);
		
		return sessionManager;
	}
	
	@Bean
	public EhCacheManager ehCacheManager() {
		
		EhCacheManager ehCacheManager = new EhCacheManager();
		
		return ehCacheManager;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager 
			securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new 
				AuthorizationAttributeSourceAdvisor();
		
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean 
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
}
