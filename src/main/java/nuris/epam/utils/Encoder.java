package nuris.epam.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class, customer Password Encryption Tool
 *
 * @author Kalenov Nurislam
 */
public class Encoder {
    private static final Logger log = LoggerFactory.getLogger(Encoder.class);

    /**
     * Encrypts a string to MD5 format
     *
     * @param txt - Unencrypted password
     * @return Return encrypted password
     */
    public static String toEncode(String txt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.warn("Don't work encoding tool",e);
        }
        return null;
    }
}
