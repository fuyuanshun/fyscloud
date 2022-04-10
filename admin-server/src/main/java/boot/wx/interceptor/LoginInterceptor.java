package boot.wx.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("userName");

        if (user == null) {
            request.setAttribute("msg","无权限请先登录");
            // 获取request返回页面到登录页
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
            }
        return true;
    }
}
