package nuris.epam.action.manager;

import nuris.epam.action.get.*;
import nuris.epam.action.post.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Class , which holds all known actions.
 *
 * @author Kalenov Nurislam
 */
public class ActionFactory {
    private Map<String, Action> actions;

    private void init() {
        actions = new HashMap<>();

        //GET request
        //guest action
        actions.put("GET/welcome", new ShowPageAction("welcome"));
        actions.put("GET/set-language", new SelectLanguageAction());

        //common user-admin action
        actions.put("GET/register", new PageRegisterAction());
        actions.put("GET/books", new PageBookAction());
        actions.put("GET/account", new PageCustomerAccountAction());
        actions.put("GET/aboutBook", new PageAboutBookAction());
        actions.put("GET/profileEdit", new PageProfileEditAction());

        //user action
        actions.put("GET/basket", new PageBasketAction());
        actions.put("GET/deptCustomerBook", new PageDeptCustomerBookAction());
        actions.put("GET/returnCustomerBook", new PageReturnCustomerBookAction());

        //admin action
        actions.put("GET/readers", new PageReadersAction());
        actions.put("GET/bookEdit", new PageBookEditAction());
        actions.put("GET/management", new PageManagementAction());
        actions.put("GET/aboutReader", new PageAboutReaderAction());
        actions.put("GET/registerBook", new PageBookRegisterAction());
        actions.put("GET/personalDataEdit", new PagePersonalDataEditAction());
        actions.put("GET/deleteBookError", new ShowPageAction("deleteBookError"));
        actions.put("GET/deleteProfileError", new ShowPageAction("deleteProfileError"));

        //POST request
        //guest action
        actions.put("POST/login", new LoginAction());
        actions.put("POST/register", new RegisterAction());

        //common user-admin action
        actions.put("POST/logout", new LogoutAction());
        actions.put("POST/email-edit", new EmailEditAction());
        actions.put("POST/password-edit", new PasswordEditAction());

        //user action
        actions.put("POST/basket", new PutBookIntoBasket());
        actions.put("POST/takeBook", new CustomerTakeBookAction());
        actions.put("POST/returnBook", new CustomerReturnBookAction());
        actions.put("POST/basket-delete", new DeleteBookFromBasketAction());
        actions.put("POST/takeBookBasket", new CustomerTakeBookFromBasket());

        //admin action
        actions.put("POST/bookEdit", new BookEditAction());
        actions.put("POST/deleteBook", new DeleteBookAction());
        actions.put("POST/registerBook", new BookRegisterAction());
        actions.put("POST/deleteProfile", new DeleteProfileAction());
        actions.put("POST/adminReturnBook", new AdminReturnBookAction());
        actions.put("POST/personalDataEdit", new PersonalDataEditAction());

    }
    public Action getAction(HttpServletRequest request) {
        if (actions == null) {
            init();
        }
        return actions.get(request.getMethod() + request.getPathInfo());
    }
}
