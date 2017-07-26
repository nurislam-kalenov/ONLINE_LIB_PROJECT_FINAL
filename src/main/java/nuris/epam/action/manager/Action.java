package nuris.epam.action.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface ,needs to be defined
 * as an interface type which should do the work based on the passed-in arguments of the abstract method
 *
 * @author Kalenov Nurislam
 */
public interface Action {
       ActionResult execute(HttpServletRequest req, HttpServletResponse resp);
}
