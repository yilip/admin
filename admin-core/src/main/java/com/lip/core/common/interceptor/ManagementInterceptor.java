package com.lip.core.common.interceptor;

import com.lip.core.management.WhitelistCache;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ManagementInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private WhitelistCache whitelist;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        if(whitelist.getWhitelist().contains(ipAddress)) return true;
        logger.info("Rejected request from {}", ipAddress);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        JSONObject obj = new JSONObject();
        obj.put("code", HttpStatus.FORBIDDEN.value());
        obj.put("msg", "Access denied");
        PrintWriter out = response.getWriter();
        out.print(obj);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
