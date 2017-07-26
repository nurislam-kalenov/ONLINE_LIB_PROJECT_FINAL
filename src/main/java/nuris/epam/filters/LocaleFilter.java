package nuris.epam.filters;

import static nuris.epam.action.constants.Constants.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

/**
 * Class filter, intended to set the current local settings of the customer whose data is stored in the cookies
 *
 * @author Kalenov Nurislam
 */
@WebFilter(filterName = "LocaleFilter", urlPatterns = "/kz/*")

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(LANG)) {
                    Locale locale = new Locale(cookie.getValue());
                    Config.set(req.getSession(), Config.FMT_LOCALE, locale);
                }
            }
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
