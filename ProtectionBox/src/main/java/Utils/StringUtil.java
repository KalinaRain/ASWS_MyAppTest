package Utils;

/**
 * Created by KalinaRain on 2015/3/19.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if ("".equals(str)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isNotEmpty(String str) {
        if ("".equals(str)) {
            return false;
        } else {
            return true;
        }
    }
}
