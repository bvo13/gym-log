package com.gymloggingapp.gymloggingapp.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenExtractor {
    public String extractToken(HttpServletRequest request, String cookieName){
        if(request.getCookies()==null){
            return null;
        }
        for(Cookie cookie: request.getCookies()){
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
