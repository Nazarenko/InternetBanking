package com.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class RedirectHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private String userUrl;
	private String adminUrl;

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request,
			HttpServletResponse response) {

		if (hasRole("ROLE_ADMIN")) {
			return adminUrl;
		} else if (hasRole("ROLE_USER")) {
			return userUrl;
		} else {
			return super.determineTargetUrl(request, response);
		}
	}

	/**
	 * Check user role in security context from local thread
	 * 
	 * @param role
	 *            - role for check
	 * @return true if current authentication authorities have given role, false
	 *         otherwise
	 */
	protected boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}
}
