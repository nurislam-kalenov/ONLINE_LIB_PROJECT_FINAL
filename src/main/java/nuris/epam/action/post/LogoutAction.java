package nuris.epam.action.post;


import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , allows a guest to execute logout.
 *
 * @author Kalenov Nurislam
 */
public class LogoutAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(LogoutAction.class);

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse resp) {
        HttpSession session = request.getSession();
        int id  = (int) session.getAttribute(ATT_CUSTOMER_ID);
        String roleName = (String) session.getAttribute(ATT_ROLE);
        log.info("Customer by id = {} and by role = {} execute logout.", id , roleName);
        session.invalidate();
        return new ActionResult(WELCOME, true);
    }
}
