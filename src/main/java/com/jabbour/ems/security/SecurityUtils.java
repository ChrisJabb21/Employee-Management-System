package com.jabbour.ems.security;

import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
/***
 * Helper class 
 * for Utility methods and static operations
 * using the Spring Security module
 * @author chris
 */
public final class SecurityUtils {

	//Private constructor prevents instantation of utility class directly
	//for serving singleton classes
	private SecurityUtils(){}
	/***
	 * determine if http request is a internal framework request to and from Vaadin.
	 * @param request => {@link HttpServletRequest} client-side http request
	 * @return true if request is an internal framework request, False if it is not
	 */
	static boolean isFrameworkInternalRequest(HttpServletRequest request)
	{
		final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return parameterValue != null 
				&& Stream.of(HandlerHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
	}
	/**
	 * Check if the current user is logged in.
	 * @return true if current user is logged in, otherwise false if not logged in.
	 */
	static boolean isUserLoggedIn() {
		Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
		return authenication != null
				&& !(authenication instanceof AnonymousAuthenticationToken)
				&& authenication.isAuthenticated();
	}
}
