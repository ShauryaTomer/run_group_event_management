package com.rungroup.web.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) { //if it is an instance of AnonymousAuthenticationToken that means user is not logged in, then we'll return null username.
            return authentication.getName();
        }
        return null;
    }
}