package com.cqgs.plus.interceptor;

import com.cqgs.plus.util.HttpResult;
import com.cqgs.plus.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.JwtException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取 token
        String authHeader = request.getHeader("Authorization");

        // 如果 token 为空，则表示未登录，返回错误提示
        if (authHeader == null || authHeader.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
//            objectMapper.writeValue(response.getWriter(), result);

            response.getWriter().write(HttpResult.nologinResult("未登录用户").toString());  // 返回未登录信息
            return false; // 阻止继续执行
        }
        String token = authHeader.substring(7); // 去掉 "Bearer " 前缀
        System.out.println(token);
        try {
            Claims claims = JwtUtil.validateToken(token);
            // 可选：将claims信息保存到request作用域，后续Controller可用
            System.out.println("登录用户" + claims.getSubject());
            request.setAttribute("userId", claims.getSubject());
            request.setAttribute("username", claims.get("username"));
            return true;
        } catch (JwtException e) {
            return sendError(response, "Token无效或已过期");
        }
    }

    private boolean sendError(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(HttpResult.nologinResult(message).toString());
        return false;
    }
}
