package nuris.epam.action.get;

import nuris.epam.action.manager.Action;
import nuris.epam.action.manager.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import static nuris.epam.action.constants.Constants.*;

/**
 * Action class, responsible for changing languages
 *
 * @author Kalenov Nurislam
 */
public class SelectLanguageAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SelectLanguageAction.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp){
        String language = req.getParameter(LANG);
        Config.set(req.getSession(), Config.FMT_LOCALE, new Locale(language));
        Cookie cookie = new Cookie(LANG, language);
        cookie.setMaxAge(HOUR * MINUTE * SEC);
        resp.addCookie(cookie);
        try {
            req.setCharacterEncoding(CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            log.error("Can't set character encoding" ,e);
        }
        return new ActionResult(req.getHeader(REFERER), true);

    }
}
