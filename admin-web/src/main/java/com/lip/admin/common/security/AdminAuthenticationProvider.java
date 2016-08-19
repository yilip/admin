/**
 * 
 */
package com.lip.admin.common.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author lip
 * @date 2015年12月28日
 */
public class AdminAuthenticationProvider implements AuthenticationProvider {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private UserDetailsService userDetailsService = null;

    public AdminAuthenticationProvider() {
        super();
    }
    /**
     * @param userDetailsService
     */
    public AdminAuthenticationProvider(UserDetailsService userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * provider的authenticate()方法，用于登录验证
     */
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 1. Check username and password
        try {
            doLogin(authentication);
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                throw (AuthenticationException) e;
            }
            logger.error("failure to doLogin", e);
        }

        // 2. Get UserDetails
        UserDetails userDetails = null;
        try {
            userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                throw (AuthenticationException) e;
            }
            logger.error("failure to get user detail", e);
        }

        // 3. Check and get all of admin roles and contexts.
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            AdminAuthenticationToken token = new AdminAuthenticationToken(authentication.getName(),
                    authentication.getCredentials(), authorities);
            token.setDetails(userDetails);
            return token;
        }
        throw new BadCredentialsException("没有分配权限");
    }

    /**
     * Check username and password. When passed, return UserDetails.
     * @param authentication
     * @throws AuthenticationException Failure to login
     */
    protected void doLogin(Authentication authentication) throws AuthenticationException {
        
    }

    /**
     * @date 2015年12月28日
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return true;
    }

}
