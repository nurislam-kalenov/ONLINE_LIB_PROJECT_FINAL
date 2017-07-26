package nuris.epam.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nuris.epam.action.constants.Constants.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Class filter, intended to restrict access for different types of users
 *
 * @author Kalenov Nurislam
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = "/kz/*", initParams = {@WebInitParam(name = "admin", value = "admin", description = "role"), @WebInitParam(name = "user", value = "user", description = "role")})

public class SecurityFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    private String admin;
    private String user;

    private final Set<String> guestAccess = new HashSet<>();
    private final Set<String> userAccess = new HashSet<>();
    private final Set<String> adminAccess = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.info("Initializing roles access");

        admin = filterConfig.getInitParameter("admin");
        user = filterConfig.getInitParameter("user");

        initGuest();
        initUser();
        initAdmin();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getPathInfo();

        if (req.getSession().getAttribute(ATT_ROLE) == null) {
            if (!guestAccess.contains(path)) {
                log.warn("Can't get permission(guest) for path {}", path);
                resp.sendRedirect(WELCOME);
                return;
            }
        } else if (req.getSession().getAttribute(ATT_ROLE).equals(user)) {
            if (!userAccess.contains(path)) {
                log.warn("Can't get permission(user) for path {}", path);
                resp.sendRedirect(BOOKS);
                return;
            }
        } else if (req.getSession().getAttribute(ATT_ROLE).equals(admin)) {
            if (!adminAccess.contains(path)) {
                log.warn("Can't get permission(admin) for path {}", path);
                resp.sendRedirect(BOOKS);
                return;
            }
        } else {
            filterChain.doFilter(req, resp);
            return;
        }
        filterChain.doFilter(req, resp);


    }

    /**
     * Method , access for guest
     */
    private void initGuest() {
        guestAccess.add("/welcome");
        guestAccess.add("/register");
        guestAccess.add("/login");
        guestAccess.add("/set-language");
    }

    /**
     * Method , access for user
     */
    private void initUser() {
        userAccess.add("/deptCustomerBook");
        userAccess.add("/takeBook");
        userAccess.add("/takeBookBasket");
        userAccess.add("/returnBook");
        userAccess.add("/basket");
        userAccess.add("/returnCustomerBook");
        userAccess.add("/basket-delete");

        userAccess.add("/profileEdit");
        userAccess.add("/email-edit");
        userAccess.add("/password-edit");
        userAccess.add("/aboutBook");
        userAccess.add("/books");
        userAccess.add("/account");
        userAccess.add("/login");
        userAccess.add("/logout");
        userAccess.add("/set-language");
    }

    /**
     * Method , access for guest admin
     */
    private void initAdmin() {
        adminAccess.add("/readers");
        adminAccess.add("/management");
        adminAccess.add("/returnCustomerBook");
        adminAccess.add("/registerBook");
        adminAccess.add("/personalDataEdit");
        adminAccess.add("/bookEdit");
        adminAccess.add("/aboutReader");
        adminAccess.add("/adminReturnBook");
        adminAccess.add("/deleteBook");
        adminAccess.add("/deleteProfile");
        adminAccess.add("/deleteBookError");
        adminAccess.add("/deleteProfileError");

        adminAccess.add("/profileEdit");
        adminAccess.add("/email-edit");
        adminAccess.add("/password-edit");
        adminAccess.add("/aboutBook");
        adminAccess.add("/books");
        adminAccess.add("/account");
        adminAccess.add("/login");
        adminAccess.add("/logout");
        adminAccess.add("/set-language");
    }

    @Override
    public void destroy() {
        admin = null;
        user = null;
        adminAccess.removeAll(adminAccess);
        userAccess.removeAll(userAccess);
        guestAccess.removeAll(guestAccess);
    }
}
