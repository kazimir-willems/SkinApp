package skinapp.luca.com.util;

/**
 * Created by Leif on 12/1/2017.
 */

public class URLManager {

    private static final String HTTP_SERVER = "http://192.168.5.144/";
    private static final String SERVER_PREFIX = "EZM_Work/";
//    private static final String HTTP_SERVER = "http://localhost/";

    public static String getSignInURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/Login.php";
    }

    public static String getSignUpURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/SignUp.php";
    }

    public static String getImageURL() {
        return HTTP_SERVER + SERVER_PREFIX + "/trunk/admin/webroot/upload/";
    }

    public static String getProductsURL() {
        return HTTP_SERVER + SERVER_PREFIX + "web_api/GetProduct.php";
    }
}
