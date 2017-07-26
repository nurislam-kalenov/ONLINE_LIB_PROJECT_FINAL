package nuris.epam.servlets;

import nuris.epam.action.manager.*;
import nuris.epam.connection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class, the main and only servlet, a single entry point for all queries
 *
 * @author Kalenov Nurislam
 */

public class ControllerServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ControllerServlet.class);

    private ActionFactory actionFactory;
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        log.info("The servlet/app start working");
        actionFactory = new ActionFactory();
        connectionPool = ConnectionPool.getInstance();
        log.info("Connection pool size : {}", connectionPool.size());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Action action = actionFactory.getAction(req);
        ActionResult result = action.execute(req, resp);
        View view = new View(req, resp);
        view.navigate(result);
    }

    @Override
    public void destroy() {
        log.info("The servlet/app stopped working");
        connectionPool.closeAllConnections();
    }

}