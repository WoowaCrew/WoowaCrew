package woowacrew.utils.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import woowacrew.user.domain.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Optional<UserDto> user = Optional.ofNullable((UserDto) session.getAttribute("user"));

        if (!user.isPresent()) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
