package nuris.epam.action.post;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Management;
import nuris.epam.services.ManagementService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class , responsible for returning the book back
 *
 * @author Kalenov Nurislam
 */

public class AdminReturnBookAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(AdminReturnBookAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService managementService = new ManagementService();
        Management management = new Management();

        int id = Integer.parseInt(req.getParameter(MANAGEMENT_ID));
        management.setId(id);
        try {
            log.debug("Admin returned book");
            managementService.adminReturnBook(management);
        } catch (ServiceException e) {
            log.warn("Can't admin return book", e);
            return new ActionResult(BOOKS, true);
        }
        return new ActionResult(MANAGEMENT, true);
    }
}
