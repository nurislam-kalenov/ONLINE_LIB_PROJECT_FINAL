package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Management;
import nuris.epam.services.ManagementService;
import nuris.epam.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, provide data for management
 *
 * @author Kalenov Nurislam
 */
public class PageManagementAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PageDeptCustomerBookAction.class);

    private boolean isActive = false;
    private boolean isActiveState = false;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        ManagementService managementService = new ManagementService();
        int page = 1;
        int recordPerPage = 3;

        if (req.getParameter(PAGE) != null) {
            page = Integer.parseInt(req.getParameter(PAGE));
        }
        if (req.getParameter(ACTIVE) != null) {
            isActive = Boolean.valueOf(req.getParameter(ACTIVE));
            isActiveState = isActive;
        }

        try {
            List<Management> managements = managementService.getListManagement(page, recordPerPage, isActiveState);

            int noOfRecords = managementService.getManagementCount(isActiveState);
            int noOfPages = (int) Math.ceil((double) noOfRecords / recordPerPage);

            req.setAttribute(ATT_MANAGEMENTS, managements);
            req.setAttribute(ATT_NO_PAGES, noOfPages);
            req.setAttribute(ATT_CURRENT_PAGE, page);
            req.setAttribute(ATT_IS_ACTIVE_STATE, isActiveState);
            log.debug("Transfer information about management to {} page ", MANAGEMENT);
        } catch (ServiceException e) {
            log.warn("Can't transfer information about management to {} page ", MANAGEMENT);
        }
        return new ActionResult(MANAGEMENT);
    }
}
