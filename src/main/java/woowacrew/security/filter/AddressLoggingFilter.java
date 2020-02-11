package woowacrew.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class AddressLoggingFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(AddressLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Request Ip : {}, {}", request.getLocalAddr(), request.getRemoteAddr());
        chain.doFilter(request,response);
    }
}
