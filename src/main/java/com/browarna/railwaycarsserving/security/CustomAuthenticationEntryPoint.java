//package com.browarna.railwaycarsserving.security;
//
//import org.json.JSONObject;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.Instant;
//
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
//        res.setContentType("application/json;charset=UTF-8");
//        res.setStatus(403);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("timestamp", Instant.now());
//        jsonObject.put("status", 403);
//        jsonObject.put("message", "Access denied");
//
//        res.getWriter().write(jsonObject.toString());
//    }
//}
