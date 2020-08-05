package com.jabbour.ems.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/**
 * Class that defining a cache 
 * to keep track of unauthenticated requests.
 * @author chris
 */
class CustomRequestCache extends HttpSessionRequestCache {	
	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response)
	{
		//if not an internal request, log/save request to cache.
		if(!SecurityUtils.isFrameworkInternalRequest(request))
		{
			super.saveRequest(request, response);
		}
	}
}
