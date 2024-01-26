package com.project.Fabo.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        Collection<? extends SimpleGrantedAuthority> authorities = (Collection<? extends SimpleGrantedAuthority>) authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_SUPERADMIN"))) {
            return "/superadmin";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN_SUPPORT"))) {
            return "/admin/support";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN_PRODUCTS"))) {
            return "/admin/products";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN_ACCOUNTS"))) {
            return "/admin/accounts";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN_SUPPORT"))) {
            return "/admin/support";
        }
        else {
            return "/access-denied"; // Default URL for unauthorized roles
        }
    }
}
