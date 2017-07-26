package nuris.epam.action.get;

import nuris.epam.entity.Basket;

import javax.servlet.http.HttpSession;

import static nuris.epam.action.constants.Constants.ATT_BASKET;

/**
 * Action class, intended to take data about the basket from sessions
 *
 * @author Kalenov Nurislam
 */
public class AbstractBasket {
    protected Basket getBasket(HttpSession session) {
        Basket basket;

        if (session.getAttribute(ATT_BASKET) == null) {
            basket = new Basket();
        } else {
            basket = (Basket) session.getAttribute(ATT_BASKET);
        }
        return basket;
    }

}
