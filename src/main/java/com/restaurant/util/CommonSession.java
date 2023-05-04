package com.restaurant.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonSession {
    
    private static final String MEMBER_INFO = "MEMBER_INFO";

    public static void setSessionUserInfo(HttpServletRequest request, Object entity) {
        HttpSession session = request.getSession();
        session.setAttribute(MEMBER_INFO, entity);
    }
}
