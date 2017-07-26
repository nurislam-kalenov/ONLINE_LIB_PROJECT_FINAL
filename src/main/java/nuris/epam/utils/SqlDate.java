package nuris.epam.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class, work with sql date.
 *
 * @author Kalenov Nurislam
 */
public class SqlDate {

    /**
     * Method , Sets the current time
     */
    public static Date currentDateAndTime() {
        return new Date(Calendar.getInstance().getTime().getTime());
    }

    /**
     * Method, formats the string in the date format
     *
     * @param date - string field
     * @return Return Sql date
     */
    public static Date stringToDate(String date) {
        Date sqlDate = null;
        if(!date.isEmpty()) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                sqlDate = new Date(dateFormat.parse(date).getTime());
                return sqlDate;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sqlDate;
    }


}
