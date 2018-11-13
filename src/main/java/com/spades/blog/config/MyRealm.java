/**
 * 
 */
package com.spades.blog.config;

import javax.swing.Spring;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.spades.blog.entity.User;
import com.spades.blog.service.UserServie;




/**
 * @author Olympus_Pactera
 *
 */
public class MyRealm extends AuthorizingRealm {
	
	private final static Logger logger = LoggerFactory.getLogger(MyRealm.class);
	
	@Autowired
	private UserServie userservice;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		logger.debug("登录验证后进行权限验证");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.debug("登陆操作进行登陆认证.......");
		
		String userName = (String)token.getPrincipal();
		
		User user = userservice.getUserByName(userName);
		if(user == null) {
			//没有找到账号
			throw new UnknownAccountException("没有在本系统中找到用户信息。");
			
		}
		
		SimpleAuthenticationInfo  info = new SimpleAuthenticationInfo (user.getUserName(),user.getPassword(),getName());
		
		return info;
	}

	
}
