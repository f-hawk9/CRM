package com.kkb.intercept;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserStateIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1.如果是请求登录或者是注册的直接放行
         * 2.判断session是否存在用户
         */
        String url = request.getRequestURL().toString();
        if (url.contains("login")||url.contains("regist")){
            return true;
        }
        Object user = request.getSession().getAttribute("user");
        if(user!=null){
            return true;
        }
        //如果未登录则返回登录界面
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }
}
