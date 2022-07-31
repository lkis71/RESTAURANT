package com.restaurant.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonSession {
    
    private static final String SESSION_INFO = "SESSION_INFO";

    public static void setSessionUserInfo(HttpServletRequest request, Object entity) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_INFO, entity);
    }
}
