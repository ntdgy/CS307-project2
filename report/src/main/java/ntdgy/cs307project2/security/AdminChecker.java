package ntdgy.cs307project2.security;

import ntdgy.cs307project2.service.LoginService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminChecker implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (LoginService.getLevel(request) > 0) {
            request.getRequestDispatcher("/main.html").forward(request, response);
            return false;
        }
        return true;
    }
}
