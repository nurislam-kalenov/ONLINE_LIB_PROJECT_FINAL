package nuris.epam.action.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static nuris.epam.action.constants.Constants.*;

/**
 * Class, calls the appropriate page depending on the request
 *
 * @author Kalenov Nurislam
 */
public class View {

    private static final Logger log = LoggerFactory.getLogger(View.class);

    private HttpServletRequest request;
    private HttpServletResponse response;

    public View(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void navigate(ActionResult result) {
        try {
            if (result.isRedirect()) {
                response.sendRedirect(result.getView());
            } else {
                String path = PATH_TO_JSP + result.getView() + JSP_FORMAT;
                request.getRequestDispatcher(path).forward(request, response);
            }
        } catch (ServletException | IOException e) {
            log.error("Can't work redirect dispatcher" ,e);
        }
    }
}
