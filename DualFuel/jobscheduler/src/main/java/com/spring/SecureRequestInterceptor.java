package com.spring;

import com.common.LoginFilter;
import com.common.SiteMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecureRequestInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger log = Logger.getLogger(SecureRequestInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
//		log.info("Secure Request");
		
		if (handler == null) {
			return true;
		}

		final Object userkey = request.getSession().getAttribute(
				LoginFilter.CURRENT_LOGGED_USER_ATTRIBUTE);
		if (userkey == null) {
			response.sendRedirect(SiteMap.LOGIN_PAGE);
			return false;
		}

		return true;
	}

}
