package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import nuris.epam.entity.Basket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nuris.epam.action.constants.Constants.ATT_BOOKS;
import static nuris.epam.action.constants.Constants.BASKET;

/**
 * Action class, provide data about basket
 *
 * @author Kalenov Nurislam
 */
public class PageBasketAction extends AbstractBasket implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){
        Basket basket = getBasket(req.getSession());
        req.setAttribute(ATT_BOOKS, basket);
        return new ActionResult(BASKET);
    }
}
