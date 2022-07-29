package com.restaurant.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class AlertUtil {
    
    public static void alertMessgaeUrl(HttpServletResponse response, String url, String message) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<script>");
        out.print("alert("+message+")");
        out.print("location.href="+url+"");
        out.print("</script>");
        out.flush();
    }
}
