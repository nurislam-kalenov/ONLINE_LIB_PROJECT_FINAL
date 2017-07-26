package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.services.CustomerService;
import nuris.epam.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nuris.epam.action.constants.Constants.CITY_LIST;
import static nuris.epam.action.constants.Constants.REGISTER;

/**
 * Action class, provide data for register new customer
 *
 * @author Kalenov Nurislam
 */
public class PageRegisterAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        CustomerService customerService = new CustomerService();
        try {
            req.setAttribute(CITY_LIST, customerService.getAllCity());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return new ActionResult(REGISTER);
    }
}
