package cn.ming.blogming.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置拦截器，用于登陆时的拦截
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在前端控制器之前执行
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;
        }else return true;
    }
}
