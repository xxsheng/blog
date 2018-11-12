/**
 * 
 */
package com.spades.blog.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author xxq_1
 *
 */
public class MySessionListener implements SessionListener {

	private final AtomicInteger sessionCount = new AtomicInteger(0);
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.session.SessionListener#onStart(org.apache.shiro.session.Session)
	 */
	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub

		sessionCount.incrementAndGet();
		System.out.println("登陆+1=="+sessionCount.get());
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session)
	 */
	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub

		sessionCount.decrementAndGet();
		System.out.println("登陆退出-1=="+sessionCount.get());
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session)
	 */
	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub

		sessionCount.decrementAndGet();
		System.out.println("登陆过期-1=="+sessionCount.get());
	}

}
