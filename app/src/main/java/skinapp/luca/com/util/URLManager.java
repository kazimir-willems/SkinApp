package skinapp.luca.com.util;

/**
 * Created by Leif on 12/1/2017.
 */

public class URLManager {

    private static final String HTTP_SERVER = "http://192.168.5.144/EZM_Work/web_api/";
//    private static final String HTTP_SERVER = "http://localhost/";

    public static String getSignInURL() {
        return HTTP_SERVER + "Login.php";
    }

    public static String getSignUpURL() {
        return HTTP_SERVER + "SignUp.php";
    }
}
